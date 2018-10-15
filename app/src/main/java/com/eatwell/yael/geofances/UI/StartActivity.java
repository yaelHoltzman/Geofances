package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Intent;

import com.eatwell.yael.geofances.R;
import com.eatwell.yael.geofances.UserPreferences.User;

public class StartActivity extends AppCompatActivity {

    private TextView login;
    private TextView alreadySU;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        User user = User.getInstance();
        user.setmContext(getApplicationContext());

        setContentView(R.layout.activity_start);

        login = (TextView)findViewById(R.id.logIn_text);
        alreadySU = (TextView)findViewById(R.id.already_SU_text);
        startButton = (Button)findViewById(R.id.start_button);

        RelativeLayout layout = new RelativeLayout(this);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}

