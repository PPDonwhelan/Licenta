/**
 * 
 */
package com.rng.site.web.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Siva
 *
 */
public class WebUtils
{
	private WebUtils()
	{
	}
	public static final String IMAGES_PREFIX = "/subject/images/";
	public static final String IMAGES_DIR = "D:/jcart/subject/";
	
	public static String getURLWithContextPath(HttpServletRequest request)
	{
		return request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
	}
}
