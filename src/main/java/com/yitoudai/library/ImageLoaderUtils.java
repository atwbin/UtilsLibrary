package com.yitoudai.library;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewTreeObserver;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.File;

/**
 * Author：Wbin on 2018/2/2 14:27
 * Email：atwbin@163.com
 * Description：ImageLoader图片工具类
 */
public class ImageLoaderUtils {

    private static DisplayImageOptions options;


    public static DisplayImageOptions defaultImageOpt() {

        if (options == null) {
            options = new DisplayImageOptions.Builder().cacheOnDisk(true).cacheOnDisk(true).build();
        }
        return options;
    }

    public static void scaleImageResources(Activity activity, final View view, int drawableResId) {
        // 解析将要被处理的图片
        Bitmap resourceBitmap = BitmapFactory.decodeResource(activity.getResources(), drawableResId);
        if (resourceBitmap == null) {
            return;
        }
        scaleImage(activity, view, resourceBitmap);
    }

    /**
     * 当线上图片没有缓冲时显示本地图片
     * @param activity
     * @param view
     * @param url
     * @param defaultResId
     */
    public static void scaleImageURL(Activity activity, final View view, String url, int defaultResId) {
        GetImgAsync getImgAsync = new GetImgAsync();
        File cacheFile = ImageLoader.getInstance().getDiskCache().get(url);
        if (cacheFile != null && cacheFile.exists()) {
            getImgAsync.execute(url, activity, view);
        } else {
            scaleImageResources(activity, view, defaultResId);
        }
    }

    /**
     * 直接展示线上图片
     * @param activity
     * @param view
     * @param url
     */
    public static void scaleImageURL(Activity activity, final View view, String url){
        GetImgAsync getImgAsync = new GetImgAsync();
        getImgAsync.execute(url, activity, view);
    }


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
            if (bitmap != null) {
                scaleImage(activity, view, bitmap);
            }
        }
    }


    public static void scaleImage(final Activity activity, final View view, final Bitmap bitmap) {

//        // 获取屏幕的高宽
//        Point outSize = new Point();
//        activity.getWindow().getWindowManager().getDefaultDisplay().getSize(outSize);
//
//
//        // 开始对图片进行拉伸或者缩放
//        // 使用图片的缩放比例计算将要放大的图片的高度
//        int bitmapScaledHeight = Math.round(bitmap.getHeight() * outSize.x * 1.0f / bitmap.getWidth());
//
//        // 以屏幕的宽度为基准，如果图片的宽度比屏幕宽，则等比缩小，如果窄，则放大
//        final Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, outSize.x, bitmapScaledHeight, false);

        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
//                //这里防止图像的重复创建，避免申请不必要的内存空间
//                if (scaledBitmap.isRecycled())
//                    //必须返回true
//                    return true;
//
//                // 当UI绘制完毕，我们对图片进行处理
//                int viewHeight = view.getMeasuredHeight();
//
//
//                // 计算将要裁剪的图片的顶部以及底部的偏移量
//                int offset = (scaledBitmap.getHeight() - viewHeight) / 2;
//
//
//                // 对图片以中心进行裁剪，裁剪出的图片就是非常适合做引导页的图片了
//                Bitmap finallyBitmap = Bitmap.createBitmap(scaledBitmap, 0, offset, scaledBitmap.getWidth(),
//                        scaledBitmap.getHeight() - offset * 2);
//
//
//                if (!finallyBitmap.equals(scaledBitmap)) {//如果返回的不是原图，则对原图进行回收
//                    scaledBitmap.recycle();
//                    System.gc();
//                }

                // 设置图片显示
                view.setBackgroundDrawable(new BitmapDrawable(activity.getResources(), bitmap));
                return true;
            }
        });
    }

}
