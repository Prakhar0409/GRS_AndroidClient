package com.prakhar_squared_mayank.grs;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class ComplaintsActivity extends AppCompatActivity {

    String imageString;
    String user_id="2";
    Bitmap bitmap = null;
    Menu menu;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        viewPager = (CustomViewPager) findViewById(R.id.viewpager);
        viewPager.setPagingEnabled(false);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        getImageString(6);


    }

    public void getImageString(int id){
        String url="http://"+Utility.IP+Utility.DOWNLOADIMAGE+"?image_id="+Integer.toString(id);
        Log.d("Url hit was:", url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        // loading.dismiss();
                        //Showing toast message of the response
                        try {
                            JSONObject res= new JSONObject(s);

                            JSONObject imageObject=res.getJSONObject("image");
                            if (imageObject!=null){
                                imageString = imageObject.getString("picture_name");
                                System.out.println("imageString : "+imageString);
                                changeImage(imageString);
                            }


                            showToast("Success ful image chuityapa");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        //loading.dismiss();
                        //Showing toast
                        Utility.showMsg(getApplicationContext(),"volley error");//, Toast.LENGTH_LONG).show();
                    }
                });
        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        //Adding request to the queue
        requestQueue.add(stringRequest);

    }

    public Bitmap getImage(String pic) {
        //if (imageString!=null){return }
        if (bitmap!=null){
            return bitmap;
        }


        byte[] imageAsBytes = Base64.decode(pic.getBytes(), Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.complaints_menu, menu);
        this.menu=menu;

        if (imageString!=null){
            Bitmap image=getImage(imageString);
            MenuItem m= (MenuItem) menu.getItem(0);//getItem(R.id.action_profile);
            m.setIcon(new BitmapDrawable(getResources(), image));
        }

        return true;
    }

    public void changeImage(String imageString){
        Bitmap image=getImage(imageString);
        MenuItem m= (MenuItem) menu.getItem(0);//getItem(R.id.action_profile);
        m.setIcon(new BitmapDrawable(getResources(), image));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        else if(id==R.id.action_profile){
            //changeImage();
            Intent it = new Intent(this, ProfileActivity.class);
            startActivity(it);
            return true;
        }
        else if(id==R.id.action_validation_requests){
            Intent it = new Intent(this, ValidationRequestActivity.class);
            startActivity(it);
            return true;
        }
        else if(id==R.id.action_bookmarks){
            Intent it = new Intent(this, BookmarksActivity.class);
            startActivity(it);
            return true;
        }
        else if(id==R.id.action_notifs){
            Intent it = new Intent(this, NotificationsActivity.class);
            startActivity(it);
            return true;
        }
        else if(id==R.id.action_new_complaint){
            Intent it = new Intent(this, SubmitComplaintActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MyComplaintsFragment(), "Complaints Affecting Me");
        adapter.addFragment(new OthersComplaintsFragment(), "Other's Complaints");
        viewPager.setAdapter(adapter);
    }

    //Shows toast with appropriate responses
    public void showToast(String msg)
    {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
