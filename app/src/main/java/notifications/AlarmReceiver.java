package notifications;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.moumita.caloriecountergeb.R;

import com.example.moumita.caloriecountergeb.HomeTabActivity;

import static android.app.NotificationManager.IMPORTANCE_DEFAULT;

public class AlarmReceiver extends BroadcastReceiver{

    private String alarmType;

    private static final String CHANNEL_ID = "com.singhajit.notificationDemo.channelId";

    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        alarmType = bundle.getString("alarm_time");

        Intent notificationIntent = new Intent(context, HomeTabActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(NotificationActivity.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notificationUri);
        r.play();
        Resources res = context.getResources();

        Notification.Builder builder = new Notification.Builder(context);
        Notification notification;
        if(alarmType.equals("breakfast")){
          notification  = builder.setContentTitle("Breakfast Reminder")
                    .setContentText("Start your day with a great breakfast")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.drawable.food2)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.food3))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent).build();
        }
        else if(alarmType.equals("lunch")){
            notification  = builder.setContentTitle("Lunch Reminder")
                    .setContentText("Take a break, Take lunch")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.drawable.food2)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.food3))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent).build();
        }
        else
        {
            notification  = builder.setContentTitle("Dinner Reminder")
                    .setContentText("Tired? Recharge yourself with a great dinner")
                    .setTicker("New Message Alert!")
                    .setSmallIcon(R.drawable.food2)
                    .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.food3))
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent).build();
        }



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setChannelId(CHANNEL_ID);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "NotificationDemo",
                    IMPORTANCE_DEFAULT
            );
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notification);
    }

}