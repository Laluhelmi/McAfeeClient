package com.hilmiproject.omdutz.mcafee.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hilmiproject.omdutz.mcafee.Interface.GetProvinsiInterface;
import com.hilmiproject.omdutz.mcafee.Interface.Wilayahlistener;
import com.hilmiproject.omdutz.mcafee.Pojo.LocationPojo;
import com.hilmiproject.omdutz.mcafee.Pojo.Provinsi;
import com.hilmiproject.omdutz.mcafee.Pojo.TokoEntity;
import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;
import com.hilmiproject.omdutz.mcafee.R;
import com.hilmiproject.omdutz.mcafee.VolleyClass.MapVolley;
import com.hilmiproject.omdutz.mcafee.VolleyClass.ProvinsiRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.TokoRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.WilayahVolley;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class Lokasi extends Fragment implements OnMapReadyCallback{

    private GoogleMap mMap;
    private TextView nama,alamat,tokodipilih,wilayahdipilih,notelp;
    private List<LocationPojo> locationPojos;
    private String namatoko,alamattoko,tokopilih,wilayahpilih,notelpString;
    private LinearLayout keterangan;
    private RelativeLayout pilihkota,pilihtoko,total;
    private List<String> kotalist;
    private MapVolley mapVolley;
    private List<Wilayah> wilayahs;
    private List<TokoEntity> tokoEntities;
    private List<Provinsi> provinsis;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_lokasi,container,false);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        nama            = (TextView)        view.findViewById(R.id.namatoko);
        alamat          = (TextView)        view.findViewById(R.id.alamattoko);
        keterangan      = (LinearLayout)    view.findViewById(R.id.keterangan);
        wilayahdipilih  = (TextView)        view.findViewById(R.id.kota);
        tokodipilih     = (TextView)        view.findViewById(R.id.tokodipilih);
        notelp          = (TextView)        view.findViewById(R.id.notelp);
       // kota            = (Spinner)         view.findViewById(R.id.kota);
        //toko            = (Spinner)         view.findViewById(R.id.toko);
        pilihkota = (RelativeLayout)view.findViewById(R.id.pilihkota);
        total = (RelativeLayout)view.findViewById(R.id.total);
        pilihtoko = (RelativeLayout)view.findViewById(R.id.pilihtoko);
        WilayahVolley wilayahVolley = new WilayahVolley(getContext());
        if(wilayahs == null){
            wilayahs = new ArrayList<>();
            wilayahVolley.getWilayah(new Wilayahlistener() {
                @Override
                public void wilayah(List<Wilayah> wi) {
                    wilayahs = wi;
                }
                @Override
                public void getToko(List<TokoEntity> tokoEntities) {
                }
            });
        }
        if(provinsis == null){
            provinsis = new ArrayList<>();
            GetProvinsi();
        }
        click();
        return view;
    }

    public void GetProvinsi(){
        new ProvinsiRequest(getContext()).getProvinsi(new GetProvinsiInterface() {
            @Override
            public void giveData(List<?> pr) {
                    provinsis = (List<Provinsi>) pr;
            }
        });
    }
    public List<String> provinsiToArrayString(){
        List<String> strings = new ArrayList<>();
        for(Provinsi p : provinsis){
            strings.add(p.nama);
        }
        return strings;
    }
    public void buatDialogKota(int kotaPosition, final Dialog parentDialog){
        final Dialog dialog = new Dialog(getContext());
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.kotadialog);
        dialog.getWindow().setLayout(total.getWidth(),
                (total.getHeight()- (total.getHeight()/5)));
        final ListView listView = (ListView)dialog.findViewById(R.id.list);
        new ProvinsiRequest(getContext()).getKota(new GetProvinsiInterface() {
            @Override
            public void giveData(List<?> wilayahList) {
                wilayahs = (List<Wilayah>) wilayahList;
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,kotaArrayToString());
                listView.setAdapter(adapter);
                // tokoEntities = new ArrayList<>();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        wilayahdipilih.setText(wilayahs.get(i).namawilaya);
                        wilayahpilih = wilayahs.get(i).namawilaya;
                        getWilayahFromToko(wilayahs.get(i).id);
                        dialog.dismiss();
                        parentDialog.dismiss();
                    }
                });
            }
        },provinsis.get(kotaPosition).id);
        dialog.show();
    }
    public void click(){
        pilihkota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getContext());
                dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.kotadialog);
                dialog.getWindow().setLayout(total.getWidth(),
                        (total.getHeight()- (total.getHeight()/5)));
                ListView listView = (ListView)dialog.findViewById(R.id.list);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,provinsiToArrayString());
                listView.setAdapter(adapter);
               // tokoEntities = new ArrayList<>();
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        buatDialogKota(i,dialog);
                    }
                });
                dialog.show();
            }
        });
        pilihtoko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tokoEntities != null){
                    final Dialog dialog = new Dialog(getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    dialog.setContentView(R.layout.kotadialog);
                    dialog.getWindow().setLayout(total.getWidth(),
                            (total.getHeight()- (total.getHeight()/5)));
                    final ListView listView = (ListView)dialog.findViewById(R.id.list);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1,tokoArrayToString());
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            dialog.dismiss();
                          LatLng latLng = tokoEntities.get(i).latLng;
                          mMap.clear();
                          Marker marker =
                                  mMap.addMarker(new MarkerOptions()
                                          .title(tokoEntities.get(i).nama_toko).position(latLng));
                          animateCamer(marker);
                            tokopilih = tokoEntities.get(i).nama_toko;
                          tokodipilih.setText(tokopilih);
                          displayKeterangan(i);
                        }
                    });
                    listView.setAdapter(adapter);
                    dialog.show();
                }
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(notelpString == null){
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-7.805868,
                    110.369475),0));
        }
    }
    public List<String> kotaArrayToString(){
        List<String> strings = new ArrayList<>();
        for (Wilayah w : wilayahs){
            strings.add(w.namawilaya);
        }
        return strings;
    }
    public List<String> tokoArrayToString(){
        List<String> strings = new ArrayList<>();
        for (TokoEntity w : tokoEntities){
            strings.add(w.nama_toko);
        }
        return strings;
    }


    public void animateCamer(Marker marker){
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(marker.getPosition());
        LatLngBounds bounds = builder.build();
        int padding = 0; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 15);
        mMap.animateCamera(cu);
    }
    @Override
    public void onResume() {
        super.onResume();
        if(namatoko != null ) {
            keterangan.setVisibility(View.VISIBLE);
            nama.setText(namatoko);
            alamat.setText(alamattoko);
            notelp.setText(notelpString);
            tokodipilih.setText(tokopilih);
        }
        if(wilayahpilih != null)
            wilayahdipilih.setText(wilayahpilih);
    }
    private LatLng lastCoordinate;
    public void setCoordinate(int toko){
        LatLng latLng = mapVolley.getLatLngs().get(toko);
        String title  = mapVolley.getlocationPojos().get(toko).getNama();
        mMap.clear();
        Marker marker = mMap.addMarker(new MarkerOptions().position(latLng).title(title));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
        displayKeterangan(toko);
    }
    public void displayKeterangan(int i){
        keterangan.setVisibility(View.VISIBLE);
        namatoko   = tokoEntities.get(i)
                .nama_toko;
        alamattoko = tokoEntities.get(i)
                .alamat;
        nama   .setText(namatoko);alamat .setText(alamattoko);
        notelpString = tokoEntities.get(i).no_telp;
        notelp.setText(notelpString);
        Animation munculAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.ke_animation);
        keterangan.setAnimation(munculAnimation);

    }
    public void getWilayahFromToko(String id){

        TokoRequest tokoRequest = new TokoRequest(getContext());
        tokoRequest.getToko(id, new Wilayahlistener() {
            @Override
            public void wilayah(List<Wilayah> wilayahs) {

            }

            @Override
            public void getToko(List<TokoEntity> tokoEntities2) {
                tokoEntities = tokoEntities2;
                tokodipilih.setText("Pilih Toko");
            }
        });
    }

}