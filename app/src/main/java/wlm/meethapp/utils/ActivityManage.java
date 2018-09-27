package wlm.meethapp.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

import wlm.meethapp.activity.MainActivity;


public class ActivityManage {
    private static ArrayList<Activity> activities = new ArrayList<Activity>();

    public static void addActivity(Activity activity) {
        activities.add(activity);

    }


    public static void finishAll() {

        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity != null) {
                activity.finish();
            }
        }
    }


    public static void backToMain(Context context) {
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity != null && (!(activity instanceof MainActivity))) {
                activities.remove(activity);
                activity.finish();
            }
        }
        Intent intentMain = null;
        intentMain = new Intent(context, MainActivity.class);
        context.startActivity(intentMain);
    }

    public static void backToNewCheckCode(Context context) {
        for (int i = activities.size()-1; i>=0; i--) {
            Activity activity = activities.get(i);
            if (activity != null && (!(activity instanceof MainActivity))) {
                activities.remove(activity);
                activity.finish();
            }else{
                break;
            }
        }
        if(activities.size()==0){
            Intent intentMain = null;
            intentMain = new Intent(context, MainActivity.class);
            context.startActivity(intentMain);
        }

    }
    public static void backToContinueCheckCode(Context context) {
        for (int i = activities.size()-1; i>=0; i--) {
            Activity activity = activities.get(i);
//            if (activity != null && (!(activity instanceof AutoContinue1Activity))) {
//                activities.remove(activity);
//                activity.finish();
//            }else{
//                break;
//            }
        }
        if(activities.size()==0){
            Intent intentMain = null;
            intentMain = new Intent(context, MainActivity.class);
            context.startActivity(intentMain);
        }

    }


}
