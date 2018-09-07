package com.way.hiway;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import com.tencent.bugly.crashreport.BuglyLog;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    TextView mTxtHi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtHi = findViewById(R.id.tv_hi);
        mTxtHi.setText(null);

    }

    private void testBuglyLog(){
        List<String> list = null;
        // 'java.util.Iterator java.util.List.iterator()' on a null object reference
        // 在Bugly奔溃分析->跟踪日志->自定义日志
        // 只打印NPE1,不打印NPE
        BuglyLog.e(TAG, "NPE1");
        for (String name : list) {
            Log.e(TAG, name);
        }
        BuglyLog.e(TAG, "NPE");
    }
}
