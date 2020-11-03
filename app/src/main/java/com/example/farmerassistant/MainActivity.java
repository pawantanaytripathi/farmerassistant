package com.example.farmerassistant;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.Interpreter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISION_CODE = 1000 ;
    private static final int IMAGE_CAPUTER_CODE = 1001;
    Button button;
    Interpreter tflite;
    ImageView img;
    Uri image_url;
    Bitmap ubit;
    TextView output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.image_view);
        button = findViewById(R.id.capture_img_btn);
        output = findViewById(R.id.output);
        try {
            tflite = new Interpreter(loadModelFile());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED
                    || checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
                        String[] permision = {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permision,PERMISION_CODE);
                    }
                    else{
                        opencamera();
                    }
                }
                else{
                    opencamera();
                }
            }
        });
    }

    private MappedByteBuffer loadModelFile() throws IOException{
        AssetFileDescriptor fileDescriptor=this.getAssets().openFd("tfmodel.tflite");
        FileInputStream inputStream=new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel=inputStream.getChannel();
        long startOffset=fileDescriptor.getStartOffset();
        long declareLength=fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY,startOffset,declareLength);
    }
    
    private void opencamera() {
        ContentValues value = new ContentValues();
        value.put(MediaStore.Images.Media.TITLE,"New Picture");
        value.put(MediaStore.Images.Media.DESCRIPTION,"from the camera");
        image_url = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,value);
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraintent.putExtra(MediaStore.EXTRA_OUTPUT,image_url);
        startActivityForResult(cameraintent,IMAGE_CAPUTER_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode){
            case PERMISION_CODE:{
                if(grantResults.length > 0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                    opencamera();
                }
                else{
                    Toast.makeText(this,"Permision denied..",Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {



            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(),image_url);
                try {
                    ubit = ImageDecoder.decodeBitmap(source);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    ubit = MediaStore.Images.Media.getBitmap(this.getContentResolver(), image_url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            Bitmap resized = Bitmap. createScaledBitmap ( ubit , 150 , 150 , false ) ;
            int X = resized.getWidth();
            int Y = resized.getHeight();
            Log.d("adohgaya","hmm ok" + String.valueOf(X));
            Log.d("adohgaya","hmm ok" + String.valueOf(Y));

            img.setImageBitmap(resized);
        }


    }
}