package com.medicians.mediciansseller.Tabs;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.medicians.mediciansseller.Adapter.ContentAdapter;
import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.Models.Content;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.NewOrderDetails;
import com.medicians.mediciansseller.PopulateList;
import com.medicians.mediciansseller.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 13/8/15.
 */
public class Process extends Fragment {

    ListView listView;
    public  static ContentAdapter contentAdapter;
    public static List<NewOrderModel> list;
    NewOrderModel newOrder;
    PopulateList populateList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.global,null);


        listView=(ListView)view.findViewById(R.id.contentList);
        list=new ArrayList<>();
        contentAdapter=new ContentAdapter(getActivity(),list,1);
        listView.setAdapter(contentAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newOrder = list.get(position);
                Intent intent=new Intent(getActivity(), NewOrderDetails.class);
                intent.putExtra("orderid",newOrder.getOrder_id());
                intent.putExtra("flag",1);
                intent.putExtra("status",newOrder.getStatus1());
                startActivityForResult(intent,1);
            }
        });
        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateList=new PopulateList(getActivity(), AppController.info+"Accept",1);
        populateList.getData();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            list=new ArrayList<>();
            contentAdapter=new ContentAdapter(getActivity(),list,1);
            listView.setAdapter(contentAdapter);


           populateList=new PopulateList(getActivity(),AppController.info+"Accept",1);
            populateList.getData();


        }

    }

}
