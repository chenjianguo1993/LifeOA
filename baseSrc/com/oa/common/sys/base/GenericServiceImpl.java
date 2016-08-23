package com.oa.common.sys.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.oa.common.tool.page.PageQueryResult;

public class GenericServiceImpl<T, ID extends Serializable> implements
		GenericService<T, ID>
{

	public List<T> findAll()
	{
		return genericDao.findAll();
	}

	public T findById(ID id)
	{
		return genericDao.findById(id);
	}

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
			final int pageSize)
	{
		return genericDao.findByPage(hql, pageNo, pageSize);
	}

	public void modify(T entity) throws Exception
	{
		genericDao.modify(entity);
	}

	public void updateOrSave(T entity) throws Exception
	{
		genericDao.saveOrUpdate(entity);
	}

	public void remove(T entity) throws Exception
	{
		genericDao.remove(entity);
	}

	public void removeById(ID id) throws Exception
	{
		genericDao.removeById(id);
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
		genericDao.removeByIds(ids, idName);
	}

	public void removeAll(Collection<T> entities) throws Exception
	{
		genericDao.removeAll(entities);
	}

	public ID save(T entity) throws Exception
	{
		return genericDao.save(entity);
	}

	/**
	 * 调用存储过程
	 * 
	 * @param callStr
	 *            "{Call proc(?,?)}"
	 */
	public void prepareCall(String callStr, Map propers)
	{
		genericDao.prepareCall(callStr, propers);
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
		return genericDao.prepareCallList(callStr, propers);
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
		return genericDao.QueryTableColList(tableName, owner);
	}

	public void setGenericDao(GenericDao<T, ID> genericDao)
	{
		this.genericDao = genericDao;
	}

	public GenericDao<T, ID> genericDao;

}
