package com.hilmiproject.omdutz.mcafee.Fragment.reward;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.Adapter.RewardAdapter;
import com.hilmiproject.omdutz.mcafee.Fragment.PointReward;
import com.hilmiproject.omdutz.mcafee.Interface.RewardHistoryInterface;
import com.hilmiproject.omdutz.mcafee.R;
import com.hilmiproject.omdutz.mcafee.VolleyClass.HistoryRewardRequest;

import java.util.List;

public class Reward extends Fragment {

     private ListView rewardsaya;
     public  static List<com.hilmiproject.omdutz.mcafee.Pojo.Reward> rewards;
    RelativeLayout r1 ;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_reward,container,false);
        rewardsaya = (ListView)view.findViewById(R.id.rewardsaya);
        if(rewards == null){
                this.view = view;
                getData();
        }else{
            ((TextView)view.findViewById(R.id.noreward)).setVisibility(View.GONE);
            if(rewards.size() == 0)
                ((TextView)view.findViewById(R.id.noreward)).setVisibility(View.VISIBLE);
            rewardsaya.setAdapter(new RewardAdapter(getContext(),rewards));
        }
       r1 = (RelativeLayout)view.findViewById(R.id.r1container);
        updateReward();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void showMessage(String s){
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }
    public void updateReward(){

        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver,
                new IntentFilter("reward_update"));
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            getData();

        }
    };
    public void getData(){
        HistoryRewardRequest historyRewardRequest = new HistoryRewardRequest(PointReward.context);
        historyRewardRequest.getData(new RewardHistoryInterface() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void getRewardHistory(final List<com.hilmiproject.omdutz.mcafee.Pojo.Reward> r) {
                rewardsaya.setAdapter(new RewardAdapter(getContext(),r));
                ((TextView)view.findViewById(R.id.noreward)).setVisibility(View.GONE);
                rewards = r;
                if(r.size() == 0){
                    ((TextView)view.findViewById(R.id.noreward)).setVisibility(View.VISIBLE);
                }
           /*     rewardsaya.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                    @Override
                    public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                        View v = rewardsaya.getChildAt(0);
                        String s = ((TextView)v.findViewById(R.id.nominal)).getText().toString();

                        Log.d("check",String.valueOf(v.getRight()));
                        Log.d("item ",String.valueOf(v.getBottom() - r1.getTop()));
                    }
                });*/
            }
        });
    }

}
