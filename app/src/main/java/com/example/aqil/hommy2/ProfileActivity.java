package com.example.aqil.hommy2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aqil.hommy2.Entity.UserAccount;
import com.example.aqil.hommy2.Login.SignInActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        UserAccount userAccount = getIntent().getParcelableExtra("ProfileData");
        TextView mail = findViewById(R.id.label_mail);
        TextView phone = findViewById(R.id.label_phone);
        TextView name = findViewById(R.id.label_name);
        mail.setText(userAccount.getMail());
        name.setText(userAccount.getName());
        Log.d("TAG", "onCreate: " + userAccount.getPhotoUrl());
        de.hdodenhof.circleimageview.CircleImageView imageView = findViewById(R.id.avatar);
        Glide.with(getBaseContext()).load(userAccount.getPhotoUrl()).apply(new RequestOptions().centerCrop()).into(imageView);
        Button btnOut = findViewById(R.id.btn_out);
        btnOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(getBaseContext(), SignInActivity.class);
                intent.putExtra("REQ_OUT", true);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK
                );
                startActivity(intent);

            }
        });

    }
}
