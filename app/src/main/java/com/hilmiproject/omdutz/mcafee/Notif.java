package com.hilmiproject.omdutz.mcafee;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Interface.GetNotifListInterface;
import com.hilmiproject.omdutz.mcafee.Pojo.Reward;
import com.hilmiproject.omdutz.mcafee.VolleyClass.Notifikas;

import java.util.List;

public class Notif extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notif2);
        Notifikas notifikas = new Notifikas(this);
        String email = new AppDetail(this).getEmail();
        final ListView listView = (ListView)findViewById(R.id.listnotif);
        notifikas.getNotifList(email, new GetNotifListInterface() {
            @Override
            public void getData(List<Reward> rewards) {
                Adapter adapter = new Adapter(rewards,Notif.this);
                Draw.setBadge(getApplicationContext(),0);

                if(rewards.size() < 1){
                    ((TextView)findViewById(R.id.nonotif)).setVisibility(View.VISIBLE);
                }
                listView.setAdapter(adapter);
            }
        });
    }
    class Adapter extends BaseAdapter{
        private List<Reward> rewards;
        private LayoutInflater inflater;

        public Adapter(List<Reward> rewards, Context context){
            this.rewards = rewards;
            inflater     = LayoutInflater.from(context);
        }
        @Override
        public int getCount() {
            return rewards.size();
        }

        @Override
        public Object getItem(int i) {
            return rewards.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflater.inflate(R.layout.item_layout_notif,viewGroup,false);
            ((TextView)view.findViewById(R.id.tipe)).setText(rewards.get(i).getType());
            ((TextView)view.findViewById(R.id.nominal)).setText(rewards.get(i).getNominal());
            ((TextView)view.findViewById(R.id.status)).setText(rewards.get(i).getStatus());

            return view;
        }
    }
}
