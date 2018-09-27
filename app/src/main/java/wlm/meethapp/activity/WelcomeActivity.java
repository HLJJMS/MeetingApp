package wlm.meethapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import wlm.meethapp.R;

public class WelcomeActivity extends AppCompatActivity {
    private Message msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        h.sendEmptyMessageDelayed(1,2000);
    }
    Handler h = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent i = new Intent(WelcomeActivity.this,LoginActivity.class);
            startActivity(i);
            finish();
        }
    };
}
