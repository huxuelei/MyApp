package com.sidney.app.ui.one.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sidney.app.R;
import com.sidney.app.util.VmSingleton;
import com.sidney.devlib.ui.BaseActivity;
import com.watermark.androidwm.WatermarkBuilder;
import com.watermark.androidwm.WatermarkDetector;
import com.watermark.androidwm.bean.WatermarkImage;
import com.watermark.androidwm.bean.WatermarkText;
import com.watermark.androidwm.listener.BuildFinishListener;
import com.watermark.androidwm.listener.DetectFinishListener;
import com.watermark.androidwm.task.DetectionReturnValue;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * author:hxl
 * e-mail:huxl@bjhzwq.com
 * time:2019/8/20 10:31
 * desc:图片水印
 * version:1.0
 */
public class VmActivity extends BaseActivity {

    private Button btnAddText;
    private Button btnAddImg;
    private Button btnAddInVisibleImage;
    private Button btnAddInvisibleText;
    private Button btnDetectImage;
    private Button btnDetectText;
    private Button btnClear;

    private ImageView backgroundView;
    private ImageView backgroundView2;
    private ImageView watermarkView;
    private Bitmap watermarkBitmap;

    //private EditText editText;

    private ProgressBar progressBar;

    String str = "01_WYY001";

    public static void launch(Context context) {
        Intent intent = new Intent(context, VmActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vm);
        initViews();
        initEvents();
    }

    private void initViews() {
        btnAddImg = findViewById(R.id.btn_add_image);
        btnAddText = findViewById(R.id.btn_add_text);
        btnAddInVisibleImage = findViewById(R.id.btn_add_invisible_image);
        btnAddInvisibleText = findViewById(R.id.btn_add_invisible_text);
        btnDetectImage = findViewById(R.id.btn_detect_image);
        btnDetectText = findViewById(R.id.btn_detect_text);
        btnClear = findViewById(R.id.btn_clear_watermark);

        //editText = findViewById(R.id.editText);
        backgroundView = findViewById(R.id.imageView);
        backgroundView2 = findViewById(R.id.imageView2);
        watermarkView = findViewById(R.id.imageView_watermark);

        progressBar = findViewById(R.id.progressBar);

        watermarkBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.test_watermark);

        watermarkView.setVisibility(View.GONE);

        //editText.setText(str);
    }

    private void initEvents() {
        // The sample method of adding a text watermark.
        btnAddText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VmSingleton.getSingleton(mActivity).watermarkText("工单编号：123456789012",
                        "张珊（zhang san san）","2019-08-21 10:21:22",backgroundView,"");
               /* WatermarkText watermarkText1 = new WatermarkText("工单编号：123456789012")
                        .setPositionX(0.50)
                        .setPositionY(0.70)
                        .setTextColor(Color.WHITE)
                        .setTextFont(R.font.champagne)
                        .setTextShadow(0.3f, 3, 3, Color.GRAY)
                        .setTextAlpha(500)
                        //.setRotation(10)
                        .setTextSize(14);

                WatermarkText watermarkText2 = new WatermarkText("张珊（zhang san san）")
                        .setPositionX(0.50)
                        .setPositionY(0.80)
                        .setTextColor(Color.WHITE)
                        .setTextFont(R.font.champagne)
                        .setTextShadow(0.3f, 3, 3, Color.GRAY)
                        .setTextAlpha(500)
                        //.setRotation(10)
                        .setTextSize(14);

                WatermarkText watermarkText3 = new WatermarkText("2019-08-21 10:21:22")
                        .setPositionX(0.50)
                        .setPositionY(0.90)
                        .setTextColor(Color.WHITE)
                        .setTextFont(R.font.champagne)
                        .setTextShadow(0.3f, 3, 3, Color.GRAY)
                        .setTextAlpha(500)
                        //.setRotation(10)
                        .setTextSize(14);

                List<WatermarkText> watermarkTexts=new ArrayList<>();
                watermarkTexts.add(watermarkText1);
                watermarkTexts.add(watermarkText2);
                watermarkTexts.add(watermarkText3);

                WatermarkBuilder.create(mActivity, backgroundView)
                        .setTileMode(false)
                        .loadWatermarkTexts(watermarkTexts)
                        .getWatermark()
                        .setToImageView(backgroundView);*/
            }
        });

        // The sample method of adding an image watermark.
        btnAddImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VmSingleton.getSingleton(mActivity).getWatermarkSrc(backgroundView);
//                Bitmap bitmap = WatermarkBuilder
//                        .create(mActivity, backgroundView)
//                        .getWatermark()
//                        .getOutputImage();
//                backgroundView2.setImageBitmap(bitmap);
                // Math.random()
               /* WatermarkImage watermarkImage = new WatermarkImage(watermarkBitmap)
                        .setImageAlpha(80)
                        .setPositionX(Math.random())
                        .setPositionY(Math.random())
                        .setRotation(15)
                        .setSize(0.1);

                WatermarkBuilder
                        .create(mActivity, backgroundView)
                        .loadWatermarkImage(watermarkImage)
                        .setTileMode(true)
                        .getWatermark()
                        .setToImageView(backgroundView);*/

            }
        });

        // The sample method of adding an invisible image watermark.
        btnAddInVisibleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                WatermarkBuilder
                        .create(mActivity, backgroundView)
                        .loadWatermarkImage(watermarkBitmap)
                        .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                            @Override
                            public void onSuccess(Bitmap object) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(mActivity,
                                        "Successfully create invisible watermark!", Toast.LENGTH_SHORT).show();
                                if (object != null) {
                                    backgroundView.setImageBitmap(object);
                                    // Save to local needs permission.
//                                BitmapUtils.saveAsPNG(object, "sdcard/DCIM/", true);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                progressBar.setVisibility(View.GONE);
                                Timber.e(message);
                            }
                        });
            }
        });

        // The sample method of adding an invisible text watermark.
        btnAddInvisibleText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                WatermarkText watermarkText = new WatermarkText(str);
                WatermarkBuilder
                        .create(mActivity, backgroundView)
                        .loadWatermarkText(watermarkText)
                        .setInvisibleWMListener(true, new BuildFinishListener<Bitmap>() {
                            @Override
                            public void onSuccess(Bitmap object) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(mActivity,
                                        "Successfully create invisible watermark!", Toast.LENGTH_SHORT).show();
                                if (object != null) {
                                    backgroundView.setImageBitmap(object);
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                progressBar.setVisibility(View.GONE);
                                Timber.e(message);
                            }
                        });
            }
        });

        // detect the text watermark.
        btnDetectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                WatermarkDetector.create(backgroundView, true)
                        .detect(new DetectFinishListener() {
                            @Override
                            public void onSuccess(DetectionReturnValue returnValue) {
                                progressBar.setVisibility(View.GONE);
                                if (returnValue != null) {
                                    Toast.makeText(mActivity, "Successfully detected invisible text: "
                                            + returnValue.getWatermarkString(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                progressBar.setVisibility(View.GONE);
                                Timber.e(message);
                            }
                        });
            }
        });

        // detect the image watermark.
        btnDetectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                WatermarkDetector.create(backgroundView, true)
                        .detect(new DetectFinishListener() {
                            @Override
                            public void onSuccess(DetectionReturnValue returnValue) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(mActivity,
                                        "Successfully detected invisible watermark!", Toast.LENGTH_SHORT).show();
                                if (returnValue != null) {
                                    watermarkView.setVisibility(View.VISIBLE);
                                    watermarkView.setImageBitmap(returnValue.getWatermarkBitmap());
                                }
                            }

                            @Override
                            public void onFailure(String message) {
                                progressBar.setVisibility(View.GONE);
                                Timber.e(message);
                            }
                        });
            }
        });

        // reload the background.
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Glide.with(mActivity).load(R.drawable.logo)
                        .into(backgroundView);
                watermarkView.setVisibility(View.GONE);
            }
        });

    }
}
