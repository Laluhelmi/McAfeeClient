package com.hilmiproject.omdutz.mcafee;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.Interface.GetProvinsiInterface;
import com.hilmiproject.omdutz.mcafee.Interface.Wilayahlistener;
import com.hilmiproject.omdutz.mcafee.Pojo.Provinsi;
import com.hilmiproject.omdutz.mcafee.Pojo.TokoEntity;
import com.hilmiproject.omdutz.mcafee.Pojo.Validasi;
import com.hilmiproject.omdutz.mcafee.Pojo.Wilayah;
import com.hilmiproject.omdutz.mcafee.VolleyClass.ProvinsiRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.RegisterAndLoginVolley;
import com.hilmiproject.omdutz.mcafee.VolleyClass.WilayahVolley;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Register extends Fragment implements AdapterView.OnItemSelectedListener,
        TextWatcher{
    public static Register registerActivity;
    private Button  tombol2;
    public static   EditText nama,email,telp;
    public static   String   idtokoDipilih,jkDipilih;
    private Spinner jenisKelamin;
    private TextView toko;
    private RelativeLayout relativeLayout;
    private boolean isJkSelected = false;
    private Dialog dialog;
    //buat object volley untuk kirim request
    RegisterAndLoginVolley registerAndLoginVolley;
    private List<Provinsi> provinsiList;
    public static ProgressDialog progressBar;
    private Spinner jk;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_register,container,false);
        tombol2 = (Button)view.findViewById(R.id.tombolnext);
        tombol2.setEnabled(false);
        tombol2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(cekEmail(email.getText().toString()) == true){
                    kirimRegistrasi();
                    progressBar.show();
                }else {
                    Toast.makeText(getContext(),
                            "Format Email Yang Anda Masukkan Salah", Toast.LENGTH_SHORT).show();
                }

            }
        });
        progressBar = new ProgressDialog(getContext());
        progressBar.setMessage("Mohon Tunggu ...");
        registerAndLoginVolley = new RegisterAndLoginVolley(getContext());
        widgetDeclaration(view);
        //setTokoData();
        tampilkanDialog("provinsi");
        registerActivity = Register.this;

        return view;
    }
    public boolean cekEmail(String email){
        return Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$").matcher(email).matches();
    }
    private EditText password,repassword;
    public void kirimRegistrasi(){
        Map<String,String> dataRegistrasi = new HashMap<>();

        password.addTextChangedListener(this);
        repassword.addTextChangedListener(this);
        //    dataRegistrasi.put("no_ktp"         ,Register.ktp.getText().toString());
        dataRegistrasi.put("nama_user"      ,Register.nama.getText().toString());
        dataRegistrasi.put("telp"           ,Register.telp.getText().toString());
        dataRegistrasi.put("email"          ,email.getText().toString());
        dataRegistrasi.put("password"       ,password.getText().toString());
        dataRegistrasi.put("id_toko"        ,Register.idtokoDipilih);
        dataRegistrasi.put("jk",jkDipilih);
        //dataRegistrasi.put("jk"      ,Register.jkDipilih);
       // dataRegistrasi.put("no_rek",rekening.getText().toString());
        dataRegistrasi.put("re_password"    ,repassword.getText().toString());
        RegisterAndLoginVolley registerAndLoginVolley = new RegisterAndLoginVolley(getContext());
        if(password.getText().toString().equals(repassword.getText().toString())){
            registerAndLoginVolley.register(dataRegistrasi);
        }else{
            Toast.makeText(getContext(),
                    "Password Tidak Sama", Toast.LENGTH_SHORT).show();
        }
   }

    public void validate(){
        Validasi validasi = new Validasi(getContext());
        validasi.setRule(nama,"nama");
        validasi.setRule(email ,"ktp");
        validasi.setRule(telp,"telp");
        if(validasi.run() == true){
            Intent c = new Intent(getContext(), Register2.class);
            startActivity(c);
        }
    }

    public void widgetDeclaration(View view){
        relativeLayout = (RelativeLayout) view.findViewById(R.id.rRegister);
        toko           = (TextView)    view.findViewById(R.id.layout7);
        nama           = (EditText)    view.findViewById(R.id.nama);
        email            = (EditText)    view.findViewById(R.id.email);
        telp           = (EditText)    view.findViewById(R.id.HP);
        jenisKelamin   = (Spinner)     view.findViewById(R.id.jk);
        password = (EditText)view.findViewById(R.id.password) ;
        repassword = (EditText)view.findViewById(R.id.ulangpassword) ;
        password.addTextChangedListener(this);
        repassword.addTextChangedListener(this);


        final List<String> jk = new ArrayList<>();
        jk.add("Pilih Jenis Kelamin");
        jk.add("Laki-laki");
        jk.add("Perempuan");

       ArrayAdapter<String> jkAdapter = new ArrayAdapter<String>(getContext(),
                R.layout.support_simple_spinner_dropdown_item,jk);
        jenisKelamin.setAdapter(jkAdapter);
        jenisKelamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != 0){
                    isJkSelected = true;
                    jkDipilih = jk.get(i);
                }else{
                    isJkSelected = false;
                }
                if(!nama.getText().toString().isEmpty()
                        && !telp.getText().toString().isEmpty()
                        && !email.getText().toString().isEmpty()
                        && isJkSelected == true
                        && !repassword.getText().toString().isEmpty()
                        && !password.getText().toString().isEmpty()){
                    tombol2.setEnabled(true);
                }else{
                    tombol2.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        nama.addTextChangedListener(this);
        email .addTextChangedListener(this);
        telp.addTextChangedListener(this);
    }

    private boolean isKota;
    private List<TokoEntity> tokolist;
    private List<Wilayah> wilayahList;

    public void setTokoData(){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pilih_toko);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        final ListView listView = (ListView) dialog.findViewById(R.id.listview);
        final TextView keterangan = (TextView)dialog.findViewById(R.id.keterangan);
        final WilayahVolley wilayahVolley = new WilayahVolley(getContext());
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wilayahList != null){
                    List<String> data = new ArrayList<>();
                    for(int i =0;i<wilayahList.size();i++){
                        data.add(wilayahList.get(i).namawilaya);
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                            android.R.layout.simple_list_item_1,data);
                    listView.setAdapter(adapter);
                }
                dialog.show();
            }
        });
        final ProgressBar progressBar = (ProgressBar)dialog.findViewById(R.id.cumanloading);
        final Wilayahlistener wilayahlistener = new Wilayahlistener() {
            @Override
            public void wilayah(List<Wilayah> wilayahs) {
                List<String> data = new ArrayList<>();
                wilayahList = wilayahs;
                for(int i =0;i<wilayahs.size();i++){
                    data.add(wilayahs.get(i).namawilaya);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
            }
            @Override
            public void getToko(List<TokoEntity> tokoEntities) {
                List<String> data = new ArrayList<>();
                progressBar.setVisibility(View.GONE);
                tokolist = tokoEntities;
                for(int i=0;i<tokoEntities.size();i++){
                    data.add(tokoEntities.get(i).nama_toko);
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext()    ,
                        android.R.layout.simple_list_item_1,data);
                listView.setAdapter(adapter);
            }
        };
        wilayahVolley.getWilayah(wilayahlistener);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                isKota = false;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isKota == false){
                    keterangan.setText("Pilih Toko");
                    wilayahVolley.getToko(wilayahlistener,wilayahList.get(i).id);
                    isKota = true;
                    progressBar.setVisibility(View.VISIBLE);
                }else{
                    keterangan.setText("Pilih Wilayah");
                    isKota = false;
                    toko.setText(tokolist.get(i).nama_toko);
                    idtokoDipilih = tokolist.get(i).id_toko;
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        idtokoDipilih = registerAndLoginVolley.getListIdtoko().get(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if(!nama.getText().toString().isEmpty()
                && !telp.getText().toString().isEmpty()
                && !email.getText().toString().isEmpty()
                && isJkSelected == true
                && !repassword.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty()){
            tombol2.setEnabled(true);
        }else{
            tombol2.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    public void tampilkanDialog(String type){
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pilih_toko);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        TextView keterangan = (TextView)dialog.findViewById(R.id.keterangan);
        keterangan.setText("Pilih Provinsi");
        ProgressBar loading =  (ProgressBar) dialog.findViewById(R.id.cumanloading);
        ambilProvinsi(listView,loading);
        toko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });
    }
    public void buatDialog(String jenisDialog,String ketValue,String id,Dialog parentDialog){
        Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_pilih_toko);
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        ListView listView = (ListView) dialog.findViewById(R.id.listview);
        TextView keterangan = (TextView)dialog.findViewById(R.id.keterangan);
        ProgressBar loading = (ProgressBar)dialog.findViewById(R.id.cumanloading);
        keterangan.setText(ketValue);
        if(jenisDialog.equals("kota")){
            ambilKota(id,loading,listView,dialog);
        }else if(jenisDialog.equals("toko")){
            ambilToko(id,loading,dialog,listView,parentDialog);
        }
        dialog.show();
    }
    public void ambilProvinsi(final ListView listView,final ProgressBar loading){
        new ProvinsiRequest(getContext()).getProvinsi(new GetProvinsiInterface() {
            @Override
            public void giveData(List<?> provinsis) {
                provinsiList = (List<Provinsi>) provinsis;
                loading.setVisibility(View.GONE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,ListProvinsiToStringArray());
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        buatDialog("kota","Pilih Kota",provinsiList.get(i).id,
                                dialog);
                    }
                });
            }
        });
    }
    public void ambilKota(String id, final ProgressBar loading, final ListView listView, final Dialog panret){
        new ProvinsiRequest(getContext()).getKota(new GetProvinsiInterface() {
            @Override
            public void giveData(List<?> provinsis) {
                loading.setVisibility(View.GONE);
                final List<Wilayah> wilayahList = (List<Wilayah>) provinsis;
                loading.setVisibility(View.GONE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,ListKotaToStringArray(wilayahList));
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        buatDialog("toko","Pilih Toko",wilayahList.get(i).id
                        ,panret);
                    }
                });
            }
        },id);
    }
    public void ambilToko(final String wilayah, final ProgressBar loading, final Dialog dialog2,
                          final ListView listView,final Dialog parent){
        new WilayahVolley(getContext()).getToko(new Wilayahlistener() {
            @Override
            public void wilayah(List<Wilayah> wilayahs) {

            }

            @Override
            public void getToko(final List<TokoEntity> tokoEntities) {
                //buatDialog("toko","Pilih Toko",wilayah);
                loading.setVisibility(View.GONE);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_list_item_1,ListTokoToArrayString(tokoEntities));
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        parent.dismiss();
                        dialog2.dismiss();
                        dialog.dismiss();
                        idtokoDipilih = tokoEntities.get(i).id_toko;
                        toko.setText(tokoEntities.get(i).nama_toko);
                    }
                });
            }
        },wilayah);
    }
    public List<String> ListTokoToArrayString(List<TokoEntity> tokolist){
        List<String> strings = new ArrayList<>();
        for(TokoEntity tokoEntity : tokolist){
            strings.add(tokoEntity.nama_toko);
        }
        return strings;
    }

    public List<String> ListKotaToStringArray(List<Wilayah> wilayahs){
        List<String> wilayahstring  = new ArrayList<>();
        for(Wilayah wilayah : wilayahs){
            wilayahstring.add(wilayah.namawilaya);
        }
        return wilayahstring;
    }
    public List<String> ListProvinsiToStringArray(){
        List<String> provinsis  = new ArrayList<>();
        for(Provinsi provinsi : provinsiList){
            provinsis.add(provinsi.nama);
        }
        return provinsis;
    }

}
