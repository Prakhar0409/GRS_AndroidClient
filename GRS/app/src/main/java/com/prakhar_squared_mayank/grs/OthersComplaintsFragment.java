package com.prakhar_squared_mayank.grs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OthersComplaintsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link OthersComplaintsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class OthersComplaintsFragment extends Fragment implements AdapterViewCompat.OnItemClickListener {
    JSONArray data=null;
    private ListView complaintsLV;
    private ComplaintListAdapter complaintsAdapter;

    public OthersComplaintsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_others_complaints, container, false);

        complaintsLV = (ListView) v.findViewById(R.id.complaint_listview_foc);
        complaintsAdapter = new ComplaintListAdapter(getActivity(), inflater);
        complaintsLV.setAdapter(complaintsAdapter);
        complaintsAdapter.setListView(complaintsLV);


        getData();
        return v;
    }

    void getData() {
        String url2=LoginActivity.ip+"/complaints/work/"+((ComplaintsActivity)getActivity()).user_id+".json";
        System.out.println("Url being hit is : " + url2);

        JsonObjectRequest req2 = new JsonObjectRequest(Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String success="";
                try {

                    success= (String) response.get("success");

                    if(success.equals("True")){
                        data = (JSONArray) response.get("data");
                        showToast("Data2 Fetched!");
                        for (int i=0;i<data.length();i++){
                            System.out.println("222data "+Integer.toString(i)+" : "+data.get(i));
                        }
                    }else{
                        showToast("Fetch data2 ");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                System.out.println("DATA22222 : "+response);
                System.out.println(success);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("volley failed");
            }
        }
        );
        RequestQueue v = Volley.newRequestQueue(getActivity());
        //v.add(req1);
        v.add(req2);

//
//
//        String s = "[{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"},{\"complaint_id\":\"21\",\"title\": \"Problem title\",\"description\": \"Problem description\",\"posted_on\":\"23:30 25-10-2016\",\"image_id\":\"\",\"posted_by\": \"U123\",\"upvote_count\":\"38\",\"downvote_count\":\"2\",\"bookmarked\":\"true\"}]";
//        JSONArray arr = null;
//        try {
//            arr = new JSONArray(s);
//        }
//        catch(JSONException e) {
//
//        }
//        updateAdapter(arr);
    }

    //Shows toast with appropriate responses
    public void showToast(String msg)
    {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    void updateAdapter(JSONArray arr) {
        Log.d("OthersComplaintFragment", arr.toString());
        complaintsAdapter.updateData(arr);
    }

    @Override
    public void onItemClick(AdapterViewCompat<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),ComplaintDetailActivity.class);
        JSONObject c= null;
        try {
            if (data!=null) {
                c = (JSONObject) data.get(position);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(c!=null) {
            intent.putExtra("Complaint", c.toString());
            Log.d("Complaint Detailed", c.toString());
            startActivity(intent);
        }

    }
}
