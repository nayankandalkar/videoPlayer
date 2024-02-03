package com.example.maruti38;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.carousel.CarouselLayoutManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements videoRvAdapter.videoClickInterface {

    RecyclerView recyclerView;

    videoRvAdapter videoRvAdapter;
    ArrayList<videoRvModel> arrayList;
    int STORAGE_PERMISSION=101;

    ProgressBar progressBar;
 ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      dialog=new ProgressDialog(MainActivity.this);
      dialog.show();

        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.rv);
         videoRvAdapter =new videoRvAdapter(arrayList, this,this::onvideoClick );
         recyclerView.setLayoutManager(new GridLayoutManager(this,2));
         recyclerView.setAdapter(videoRvAdapter);

         if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED){
             ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},STORAGE_PERMISSION );
         }else {


//             BG bg=new BG();
//             bg.execute();
             getVideo();
         }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode== STORAGE_PERMISSION){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "PERMISSION GRANTED SUCESSFULLY", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "PERMISSION REJECTED ", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public  void  getVideo(){
        ContentResolver contentResolver=getContentResolver();
        Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor=contentResolver.query(uri,null, null, null, null);
        if (cursor != null && cursor.moveToFirst()){
            do {
                String videoTitle=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE));
                String videoPath=cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA));
                Bitmap videoThumbNail= ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MICRO_KIND);

                arrayList.add(new videoRvModel(videoTitle,videoPath,videoThumbNail));

            }while (cursor.moveToNext());
        }

      //  progressBar.setVisibility(View.GONE);
        videoRvAdapter.notifyDataSetChanged();
        dialog.dismiss();
      //  recyclerView.setVisibility(View.VISIBLE);

    }

    @Override
    public void onvideoClick(int position) {

       // Intent intent=new Intent();

        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        intent.putExtra("videoname", arrayList.get(position).videoNmae);
        intent.putExtra("videoPath",arrayList.get(position).videoPath);
      //  intent.putExtra("video")
        startActivity(intent);
    }


//    public  class  BG extends AsyncTask<Void,Void,Void>{
//        @Override
//        protected Void doInBackground(Void... voids) {
//            getVideo();
//            return null;
//        }



   // }
}