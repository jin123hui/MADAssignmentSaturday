package com.example.user.assignment;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public void showSnakebar(View view) {
        Button button = (Button) findViewById(R.id.change_password_button);
        Snackbar.make(view, button.getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void showSnakebar1(View view) {
        Button button = (Button) findViewById(R.id.deactivate_acc_button);
        Snackbar.make(view, button.getText(), Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
}
