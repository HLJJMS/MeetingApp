package wlm.meethapp.common;


/**
 * Created by wlm on 2017/6/6.
 */

public class Constants {

    //正式环境
//    public static final String URL = "http://192.168.1.8:8086/api/VMIPdaApi/";

//    public static String Common_URL = " http://192.168.0.8:8666/";
//    public static String URL = Common_URL +  "/api/MeetingApi/";
//公司编码(内部)
//public static final String CPCode = "10008";
    //基础地址
    public static String Common_URL = "http://mt.baron-bj.com/";
//
    public static String URL = Common_URL +  "/api/MeetingApi/";
    //公司编码(北京人福医疗器械有限公司)
    public static final String CPCode = "10012";

    //查找错误信息的方法
    public static final String ErrorInfoMethodName = "TEST";

    //key
    public static final String key = "QspZCjj7iEGrPWQUg4eqyw";




    //登录接口
    public static final String LoginMN = "APP_UserLogin";

    //获取短信验证码接口
    public static final String GetMsgMN = "A001GetPhoneCode";

    //忘记密码接口
    public static final String ForgetPasMN = "A001AppBackPwd";

}