package com.jonathanwaters.termmanager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    static int notificationID = 0;
    String channelID = "channelID";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.getStringExtra("mesg"), Toast.LENGTH_LONG).show();
        createNotificationChannel(context, channelID);
        Notification notification = new NotificationCompat.Builder(context, channelID)
                .setContentTitle("Term Manager Notification")
                .setContentText(intent.getStringExtra("mesg"))
                .setSmallIcon(R.drawable.ic_launcher_foreground).build();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(notificationID++, notification);
    }

    private void createNotificationChannel(Context context, String channelID) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence channelName = "channel name";
            String channelDescription = "channel description";
            int channelImportance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(channelID, channelName, channelImportance);
            channel.setDescription(channelDescription);
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
