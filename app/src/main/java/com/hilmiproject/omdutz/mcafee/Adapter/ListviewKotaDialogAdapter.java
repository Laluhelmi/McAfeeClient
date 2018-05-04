package com.hilmiproject.omdutz.mcafee.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;

import java.util.List;

/**
 * Created by L on 01/12/17.
 */

public class ListviewKotaDialogAdapter extends BaseAdapter {

    Context context;
    private List<Wilayah> wilayahs;

    public ListviewKotaDialogAdapter(Context context,List<Wilayah> strings){
        this.context = context;
        this.wilayahs = strings;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getCount() {
        return wilayahs.size();
    }

    @Override
    public Object getItem(int i) {
        return wilayahs.get(i);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        return null;
    }
}
