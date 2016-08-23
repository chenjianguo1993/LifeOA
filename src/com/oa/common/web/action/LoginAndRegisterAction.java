package com.oa.common.web.action;

import java.util.Date;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;

import com.oa.common.sys.model.UserInfo;
import com.oa.common.sys.service.UserService;
import com.oa.common.web.utils.WebUtils;

/**
 * 
 * ClassName: LoginAndRegisterAction
 * 
 * @Description: TODO
 * @author 陈建国
 * @date 2016-8-21
 */
public class LoginAndRegisterAction extends BaseAction
{

	// 用户实体，封装用户数据
	UserInfo userInfo = new UserInfo();
	// -- 定义Service --//
	@Autowired
	protected UserService userService;

	// 1.转发到登陆页面
	public String toLogin() throws Exception
	{
		return "toLogin";
	}

	// 2.转发到注册页面
	public String register() throws Exception
	{
		// 构造json
		JSONObject json = new JSONObject();
		// 获取表单带过来数据,（生日就不存进数据库了）
		userInfo = WebUtils.request2Bean(request, UserInfo.class);
		Date createTime = new Date(System.currentTimeMillis());
		userInfo.setCreateTime(createTime);
		// 获取确认密码
		String password1 = this.getParameter("password1");
		if (userInfo.getUserPassword().endsWith(password1))
		{
			// 存到数据库
			this.userService.save(userInfo);
			// 存到session
			this.setSessionAttribute("userInfo", userInfo);
			json.put("status", "true");
			json.put("message", "注册成功");
			response.getWriter().write(json.toString());
		} else
		{
			json.put("status", "false");
			json.put("passWord", "密码不一致");
			response.getWriter().write(json.toString());
		}

		return null;
	}

	@Override
	public String list() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String alter() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String save() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete() throws Exception
	{
		// TODO Auto-generated method stub
		return null;
	}

}
