package com.yitoudai.library;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Wbin on 2018/2/2 15:39
 * Email：atwbin@163.com
 * Description：url集合
 */
public class UrlUtils {

    /**  分享参数*/
    public static final String WX_SHARE_KEY = "wx_share_key";


    /**
     * 解析出url请求的路径，包括页面
     *
     * @param strURL url地址
     * @return url路径
     */
    public static String UrlPage(String strURL) {
        String strPage = null;
        String[] arrSplit = null;
        if (TextUtils.isEmpty(strURL)) {
            return strURL;
        }

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 0) {
            if (arrSplit.length > 1) {
                if (arrSplit[0] != null) {
                    strPage = arrSplit[0];
                }
            } else {
                strPage = strURL;
            }
        }

        return strPage;
    }

    /**
     * 去掉url中的路径，留下请求参数部分
     *
     * @param strURL url地址
     * @return url请求参数部分
     */
    public static String TruncateUrlPage(String strURL) {
        String strAllParam = null;
        String[] arrSplit = null;

        strURL = strURL.trim();

        arrSplit = strURL.split("[?]");
        if (strURL.length() > 1) {
            if (arrSplit.length > 1) {
                if (arrSplit[1] != null) {
                    strAllParam = arrSplit[1];
                }
            }
        }

        return strAllParam;
    }


    /**
     * 解析出url参数中的键值对
     * 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
     *
     * @param URL url地址
     * @return url请求参数部分
     */
    public static Map<String, String> URLRequest(String URL) {
        Map<String, String> mapRequest = new HashMap<String, String>();

        String[] arrSplit = null;

        String strUrlParam = TruncateUrlPage(URL);
        if (strUrlParam == null) {
            return mapRequest;
        }
        //每个键值为一组
        arrSplit = strUrlParam.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    mapRequest.put(arrSplitEqual[0], "");
                }
            }
        }
        return mapRequest;
    }


    public static Map<String, String> getAppParamsMap(String appParams) {
        Map<String, String> map = new HashMap<String, String>();

        String[] arrSplit = null;
        arrSplit = appParams.split("[&]");

        //每个键值为一组
        arrSplit = appParams.split("[&]");
        for (String strSplit : arrSplit) {
            String[] arrSplitEqual = null;
            arrSplitEqual = strSplit.split("[=]");

            //解析出键值
            if (arrSplitEqual.length > 1) {
                //正确解析
                map.put(arrSplitEqual[0], arrSplitEqual[1]);

            } else {
                if (arrSplitEqual[0] != "") {
                    //只有参数没有值，不加入
                    map.put(arrSplitEqual[0], "");
                }
            }
        }
        return map;
    }

    /**
     * 给一个url添加参数
     *
     * @param rootUrl
     * @param params  参数写成　paramskey=paramsvalue 的格式
     * @return
     */
    public static String addParams(String rootUrl, String params) {

        if (!TextUtils.isEmpty(rootUrl)) {

            if (rootUrl.contains("?")) {
                rootUrl = rootUrl + "&" + params;
            } else {

                rootUrl = rootUrl + "?" + params;
            }
        }

        return rootUrl;
    }

    /**
     * 解析分享参数
     * @param url 分享地址
     * @return 参数值
     */
    public static String parseShareParam(String url){
        if(TextUtils.isEmpty(url)){
            return "";
        }
        Map<String, String> maps = URLRequest(url);
        if(maps == null || !maps.keySet().contains(WX_SHARE_KEY)){
            return "";
        }
        return maps.get(WX_SHARE_KEY);
    }
}
