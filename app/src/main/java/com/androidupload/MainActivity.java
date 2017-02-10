package com.androidupload;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.utils.FormFile;
import com.utils.SocketHttpRequester;

public class MainActivity extends Activity
{
	private File file;
	private Handler handler;
	private static final String TAG = "MainActivity";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Log.i(TAG, "onCreate");

		file = new File(Environment.getExternalStorageDirectory(), "1.xml");
		
		Log.i(TAG, "is file exists: " + file.exists());
		

		new Thread()
		{
			public void run()
			{
				uploadFile(file);
			};
		}.start();
	}

	public void uploadFile(File file)
	{
		Log.i(TAG, "upload start");
		
		try
		{
			String requestUrl = "http://10.76.9.189:8080/upload/upload/execute.do";
			
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", "wangyuhang");
			params.put("pwd", "Steven");
			params.put("age", "21");
			params.put("fileName", file.getName());
			
			FormFile formfile = new FormFile(file.getName(), file, "file", "application/octet-stream");
			
			SocketHttpRequester.post(requestUrl, params, formfile);
			
			Log.i(TAG, "upload success");
		}
		catch (Exception e)
		{
			Log.e(TAG, e.toString());

			e.printStackTrace();
		}
		Log.i(TAG, "upload end");
	}
}
