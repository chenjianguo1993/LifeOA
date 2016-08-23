package com.oa.common.sys.base;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.oa.common.tool.page.PageQueryResult;

/**
 * @author linjiekai
 * 逻辑接口，service层
 * @param <T> 
 * @param <ID>
 */
public abstract interface GenericService<T, ID extends Serializable> {
	/**
	 * 保存实体
	 * 
	 * @param entity
	 *            : 实体
	 * @return 保存后得到的id
	 */
	public ID save(T entity) throws Exception;

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
	public void removeById(ID id)  throws Exception;

	/**
	 * @param ids 如1,2,3,4,5,6,7,8
	 * @param idName 可为空,但id主键就会默认为"id",要看实体的定义
	 * @throws Exception
	 */
	public void removeByIds(String ids,String idName)  throws Exception;
	/**
	 * <p>
	 * 修改实体
	 * </p>
	 * 
	 * @param entity
	 *            : 实体
	 */
	public void modify(T entity) throws Exception;

	public void updateOrSave(T entity) throws Exception;
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
	 * <p>
	 * 查找全部实体
	 * <p>
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
    public PageQueryResult<T> findByPage(final String hql,final int pageNo, final int pageSize);

    /**
     * 调用存储过程
     * @param callStr "{Call proc()}"
     */
    public void prepareCall(String callStr,Map propers);
    
    /**
     * 调用存储过程
     * @param callStr "{Call proc(?,?)}"
     * @param propers 参数
     * @return list<Map>
     */
    public List<Map> prepareCallList(String callStr,Map propers);
	/**
	 * 查询某表的列信息
	 * @param tableName
	 * @return
	 * @throws Exception
	 */
	public List<Map> QueryTableColList(String tableName,String owner) throws Exception;
	public abstract void setGenericDao(GenericDao<T, ID> genericDao);

}
