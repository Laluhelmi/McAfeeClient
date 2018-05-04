package com.hilmiproject.omdutz.mcafee.ClientPackage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.Interface.LoadScanInfo;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by L on 28/11/17.
 */

public class AmbilGambar extends AsyncTask<Void,Bitmap,Bitmap> {
    private String url =null;
    private ImageView imageView;
    private Context context;
    private LoadScanInfo loadScanInfo;

    public AmbilGambar(String url,ImageView imageView,Context context,LoadScanInfo loadScanInfo){
        this.url            = url;
        this.context        = context;
        this.loadScanInfo   = loadScanInfo;
        this.imageView      = imageView;
    }

    @Override
    protected Bitmap doInBackground(Void... voids) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(this.url);
            InputStream inputStream = url.openConnection().getInputStream();

            bitmap  = BitmapFactory.decodeStream(inputStream);

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        bitmap = Bitmap.createScaledBitmap(bitmap,410,500,true);
        imageView.setImageBitmap(bitmap);
        loadScanInfo.setelahScan();
    }
}
