package com.example.gl.api.web.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class HeaderFilter extends ZuulFilter {

	public static String USER_HEADER_NAME = "sso_user";
	
	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest req = ctx.getRequest();
		
		HttpSession session = req.getSession();
		if (null != session) {
			SecurityContext sctx = (SecurityContext) session.getAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
			if (null != sctx) {
				Authentication auth = sctx.getAuthentication();
				if (null != auth) {
					String username = auth.getName();
					ctx.addZuulRequestHeader(USER_HEADER_NAME, username);
				}
			}
		}
		return null;
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 0;
	}

}
