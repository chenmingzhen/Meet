package com.example.meet.text;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import com.example.framework.view.TouchPictureV;
import com.example.meet.R;

public class TouchPictureText extends AppCompatActivity {

    private TouchPictureV touchPictureV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_touch_picture_text);
        touchPictureV=(TouchPictureV)findViewById (R.id.touch);
        touchPictureV.setViewResultListener (new TouchPictureV.OnViewResultListener () {
            @Override
            public void onResult() {
                Toast.makeText (TouchPictureText.this,"验证成功",Toast.LENGTH_SHORT).show ();
            }
        });
    }
}
