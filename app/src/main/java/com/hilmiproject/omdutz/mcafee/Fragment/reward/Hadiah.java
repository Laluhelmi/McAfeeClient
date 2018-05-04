package com.hilmiproject.omdutz.mcafee.Fragment.reward;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Draw;
import com.hilmiproject.omdutz.mcafee.Fragment.reward.iklan.iklan1;
import com.hilmiproject.omdutz.mcafee.Interface.HadiahInterface;
import com.hilmiproject.omdutz.mcafee.Interface.MyGoodListener;
import com.hilmiproject.omdutz.mcafee.Pojo.HadiahPojo;
import com.hilmiproject.omdutz.mcafee.R;
import com.hilmiproject.omdutz.mcafee.VolleyClass.ClaimRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.HadiahVolley;
import com.hilmiproject.omdutz.mcafee.VolleyClass.IklanRequest;
import com.hilmiproject.omdutz.mcafee.VolleyClass.MyVolley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Hadiah extends Fragment {
    private ImageView    imageView;
    private RecyclerView recyclerView;
    private static boolean isloaded = false;
    public  static List<HadiahPojo> hadiahPojosst;
    private ViewPager viewPager;
    private View view;
    private List<ImageView> imageViews;
    private int getViewpagerPageActive;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view       = inflater.inflate(R.layout.activity_hadiah,container,false);
        final ImageView tanda1,tanda2;
     //   tanda1 = (ImageView)view.findViewById(R.id.tanda1);
       // tanda2 = (ImageView)view.findViewById(R.id.tanda2);
       // imageView       = (ImageView)view.findViewById(R.id.iklan);
        recyclerView    = (RecyclerView)view.findViewById(R.id.hadiah);
        final GridLayoutManager gridLayoutManager = new ScrollDisable(getContext(),2);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new ItemOffsetDecoration(10));
        recyclerView.setFocusable(false);
        HadiahVolley hadiahVolley = new HadiahVolley(getContext());
        viewPager = (ViewPager)view.findViewById(R.id.iklan);

        viewPagerListener();
        getAllIklan();
        if(hadiahPojosst != null){
            Myadapter myadapter = new Myadapter(hadiahPojosst);
            recyclerView.setAdapter(myadapter);
        }else{

            hadiahVolley.dataHadiah(new HadiahInterface() {
                @Override
                public void setelahAdaData(List<HadiahPojo> hadiahPojos) {
                    Myadapter myadapter = new Myadapter(hadiahPojos);
                    recyclerView.setAdapter(myadapter);
                    hadiahPojosst = hadiahPojos;
                    final NestedScrollView nestedScrollView =
                            ((NestedScrollView)(view.findViewById(R.id.nested)));
                    final RelativeLayout relativeLayout = (RelativeLayout)nestedScrollView.getChildAt(0);
                    nestedScrollView.scrollTo(0,1000);
                    /*nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                        @Override
                        public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                            if (i1== (nestedScrollView.getChildAt(0).getMeasuredHeight() -
                                    nestedScrollView.getMeasuredHeight())) {
                                Log.d("chhild",String.valueOf(nestedScrollView.getChildAt(0).getMeasuredHeight()));

                                Log.d("parent",String.valueOf(nestedScrollView.getMeasuredHeight()));
                                Log.i("tes doang", "BOTTOM SCROLL");
                            }
                        }
                    });*/
                }
            });
        }

        return view;
    }

    private void viewPagerListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                /*if(position == 0){
                    tanda1.setBackgroundResource(R.drawable.iklanaktif);
                    tanda2.setBackgroundResource(R.drawable.iklannonaktif);
                }else{
                    tanda1.setBackgroundResource(R.drawable.iklannonaktif);
                    tanda2.setBackgroundResource(R.drawable.iklanaktif);
                }*/
                imageViews.get(getViewpagerPageActive).setBackgroundResource(R.drawable.iklannonaktif);
                imageViews.get(position).setBackgroundResource(R.drawable.iklanaktif);
                getViewpagerPageActive = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void getIklan(String iklan){
            Dialog dialog = new Dialog(getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.iklan_layout);
            Bitmap bitmap = new IklanRequest().loadIklan(iklan,getContext());
            ((ImageView)dialog.findViewById(R.id.gambariklan)).setImageBitmap(bitmap);
            FrameLayout frameLayout = ((FrameLayout)getActivity().findViewById(R.id.ini_Fragment));
            AppBarLayout appBarLayout = (AppBarLayout)getActivity().findViewById(R.id.myappbar);
           dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
                   ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(null);
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
            dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;
            dialog.show();
            //new AppDetail(getContext()).setIklan(false,iklan);

    }



    class Myadapter extends RecyclerView.Adapter<Myadapter.Holder>{

        public class Holder extends RecyclerView.ViewHolder{
            public TextView nominal,point,pointCount,idhadiah;
            public ImageView jenis;

            public Holder(View view){
                super(view);
                jenis = (ImageView)view.findViewById(R.id.jenishadiah);
                nominal = (TextView)view.findViewById(R.id.nominalhadiah);
                pointCount = (TextView)view.findViewById(R.id.pointcount);
                idhadiah = (TextView)view.findViewById(R.id.idhadiah);
                point = (TextView)view.findViewById(R.id.point);
                ((TextView)view.findViewById(R.id.tukar)).setOnClickListener(
                        new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     cekProfilSudahLengkap(pointCount,idhadiah);
                    }
                });

            }
        }
        private List<HadiahPojo> hadiahPojos;
        public Myadapter(List<HadiahPojo> hadiahPojos){
            this.hadiahPojos = hadiahPojos;

        }

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_item_hadiah,parent,false);
            Holder holder = new Holder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            holder.point.setText("Point : "+hadiahPojos.get(position).getPoint());
            holder.nominal.setText(hadiahPojos.get(position).getNama_hadiah());
            holder.pointCount.setText(hadiahPojos.get(position).getPoint());
            holder.idhadiah.setText(hadiahPojos.get(position).getIdhadiah());
            if(hadiahPojos.get(position).getTipe().equals("Pulsa")){
                holder.jenis.setImageResource(R.drawable.pulsa);
            }else{
                holder.jenis.setImageResource(R.drawable.uang2);
            }
        }

        @Override
        public int getItemCount() {
            return hadiahPojos.size();
        }
    }
    public class ItemOffsetDecoration extends RecyclerView.ItemDecoration {

        private int mItemOffset;

        public ItemOffsetDecoration(int itemOffset) {
            mItemOffset = itemOffset;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                                   RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(mItemOffset, mItemOffset, mItemOffset, mItemOffset);
        }
    }
    class ScrollDisable extends GridLayoutManager{

        public ScrollDisable(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public ScrollDisable(Context context, int spanCount) {
            super(context, spanCount);
        }

        public ScrollDisable(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
        @Override
        public boolean canScrollVertically() {
            return false;
        }
    }
    class MYPageAdapter extends FragmentStatePagerAdapter{
        private List<String> files;

        public MYPageAdapter(FragmentManager fragmentManager,List<String> files){
            super(fragmentManager);
            this.files = files;
        }

        @Override
        public Fragment getItem(int position) {
            Bundle bundle = new Bundle();
            bundle.putString("iklanpath",files.get(position));
            Fragment fragment = new iklan1();
            fragment.setArguments(bundle);
            return fragment;
        }

        @Override
        public int getCount() {
            return files.size();
        }
    }
    private List<String> listiklan;
    private List<String> listiklanbelumada;
    public void getAllIklan(){
        new MyVolley(getContext()).doRequest("upload_iklan/getall", MyVolley.get,
                new MyGoodListener() {
                    @Override
                    public void doAny(Object object) {
                        iklanliStrings = new ArrayList<>();
                        listiklanbelumada = new ArrayList<>();
                        try {
                            JSONObject obj = new JSONObject(object.toString());
                            JSONArray array = obj.getJSONArray("data");
                            for(int i =0;i<array.length();i++){
                                JSONObject dd = array.getJSONObject(i);
                                iklanliStrings.add(dd.getString("url"));
                            }
                            listiklan = new ArrayList<>();
                        //cocokkan
                        for(String s : iklanliStrings){
                            boolean ketemu = false;
                            for(File file : getLocalIklan()){
                                if(s.equals(file.getName())){
                                    ketemu = true;
                                    listiklan.add(s);
                                    break;
                                }
                            }
                            if(ketemu == false){
                                listiklanbelumada.add(s);
                            }

                        }
                      /*  for(File file : getLocalIklan()){
                                boolean ketemu = false;
                                for (String s : iklanliStrings){
                                    if(file.getName().equals(s)){
                                        ketemu = true;
                                        iklanliStrings.remove(s);
                                        break;
                                    }
                                }
                                if(ketemu == false){
                                    file.delete();
                                }
                          }*/
                            for(final String s : listiklanbelumada){
                                new IklanRequest().getIklan(s, getContext(), null,
                                        new MyGoodListener() {
                                            @Override
                                            public void doAny(Object object) {
                                                getAllIklan();
                                                getIklan(s);
                                            }
                                        });

                            }
                            hapusIklaYangTidakada();
                            setViewpagerAdapter();
                            buatBullet(view,getLocalIklan().length);
                        }catch (Exception e){
                            Log.d("error json",e.getMessage());
                        }

                    }
                },null);
    }
    public void hapusIklaYangTidakada(){
        for(File file : getLocalIklan()){
            boolean ketemu = false;
            for (String s : listiklan){
                if(s.equals(file.getName())){
                    ketemu = true;
                    break;
                }
            }
            if(ketemu == false){
                file.delete();
            }
        }
    }
    public List<String> iklanliStrings;
    public File[] getLocalIklan(){
        File file  = new File(getContext().getCacheDir().getAbsolutePath()+"/gambar");
        if(file.exists() == false){
            file.mkdirs();
        }
        File[] arrayFile = file.listFiles();
        return arrayFile;
    }
    public void buatBullet(View view,int bnyak){
        imageViews = new ArrayList<>();
        ((LinearLayout)view.findViewById(R.id.tandaiklan)).
                removeAllViews();
        for(int i =0;i<bnyak;i++){
            ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.tanda_iklan,
                    null,false);
            RelativeLayout.LayoutParams  params = new RelativeLayout.LayoutParams(20,
                    20);
            params.setMargins(0,0,10,10);
            imageView.setLayoutParams(params);
            imageView.setId(i);
            imageViews.add(imageView);

            ((LinearLayout)view.findViewById(R.id.tandaiklan)).addView(imageView);
        }
        getViewpagerPageActive = 0;
        imageViews.get(0).setBackgroundResource(R.drawable.iklanaktif);
    }
    public void tambahBullet(){
        ImageView imageView = (ImageView) LayoutInflater.from(getContext()).inflate(R.layout.tanda_iklan,
                null,false);
        RelativeLayout.LayoutParams  params = new RelativeLayout.LayoutParams(20,
                20);
        params.setMargins(0,0,10,10);
        imageView.setLayoutParams(params);
        imageView.setId(getLocalIklan().length-1);
        imageViews.add(imageView);

        ((LinearLayout)view.findViewById(R.id.tandaiklan)).addView(imageView);
    }
    public void setViewpagerAdapter(){
        MYPageAdapter adapter = new MYPageAdapter(getChildFragmentManager(),
                listiklan   );
        viewPager.setAdapter(adapter);
    }
    public void cekProfilSudahLengkap(final TextView pointCount, final TextView idhadiah){
        String email = new AppDetail(getContext()).getEmail();
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Mohon Tunggu ....");
        progressDialog.show();
        new MyVolley(getContext()).doRequest("profil/cek/" + email, MyVolley.get,
                new MyGoodListener() {
                    @Override
                    public void doAny(Object object) {
                        progressDialog.dismiss();
                        String respoonse = object.toString();
                        try {
                            JSONObject jsonObject = new JSONObject(respoonse);
                            if(jsonObject.getString("status").equals("false")){
                                Toast.makeText(getContext(), "Lengkapi dulu Biodata Anda",
                                        Toast.LENGTH_SHORT).show();
                            }else{
                                if(Integer.parseInt(Draw.globalPoint) < Integer.parseInt(pointCount
                                        .getText().toString())){
                                    Toast.makeText(getContext(), "Maaf Point Anda Tidak Cukup"
                                            , Toast.LENGTH_SHORT).show();
                                }
                                else if(new AppDetail(getContext()).getRek() == false){
                                    Toast.makeText(getContext(), "Silahkan Lengkapi Biodata Anda"
                                            , Toast.LENGTH_SHORT).show();
                                }else{
                                    AlertDialog.Builder  builder = new AlertDialog.Builder(getContext());
                                    builder.setMessage("Apakah Anda Yakin ?");
                                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            String email   = new AppDetail(getContext()).getEmail();
                                            String point    = pointCount.getText().toString();
                                            String idHadiah = idhadiah.getText().toString();
                                            new ClaimRequest(getContext()).kirimClaim("",
                                                    point,idHadiah,email);
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    });
                                    builder.create().show();
                                }
                            }

                        }catch (Exception e){
                            e.printStackTrace();
                        }

                    }
                },null);
    }

}
