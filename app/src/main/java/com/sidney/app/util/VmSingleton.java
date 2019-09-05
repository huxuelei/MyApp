package com.sidney.app.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.sidney.app.R;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * author:hxl
 * e-mail:huxl@bjhzwq.com
 * time:2019/8/21 10:36
 * desc:图片水印 https://github.com/huangyz0918/AndroidWM
 * version:1.0
 */
public class VmSingleton {

    private volatile static VmSingleton singleton;

    private Context mContext;

    private String mImgSrc;//图片原路径

    private VmSingleton(Context cxt) {
        this.mContext = cxt;
    }

    public static VmSingleton getSingleton(Context cxt) {
        if (singleton == null) {
            synchronized (VmSingleton.class) {
                if (singleton == null) {
                    singleton = new VmSingleton(cxt);
                }
            }
        }
        return singleton;
    }

    /**
     * 生成文字水印 3行文字
     *
     * @appNo 工单编号
     * @name 姓名+账号
     * @time 工单时间
     * @src 图片原路径
     */
    public void watermarkText(String appNo, String name, String time, ImageView img, String src) {
        try {
            this.mImgSrc = src;
            WatermarkText watermarkText1 = new WatermarkText(appNo)
                    .setPositionX(0.50)
                    .setPositionY(0.70)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(400)
                    //.setRotation(10)
                    .setTextSize(14);

            WatermarkText watermarkText2 = new WatermarkText(name)
                    .setPositionX(0.50)
                    .setPositionY(0.80)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(400)
                    //.setRotation(10)
                    .setTextSize(14);

            WatermarkText watermarkText3 = new WatermarkText(time)
                    .setPositionX(0.50)
                    .setPositionY(0.90)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(400)
                    //.setRotation(10)
                    .setTextSize(14);

            List<WatermarkText> watermarkTexts = new ArrayList<>();
            watermarkTexts.add(watermarkText1);
            watermarkTexts.add(watermarkText2);
            watermarkTexts.add(watermarkText3);

            ImageView img1 = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 400);
            img1.setLayoutParams(params);

            img1.setImageResource(R.drawable.logo);
            WatermarkBuilder.create(mContext, img1)
                    .setTileMode(false)
                    .loadWatermarkTexts(watermarkTexts)
                    .getWatermark()
                    .setToImageView(img1);

            getWatermarkSrc(img1, "01", "1", "1", "1", "1", "1", "1");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文字水印 3行文字
     *
     * @appNo 工单编号
     * @name 姓名+账号
     * @time 工单时间
     * @x1 坐标(左上角起) 0-1
     * @y1 坐标(左上角起) 0-1
     * @textAlpha 文字透明度
     * @textSize 文字大小
     */
    public void watermarkText(String appNo, String name, String time, ImageView img, String src,
                              double x1, double y1, double x2, double y2, double x3, double y3,
                              int textSize, int textAlpha) {
        try {
            this.mImgSrc = src;
            WatermarkText watermarkText1 = new WatermarkText(appNo)
                    .setPositionX(x1)
                    .setPositionY(y1)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(textAlpha)
                    //.setRotation(10)
                    .setTextSize(textSize);

            WatermarkText watermarkText2 = new WatermarkText(name)
                    .setPositionX(x2)
                    .setPositionY(y2)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(textAlpha)
                    //.setRotation(10)
                    .setTextSize(textSize);

            WatermarkText watermarkText3 = new WatermarkText(time)
                    .setPositionX(x3)
                    .setPositionY(y3)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(textAlpha)
                    //.setRotation(10)
                    .setTextSize(textSize);

            List<WatermarkText> watermarkTexts = new ArrayList<>();
            watermarkTexts.add(watermarkText1);
            watermarkTexts.add(watermarkText2);
            watermarkTexts.add(watermarkText3);

            WatermarkBuilder.create(mContext, img)
                    .setTileMode(false)
                    .loadWatermarkTexts(watermarkTexts)
                    .getWatermark()
                    .setToImageView(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成文字水印 1行文字
     *
     * @str 生成水印的文字
     * @x 坐标(左上角起) 0-1
     * @y 坐标(左上角起) 0-1
     * @textAlpha 文字透明度
     * @textSize 文字大小
     */
    public void watermarkText(String str, ImageView img, String src, double x, double y, int textSize, int textAlpha) {
        try {
            this.mImgSrc = src;
            WatermarkText watermarkText = new WatermarkText(str)
                    .setPositionX(x)
                    .setPositionY(y)
                    .setTextColor(Color.WHITE)
                    .setTextFont(R.font.champagne)
                    .setTextShadow(0.3f, 3, 3, Color.GRAY)
                    .setTextAlpha(textAlpha)
                    //.setRotation(10)
                    .setTextSize(textSize);

            WatermarkBuilder.create(mContext, img)
                    .setTileMode(false)
                    .loadWatermarkText(watermarkText)
                    .getWatermark()
                    .setToImageView(img);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成图片水印
     *
     * @drawable 生成水印的文字
     * @img 水印图片控件
     */
    public void WatermarkImage(ImageView img, int drawable) {
        Bitmap watermarkBitmap = BitmapFactory.decodeResource(mContext.getResources(),
                drawable);
        WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                .setImageAlpha(80)
                .setPositionX(Math.random())
                .setPositionY(Math.random())
                .setRotation(15)
                .setSize(0.1);

        WatermarkBuilder.create(mContext, img)
                .loadWatermarkImage(watermarkImage)
                .setTileMode(true)
                .getWatermark()
                .setToImageView(img);
    }

    /**
     * 获取图片水印路径
     *
     * @img 生成的图片水印
     * @type 01工单类 02非工单类
     * @userName 账号
     * @orderType 工单类型编号
     * @orderNo 工单编号
     * @propertyNo 资产编号
     * @time 时间yyyy-MM-dd-HH-mm-ss
     * @functionCode 功能名称
     */
    public String getWatermarkSrc(ImageView img, String type, String userName, String orderType, String orderNo,
                                  String propertyNo, String time, String functionCode) {
        try {
            Bitmap bitmap = WatermarkBuilder
                    .create(mContext, img)
                    .getWatermark()
                    .getOutputImage();

            //生成路径
            String root = Environment.getExternalStorageDirectory().getAbsolutePath();
            String dirName = "watermark";
            File appDir = new File(root, dirName);
            if (!appDir.exists()) {
                appDir.mkdirs();
            }

            //文件名生成
            String fileName = null;
            if (TextUtils.equals("01", type)) {
                fileName = type + "_" + userName + "_" + orderType + "_" + orderNo + "_" + propertyNo + "_" + time + ".jpg";
            }
            if (TextUtils.equals("02", type)) {
                fileName = type + "_" + userName + "_" + functionCode + "_" + time + ".jpg";
            }
            //获取文件
            File file = new File(appDir, fileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                fos.flush();
                //通知系统相册刷新
                mContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.fromFile(new File(file.getPath()))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 返回原图片路径
     */
    public String getImgSrc() {
        return mImgSrc;
    }
}
