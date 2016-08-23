package com.oa.common.web.utils;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.locale.converters.DateLocaleConverter;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

public class WebUtils
{
	/** 把request对象中的请求参数封装到bean中 **/
	public static <T> T request2Bean(HttpServletRequest request, Class<T> clazz)
	{
		try
		{
			T bean = clazz.newInstance();
			ConvertUtils.register(new DateLocaleConverter(), Date.class);
			Enumeration e = request.getParameterNames();

			while (e.hasMoreElements())
			{
				String name = (String) e.nextElement(); // username=aaa
														// password=123
				String value = request.getParameter(name);
				BeanUtils.setProperty(bean, name, value);
			}
			return bean;

		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static String makeID()
	{
		return UUID.randomUUID().toString();
	}

	public static String getBASE64(String message)
	{
		BASE64Encoder encoder = new BASE64Encoder();
		return encoder.encode(message.getBytes());
	}

	public static String getFromBASE64(String s)
	{
		if (s == null)
		{
			return null;
		}
		BASE64Decoder decoder = new BASE64Decoder();

		byte[] b = (byte[]) null;
		try
		{
			b = decoder.decodeBuffer(s);
		} catch (IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return new String(b);
	}

	public static String generateFilepath(String uploadpath, String filename)
	{
		int hashcode = filename.hashCode();
		int dir1 = hashcode & 0xF;
		int dir2 = hashcode >> 4 & 0xF;

		String savepath = uploadpath + File.separator + dir1 + File.separator
				+ dir2;
		File file = new File(savepath);
		if (!(file.exists()))
		{
			file.mkdirs();
		}
		return savepath;
	}

	public static String generateTimeFilepath(HttpServletRequest request,
			String uploadpath, String filename)
	{
		Date date;
		try
		{
			date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String d = sdf.format(date);
			String[] str = d.split("-");
			File ydir = new File(uploadpath, str[0]);
			if (!(ydir.exists()))
			{
				ydir.mkdirs();
			}

			File mdir = new File(ydir, str[1]);
			if (!(mdir.exists()))
			{
				mdir.mkdirs();
			}
			File ddir = new File(mdir, str[2]);
			if (!(ddir.exists()))
			{
				ddir.mkdirs();
			}
			File fdir = new File(ddir, filename);
			return fdir.getPath();
		} catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public static List enumeration2List(Enumeration e)
	{
		List list = new ArrayList();
		while (e.hasMoreElements())
		{
			list.add(e.nextElement());
		}
		return list;
	}

	public static String DateToStr(Date date)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = format.format(date);
		return str;
	}

	public static Date StrToDatetime(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try
		{
			date = format.parse(str);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	public static Date StrToDate(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try
		{
			date = format.parse(str);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	public static Date StrToDateTime(String str)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date date = null;
		try
		{
			date = format.parse(str);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		return date;
	}

	public static Long DateTimeToStamp(Date date)
	{
		System.out.print("Format To times:" + date.getTime());
		return Long.valueOf(date.getTime());
	}

	public static Date TimeStampToDate(Long long_time)
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = format.format(long_time);
		Date date = null;
		try
		{
			date = format.parse(d);
		} catch (ParseException e)
		{
			e.printStackTrace();
		}
		System.out.println("Format To String(Date):" + d);
		System.out.println("Format To Date:" + date);
		return date;
	}
}