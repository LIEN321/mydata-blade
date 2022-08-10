package org.springblade.common.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.http.Method;

import java.util.Map;

/**
 * Http请求工具类，基于hutool的cn.hutool.http包
 *
 * @author LIEN
 */
public class HttpUtils {

    public static String get(String url) {
        return get(url, null);
    }

    public static String get(String url, Map<String, Object> params) {
        return send(Method.GET, url, params);
    }

    public static String post(String url) {
        return post(url, null);
    }

    public static String post(String url, Map<String, Object> params) {
        return send(Method.POST, url, params);
    }

    public static String put(String url) {
        return put(url, null);
    }

    public static String put(String url, Map<String, Object> params) {
        return send(Method.PUT, url, params);
    }

    public static String delete(String url) {
        return delete(url, null);
    }

    public static String delete(String url, Map<String, Object> params) {
        return send(Method.DELETE, url, params);
    }

    public static String send(Method method, String url) {
        return send(method, url, null);
    }

    public static String send(Method method, String url, Map<String, Object> params) {
        return send(method, url, null, params);
    }

    public static String send(Method method, String url, Map<String, String> headers, Map<String, Object> params) {
        return send(method, url, headers, params, null);
    }

    public static String send(Method method, String url, Map<String, String> headers, Map<String, Object> params, String body) {
        HttpRequest httpRequest = HttpUtil.createRequest(method, url);
        if (CollUtil.isNotEmpty(headers)) {
            httpRequest.headerMap(headers, true);
        }
        if (CollUtil.isNotEmpty(params)) {
            httpRequest.form(params);
        }
        if (StrUtil.isNotBlank(body)) {
            httpRequest.body(body);
        }

        HttpResponse httpResponse = httpRequest.execute();
        if (!httpResponse.isOk()) {
            throw new RuntimeException(httpResponse.getStatus() + "," + httpResponse.body());
        }

        return httpResponse.body();
    }
}
