package com.oa.common.sys.base;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.IntegerType;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.oa.common.tool.page.PageQueryResult;

public interface GenericDao<T, ID extends Serializable>
{
	/**
	 * 在查找所有记录的时候,使用提供查询语句,查询匹配的记录,否则将使用默认的查询语句查询数据的所有记录.
	 * 
	 * @param hql
	 *            : 设置自定义的HQL语句
	 */
	public void setHql(String hql);

	/**
	 * 
	 * @return 获取自定义的HQL语句
	 */
	public String getHql();

	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            : 实体
	 * @return 保存后得到的id
	 */
	public ID save(T entity) throws Exception;

	public void saveOrUpdateAll(List<T> entities);

	/**
	 * <p>
	 * 删除实体
	 * </p>
	 * 
	 * @param entity
	 *            : 实体
	 */
	public void remove(T entity) throws Exception;

	/**
	 * <p>
	 * 删除实体集合
	 * </p>
	 * 
	 * @param entities
	 *            : 实体
	 */
	public void removeAll(Collection<T> entities) throws Exception;

	/**
	 * 根据Id删除对象
	 * 
	 * @param obj
	 *            要删除的对象
	 */
	public void removeById(ID id) throws Exception;

	/**
	 * @param ids
	 *            如1,2,3,4,5,6,7,8
	 * @param idName
	 *            可为空,但id主键就会默认为"id",要看实体的定义
	 * @throws Exception
	 */
	public void removeByIds(String ids, String idName) throws Exception;

	/**
	 * <p>
	 * 修改实体
	 * </p>
	 * 
	 * @param entity
	 *            : 实体
	 */

	public void modify(T entity) throws Exception;

	/**
	 * 用于直接执行sql
	 * 
	 * @param sql
	 * @throws Exception
	 */
	public int excuteSql(String sql) throws Exception;

	public void saveOrUpdate(T entity) throws Exception;

	/**
	 * <p>
	 * 通过名字查找
	 * </p>
	 * 
	 * @param id
	 *            : id
	 * @return 找到的实体
	 */
	public T findById(ID id);

	/**
	 * <p/>
	 * 查找全部实体
	 * <p/>
	 * 
	 * @return 所有实体的列表
	 */
	public List<T> findAll();

	/**
	 * <p/>
	 * 根据给定的页码进行分页查找,这是纯Hibernate分页.
	 * <p/>
	 * 
	 * @param pageNo
	 *            : 要查询的页码
	 * @pageNo 第几页
	 * @param pageSize
	 *            : 每页记录数
	 * @pageSize 每页数据条数
	 * @return 匹配的实体列表
	 */
	public PageQueryResult<T> findByPage(final String hql, final int pageNo,
			final int pageSize);

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return 对象
	 */
	public T findObj(final String hql, final Map<Serializable, Serializable> map);

	/**
	 * 查询对象(防止依赖注入)
	 * 
	 * @param hql
	 *            查找语句
	 * @param map
	 *            参数
	 * @return list对象
	 */
	public List<T> findListObj(final String hql, final Map<String, Object> map);

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
			final Map<String, Object> params) throws Exception;

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
			throws Exception;

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
			throws Exception;

	/**
	 * 获得sql-query name="car.analysis_3"的SQL语句
	 * 
	 * @param queryName
	 * @return
	 * @throws Exception
	 */
	public String getHbmXmlQuerySQL(String queryName) throws Exception;

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
			int currentPage, int pageSize) throws Exception;

	/**
	 * @param querySQL
	 *            =
	 *            "select u.id,u.code,u.password,z.empname,z.emptel,z.mobi,z.name_pinyin,z.empid,z.state,z.empzc from u_reg_user u,z_pubzcyinfo z where u.code=z.name_pinyin and z.state=1"
	 *            ;
	 * @throws Exception
	 *             用于sql直接查询
	 */
	public List getBySQLQueryList(String querySQL) throws Exception;

	/**
	 * 按条件查询所有
	 * 
	 * @param querySQL
	 * @param propers
	 * @return
	 * @throws Exception
	 */
	public List getBySQLQueryList(String querySQL, Map propers)
			throws Exception;

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
			int currentPage, int pageSize) throws Exception;

	/**
	 * @return 取得系统时间sysdate
	 */
	public Timestamp getSysDate();

	/**
	 * @param seqname
	 *            SEQ名
	 * @return 取得当关SEQ的nextval
	 */
	public Long getSeqNextval(String seqname);

	/**
	 * 调用存储过程
	 * 
	 * @param callStr
	 *            "{Call proc()}"
	 */
	public void prepareCall(String callStr, Map propers);

	/**
	 * 查询某表的列信息
	 * 
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryTableColList(String tableName, String owner)
			throws Exception;

	/**
	 * 调用存储过程
	 * 
	 * @param callStr
	 *            "{Call proc(?,?)}"
	 * @param propers
	 *            参数
	 * @return list<Map>
	 */
	public List<Map> prepareCallList(String callStr, Map propers);
}
