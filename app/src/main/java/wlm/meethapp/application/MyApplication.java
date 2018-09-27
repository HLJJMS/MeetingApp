package wlm.meethapp.application;

import android.app.Application;
import android.content.SharedPreferences;

import wlm.meethapp.common.Constants;
import wlm.meethapp.common.SPCommonInfoBean;

/**
 * Created by wlm on 2018/7/5.
 */

public class MyApplication extends Application{

    private SharedPreferences spConfig;
    @Override
    public void onCreate() {
        super.onCreate();
        spConfig = getSharedPreferences(SPCommonInfoBean.SPName, MODE_PRIVATE);

        String url = spConfig.getString(SPCommonInfoBean.url, "");
        if (url != null && !url.equals("")){
            Constants.Common_URL = url;
//        }else {
//            Toast.makeText(this, "未获取到网址，请到登录页右上角设置", Toast.LENGTH_SHORT).show();
        }

    }
}
