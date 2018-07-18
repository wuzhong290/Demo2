package com.ybs.ymt;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private String reqData;
    private int IMPORTANCE_FOREGROUND=100;
    private int IMPORTANCE_VISIBLE=130;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }




    @SuppressLint("WrongConstant")
    public void onClcik(View view) {
//        //此方法也可以去启动另一个APP
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.njqg.orchard.edu_fjyd", "com.njqg.orchard.edu.ui.WebHomeActivity"));
//        if (!isBackgroundRunning("com.njqg.orchard.edu_fjyd")) {
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.addCategory(Intent.CATEGORY_LAUNCHER);
//        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//        intent.putExtra("key_web_url", "http://112.50.251.24:28080/gpf-web-fjyd/login.html?actionName=edu_login&recommendName=edu_login");
//        try{
//            startActivity(intent);
//        }catch (Exception e){
//            Log.i("tag", e.getMessage());
//        }

        Intent intent = new Intent();
        intent.setFlags(101);
        Bundle bundle = new Bundle();
        bundle.putString("data", "调用者传递过来的数据aaa");
        intent.putExtras(bundle);
        //参数1：要调用另一个APP的activity所在的包名
        //参数2：要调用另一个APP的activity名字
        intent.setClassName("com.ybs.demo_ybs", "com.ybs.demo_ybs.MainActivity");
        try{
            startActivityForResult(intent, 1);
        }catch (Exception e){
            Log.i("tag", e.getMessage());
        }
    }

    //在启动前进行一次判断：app是否在后台运行。一下是判断app是否运行的方法：
    private boolean isBackgroundRunning(String pkg) {
        String processName = pkg;
        ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
        if (activityManager == null)
            return false;
        // get running application processes
        List<RunningAppProcessInfo> processList = activityManager
                .getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo process : processList) {
             /*
            BACKGROUND=400 EMPTY=500 FOREGROUND=100
            GONE=1000 PERCEPTIBLE=130 SERVICE=300 ISIBLE=200
             */
            if (process.processName.startsWith(processName)) {
                boolean isBackground = process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
                        && process.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE;
                boolean isLockedState = keyguardManager
                        .inKeyguardRestrictedInputMode();
                if (isBackground || isLockedState)
                    return true;
                else
                    return false;
            }
        }
        return false;
    }


    //此方法中可以接收另一个app回传回来的数据
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("tag", "onActivityResult1"+resultCode +":"+ requestCode);
        if(resultCode == 10 && requestCode == 1){
            Log.i("tag", "onActivityResult2");

            String return_data=data.getStringExtra("return_data");

            Log.i("tag", "调用者返回的数据:" + return_data);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

}
