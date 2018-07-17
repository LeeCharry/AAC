package com.shyouhan.aac.google.zxing.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tulib.util.utils.DeviceUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.RGBLuminanceSource;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.common.HybridBinarizer;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Vector;

import com.shyouhan.aac.ProcessType;
import com.shyouhan.aac.R;
import com.shyouhan.aac.activity.DomesticTransferActivity;
import com.shyouhan.aac.activity.MainActivity;
import com.shyouhan.aac.constant.AppConstant;
import com.shyouhan.aac.google.zxing.camera.CameraManager;
import com.shyouhan.aac.google.zxing.decoding.CaptureActivityHandler;
import com.shyouhan.aac.google.zxing.decoding.InactivityTimer;
import com.shyouhan.aac.google.zxing.view.ViewfinderView;
import com.shyouhan.aac.widget.LogUtils;
import com.shyouhan.aac.widget.ToastUtils;


/**
 * Initial the camera
 *
 * @author Ryan.Tang
 */
public class CaptureActivity extends AppCompatActivity implements Callback {

    private static final int REQUEST_CODE_SCAN_GALLERY = 100;
    private static final int PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE = 1001;
    private static final int REQUEST_CODE_GET_PIC_URI = 1002;

    private CaptureActivityHandler handler;
    private ViewfinderView viewfinderView;
    private ImageView back;
    private boolean hasSurface;
    private Vector<BarcodeFormat> decodeFormats;
    private String characterSet;
    private InactivityTimer inactivityTimer;
    private MediaPlayer mediaPlayer;
    private boolean playBeep;
    private static final float BEEP_VOLUME = 0.10f;
    private boolean vibrate;
    private ProgressDialog mProgress;
    private String photo_path;
    private Bitmap scanBitmap;
    //	private Button cancelScanButton;
    public static final int RESULT_CODE_QR_SCAN = 0xA1;
    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";
    private boolean isOpen = false;
    private int processType = -1;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner);
        //ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
        CameraManager.init(getApplication());

        setImmersiveStatus(this);
        viewfinderView = findViewById(R.id.viewfinder_content);
        back = findViewById(R.id.scanner_toolbar_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
        hasSurface = false;
        inactivityTimer = new InactivityTimer(this);

        //获取从上一界面传来的数据
        processType = getIntent().getIntExtra(AppConstant.PROCESS_TYPE, -1);


        /**
         * 开关灯
         */
        findViewById(R.id.iv_flashlight).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO 开灯
                if (!isOpen) {
                    CameraManager.get().enableFlash();
                    isOpen = true;
                } else {  // 关灯
                    CameraManager.get().disableFlash();
                    isOpen = false;
                }
            }
        });
        /**
         * 从相册选择
         */
        findViewById(R.id.iv_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先申请权限
//                goPicture();
                int checked = ContextCompat.checkSelfPermission(CaptureActivity.this
                        , Manifest.permission.WRITE_EXTERNAL_STORAGE);
                if (checked == PackageManager.PERMISSION_GRANTED) {
                    goPicture();
                } else {
                    ActivityCompat.requestPermissions(CaptureActivity.this
                            , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE);
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_WRITE_EXTERNAL_STORAGE) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ToastUtils.showShort(R.string.permission_request_failed);
                return;
            }
            goPicture();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 选择相册
     */
    private void goPicture() {
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        Intent intent = new Intent();
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
        startActivityForResult(intent, REQUEST_CODE_SCAN_GALLERY);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.scanner_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_SCAN_GALLERY:
                    //  第三种
                    Uri sourceUri = data.getData();
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        sourceUri = FileProvider.getUriForFile(this, "com.shyouhan.aac", new File(sourceUri.getPath()));
//                    }
                    try {
                        // 下面这句话可以通过URi获取到文件的bitmap
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), sourceUri);
                        // 在这里我用到的 getSmallerBitmap 非常重要，下面就要说到
                        bitmap = getSmallerBitmap(bitmap);

                        // 获取bitmap的宽高，像素矩阵
                        int width = bitmap.getWidth();
                        int height = bitmap.getHeight();
                        int[] pixels = new int[width * height];
                        bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

                        // 最新的库中，RGBLuminanceSource 的构造器参数不只是bitmap了
                        RGBLuminanceSource source = new RGBLuminanceSource(width, height, pixels);
                        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(source));
                        Reader reader = new MultiFormatReader();
                        Result result = null;

                        // 尝试解析此bitmap，！！注意！！ 这个部分一定写到外层的try之中，因为只有在bitmap获取到之后才能解析。写外部可能会有异步的问题。（开始解析时bitmap为空）
                        try {
                            result = reader.decode(binaryBitmap);
                            if (null == result) {
                                showToastAtCenter(getString(R.string.shibie_failed));
                                return;
                            }
                            String s = result.getText().toString();
                            if (s.contains("http://") || s.contains("https://")) {
                                showToastAtCenter(getString(R.string.shibie_failed));
                                return;
                            }
                            LogUtils.a(AppConstant.TAG, result.getText());
                            Intent intent;
                            if (processType == ProcessType.REQUEST_CODE_TRANSFER) {
                                //中转国内快递
                                intent = new Intent(this, DomesticTransferActivity.class);
                                intent.putExtra(INTENT_EXTRA_KEY_QR_SCAN, result.getText());
                                startActivity(intent);
                            } else {
                                intent = new Intent();
                                intent.putExtra(CaptureActivity.INTENT_EXTRA_KEY_QR_SCAN, result.getText());
                            }
                            this.setResult(RESULT_CODE_QR_SCAN, intent);
                            finish();
                        } catch (NotFoundException e) {
                            Log.i(AppConstant.TAG, "onActivityResult: notFind");
                            showToastAtCenter(getString(R.string.shibie_failed));
                            e.printStackTrace();
                        } catch (ChecksumException e) {
                            e.printStackTrace();
                        } catch (FormatException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }


    protected void showToastAtCenter(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();

    }

    public static Bitmap getSmallerBitmap(Bitmap bitmap) {
        int size = bitmap.getWidth() * bitmap.getHeight() / 160000;
        if (size <= 1) return bitmap; // 如果小于
        else {
            Matrix matrix = new Matrix();
            matrix.postScale((float) (1 / Math.sqrt(size)), (float) (1 / Math.sqrt(size)));
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SurfaceView surfaceView = findViewById(R.id.scanner_view);
        SurfaceHolder surfaceHolder = surfaceView.getHolder();
        if (hasSurface) {
            initCamera(surfaceHolder);
        } else {
            surfaceHolder.addCallback(this);
            surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }
        decodeFormats = null;
        characterSet = null;

        if (isOpen) {
            CameraManager.get().disableFlash();
            isOpen = false;
        }

        playBeep = true;
        AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
            playBeep = false;
        }
        initBeepSound();
        vibrate = true;

        //quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (handler != null) {
            handler.quitSynchronously();
            handler = null;
        }
        CameraManager.get().closeDriver();
    }

//
//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onBack(Object msg) {
//        Resources resources = getResources();
//        String language = SPUtils.getInstance().getString(AppConstant.LANGUAGE_TYPE, getlocaLanguage(resources));
//
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        Configuration config = resources.getConfiguration();
//        if (language == AppConstant.zh_TW) {
//            //繁体字
//            config.locale = Locale.TRADITIONAL_CHINESE;
//        } else {
//            //简体
//            config.locale = Locale.SIMPLIFIED_CHINESE;
//        }
//        resources.updateConfiguration(config, dm);
//        setTextString();
//    }

    private void setTextString() {

    }

    /**
     * 获取系统语言环境
     *
     * @param resources
     */
    protected String getlocaLanguage(Resources resources) {
        Locale locale = resources.getConfiguration().locale;
        return locale.getLanguage();
    }

    @Override
    protected void onDestroy() {
        inactivityTimer.shutdown();
        if (isOpen) {
            CameraManager.get().disableFlash();
            isOpen = false;
        }

        super.onDestroy();
    }

    /**
     * Handler scan result
     *
     * @param result
     * @param barcode
     */
    public void handleDecode(Result result, Bitmap barcode) {
        inactivityTimer.onActivity();
        playBeepSoundAndVibrate();
        String resultString = result.getText();
        //FIXME
        if (resultString.contains("http://") || resultString.contains("https://")) {
            showToastAtCenter(getString(R.string.shibie_failed));
            return;
        } else if (TextUtils.isEmpty(resultString)) {
            showToastAtCenter(getString(R.string.shibie_failed));
        } else if (processType == ProcessType.REQUEST_CODE_TRANSFER) {
            //中转国内快递
            Intent intent = new Intent(this, DomesticTransferActivity.class);
            intent.putExtra(INTENT_EXTRA_KEY_QR_SCAN, resultString);
            startActivity(intent);
        } else {
            Intent resultIntent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString(INTENT_EXTRA_KEY_QR_SCAN, resultString);
            System.out.println("sssssssssssssssss scan 0 = " + resultString);
            // 不能使用Intent传递大于40kb的bitmap，可以使用一个单例对象存储这个bitmap
            resultIntent.putExtras(bundle);
            this.setResult(RESULT_CODE_QR_SCAN, resultIntent);
        }
        CaptureActivity.this.finish();
    }

    private void initCamera(SurfaceHolder surfaceHolder) {
        try {
            CameraManager.get().openDriver(surfaceHolder);
        } catch (IOException ioe) {
            return;
        } catch (RuntimeException e) {
            return;
        }
        if (handler == null) {
            handler = new CaptureActivityHandler(this, decodeFormats,
                    characterSet);
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (!hasSurface) {
            hasSurface = true;
            initCamera(holder);
        }

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        hasSurface = false;

    }

    /**
     * 沉浸式标题栏
     */
    public static void setImmersiveStatus(Activity context) {
        Window window = context.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
            if (context instanceof MainActivity) {
                window.setStatusBarColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                window.setStatusBarColor(context.getResources().getColor(R.color.dark_gray));
            }
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup systeiew = window.findViewById(android.R.id.content);
            View view = new View(context);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DeviceUtil.getStatusHeight(context));

            if (context instanceof MainActivity) {
                view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            } else {
                view.setBackgroundColor(context.getResources().getColor(R.color.dark_gray));
            }
//            view.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            systeiew.getChildAt(0).setFitsSystemWindows(true);
            systeiew.addView(view, 0, layoutParams);
        }
    }

    public ViewfinderView getViewfinderView() {
        return viewfinderView;
    }

    public Handler getHandler() {
        return handler;
    }

    public void drawViewfinder() {
        viewfinderView.drawViewfinder();

    }

    private void initBeepSound() {
        if (playBeep && mediaPlayer == null) {
            // The volume on STREAM_SYSTEM is not adjustable, and users found it
            // too loud,
            // so we now play on the music stream.
            setVolumeControlStream(AudioManager.STREAM_MUSIC);
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnCompletionListener(beepListener);

            AssetFileDescriptor file = getResources().openRawResourceFd(
                    R.raw.beep);
            try {
                mediaPlayer.setDataSource(file.getFileDescriptor(),
                        file.getStartOffset(), file.getLength());
                file.close();
                mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
                mediaPlayer.prepare();
            } catch (IOException e) {
                mediaPlayer = null;
            }
        }
    }

    private static final long VIBRATE_DURATION = 200L;

    private void playBeepSoundAndVibrate() {
        if (playBeep && mediaPlayer != null) {
            mediaPlayer.start();
        }
        if (vibrate) {
            Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
            vibrator.vibrate(VIBRATE_DURATION);
        }
    }

    /**
     * When the beep has finished playing, rewind to queue up another one.
     */
    private final OnCompletionListener beepListener = new OnCompletionListener() {
        public void onCompletion(MediaPlayer mediaPlayer) {
            mediaPlayer.seekTo(0);
        }
    };

}