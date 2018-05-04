package com.hilmiproject.omdutz.mcafee.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.Pojo.TabunganEntity;
import com.hilmiproject.omdutz.mcafee.R;

import java.util.List;

/**
 * Created by L on 05/12/17.
 */

public class RiwayatAdapter extends BaseAdapter {

    private List<TabunganEntity> tabunganEntities;
    private LayoutInflater inflater;

    public RiwayatAdapter(Context context,List<TabunganEntity> tabunganEntities){
        inflater = LayoutInflater.from(context);
        this.tabunganEntities = tabunganEntities;
    }

    @Override
    public int getCount() {
        return tabunganEntities.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflater.inflate(R.layout.item_riwayat,viewGroup,false);
        TextView kodescan = (TextView)view.findViewById(R.id.kode_scan);
        TextView nama     = (TextView)view.findViewById(R.id.nama_produk);
        TextView serial   = (TextView)view.findViewById(R.id.serial_number);
        TextView point    = (TextView)view.findViewById(R.id.point);
        TextView tgl_scan = (TextView)view.findViewById(R.id.tanggal);
        TabunganEntity tabunganEntity = tabunganEntities.get(i);
        nama.setText(tabunganEntity.getNama_produk());
        serial.setText(tabunganEntity.getSerial_number());
        point.setText(tabunganEntity.getPoint());
        tgl_scan.setText(tabunganEntity.getTnggal_scan());
        kodescan.setText(tabunganEntity.getKode_scan());

        return view;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }
}
