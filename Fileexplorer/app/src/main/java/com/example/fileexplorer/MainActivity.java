package com.example.fileexplorer;
  
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity; 
import android.content.Intent;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends Activity {
 
	private static final int REQUEST_SOURCE = 1;
	private static final int REQUEST_DESTINATION = 2;

	String curFileName,file_name;
	Button select_source,Select_destination,select_compress;
	EditText edittext_source,edittext_destination;
	ImageView select_image;
	Uri myUri;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fileexplorer);
		edittext_source = (EditText)findViewById(R.id.editText);
		edittext_destination = (EditText)findViewById(R.id.editText_destination);
		select_image= (ImageView) findViewById(R.id.select_image);
		select_source= (Button) findViewById(R.id.select_source);
		Select_destination= (Button) findViewById(R.id.select_destination);
		select_compress= (Button) findViewById(R.id.select_compress);
		select_source.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getSource(view);
			}
		});
		Select_destination.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getDestination(view);
			}
		});
		select_compress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
			compress_bitmap(myUri);
			}
		});
    }
 
    public void getSource(View view){
    	Intent intent1 = new Intent(this, Explorer.class);
		intent1.putExtra("REQUEST_CODE", REQUEST_SOURCE);
        startActivityForResult(intent1, REQUEST_SOURCE);
    }
	public void getDestination(View view){
    	Intent intent1 = new Intent(this, Explorer.class);
		intent1.putExtra("REQUEST_CODE",REQUEST_DESTINATION);
        startActivityForResult(intent1,REQUEST_DESTINATION);
    }
 // Listen for results.
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        // See which child activity is calling us back.
    	if (requestCode == REQUEST_SOURCE){
    		if (resultCode == RESULT_OK) {
    			curFileName = data.getStringExtra("GetFilePath");
				file_name = data.getStringExtra("GetFileName");
				edittext_source.setText(curFileName);
				myUri = Uri.parse(curFileName);
    		}
    	 } else if (requestCode == REQUEST_DESTINATION){
			if (resultCode == RESULT_OK) {
				curFileName = data.getStringExtra("GetFilePath");
				edittext_destination.setText(curFileName);
			}
		}
    }
	private void compress_bitmap(Uri bitmap_uri){
		try {
			Bitmap mBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), bitmap_uri);
			createDirectoryAndSaveFile(mBitmap,file_name,bitmap_uri);
		} catch (IOException e) {
			Toast.makeText(getApplicationContext(),"Please Check The Input Again",Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
		int width = bm.getWidth();
		int height = bm.getHeight();
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,matrix, false);

		return resizedBitmap;
	}

	private void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName,Uri save_destination) {

		File file = new File(new File(save_destination.getPath()), fileName);
		if (file.exists()) {
			file.delete();
		}
		try {
			FileOutputStream out = new FileOutputStream(file);
			imageToSave.compress(Bitmap.CompressFormat.JPEG, 100, out);
			out.flush();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
