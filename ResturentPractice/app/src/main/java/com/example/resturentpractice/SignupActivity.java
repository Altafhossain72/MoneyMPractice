package com.example.resturentpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.resturentpractice.model.UploadFileResponse;
import com.example.resturentpractice.model.User;
import com.example.resturentpractice.retrofit.RetrofitConfig;
import com.example.resturentpractice.service.UserService;
import com.example.resturentpractice.util.ImageManager;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Intent i;
    CircleImageView cimageview;
    EditText eName,eEmail,ePassword,ePhone;
    Button imagebtn;
    Context mtx;
    UserService userService;
    Uri imageUri;
    ImageManager imageManager;
    String imgName;
    private static final int PICK_IMAGE = 100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        imageManager = new ImageManager(this);
        cimageview = (CircleImageView)findViewById(R.id.profile_image);
        imagebtn  = (Button) findViewById(R.id.loadImgbtn);
        mtx = this;
        userService = RetrofitConfig.createService(UserService.class);
        eName = findViewById(R.id.userName);
        eEmail = findViewById(R.id.userEmail);
        ePassword = findViewById(R.id.userpass);
        ePhone = findViewById(R.id.userPhone);
      imagebtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              openGallery();
          }
      });
      imageManager.checkPermission();
      //  imageManager.showImage("http://192.168.2.130:8080/downloadFile/"+imageUri, cimageview);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            cimageview.setImageURI(imageUri);
        }
    }



    public void navigateLogin(View view){
        i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }



    public void add(View view){
        User user = new User();
        String name = eName.getText().toString();
        String email = eEmail.getText().toString();
        String password = ePassword.getText().toString();
        String phone = ePhone.getText().toString();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setPhone(phone);
        if(imageUri != null){
            String imgName = imageManager.uploadImage(imageUri).getFileName();
            System.out.println("=================="+imgName);
            user.setImage(imgName);
        }else{
            user.setImage("default.jpeg");
        }
        Call<User> res = userService.add(user);
        res.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User user =  response.body();
                startActivity(new Intent(mtx, LoginActivity.class));
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }




    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }


}