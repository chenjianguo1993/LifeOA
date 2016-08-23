package com.oa.common.web.action;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.hibernate.engine.jdbc.SerializableClobProxy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.oa.common.tool.cache.OSCacheManage;
import com.oa.common.tool.excelxml.OutExcel;
import com.opensymphony.xwork2.ActionSupport;

/**
 * ActionSupport stuct 提供的父类
 * 
 * @className:BaseAction.java
 * @classDescription:父类Action,包括一些通用的方法
 * @author:linjiekai
 * @createTime:2010-6-24
 */
@SuppressWarnings("serial")
@Controller
@Scope("prototype")
public abstract class BaseAction extends ActionSupport implements
		ServletRequestAware, ServletResponseAware
{
	private static final Logger log = Logger.getLogger(BaseAction.class);
	/** 进行增删改操作后,以redirect方式重新打开action默认页的result名. */
	public static final String RELOAD = "reload";

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	// 申明缓存对象
	protected OSCacheManage osCacheManage = OSCacheManage.getInstance();

	boolean flag;
	/*
	 * page:2 pagesize:10 sortname:ID sortorder:desc
	 */
	protected Integer page;// 表第几页
	protected Integer pagesize;// 表每页显示几条数据
	protected String sortname;// 每排序的字段名
	protected String sortorder;// 指排序的方式,如desc,asc

	protected String _gt_json;

	/**
	 * Action函数, 默认的action函数, 默认调用list()函数.
	 */
	public String execute() throws Exception
	{
		return list();
	}

	// -- CRUD Action函数 --//
	/**
	 * Action函数,显示Entity列表界面. return SUCCESS.
	 */
	public abstract String list() throws Exception;

	/**
	 * Action函数,显示新增或修改Entity界面. return INPUT.
	 */
	public abstract String alter() throws Exception;

	/**
	 * Action函数,新增或修改Entity. return RELOAD.
	 */
	public abstract String save() throws Exception;

	/**
	 * Action函数,删除Entity. return RELOAD.
	 */
	public abstract String delete() throws Exception;

	// -- 简化取值----//
	/**
	 * 取得HttpRequest中Parameter的简化方法.
	 */
	public String getParameter(String name)
	{
		return (null == this.request.getParameter(name)) ? null : this.request
				.getParameter(name);
	}

	/**
	 * 取得HttpRequest中Attribute的简化函数.
	 */
	public Object getRequestAttribute(String name)
	{
		return request.getAttribute(name);
	}

	/**
	 * 取得HttpSession中Attribute的简化函数.
	 */
	public Object getSessionAttribute(String name)
	{
		return this.getSession().getAttribute(name);
	}

	/**
	 * 取得HttpSession的简化函数.
	 */
	public HttpSession getSession()
	{
		return request.getSession();
	}

	/**
	 * 设置HttpRequest中Attribute的简化函数.
	 */
	public boolean setRequestAttribute(String key, Object object)
	{
		try
		{
			flag = false;
			request.setAttribute(key, object);
			flag = true;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return flag;

	}

	/**
	 * 设置HttpSession中Attribute的简化函数.
	 */
	public void setSessionAttribute(String name, Object object)
	{
		getSession().setAttribute(name, object);
	}

	/**
	 * 获取根目录
	 */
	public String getRoot()
	{
		return request.getContextPath();
	}

	/**
	 * 获取根目录
	 */
	public String getRealRoot()
	{
		return this.getSession().getServletContext().getRealPath("/");
	}

	// -------自动生成----------//
	public void setServletRequest(HttpServletRequest request)
	{
		this.request = request;

	}

	public void setServletResponse(HttpServletResponse response)
	{
		this.response = response;

	}

	/**
	 * @param message
	 *            操作信息结果的描述
	 * @param type
	 *            Y为成功，N为失败,error为错误
	 * @return {type:'Y',message:"操作信息"}
	 */
	public String optMessage(String type, String message)
	{
		Map map = new HashMap();
		map.put("Type", type);
		map.put("Message", message);
		String jsonStr = JSONObject.fromObject(map).toString();
		log.debug("json:" + jsonStr);
		return jsonStr;
	}

	/**
	 * @param message
	 *            操作信息结果的描述
	 * @param type
	 *            Y为成功，N为失败,error为错误
	 * @return {type:'Y',message:"操作信息"}
	 */
	public String optMessageAndId(String type, String message, Long id)
	{
		Map map = new HashMap();
		map.put("Type", type);
		map.put("Message", message);
		map.put("id", id);
		String jsonStr = JSONObject.fromObject(map).toString();
		log.debug("json:" + jsonStr);
		return jsonStr;
	}

	/**
	 * @param message
	 *            操作信息结果的描述
	 * @param type
	 *            Y为成功，N为失败,error为错误
	 * @return {type:'Y',message:"操作信息"}
	 */
	public String optMessageAndAlertType(String type, String message,
			String alertType)
	{
		Map map = new HashMap();
		map.put("Type", type);
		map.put("Message", message);
		map.put("alertType", alertType);
		String jsonStr = JSONObject.fromObject(map).toString();
		log.debug("json:" + jsonStr);
		return jsonStr;
	}

	public String errorOptMessage(String message)
	{
		return optMessage("error", message);
	}

	public String saveOptMessage()
	{
		return optMessage("Y", "保存成功");
	}

	public String delOptMessage()
	{
		return optMessage("Y", "删除成功");
	}

	public String modiOptMessage()
	{
		return optMessage("Y", "修改成功");
	}

	public String error300(String url, String navTabId, String errMsg)
	{
		return errorOptMessage("操作失败,请检查数据的完整性或其它原因." + errMsg);
	}

	public String error301(String url, String navTabId, String errMsg)
	{
		StringBuffer str = new StringBuffer();
		str.append("{");
		str.append("\"statusCode\":\"301\",");
		str.append("\"message\":\"会话超时,请重新登陆." + errMsg + "\",");
		str.append("}");
		log.debug(str.toString());
		return str.toString();
	}

	public String getClobStr(Object proxyObj)
	{
		if (proxyObj != null)
		{
			SerializableClobProxy proxy = (SerializableClobProxy) Proxy
					.getInvocationHandler(proxyObj);
			java.sql.Clob realClob = proxy.getWrappedClob();
			String content = OutExcel.ClobToString((oracle.sql.CLOB) realClob);
			return content;
		} else
			return "";
	}

	public Integer getPage()
	{
		return page;
	}

	public void setPage(Integer page)
	{
		this.page = page;
	}

	public Integer getPagesize()
	{
		return pagesize;
	}

	public void setPagesize(Integer pagesize)
	{
		this.pagesize = pagesize;
	}

	public String getSortname()
	{
		return sortname;
	}

	public void setSortname(String sortname)
	{
		this.sortname = sortname;
	}

	public String getSortorder()
	{
		return sortorder;
	}

	public void setSortorder(String sortorder)
	{
		this.sortorder = sortorder;
	}

	public String get_gt_json()
	{
		return _gt_json;
	}

	public void set_gt_json(String gtJson)
	{
		_gt_json = gtJson;
	}

}
