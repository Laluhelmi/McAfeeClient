package com.hilmiproject.omdutz.mcafee.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;

import com.hilmiproject.omdutz.mcafee.FaqActivity;
import com.hilmiproject.omdutz.mcafee.R;

/**
 * Created by L on 07/12/17.
 */

public class CostumMediaController extends MediaController {
    public CostumMediaController(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CostumMediaController(Context context, boolean useFastForward) {
        super(context, useFastForward);
    }

    public CostumMediaController(Context context) {
        super(context);
    }
    private VideoView videoView;
    private WindowManager manager;
    public void setManager(WindowManager manager,Activity activity){
        this.manager = manager;
        this.activity = activity;
    }

    private Activity activity;
    @Override
    public void setAnchorView(final View view) {
        super.setAnchorView(view);

        Button fullscrenn = new Button(getContext());
        fullscrenn.setBackgroundResource(R.drawable.fullscreen1);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(90 ,90);

        params.gravity =  Gravity.RIGHT|Gravity.TOP;
        params.setMargins(0,10,0,0);
        addView(fullscrenn,params);
        fullscrenn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(FaqActivity.isPlayedBefore){
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    FaqActivity.isPlayedBefore = false;
                }else{
                    activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                    FaqActivity.isPlayedBefore = true;
                }
            }
        });
        view.setMinimumHeight(30);
    }
}
