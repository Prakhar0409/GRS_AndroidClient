package com.prakhar_squared_mayank.grs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by mayank on 26/03/16.
 */
public class Utility {
    public static String IP="192.168.43.147:8000";
    public static String LOGIN_URL = "/user/login.json";
    public static String HOSTELS = "/hostel_list.json";
    public static String UPLOADIMAGE="/image/upload";
    public static String UPLOADUSERDATA="/user/signup";
    public static String POSTSTATUS="/complaint/status";
    public static String POSTSTATUSCOMMENT="/status/comment";
    public static String GETSTATUSCOMMENT="complaint/status/comment";
    public static String POSTWALLCOMMENT="/complaint/comment";
    public static String BOOKMARKCOMPLAINTS="/complaint/comment";

    public static JSONObject USER=null;
    public static List<ComplaintLevel> complaintLevels=null;
    public static boolean DEBUG = true;


    public static void showMsg(Context ctx, String m)
    {
        Toast.makeText(ctx, m, Toast.LENGTH_SHORT).show();
    }


    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }

    public static void setupUI(final Activity a, View view) {
        //Set up touch listener for non-text box views to hide keyboard.
        if(!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(a);
                    return false;
                }
            });
        }
        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(a,innerView);
            }
        }
    }


    public static String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if(bmp==null){
            Log.d("NULL BMP","NULL");
        }
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }




}
