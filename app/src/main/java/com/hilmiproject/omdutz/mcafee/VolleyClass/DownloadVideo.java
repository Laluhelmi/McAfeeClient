package com.hilmiproject.omdutz.mcafee.VolleyClass;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.hilmiproject.omdutz.mcafee.ClientPackage.Http;
import com.hilmiproject.omdutz.mcafee.FaqActivity;
import com.hilmiproject.omdutz.mcafee.Interface.VideoListener;
import com.hilmiproject.omdutz.mcafee.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by L on 24/01/18.
 */

public class DownloadVideo {

    public String downloadFile(Listener listenerd){
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(Http.url+"template/assets/img/McAfee_Install(ProcessorI3).mp4");
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP " + connection.getResponseCode()
                        + " " + connection.getResponseMessage();
            }

            // this will be useful to display download percentage
            // might be -1: server did not report the length
            int fileLength = connection.getContentLength();

            // download the file
            input = connection.getInputStream();
            File folderDownload = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/kimsfile/video/");
            if(folderDownload.exists() == false){
                folderDownload.mkdirs();
            }
            output = new FileOutputStream(folderDownload+"/videomacfee.mp4");

            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                // publishing the progress....
                if (fileLength > 0) // only if total length is known
                    //  publishProgress((int) (total * 100 / fileLength));
                    listenerd.download((int) (total * 100 / fileLength));
                    output.write(data, 0, count);
            }
        } catch (Exception e) {
            return e.toString();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (input != null)
                    input.close();
            } catch (IOException ignored) {
            }

            if (connection != null)
                connection.disconnect();
        }
        return null;
    }
    private Context context;
    private VideoListener listener;
    public DownloadVideo(Context c, FaqActivity faqActivity,VideoListener listener){
        this.context = c;
        this.faqActivity = faqActivity;
        this.listener = listener;
        showDialogProgress();

    }
    class AsyntasObject extends AsyncTask<Void,Void,String>{
        private Listener listenerf;
        public AsyntasObject(Listener f){
            this.listenerf = f;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dialog.dismiss();
            listener.lakukan();
            Toast.makeText(context,
                    "Video telah diDownload ", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected String doInBackground(Void... voids) {
            return downloadFile(listenerf);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(context,
                    "Mendownload Video ", Toast.LENGTH_SHORT).show();
        }
    }
    public void downloadProgress(int progres){
        String prog = String.valueOf(progres);
    }
    public void showDialogProgress(){
         dialog = new Dialog(context);
        dialog.setCancelable(false);;
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_progress_download);
        final TextView progress = (TextView) dialog.findViewById(R.id.progress);
        new AsyntasObject(new Listener() {
            @Override
            public void download(final int p) {
                faqActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progress.setText(String.valueOf(p)+" / 100");
                    }
                });
            }
        }).execute();

        dialog.show();
    }
    private Dialog dialog;
    FaqActivity faqActivity;

    interface Listener{
        public void download(int p);
    }
}
