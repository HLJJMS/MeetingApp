package wlm.meethapp.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import wlm.meethapp.utils.LoadingBox;


public class BaseActivity extends AppCompatActivity {
    protected Context mContext;
    private LoadingBox loadingBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = this;
//        imageLoader.init(ImageLoaderConfiguration.createDefault(this));
//        //沉浸式状态栏
//        TranslucentUtils.setTranslucentStatusTwo(this);

    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 屏蔽菜单按键
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }


    protected void showLoading() {

        if (loadingBox == null) {
            loadingBox = new LoadingBox(this,null);
        }
        loadingBox.Show();
    }

    protected void closeLoading() {
        if (loadingBox != null) {
            loadingBox.close();
            loadingBox = null;
        }
    }

    public void showLoading1() {

        if (loadingBox == null) {
            loadingBox = new LoadingBox(this,null);
        }
        loadingBox.Show1();
    }


}
