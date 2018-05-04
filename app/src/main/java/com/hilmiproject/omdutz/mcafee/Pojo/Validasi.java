package com.hilmiproject.omdutz.mcafee.Pojo;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by L on 19/11/17.
 */

public class Validasi {
    private boolean valid = true;
    private Context context;
    private Button button;

    public Validasi(Context context){
        this.context        = context;
    }
    public void setRule(EditText editText,String name){
        
        if(editText.getText().toString().isEmpty()){
            valid = false;
        }
    }
    public boolean run(){
        return valid;
    }
    
    
}
