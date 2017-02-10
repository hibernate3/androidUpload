package com.example.xutils3download;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.xutils.common.Callback;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "myLog";

    @BindView(R.id.downloadBtn)
    Button downloadBtn;

    /**
     * 可取消的任务
     */
    private Callback.Cancelable cancelable;

    /**
     * 进度条对话框
     */
    private ProgressDialog progressDialog;

    private String pathApk = "http://down.72g.com/upload/app/201407/201407150923238621.apk";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initProgressDialog();
    }

    /*初始化对话框*/
    private void initProgressDialog() {
        //创建进度条对话框
        progressDialog = new ProgressDialog(this);

        //设置标题
        progressDialog.setTitle("下载文件");

        //设置信息
        progressDialog.setMessage("玩命下载中...");

        //设置显示的格式
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

        //设置按钮
        progressDialog.setButton(ProgressDialog.BUTTON_NEGATIVE, "暂停", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //点击取消正在下载的操作
                cancelable.cancel();
            }
        });
    }

    @OnClick(R.id.downloadBtn)
    public void onClick() {
        MainActivityPermissionsDispatcher.downloadFileWithCheck(this);
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void downloadFile() {
        String saveDir = "/sdcard/xutils/";
        String fileName = "1.apk";

        File file = new File(saveDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        //设置请求参数
        RequestParams params = new RequestParams(pathApk);
        params.setAutoResume(true);//设置是否在下载是自动断点续传
        params.setAutoRename(false);//设置是否根据头信息自动命名文件
        params.setSaveFilePath(saveDir + fileName);
        params.setExecutor(new PriorityExecutor(2, true));//自定义线程池,有效的值范围[1, 3], 设置为3时, 可能阻塞图片加载.
        params.setCancelFast(true);//是否可以被立即停止.

        //下面的回调都是在主线程中运行的,这里设置的带进度的回调
        cancelable = x.http().get(params, new Callback.ProgressCallback<File>() {

            @Override
            public void onSuccess(File result) {
                Log.i(TAG, "下载成功");
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.i(TAG, "onError: 失败, " + ex.toString());
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.i(TAG, "取消");
            }

            @Override
            public void onFinished() {
                Log.i(TAG, "完成,每次取消下载也会执行该方法");
                progressDialog.dismiss();
            }

            @Override
            public void onWaiting() {
                Log.i(TAG, "等待,在onStarted方法之前执行");
            }

            @Override
            public void onStarted() {
                Log.i(TAG, "开始下载");
                progressDialog.show();
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                if (isDownloading) {
                    progressDialog.setProgress((int) (current * 100 / total));
                }
            }
        });
    }

    @OnPermissionDenied(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showDenied() {
        Toast.makeText(this, "读取SD卡权限被禁止", Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void neverAsk() {
        Toast.makeText(this, "读取SD卡权限未被授予，请手动在设置中开启", Toast.LENGTH_SHORT).show();
    }

    @OnShowRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    void showRational(PermissionRequest request) {
        showRationaleDialog("需要开启SD卡读写权限", request);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    private void showRationaleDialog(String message, final PermissionRequest request) {
        new AlertDialog.Builder(this)
                .setPositiveButton("允许", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        request.cancel();
                    }
                })
                .setCancelable(false).setMessage(message).show();
    }
}
