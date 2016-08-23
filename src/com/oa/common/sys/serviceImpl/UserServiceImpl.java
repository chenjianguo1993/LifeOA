package com.oa.common.sys.serviceImpl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oa.common.sys.base.GenericDao;
import com.oa.common.sys.base.GenericServiceImpl;
import com.oa.common.sys.dao.UserInfoDao;
import com.oa.common.sys.model.UserInfo;
import com.oa.common.sys.service.UserService;
import com.oa.common.tool.page.PageQueryResult;

/**
 * 
 * ClassName: UserServiceImpl
 * 
 * @Description: 用户服务实现层
 * @author 陈建国
 * @date 2016-8-22
 */
@Service
public class UserServiceImpl extends GenericServiceImpl<UserInfo, Integer>
		implements UserService
{
	@Autowired
	private UserInfoDao userInfoDao;// 用户dao层实例

	// ====方法定义区====//
	@Resource(name = "userInfoDao")
	public void setGenericDao(GenericDao<UserInfo, Integer> genericDao)
	{
		super.genericDao = genericDao;
	}

	

}
