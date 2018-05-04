package com.hilmiproject.omdutz.mcafee.Firebase;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.hilmiproject.omdutz.mcafee.ClientPackage.AppDetail;
import com.hilmiproject.omdutz.mcafee.Draw;
import com.hilmiproject.omdutz.mcafee.Notif;
import com.hilmiproject.omdutz.mcafee.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by L on 18/12/17.
 */

public class TerimaPesan extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String type = remoteMessage.getData().get("type");
        if(type.equals("iklanbaru")){
            String iklan = remoteMessage.getData().get("iklan");
            new AppDetail(getApplicationContext()).setIklan(true,iklan);
           // new IklanRequest().getIklan(iklan,getApplicationContext(),null);

        }else{
            AppDetail appDetail = new AppDetail(this);
            int badget = appDetail.getBadgetTotal()+1;
            appDetail.setBadget(badget);
            Draw.setBadge(this,badget);
            sendNotification("Hadiah Anda Telah Kami Kirim","Pemberitahuan Baru");
            Intent intent = new Intent("updateAngka");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
            Intent intent2 = new Intent("reward_update");
            LocalBroadcastManager.getInstance(this).sendBroadcast(intent2);
        }

    }
    private void sendNotification(String messageBody,String type) {
        Intent intent = new Intent(this, Notif.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(type)
                .setContentText(messageBody)
                .setSmallIcon(R.drawable.logopem)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
