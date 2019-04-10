package com.example.hsaee.iocdemo;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.library.ContentView;
import com.example.library.InjectView;
import com.example.library.OnClick;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @InjectView(R.id.btn)
    public Button mBtn;

    @InjectView(R.id.tv)
    public TextView mTv;

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test",mBtn.getText().toString());
    }
    @OnClick({R.id.btn,R.id.tv})
    public  void show(View view){
        switch (view.getId()){
            case R.id.btn:
                Log.d("study","hello world btn");
                break;
            case R.id.tv:
                Log.d("study","hello world tv");
                break;
        }
    }
}
