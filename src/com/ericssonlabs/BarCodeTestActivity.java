package com.ericssonlabs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.jar.Attributes.Name;

import org.apache.http.util.EncodingUtils;

import com.google.zxing.WriterException;
import com.zxing.activity.CaptureActivity;
import com.zxing.encoding.EncodingHandler;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BarCodeTestActivity extends Activity {
    /** Called when the activity is first created. */
	private TextView result_3622;
	private TextView result_605;
	private TextView result_16;
	private String seedType = "";
	//private ImageView qrImgImageView;
	private EditText et_ord;
	private EditText et_name;
	private String name = "";
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        result_3622 = (TextView) this.findViewById(R.id.tv_3622);
        result_605 = (TextView) this.findViewById(R.id.tv_605);
        result_16 = (TextView) this.findViewById(R.id.tv_16);
        et_ord = (EditText) this.findViewById(R.id.et_ord);
        et_name = (EditText) this.findViewById(R.id.et_name);
        
       
        
        Button scan_3622 = (Button) this.findViewById(R.id.btn_3622);
        scan_3622.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seedType = "登海3622:  ";
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
       
        Button scan_605 = (Button) this.findViewById(R.id.btn_605);
        scan_605.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seedType = "登海605:  ";
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
        Button scan_16 = (Button) this.findViewById(R.id.btn_16);
        scan_16.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seedType = "蠡玉16:  ";
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
        Button scan_ord = (Button) this.findViewById(R.id.btn_ord);
        scan_ord.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seedType = et_ord.getText().toString() + "：";
				//打开扫描界面扫描条形码或二维码
				Intent openCameraIntent = new Intent(BarCodeTestActivity.this,CaptureActivity.class);
				startActivityForResult(openCameraIntent, 0);
			}
		});
        
        Button scan_note = (Button) this.findViewById(R.id.btn_note);
        scan_note.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				seedType =  "备注: " + et_ord.getText().toString();
				String filename = "qyj.txt";
				try {
					writeFile(filename, seedType);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
       
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			if(seedType.equals("登海3622:  ")){
				result_3622.setText(scanResult);
			}else if(seedType.equals("登海605:  ")){
				result_605.setText(scanResult);
			}else if(seedType.equals("蠡玉16:  ")){
				result_16.setText(scanResult);
			}
			
			name = "(" + et_name.getText().toString() + ")";
			scanResult = name + seedType + scanResult;
			
			try {
				String filename = "qyj.txt";
				writeFile(filename, scanResult);
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
	}
	
	
	//写数据  
	public void writeFile(String fileName,String str) throws IOException{   
		File file = new File (Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhongzi");         
		 if(!file.exists()){          
		      file.mkdirs();
		   }
		 String ss = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zhongzi/"+fileName;
		 File dir = new File(ss);
			if (!dir.exists()) {
				  try {
					  //在指定的文件夹中创建文件
					  dir.createNewFile();
				} catch (Exception e) {
				}
			}
			FileWriter fw = null;
			BufferedWriter bw = null;
			
			try {
				
				fw = new FileWriter(ss, true);//
				// 创建FileWriter对象，用来写入字符流
				bw = new BufferedWriter(fw); // 将缓冲对文件的输出
				String myreadline =  str;
				
				bw.write(myreadline + "\n"); // 写入文件
				bw.newLine();
				bw.flush(); // 刷新该流的缓冲
				bw.close();
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				try {
					bw.close();
					fw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
				}
			}
		
	}   
	
	//读数据  
	public String readFile(String fileName) throws IOException{   
	  String res="";   
	  try{   
	         FileInputStream fin = openFileInput(fileName);   
	         int length = fin.available();   
	         byte [] buffer = new byte[length];   
	         fin.read(buffer);       
	         res = EncodingUtils.getString(buffer, "UTF-8");   
	         fin.close();       
	     }   
	     catch(Exception e){   
	         e.printStackTrace();   
	     }   
	     return res;   
	  
	}
	
	
}