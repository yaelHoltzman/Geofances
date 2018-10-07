package com.eatwell.yael.geofances.UI;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.content.Intent;

import com.eatwell.yael.geofances.R;

public class StartActivity extends AppCompatActivity {

    private TextView login;
    private TextView alreadySU;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start);

        login = (TextView)findViewById(R.id.logIn_text);
        alreadySU = (TextView)findViewById(R.id.already_SU_text);
        startButton = (Button)findViewById(R.id.start_button);

        RelativeLayout layout = new RelativeLayout(this);
        //layout.setBackgroundColor(Color.MAGENTA);

        //text view - already logged in
        //TextView alreadySIText = new TextView(this);
        //alreadySIText.setId(2);
        //alreadySIText.setText("Already signed up? Login");
        //alreadySIText.setTextColor(Color.BLUE);

        //start button
        //Button startButton = new Button(this);
        //startButton.setId(1);
        //startButton.setText("Start");
        //startButton.setBackgroundColor(Color.GREEN);

        //button rules
        //RelativeLayout.LayoutParams buttonDetails = new RelativeLayout.LayoutParams(
        //      RelativeLayout.LayoutParams.WRAP_CONTENT,
        //    RelativeLayout.LayoutParams.WRAP_CONTENT
        //);

        //text rules
        /*RelativeLayout.LayoutParams alreadySITextDetails = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );

        //buttonDetails.addRule(RelativeLayout.CENTER_HORIZONTAL);
        //buttonDetails.addRule(RelativeLayout.CENTER_VERTICAL);

        alreadySITextDetails.addRule(RelativeLayout.ABOVE, startButton.getId());
        alreadySITextDetails.addRule(RelativeLayout.ALIGN_LEFT);
        alreadySITextDetails.setMargins(0, 0, 10, 50);
*/

        //layout.addView(startButton);
        //layout.addView(alreadySU);
        //setContentView(layout);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, Goal_Setting.class);
        startActivity(intent);
    }

    public void onClickLogin(View view) {
        Intent intentLogin = new Intent(this, LoginActivity.class);
        startActivity(intentLogin);
    }
}

