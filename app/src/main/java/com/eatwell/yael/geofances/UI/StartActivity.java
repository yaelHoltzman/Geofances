package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
//import android.widget.RelativeLayout;
import android.content.Intent;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        TextView login;
        //TextView alreadySU;
        Button startButton;

        super.onCreate(savedInstanceState);

        User user = User.getInstance();
        user.setmContext(getApplicationContext());

        setContentView(R.layout.activity_start);

        login = findViewById(R.id.logIn_text);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickLogin(v);
            }
        });

        //alreadySU = findViewById(R.id.already_SU_text);

        startButton = findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onStartClick(v);
            }
        });

        //RelativeLayout layout = new RelativeLayout(this);
    }

    public void onStartClick(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}

