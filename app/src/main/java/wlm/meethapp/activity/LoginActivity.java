package wlm.meethapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import wlm.meethapp.R;
import wlm.meethapp.common.BaseActivity;
import wlm.meethapp.common.Constants;
import wlm.meethapp.common.SPCommonInfoBean;
import wlm.meethapp.utils.AlertDialogUtil;
import wlm.meethapp.utils.PopupWindowUtil;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_forget_password;
    private TextView tv_logon;

    private Context context;
    private Handler mHandler;
    private EditText et_phone, et_password;

    private String st_phone, st_mesg, st_password;

    private SharedPreferences spConfig;

    private Button ll_setting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        context = this;
        mHandler = new Handler();
        spConfig = getSharedPreferences(SPCommonInfoBean.SPName, MODE_PRIVATE);

        isLog();
        findView();

    }


    private void findView() {
//        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        tv_logon = (TextView) findViewById(R.id.sure_tv);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_password = (EditText) findViewById(R.id.et_password);
        ll_setting = (Button) findViewById(R.id.ll_setting);

//        tv_forget_password.setOnClickListener(this);
        tv_logon.setOnClickListener(this);

        ll_setting.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupWindow();
                return false;
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.tv_forget_password:
//                Intent intent = new Intent(context, ForgetPassActivity.class);
//                startActivity(intent);
//                break;
            case R.id.sure_tv:
                st_phone = et_phone.getText().toString().trim();
                st_password = et_password.getText().toString().trim();

                if (st_phone == null || st_phone.equals("")){
                    AlertDialogUtil.showAlertDialog(context, "提示", "用户账号不能为空，请填写");
                    return;
                }
                if (st_password == null || st_password.equals("")){
                    AlertDialogUtil.showAlertDialog(context, "提示", "用户密码不能为空，请填写");
                    return;
                }

                //请求网络登录并跳转
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.execute();
                break;

            case R.id.ll_setting:

//                showPopupWindow();

                break;
        }
    }

    private void showPopupWindow(){

        View view = LayoutInflater.from(context).inflate(R.layout.pop_view1, null);

        final PopupWindow popupWindow = PopupWindowUtil.getPopuWindow2(context,view, (float) 1.2,2,0.5f,-1);

        TextView textView = (TextView) view.findViewById(R.id.sure_tv);
        TextView textView1 = (TextView) view.findViewById(R.id.cancel_tv);
        final EditText editText = (EditText) view.findViewById(R.id.et_url);

        editText.setText(Constants.Common_URL);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constants.Common_URL = editText.getText().toString();
                SharedPreferences.Editor editor = spConfig.edit();
                editor.putString(SPCommonInfoBean.url, Constants.Common_URL);
                editor.commit();
                popupWindow.dismiss();
            }
        });


        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAtLocation(ll_setting, Gravity.CENTER,0,0);

    }

    //用户登录
    private class LoginInfo extends AsyncTask<Void, Void, String> {

        String result = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showLoading1();
        }

        @Override
        protected String doInBackground(Void... arg0) {
            // 刷新时，重置地址

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userName", st_phone);
            jsonObject.put("userPwd", st_password);
            jsonObject.put("companyID", Constants.CPCode);

            FormBody formBody = new FormBody
                    .Builder()
                    .add("userName",st_phone)//设置参数名称和参数值
                    .add("userPwd",st_password)//设置参数名称和参数值
                    .add("companyID",Constants.CPCode)//设置参数名称和参数值
                    .build();

            String url = Constants.URL + Constants.LoginMN;
            Log.d("ggggggggggggggggg", url);
            Log.d("ggggggggg参数", jsonObject.toJSONString());
            String result="";
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Response response = null;
            try {
                response = client.newCall(request).execute();
//                Log.d("33222222222222", response.)
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {

            super.onPostExecute(result);

            Log.d("333333333333333", result);
            closeLoading();

            if (result != null && !result.equals("")){
                if (!result.contains("code")){
                    Toast.makeText(LoginActivity.this, "登陆失败", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject jsonObject = JSONObject.parseObject(result);
                String code = jsonObject.getString("code");
                String message = jsonObject.getString("message");

                if (code != null && code.equals("0")){
                    saveInfo(jsonObject);
                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                }

            }
        }
    }


    /**
     * 保存用户信息
     * @param jsonObject
     */
    private void saveInfo(JSONObject jsonObject) {
        SharedPreferences.Editor editor = spConfig.edit();

        String st_user = jsonObject.getString("myDynamicData");

        JSONObject jsonObject1 = JSONObject.parseObject(st_user);


        JSONArray jsonArray = jsonObject1.getJSONArray("userInfo");
//        JSONObject jsonObject1 = JSONObject.parseObject(st_user);
        if (jsonArray != null && jsonArray.size() > 0){
            JSONObject jsonObject2 = jsonArray.getJSONObject(0);
            editor.putString(SPCommonInfoBean.userName, jsonObject2.getString("F0002"));
            editor.putString(SPCommonInfoBean.userCode, jsonObject2.getString("F0005"));
            editor.putString(SPCommonInfoBean.passWord, jsonObject2.getString("F0003"));
            editor.putString(SPCommonInfoBean.userNameReal, jsonObject2.getString("员工姓名"));
            editor.putString(SPCommonInfoBean.part, jsonObject2.getString("部门"));
        }

        JSONArray jsonArray1 = jsonObject1.getJSONArray("userRole");

        if (jsonArray1 != null && jsonArray1.size() > 0){
            JSONObject jsonObject3 = jsonArray1.getJSONObject(0);
            editor.putString(SPCommonInfoBean.userType_code, jsonObject3.getString("F0002"));
            editor.putString(SPCommonInfoBean.userType, jsonObject3.getString("F0003"));
        }

        editor.commit();

    }

    //判断要不要再走登录页
    public void isLog(){
        String userName = spConfig.getString(SPCommonInfoBean.userName, "");

        if ( userName == null || userName.equals("")){
            return;
        }
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
