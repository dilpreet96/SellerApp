package com.medicians.mediciansseller.Tabs;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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

import com.medicians.mediciansseller.Adapter.ContentAdapter;

import com.medicians.mediciansseller.AppController;
import com.medicians.mediciansseller.MainActivity;
import com.medicians.mediciansseller.Models.NewOrderModel;
import com.medicians.mediciansseller.NewOrderDetails;
import com.medicians.mediciansseller.NewOrderServices.NewOrderReceiver;
import com.medicians.mediciansseller.PopulateList;
import com.medicians.mediciansseller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dilpreet on 10/8/15.
 */
public class NewOrder extends Fragment {


    ListView listView;
    public  static ContentAdapter contentAdapter;
    public static List<NewOrderModel> list;
    NewOrderModel newOrder;
    Intent intent;
    public static PopulateList populateList;;
    PendingIntent pendingIntent;
    AlarmManager alarmManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.global,null);





        listView=(ListView)view.findViewById(R.id.contentList);
        list=new ArrayList<>();
        contentAdapter=new ContentAdapter(getActivity(),list,10);
        listView.setAdapter(contentAdapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newOrder = list.get(position);
                Intent intent=new Intent(getActivity(), NewOrderDetails.class);
                intent.putExtra("status",newOrder.getStatus1());
                intent.putExtra("orderid",newOrder.getOrder_id());
                intent.putExtra("flag",0);
                startActivityForResult(intent, 1);
            }
        });

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        populateList=new PopulateList(getActivity(), AppController.basic+"New",10);
        populateList.getData();
        registerAlarm();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {
            populateList=new PopulateList(getActivity(),AppController.basic+"New",10);
            populateList.getData();
            registerAlarm();

        }
        else
            unRegisterAlarm();

    }

    public static void changeList(){
        if (populateList!=null)
                 populateList.getData();
    }
  private void registerAlarm(){
      intent=new Intent(getActivity(), NewOrderReceiver.class);
      pendingIntent=PendingIntent.getBroadcast(getActivity(),0,intent,0);
      alarmManager =(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);
      Log.d("Mytag", "Registered");
      alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,System.currentTimeMillis(),33000,pendingIntent);
  }

  private void unRegisterAlarm(){
      Log.d("Mytag", "UnRegistered");
      if(alarmManager!=null)
          alarmManager.cancel(pendingIntent);


  }

}
