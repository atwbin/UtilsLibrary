package com.yitoudai.library;

import android.webkit.CookieManager;
import android.webkit.WebView;

/**
 * Author：Wbin on 2018/2/5 10:15
 * Email：atwbin@163.com
 * Description：  有关webview的工具类
 */
public class WebViewUtils {

    /**
     * 清除cookie
     */
    public static void cleanCookie() {
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieManager.removeAllCookie();
    }

    /**
     * 自定义UA
     */
    public static String generateCustomUserAgent(WebView webView) {
        return webView.getSettings().getUserAgentString()
                + "versioncode=" + SystemUtils.getVersionCode(webView.getContext())
                + "/LyAndroid/" + SystemUtils.getVersionName(webView.getContext());

    }

}
