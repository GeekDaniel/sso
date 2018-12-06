package com.piaoniu.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by yan.liu on 2015/9/8.
 */
public class WebUtils {


    private static final String UNKNOWN = "unknown";

    private static final String DEFAULT_IP = "0.0.0.0";

    private static final String REQ_HEADER_USERIP_KEY ="X-Forwarded-For";
    private static final String REQ_HEADER_LATITUDE ="X-Latitude";
    private static final String REQ_HEADER_LONGITUDE ="X-Longitude";

    public static final int DEFAULT_MAX_AGE = 31536000;

    private static final List<String> filterList = new ArrayList<String>(){{add("127.0.0.1");add("10.1.");add("10.2.");add("192.168.");}};

    private static final String HTTP_PATTERN = "(http|https):";
    private static final String USERINFO_PATTERN = "([^@/]*)";
    private static final String HOST_PATTERN = "([^/?#:]*)";
    private static final String PORT_PATTERN = "(\\d*)";
    private static final String PATH_PATTERN = "([^?#]*)";
    private static final String LAST_PATTERN = "(.*)";

    private static final Pattern HTTP_URL_PATTERN = Pattern.compile("^" + HTTP_PATTERN + "(//(" + USERINFO_PATTERN + "@)?" + HOST_PATTERN + "(:" + PORT_PATTERN + ")?" + ")?" + PATH_PATTERN + "(\\?" + LAST_PATTERN + ")?");

    public static final String COOKIE_DOMAIN_SSO = "sso.piaoniu.com";


    private WebUtils(){
        // hide constructor
    }

    /**
     * 添加cookie
     *
     * @param response 响应
     * @param name  cookie名称
     * @param value cookie的值
     * @param maxAge 过期时间(秒数)
     */
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setDomain(COOKIE_DOMAIN_SSO);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }

    public static void addCookie(HttpServletResponse response, String name, String value) {
        addCookie(response, name, value, DEFAULT_MAX_AGE);
    }

    public static void deleteCookie(HttpServletResponse response, String name) {
        addCookie(response, name, "", 0);
    }

    /**
     * 获取cookie
     * @param request http请求
     * @param name cookie名字
     * @return cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (name.equals(cookies[i].getName())) {
                    return cookies[i];
                }
            }
        }
        return null;
    }

    /**
     * 获取cookie值
     * @param request http请求
     * @param name cookie名字
     * @return 值
     */
    public static String getCookieValue(HttpServletRequest request, String name) {
        Cookie cookie = getCookie(request, name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 获取指定域名的cookie值
     * @param request http请求
     * @param domain 域
     * @param name cookie名字
     * @return String
     */
    public static String getCookieValue(HttpServletRequest request, String domain, String name) {
        Cookie cookie = getCookie(request, domain,name);
        return cookie != null ? cookie.getValue() : null;
    }

    /**
     * 获取指定域名的cookie对象
     * @param request http请求
     * @param domain 域
     * @param name cookie名字
     * @return Cookie
     */
    public static Cookie getCookie(HttpServletRequest request, String domain, String name) {
        Cookie cookies[] = request.getCookies();
        if (cookies == null) return null;
        if(domain==null || domain.equalsIgnoreCase("localhost")){
            for (Cookie cookie:cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie;
                }
            }
        }
        for (Cookie cookie:cookies) {
            if (domain.equals(cookie.getDomain()) && name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    /**
     * 是否合法的URI
     *
     * @param uri
     * @return
     */
    public static boolean isValidUri(String uri) {
        return !uri.contains("/etc/passwd") && HTTP_URL_PATTERN.matcher(uri).matches();
    }

    /**
     * 清除潜在的XSS攻击字符
     *
     * @param url
     * @return
     */
    public static String cleanXSS(String url) {
        url = url.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        url = url.replaceAll("\\(", "&#40;").replaceAll("\\)", "&#41;");
        url = url.replaceAll("'", "&#39;");
        url = url.replaceAll("eval\\((.*)\\)", "");
        url = url.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
        url = url.replaceAll("script", "");
        return url;
    }

    /**
     * 获取域名
     *
     * @param uri
     * @return
     */
    public static String getHost(String uri) {
        Matcher m = HTTP_URL_PATTERN.matcher(uri);
        if (!m.matches()) {
            return null;
        }
        String host = m.group(2);
        if (host.startsWith("//")) {
            host = host.substring(2, host.length());
        }
        return host.indexOf(":") == -1 ? host : host.substring(0, host.indexOf(":"));
    }
}
