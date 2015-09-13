package com.integralsmusic.jackodiamond.travelphotos;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    
    Button cam,open,save ;
    ImageView iv ;
    Intent cameraIntent;
    Bitmap bmp;
    String patha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        cam = (Button)findViewById(R.id.button);
        save =(Button)findViewById(R.id.button2);
        open =(Button)findViewById(R.id.button3);
        iv =(ImageView)findViewById(R.id.imageView);
    }
    
    
    
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                cameraIntent = new Intent(
                                          android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,0);
                break;
            case R.id.button2:
                patha = saveToInternalSorage(bmp);
                break;
            case R.id.button3:
                
                try {
                    File f=new File(patha, "profile.jpg");
                    Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                    ImageView img=(ImageView)findViewById(R.id.imageView);
                    img.setImageBitmap(b);
                }
                catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
                
                break;
        }
    }
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            bmp = (Bitmap) extras.get("data");
            iv.setImageBitmap(bmp);
        }
    }
    
    private String saveToInternalSorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");
        
        FileOutputStream fos = null;
        try {
            
            fos = new FileOutputStream(mypath);
            
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }
    
}
