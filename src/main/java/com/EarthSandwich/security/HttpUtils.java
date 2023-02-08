package com.EarthSandwich.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

@Component
public class HttpUtils {

	// ip headers
	private static final String[] IP_HEADERS = { "X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
			"HTTP_X_FORWARDED_FOR", "HTTP_X_FORWARDED", "HTTP_X_CLUSTER_CLIENT_IP", "HTTP_CLIENT_IP",
			"HTTP_FORWARDED_FOR", "HTTP_FORWARDED", "HTTP_VIA", "REMOTE_ADDR"

	};

	public String getRequestIP(HttpServletRequest request) {
		for (String header : IP_HEADERS) {
			String value = request.getHeader(header);
			if (value == null || value.isEmpty()) {
				continue;
			}
			return value;

		}
		return request.getRemoteAddr();
	}

}
