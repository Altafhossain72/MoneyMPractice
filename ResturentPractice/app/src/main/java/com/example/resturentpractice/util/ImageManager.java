package com.example.resturentpractice.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.widget.ImageView;


import com.example.resturentpractice.model.UploadFileResponse;
import com.example.resturentpractice.retrofit.RetrofitConfig;
import com.example.resturentpractice.service.ImageUploadService;
import com.squareup.picasso.Picasso;

import java.io.File;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ImageManager {
    public String s;
    Activity activity;
    ImageUploadService imageUploadService;
    private static final int PICK_IMAGE = 100;
    UploadFileResponse uploadFileResponse = new UploadFileResponse();

    public ImageManager(Activity activity) {
        this.activity = activity;
        imageUploadService = RetrofitConfig.createService(ImageUploadService.class);

    }

    public void checkPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            // Verify permissions
            for (String str : permissions) {
                if (activity.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //request for access
                    activity.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return;
                } else {
                    // Here is the logic of their own to open after permission to operate
                }
            }
        }
    }

//not working
//    public Uri pickImage() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
//        activity.startActivityForResult(intent, PICK_IMAGE);
//        return intent.getData();
//    }

    public UploadFileResponse uploadImage(Uri uri){
        File file = new File(getPath(uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part imageFile = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        Call<UploadFileResponse> res = imageUploadService.imageUpload(imageFile);
        res.enqueue(new Callback<UploadFileResponse>() {
            @Override
            public void onResponse(Call<UploadFileResponse> call, Response<UploadFileResponse> response) {
                uploadFileResponse = response.body();
            }

            @Override
            public void onFailure(Call<UploadFileResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
        return uploadFileResponse;
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        //added for cursor bound exception
        if( cursor != null && cursor.moveToFirst() ){
            s=cursor.getString(column_index);
            cursor.close();
        }

        return s;
    }

    public void showImage(Uri imageUrl, ImageView imageView){
        Picasso.get().load(imageUrl).into(imageView);
    }


}
