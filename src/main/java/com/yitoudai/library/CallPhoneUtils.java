package com.yitoudai.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author：Wbin on 2018/2/5 09:58
 * Email：atwbin@163.com
 * Description： 拨打电话
 */
public class CallPhoneUtils {


    /**
     * 手机号验证
     * @author ：shijing
     * @param  str 类型
     * @return 验证通过返回true
     */
    public static boolean isMobile(final String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][0-9][0-9]{9}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }


    /**
     * 发起打电话
     * @param context
     * @param phoneNumber
     */
    private static void call(Activity context, String phoneNumber){
        PackageManager pm = context.getPackageManager();
        boolean permission = (PackageManager.PERMISSION_GRANTED ==
                pm.checkPermission("android.permission.CALL_PHONE",context.getPackageName()));

        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber));
        if(isIntentAvailable(context, intent)){
            if(permission){
                context.startActivity(intent);
            }else{
                ToastUtils.show(context, "没有拨号权限");
            }
        }else{
            ToastUtils.show(context,"请插入手机卡");
        }
    }

    public static boolean isIntentAvailable(Context context, Intent intent) {
        final PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }


    /**
     * 格式化手机号
     *
     * @param phoneNum 手机号
     * @return 形如131 0000 0000
     */
    public static String formatPhoneNum(String phoneNum){
        if(TextUtils.isEmpty(phoneNum) || phoneNum.length() != 11){
            return "";
        }

        StringBuilder sb = new StringBuilder(phoneNum);
        sb.insert(3, " ");
        sb.insert(8, " ");
        return sb.toString();
    }


}
