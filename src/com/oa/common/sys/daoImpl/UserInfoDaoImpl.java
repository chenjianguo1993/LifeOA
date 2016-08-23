package com.oa.common.sys.daoImpl;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.oa.common.sys.base.GenericDaoImpl;
import com.oa.common.sys.dao.UserInfoDao;
import com.oa.common.sys.model.UserInfo;
import com.oa.common.tool.page.PageQueryResult;

/**
 * 
 * ClassName: UserInfoDaoImpl
 * 
 * @Description: TODO
 * @author 陈建国
 * @date 2016-8-22
 */
@Repository("userInfoDao")
public class UserInfoDaoImpl extends GenericDaoImpl<UserInfo, Integer>
		implements UserInfoDao
{
	//1.无参构造方法
	public UserInfoDaoImpl()
	{
		super(UserInfo.class);
	}

}
