package com.yitoudai.library;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Author：Wbin on 2018/2/5 10:55
 * Email：atwbin@163.com
 * Description：日志上传工具类
 */
public class LogSendUtils {


    private static final String ERROR_TITLE = "程序报错了！";
    private static final String SEND_LOG_MSG = "复制日志到剪贴板";
    private static final String COPY_SUCCESS = "复制成功";


    /**
     * 显示发送日志dialog
     *
     * @param context  上下文
     * @param ex  异常
     */
    public static void showSendLogDialog(final Context context, Exception ex) {

        final StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(ERROR_TITLE).setMessage(sw.toString()).setNegativeButton(SEND_LOG_MSG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                copyLogMsg(context, sw.toString());
            }
        });

        builder.show();
    }


    /**
     * 发送日志
     *
     * @param logMsg  输出信息
     */
    public static void sendLogMsg(String logMsg) {


    }

    /**
     * 　将日志复制到剪贴板
     *
     * @param context  上下文
     * @param logMsg   输出信息
     */
    public static void copyLogMsg(Context context, String logMsg) {
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Activity.CLIPBOARD_SERVICE);
        cmb.setPrimaryClip(ClipData.newPlainText(null, logMsg));
        ToastUtils.show(context, COPY_SUCCESS);
    }


}
