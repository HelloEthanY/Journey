package com.journey.org.app.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * 分享
 *
 * @author 逍遥
 * @Date 2019/8/6
 */
public class ShareUtil {

    /**
     * 分享文字内容
     *
     * @param dlgTitle 分享对话框标题
     * @param subject  主题
     * @param content  分享内容（文字）
     */
    public static void shareText(Activity activity, String dlgTitle, String subject, String content) {
        if (content == null || "".equals(content)) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        intent.putExtra(Intent.EXTRA_TEXT, content);
        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            activity.startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            activity.startActivity(intent);
        }
    }

    /**
     * 分享本地图片图片和文字内容
     *
     * @param dlgTitle 分享对话框标题
     * @param subject  主题
     * @param content  分享内容（文字）
     * @param uri      图片资源URI
     */
    public static void shareImg(Activity activity, String dlgTitle, String subject, String content,
                                Uri uri) {
        if (uri == null) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        if (subject != null && !"".equals(subject)) {
            intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        }
        if (content != null && !"".equals(content)) {
            intent.putExtra(Intent.EXTRA_TEXT, content);
        }
        // 设置弹出框标题
        if (dlgTitle != null && !"".equals(dlgTitle)) { // 自定义标题
            activity.startActivity(Intent.createChooser(intent, dlgTitle));
        } else { // 系统默认标题
            activity.startActivity(intent);
        }
    }

    //分享多张图片
    public static void shareMultipleImage(Activity activity, View view) {
        ArrayList<Uri> uriList = new ArrayList<>();
        String path = Environment.getExternalStorageDirectory() + File.separator;
        uriList.add(Uri.fromFile(new File(path + "australia_1.jpg")));
        uriList.add(Uri.fromFile(new File(path + "australia_2.jpg")));
        uriList.add(Uri.fromFile(new File(path + "australia_3.jpg")));

        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        activity.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
    /******************************************分享网络图片*****************************************/
    /**
     * @param path 网络图片地址
     */
    public static void getBitmap(final String path, final String title) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(path);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setRequestMethod("GET");
                    if (conn.getResponseCode() == 200) {
                        InputStream inputStream = conn.getInputStream();
                        saveFile(BitmapFactory.decodeStream(inputStream),title);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 保存文件
     */
    public static void saveFile(Bitmap bm, String title) {
        File sd = Environment.getExternalStorageDirectory();
        File dirFile = new File(sd.getPath() + "/journey/" + title + "1.jpg");
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(dirFile);
            if (fos != null) {
                bm.compress(Bitmap.CompressFormat.JPEG, 50, fos);
                fos.flush();
                fos.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Uri U;

    public static void shareCode(Activity activity, String title, String url) {
        getBitmap(url, title);
        File sd = Environment.getExternalStorageDirectory();
        String path = sd.getPath() + "/journey/" + title + "1.jpg";
        File file = new File(path);
        if (Build.VERSION.SDK_INT == 24) {
            //android 7.0及以上权限适配
            U = FileProvider.getUriForFile(activity,
                    "com.journey.org.provider",
                    file);
        } else {
            U = Uri.fromFile(file);
        }
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_STREAM, U);
        activity.startActivity(Intent.createChooser(intent, title));
    }


    /**
     * 分享网络图片
     *
     * @param url
     * @param activity
     */
    public static void share(String url, Activity activity) {
        Bitmap bp = returnBitMap(url);
        Bitmap bpend = compressImage(bp);
        bp.recycle();
        File file = bitMap2File(bpend);
        bpend.recycle();
        if (file != null && file.exists() && file.isFile()) {//由文件得到uri
            Uri imageUri;//= Uri.fromFile(file);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(activity, "com.journey.org.fileProvider", file);
            } else {
                imageUri = Uri.fromFile(file);
            }
            KLog.e("==========imageUri：" + imageUri.getPath());
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
            shareIntent.setType("image/*");
            activity.startActivity(Intent.createChooser(shareIntent, "分享图片"));
        }
    }

    private static Bitmap bitmap;

    private static Bitmap returnBitMap(final String url) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL imageurl = null;
                try {
                    imageurl = new URL(url);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                try {
                    HttpURLConnection conn = (HttpURLConnection) imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        return bitmap;
    }


    /**
     * 得到Bitmap
     *
     * @param headerView
     * @return
     */
    private static Bitmap getBitmapByView(View headerView) {
        int h = headerView.getHeight();
        Bitmap bitmap = Bitmap.createBitmap(headerView.getWidth(), h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        headerView.draw(canvas);
        return bitmap;
    }

    /**
     * 压缩图片
     *
     * @param image
     * @return
     */
    private static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 10, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 400) {  //循环判断如果压缩后图片是否大于400kb,大于继续压缩（这里可以设置大些）
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }

    /**
     * 将bitmap 转换为文件
     *
     * @param bitmap
     * @return
     */
    private static File bitMap2File(Bitmap bitmap) {
        String path = "";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            path = Environment.getExternalStorageDirectory() + File.separator;//保存到sd根目录下
        }
        KLog.e("=====path:" + path);
        //        File f = new File(path, System.currentTimeMillis() + ".jpg");
        File f = new File(path, "share" + ".jpg");
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            bitmap.recycle();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return f;
        }
    }

}
