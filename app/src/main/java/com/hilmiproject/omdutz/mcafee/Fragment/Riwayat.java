package com.hilmiproject.omdutz.mcafee.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.Adapter.RiwayatAdapter;
import com.hilmiproject.omdutz.mcafee.Interface.TabunganInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.TabunganEntity;
import com.hilmiproject.omdutz.mcafee.R;
import com.hilmiproject.omdutz.mcafee.VolleyClass.RiwayatRequest;

import java.util.List;

public class Riwayat extends Fragment {

    private TextView noRiwayat;
    private ListView listhistory;
    private List<TabunganEntity> tabunganEntities;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_scan_code,container,false);

        noRiwayat = (TextView)v.findViewById(R.id.noriwayat);
        listhistory = (ListView)v.findViewById(R.id.isiscan);
        if(tabunganEntities == null){
            new RiwayatRequest(getContext()).getTabungan(new TabunganInterface() {
                @Override
                public void setTabunga(List<TabunganEntity> t) {
                    tabunganEntities = t;
                    listhistory.setAdapter(new RiwayatAdapter(getContext(),tabunganEntities));
                    if(t.size() > 0) noRiwayat.setVisibility(View.GONE);
                }
            });
        }else{
            listhistory.setAdapter(new RiwayatAdapter(getContext(),tabunganEntities));
            if(tabunganEntities.size() > 0) noRiwayat.setVisibility(View.GONE);
        }


        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,
                new IntentFilter("riwayat_update"));
        return v;
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getData();

        }
    };
    public void getData(){
        new RiwayatRequest(getContext()).getTabungan(new TabunganInterface() {
            @Override
            public void setTabunga(List<TabunganEntity> t) {
                tabunganEntities = t;
                listhistory.setAdapter(new RiwayatAdapter(getContext(),tabunganEntities));
                if(t.size() > 0) noRiwayat.setVisibility(View.GONE);
            }
        });
    }
}