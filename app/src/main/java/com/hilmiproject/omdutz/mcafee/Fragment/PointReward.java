package com.hilmiproject.omdutz.mcafee.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hilmiproject.omdutz.mcafee.Draw;
import com.hilmiproject.omdutz.mcafee.Fragment.reward.Hadiah;
import com.hilmiproject.omdutz.mcafee.Fragment.reward.Reward;
import com.hilmiproject.omdutz.mcafee.R;

public class PointReward extends Fragment {

    private ViewPager viewPager;
    private ViewPagerAdapter pagerAdapter;
    private boolean isloaded = false;
    public static Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_point_reward,container,false);
        viewPager =   view.findViewById(R.id.viewpager);
        pagerAdapter = new ViewPagerAdapter(getActivity()
                .getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        isloaded = true;
        context = getContext();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Point Reward");

        Draw.tabLayout.setVisibility(View.VISIBLE);
        if(isloaded == true){
            pagerAdapter = new ViewPagerAdapter(getActivity()
                    .getSupportFragmentManager());
            Handler handler = new Handler();
            Draw.tabLayout.removeAllTabs();
            viewPager.setAdapter(pagerAdapter);
            viewPager.setVisibility(View.GONE);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    viewPager.setVisibility(View.VISIBLE);
                }
            },270);
        }
        isloaded = false;

        viewPager.setCurrentItem(1);
        Draw.tabLayout.setupWithViewPager(viewPager);
    }

    class ViewPagerAdapter extends FragmentStatePagerAdapter{

        public ViewPagerAdapter(FragmentManager fragmentManager){
            super(fragmentManager);
        }
        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if(position == 0){
                return "REWARD SAYA";
            }else{
                return "HADIAH";
            }
        }

        @Override
        public Fragment getItem(int position) {
            if(position == 0){
                return new Reward();
            }else
                return new Hadiah();
        }
    }
}

