package com.hjl.filedemo;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.hjl.filepicker.FileGridActivity;

import com.hjl.filepicker.FilePicker;
import com.hjl.filepicker.bean.FileItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private int RESULT_CODE = 100;
    private Button mBtn1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //一般在Application初始化配置一次就可以
                FilePicker filePicker = FilePicker.getInstance();
                filePicker.selectLimit = 3;

                Intent intent = new Intent(MainActivity.this, FileGridActivity.class);
                startActivityForResult(intent, RESULT_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == FilePicker.RESULT_CODE_FILES) {
            if (data != null && requestCode == RESULT_CODE) {
                ArrayList<FileItem> mFiles = (ArrayList<FileItem>) data.getSerializableExtra(FilePicker.EXTRA_RESULT_FILES);

                Log.d("images", "onActivityResult: " + mFiles.size());
            }
        }
    }
}
