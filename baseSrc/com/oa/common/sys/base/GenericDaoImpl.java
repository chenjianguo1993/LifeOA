package com.oa.common.sys.base;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.oa.common.tool.page.PageQueryResult;
import com.oa.common.tool.page.PageSettings;

/**
 * @author linjiekai<br/>
 *         Session session = getSession();//用此种方法取session,就不需要自己关掉session<br>
 *         Session session =
 *         getHibernateTemplate().getSessionFactory().openSession
 *         ();//注意用这种方法取session的话,得自己关掉它<br>
 * 
 * @param <T>
 *            指实体类名
 * @param <ID>
 *            指实体类的主键ID的对像类型
 */
public class GenericDaoImpl<T, ID extends Serializable> extends
		HibernateDaoSupport implements GenericDao<T, ID>
{
	private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);

	// 查询某表相关列的信息
	public static String sqlColQuery = "SELECT A.COLUMN_NAME,A.DATA_TYPE,A.NULLABLE,B.COMMENTS,A.DATA_LENGTH,A.COLUMN_ID FROM "
			+ " ALL_TAB_COLUMNS A,ALL_COL_COMMENTS B WHERE A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME"
			+ " AND A.TABLE_NAME = :tableName and A.OWNER=:owner ORDER BY A.COLUMN_ID ASC ";

	// 具体的实体类型
	private Class<T> type;

	// 查询条件
	private String hql;

	/**
	 * <p>
	 * 必须提供的构造方法,以便创建实例的时候就知道具体实体的类型
	 * <p>
	 * 
	 * @param type
	 *            : 实体类型
	 */
	public GenericDaoImpl(Class<T> type)
	{
		this.type = type;
		this.hql = "from " + type.getName();
	}

	public void setHql(String hql)
	{
		this.hql = hql;
	}

	public String getHql()
	{
		return hql;
	}

	// 查找所有，无条件
	@SuppressWarnings("unchecked")
	public List<T> findAll()
	{
		String hql = "from " + type.getName();
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	// 根据id查找
	@SuppressWarnings("unchecked")
	public T findById(ID id)
	{
		if (null == id || "".equals(id))
		{
			return null;
		} else
		{
			return (T) this.getHibernateTemplate().get(type, id);
		}

	}

	// 修改实体
	public void modify(T entity) throws Exception
	{
		this.getHibernateTemplate().update(entity);
	}

	// 删除实体
	public void remove(T entity) throws Exception
	{
		this.getHibernateTemplate().delete(entity);
	}

	//根据Id删除对象
	public void removeById(ID id) throws Exception
	{
		// 根据id查找实体
		T obj = findById(id);
		// 删除
		this.getHibernateTemplate().delete(obj);
	}

	//用于直接执行sql
	public int excuteSql(String sql) throws Exception
	{
		int count = 0;
		if (null != sql)
			count = getSession().createSQLQuery(sql).executeUpdate();
		return count;
	}

	/**
	 * @param ids
	 *            如1,2,3,4,5,6,7,8
	 * @param idName
	 *            可为空,但id主键就会默认为"id",要看实体的定义
	 * @throws Exception
	 */
	public void removeByIds(String ids, String idName) throws Exception
	{
		if (null == idName)
			idName = "id";
		String delHql = "delete from " + type.getName() + " where " + idName
				+ " in (" + ids + ")";

		getSession().createQuery(delHql).executeUpdate();
	}

	// 删除所有的
	public void removeAll(Collection<T> entities) throws Exception
	{
		this.getHibernateTemplate().deleteAll(entities);
	}

	// 保存，返回id
	@SuppressWarnings("unchecked")
	public ID save(T entity) throws Exception
	{
		return (ID) this.getHibernateTemplate().save(entity);
	}

	// 保存或者更新
	public void saveOrUpdateAll(List<T> entities)
	{
		this.getHibernateTemplate().saveOrUpdateAll(entities);
	}

	/**
	 * <p>
	 * 计算匹配查询条件的记录总数,如果没有注入或者设置hql语句,将使用默认的查询语句返回数据库中所有记录
	 * </p>
	 * 
	 * @return 记录总数
	 */
	private int getTotalRows(String hql)
	{
		String actualHql = "select count(*) "
				+ hql.substring(hql.indexOf("from"));
		return ((Long) this.getHibernateTemplate().find(actualHql).get(0))
				.intValue();
	}

	/*
	 * @pageNo 第几页
	 * 
	 * @pageSize 每页数据条数
	 */
	@SuppressWarnings("unchecked")
	public PageQueryResult findByPage(final String hql, final int pageNo,
			final int pageSize)
	{
		PageQueryResult page = new PageQueryResult(PageSettings.of(pageNo,
				pageSize));
		final int totalRows = this.getTotalRows(hql);
		page.setTotalCount(totalRows);// /记下总记录数
		final int pageMaxNo = page.getPageMaxNo(pageSize, totalRows);

		List list = (List) this.getHibernateTemplate().executeFind(
				new HibernateCallback()
				{
					public List doInHibernate(Session session)
							throws HibernateException, SQLException
					{
						// 实际页码
						int actualPage = (pageNo > pageMaxNo) ? pageMaxNo
								: pageNo;
						// 计算实际每页的条数,如果请求的每页数据条数大于总条数, 则等于总条数
						int actualSize = (pageSize > totalRows) ? totalRows
								: pageSize;
						// 计算请求页码的第一条记录的索引值,如果
						int startRow = (actualPage > 0) ? (actualPage - 1)
								* actualSize : 0;
						Query query = session.createQuery(hql);
						// 设置第一条记录
						query.setFirstResult(startRow);
						query.setMaxResults(actualSize);

						return query.list();
					}
				});
		page.setResult(list);
		return page;
	}

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return 对象
	 */
	@SuppressWarnings("unchecked")
	public T findObj(final String hql, final Map<Serializable, Serializable> map)
	{
		if (null == hql || "".equals(hql))
		{
			return null;
		}
		return (T) this.getHibernateTemplate().execute(new HibernateCallback()
		{
			public T doInHibernate(Session session) throws HibernateException,
					SQLException
			{
				Query query = session.createQuery(hql);
				for (Serializable key : map.keySet())
				{
					query.setParameter((String) key, map.get(key));
				}
				query.setFirstResult(0);
				query.setMaxResults(1);
				Object obj = query.uniqueResult();
				return (T) obj;
			}
		});
	}

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return list对象
	 */
	@SuppressWarnings("unchecked")
	public List<T> findListObj(final String hql, final Map<String, Object> map)
	{
		if (null == hql || "".equals(hql))
		{
			return null;
		}
		return (List<T>) this.getHibernateTemplate().execute(
				new HibernateCallback()
				{
					public List doInHibernate(Session session)
							throws HibernateException, SQLException
					{
						Query query = session.createQuery(hql);
						query.setProperties(map);
						return query.list();
					}
				});
	}

	public void saveOrUpdate(T entity) throws Exception
	{
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

	/**
	 * 根据id查询延时对象
	 * 
	 * @param id
	 *            类id
	 * @return 对象
	 */
	public T findObjByIdLoad(ID id)
	{
		if (null == id || "".equals(id))
		{
			return null;
		} else
		{
			return (T) this.getHibernateTemplate().load(type, id);
		}
	}

	/**
	 * 功能描述：使用命名SQL查询列表数据 Map params=new HashMap();//此为where条件的值
	 * params.put("code", StrFuncs.anyLike("123456")); params.put("id", 3);
	 * 
	 * @param queryName
	 *            命名SQL查询名称
	 * @param params
	 *            参数列表
	 * @return 满足条件的列表记录
	 * @throws Exception
	 *             数据库访问异常 数据访问异常 return 查询结果转换成List<Map<字段名,字段值>>
	 */
	@SuppressWarnings("unchecked")
	public List getByNamedQueryPageList(final String queryName,
			final Map<String, Object> params) throws Exception
	{
		try
		{
			return (List) getHibernateTemplate().execute(
					new HibernateCallback()
					{
						public Object doInHibernate(Session session)
						{
							Query query = session.getNamedQuery(queryName);
							query.setProperties(params);
							// /setQueryNamedParameters(query,params);

							// 1.可以对原生SQL
							// 查询使用ResultTransformer。这会返回不受Hibernate管理的实体。
							// /query.setResultTransformer(Transformers.aliasToBean(String.class));
							// 2.将查询结果转换成List<Map<字段名,字段值>>
							// query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
							query.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

							return query.list();
						};
					});
		} catch (Exception he)
		{
			throw new Exception(he);
		}
	}

	/**
	 * 功能描述：使用命名SQL查询列表数据 Map params=new HashMap();//此为where条件的值
	 * params.put("code", StrFuncs.anyLike("123456")); params.put("id", 3);
	 * 
	 * @param queryName
	 *            命名SQL查询名称
	 * @param params
	 *            参数列表
	 * @param currentPage
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @return 满足条件的一页列表记录
	 * @throws Exception
	 *             数据库访问异常 数据访问异常 return 查询结果转换成List<Map<字段名,字段值>>
	 */
	public List getByNamedQueryPage(String queryName,
			Map<String, Object> params, int currentPage, int pageSize)
			throws Exception
	{

		// Session session =
		// getHibernateTemplate().getSessionFactory().openSession();//注意用这种方法取session的话,得自己关掉它
		Query query = getSession().getNamedQuery(queryName);

		// setQueryNamedParameters(query,params);

		// 1.可以对原生SQL 查询使用ResultTransformer。这会返回不受Hibernate管理的实体。
		// /query.setResultTransformer(Transformers.aliasToBean(String.class));
		// 2.将查询结果转换成List<Map<字段名,字段值>>
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setProperties(params);
		int startIndex = (currentPage - 1) * pageSize;
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return list;
	}

	/**
	 * 功能描述：使用命名SQL查询列表数据 Map params=new HashMap();//此为where条件的值
	 * params.put("code", StrFuncs.anyLike("123456")); params.put("id", 3);
	 * 
	 * @param queryName
	 *            命名SQL查询名称
	 * @param params
	 *            参数列表
	 * @param currentPage
	 *            页码
	 * @param pageSize
	 *            页面大小
	 * @return 满足条件的一页列表记录
	 * @throws Exception
	 *             数据库访问异常 数据访问异常 return long
	 */
	public long getByNamedQueryCount(String queryName,
			Map<String, Object> params, int currentPage, int pageSize)
			throws Exception
	{

		StringBuffer countSQL = new StringBuffer(
				"SELECT /*+ROWID(USER)*/ count(1) total FROM (").append(
				getSession().getNamedQuery(queryName).getQueryString()).append(
				") this ");
		SQLQuery queryCount = getSession().createSQLQuery(countSQL.toString());
		// setQueryNamedParameters(queryCount,params);
		queryCount.setProperties(params);
		queryCount.addScalar("total", new IntegerType());
		long totalCount = ((Integer) queryCount.uniqueResult()).longValue();

		return totalCount;
	}

	/**
	 * 获得sql-query name="car.analysis_3"的SQL语句
	 * 
	 * @param queryName
	 * @return
	 * @throws Exception
	 */
	public String getHbmXmlQuerySQL(String queryName) throws Exception
	{
		String querySQL = getSession().getNamedQuery(queryName)
				.getQueryString();
		return querySQL;
	}

	/**
	 * @param querySQL
	 *            =
	 *            "select u.id,u.code,u.password,z.empname,z.emptel,z.mobi,z.name_pinyin,z.empid,z.state,z.empzc from u_reg_user u,z_pubzcyinfo z where u.code=z.name_pinyin and z.state=1"
	 *            ;
	 * @param propers
	 * @param currentPage
	 * @param pageSize
	 * @return List<Map<字段名,字段值>>
	 * @throws Exception
	 */
	public List getBySQLQueryPage(String querySQL, Map propers,
			int currentPage, int pageSize) throws Exception
	{

		Query query = getSession().createSQLQuery(querySQL);
		// /query.setResultTransformer(new AliasToEntityMapResultTransformer());
		// 2.将查询结果转换成List<Map<字段名,字段值>>
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setProperties(propers);
		int startIndex = (currentPage - 1) * pageSize;
		List list = query.setFirstResult(startIndex).setMaxResults(pageSize)
				.list();
		return list;
	}

	/**
	 * @param querySQL
	 *            =
	 *            "select u.id,u.code,u.password,z.empname,z.emptel,z.mobi,z.name_pinyin,z.empid,z.state,z.empzc from u_reg_user u,z_pubzcyinfo z where u.code=z.name_pinyin and z.state=1"
	 *            ;
	 * @throws Exception
	 *             yxy用于sql直接查询
	 */
	public List getBySQLQueryList(String querySQL) throws Exception
	{

		Query query = getSession().createSQLQuery(querySQL);
		// /query.setResultTransformer(new AliasToEntityMapResultTransformer());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;
	}

	/**
	 * 按条件查询所有
	 * 
	 * @param querySQL
	 * @param propers
	 * @return list<map>
	 * @throws Exception
	 */
	public List getBySQLQueryList(String querySQL, Map propers)
			throws Exception
	{

		Query query = getSession().createSQLQuery(querySQL);
		// /query.setResultTransformer(new AliasToEntityMapResultTransformer());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		query.setProperties(propers);
		List list = query.list();
		return list;
	}

	/**
	 * @param querySQL
	 *            select * from u_reg_user t where t.id>5
	 * @param propers
	 *            Map propers=new HashMap();//此为where条件的值 propers.put("code",
	 *            StrFuncs.anyLike("123456")); propers.put("id", 3);
	 * @param currentPage
	 * @param pageSize
	 * @return List<Map<字段名,字段值>>
	 * @throws Exception
	 */
	public long getBySQLQueryCount(String querySQL, Map propers,
			int currentPage, int pageSize) throws Exception
	{
		// /Session session =
		// getHibernateTemplate().getSessionFactory().openSession();
		// http://chwshuang.iteye.com/blog/1537487
		/* +ROWID(USER) */
		/* + INDEX(USER ID) */
		StringBuffer countSQL = new StringBuffer(
				"SELECT /*+ROWID(USER)*/ count(1) total FROM (").append(
				querySQL).append(") this ");
		SQLQuery queryCount = getSession().createSQLQuery(countSQL.toString());
		queryCount.setProperties(propers);
		queryCount.addScalar("total", new IntegerType());
		long totalCount = ((Integer) queryCount.uniqueResult()).longValue();

		return totalCount;
	}

	/**
	 * @return 取得系统时间sysdate
	 */
	public Timestamp getSysDate()
	{
		Timestamp time = (Timestamp) getSession()
				.createSQLQuery("select sysdate as tm from dual")
				.addScalar("tm", Hibernate.TIMESTAMP).uniqueResult();
		return time;
	}

	/**
	 * @param seqname
	 *            SEQ名
	 * @return 取得当关SEQ的nextval
	 */
	public Long getSeqNextval(String seqname)
	{
		String sql = "select " + seqname + ".nextval as seq from dual";
		Long seq = (Long) getSession().createSQLQuery(sql)
				.addScalar("seq", Hibernate.LONG).uniqueResult();
		return seq;
	}

	/**
	 * 调用存储过程 无返回值
	 * 
	 * @param callStr
	 *            "{Call proc(?,?)}"
	 */
	public void prepareCall(String callStr, Map propers)
	{
		SQLQuery query = getSession().createSQLQuery(callStr);
		query.setProperties(propers);
		query.executeUpdate();
	}

	/**
	 * 调用存储过程
	 * 
	 * @param callStr
	 *            "{Call proc(?,?)}"
	 * @param propers
	 *            参数
	 * @return list<Map>
	 */
	public List<Map> prepareCallList(String callStr, Map propers)
	{
		SQLQuery query = getSession().createSQLQuery(callStr);
		query.setProperties(propers);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		List list = query.list();
		return list;
	}

	/**
	 * 查询某表的列信息
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryTableColList(String tableName, String owner)
			throws Exception
	{
		Query query = getSession().createSQLQuery(sqlColQuery);
		// /query.setResultTransformer(new AliasToEntityMapResultTransformer());
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		Map mapQuery = new HashMap();
		mapQuery.put("tableName", tableName);
		mapQuery.put("owner", owner);
		query.setProperties(mapQuery);
		List list = query.list();
		return list;
	}

	/**
	 * 为父类HibernateDaoSupport注入sessionFactory的值
	 * 
	 * @param sessionFactory
	 */
	@Resource(name = "sessionFactory")
	public void setSuperSessionFactory(SessionFactory sessionFactory)
	{
		super.setSessionFactory(sessionFactory);
	}
}
