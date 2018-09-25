package com.way.hiway;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;
import com.tencent.bugly.crashreport.BuglyLog;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private final String TAG = MainActivity.class.getSimpleName();

    // 通过静态方法创建ScheduledExecutorService的实例
    private ScheduledExecutorService mScheduledExecutorService = Executors.newScheduledThreadPool(4);

    TextView mTxtHi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtHi = findViewById(R.id.tv_hi);
        mTxtHi.setText(null);

//        CrashReport.testJavaCrash();
//        adb();

        mTxtHi.setText(getDomain("http://blog.csdn.net/meetings/article/details/78785424"));
    }

    /**
     * 获取url对应的域名
     */
    public String getDomain(String url) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }

        int j = 0;
        int startIndex = 0;
        int endIndex = 0;
        for (int i = 0; i < url.length(); i++) {
            if (url.charAt(i) == '/') {
                j++;
                if (j == 2) {
                    startIndex = i;
                } else if (j == 3) {
                    endIndex = i;
                }
            }
        }

        return url.substring(/*startIndex + 1*/0, endIndex);
    }

    private void adb() {
        String str;

        try {
            // System.err: java.io.FileNotFoundException: /proc/stat (Permission denied)
//            BufferedReader r = new BufferedReader(new InputStreamReader(
//                    new FileInputStream("/proc/stat")), 1000);
//            Process p = Runtime.getRuntime().exec("top -n 1");
            Process p = Runtime.getRuntime().exec("dumpsys");
            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((str = br.readLine()) != null) {
                Log.e(TAG, str);
                if (str.trim().length() < 1) {
                    continue;
                } else {

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(@NonNull final Runnable r) {
            return new Thread() {
                @Override
                public void run() {
                    r.run();
                    Log.e("lzp", "嘿嘿嘿");
                }
            };
        }
    };

    private ScheduledExecutorService mScheduledExecutorServic = Executors.newScheduledThreadPool(4, new ThreadFactory
            () {
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r) {
                @Override
                public void run() {
                    Log.e("lzp", "newThread");
                    super.run();
                    Log.e("lzp", "newThread over");
                }
            };
        }
    });

    // https://blog.csdn.net/u011315960/article/details/71422386
    private void testScheduledExecutorService() {
        // 延时任务
        mScheduledExecutorService.schedule(threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                Log.e("lzp", "first task");
            }
        }), 1, TimeUnit.SECONDS);

        // 循环任务，按照上一次任务的发起时间计算下一次任务的开始时间
        mScheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.e("lzp", "first:" + System.currentTimeMillis() / 1000);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        // 循环任务，以上一次任务的结束时间计算下一次任务的开始时间
        mScheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
                Log.e("lzp", "scheduleWithFixedDelay:" + System.currentTimeMillis() / 1000);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

    }

    private void testBuglyLog() {
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
