package com.yitoudai.library;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewTreeObserver;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * 裁剪图工具类
 * Created by cfp on 17-1-4.
 */

public class ScaleViewUtils {


    private static DisplayImageOptions options;

    /**
     * 默认的DisplayImageOptions
     * @return
     */
    public static DisplayImageOptions defaultImageOpt() {

        if (options == null) {
            options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheOnDisk(true).build();
        }
        return options;
    }

    /**
     * 裁剪本地View
     * @param activity
     * @param view
     * @param drawableResId
     */
    public static void scaleImageResources(Activity activity, final View view, int drawableResId){
        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);
        if (resourceBitmap == null) {
            return;
        }
        scaleImage(activity, view, resourceBitmap);
    }

    /**
     * 裁剪线上图片
     * @param activity
     * @param view
     * @param url
     */
    public static void scaleImageURL(Activity activity, final View view, String url){

        GetImgAsync getImgAsync = new GetImgAsync();
        getImgAsync.execute(url, activity, view);
    }

    /**
     * 获取远程图片
     */
    private static class GetImgAsync extends AsyncTask<Object, Object, Bitmap> {
        private Activity activity;
        private View view;
        @Override
        protected Bitmap doInBackground(Object... params) {
            activity = (Activity) params[1];
            view = (View) params[2];
            return ImageLoader.getInstance().loadImageSync((String) params[0], defaultImageOpt());
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap != null){
                scaleImage(activity, view, bitmap);
            }
        }
    }

    /**
     * 裁剪图片
     * @param activity
     * @param view
     * @param bitmap
     */
    public static void scaleImage(final Activity activity, final View view, Bitmap bitmap) {

        // 获取屏幕的高宽
        Point outSize = new Point();
        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);


        // 开始对图片进行拉伸或者缩放
        // 使用图片的缩放比例计算将要放大的图片的高度
        int bitmapScaledHeight = Math.round(bitmap.getHeight() * outSize.x * 1.0f / bitmap.getWidth());

        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                //这里防止图像的重复创建，避免申请不必要的内存空间
                if (scaledBitmap.isRecycled())
                    //必须返回true
                    return true;

                // 当UI绘制完毕，我们对图片进行处理
                int viewHeight = view.getMeasuredHeight();


                // 计算将要裁剪的图片的顶部以及底部的偏移量
                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;


                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
                        scaledBitmap.getHeight() - offset * 2);


                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
                    scaledBitmap.recycle();
                    System.gc();
                }

                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), finallyBitmap));
                return true;
            }
        });
    }
}
