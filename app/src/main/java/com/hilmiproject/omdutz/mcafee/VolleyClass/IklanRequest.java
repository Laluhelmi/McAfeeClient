package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.Interface.MyGoodListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * Created by L on 04/01/18.
 */

public class IklanRequest {

        public Bitmap getBitmapFromURL(String src) {
            try {
                java.net.URL url = new java.net.URL(src);
                HttpURLConnection connection = (HttpURLConnection) url
                        .openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        public void getIklan(final String iklan, final Context context, final ImageView imageView
                            , final MyGoodListener listener){
            new AsyncTask<Void,Void,Bitmap >(){
                @Override
                protected Bitmap doInBackground(Void... voids) {
                    String src = Http.url+"template/iklan/"+iklan;
                    return getBitmapFromURL(src);
                }

                @Override
                protected void onPostExecute(Bitmap s) {
                    super.onPostExecute(s);
                    try {
                        File folder = new File(context.getCacheDir().getAbsolutePath()+"/gambar");
                        if(folder.exists() == false)
                            folder.mkdirs();
                        File file = new File(context.getCacheDir()+"/gambar",iklan);
                        FileOutputStream stream = new FileOutputStream(file);
                        s.compress(Bitmap.CompressFormat.JPEG,100,stream);
                        Bitmap bitmap = loadIklan(iklan,context);
                        if(imageView != null)imageView.setImageBitmap(bitmap);
                        listener.doAny(s.toString());
                       // new AppDetail(context).setIklan(true,iklan);
                   }catch (Exception e){
                        Toast.makeText(context,
                                e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }.execute();
        }
        public Bitmap loadIklan(String iklan,Context context){
            try {
                File file = new File(context.getCacheDir()+"/gambar",iklan);
                Bitmap bitmap = BitmapFactory.decodeStream(new FileInputStream(file));

                return bitmap;
            }catch (Exception e){}

            return null;
        }
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height,
                matrix, false);

        return resizedBitmap;
    }
    public void loadIklanData(){

    }
}
