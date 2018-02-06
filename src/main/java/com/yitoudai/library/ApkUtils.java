package com.yitoudai.library;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Author：Wbin on 2018/2/2 15:50
 * Email：atwbin@163.com
 * Description：APK工具类
 */
public class ApkUtils {

    /**
     * 安装一个apk文件
     * @param context  上下文
     * @param uriFile uri文件
     */
    public static void install(Context context, File uriFile) {

        // TODO: 17-8-11 SDK >= 24时需要做兼容
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 卸载一个app
     * @param context   上下文
     * @param packageName 应用包名
     */
    public static void uninstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE,packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /**
     * 检查手机上是否安装了指定的软件
     *
     * @param context 上下文
     * @param packageName 应用包名
     * @return
     */
    public static boolean isAvilible(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<String>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /**
     * 检测包名
     *
     * @param context 上下文
     * @param path apk路径
     * @return true: 验证包通过
     */
    public static boolean checkPackageName(Context context, String path) {
        PackageInfo packageInfo = context.getPackageManager()
                .getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);
        if(packageInfo != null){
            Timber.d("version_update--> packageInfo: " + packageInfo.packageName);
        }
        return packageInfo != null && TextUtils.equals(context.getPackageName(), packageInfo.packageName);

    }

}
