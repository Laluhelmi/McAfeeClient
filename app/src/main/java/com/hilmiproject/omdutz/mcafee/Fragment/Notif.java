package com.hilmiproject.omdutz.mcafee.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.hilmiproject.omdutz.mcafee.R;

public class Notif extends Fragment{

    static WebView myWebView;
    static boolean state = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_scan_code,container,false);


        return view;
    }


}