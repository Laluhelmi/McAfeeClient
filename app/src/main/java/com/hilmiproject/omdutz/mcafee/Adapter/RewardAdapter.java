package com.hilmiproject.omdutz.mcafee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.Pojo.Reward;
import com.hilmiproject.omdutz.mcafee.R;

import java.util.List;

/**
 * Created by L on 04/12/17.
 */

public class RewardAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    List<Reward> rewards;
    private Context context;

    public RewardAdapter(Context context,List<Reward> rewards){
        inflater = LayoutInflater.from(context);
        this.rewards = rewards;
        this.context = context;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_listview_reward,viewGroup,false);
        TextView type,nominal,point,status,tanggal;
        type = (TextView)view.findViewById(R.id.type);
        nominal = (TextView)view.findViewById(R.id.nominal);
        point = (TextView)view.findViewById(R.id.point);
        status = (TextView)view.findViewById(R.id.status);
        tanggal = (TextView)view.findViewById(R.id.tanggal);

        Reward reward = rewards.get(i);
        type.setText(reward.getType());
        nominal.setText(reward.getNominal());
        point.setText(reward.getPoint());
        status.setText(reward.getStatus());
        tanggal.setText(reward.getTgl_kirim());


        return view;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public int getCount() {
        return rewards.size();
    }
}
