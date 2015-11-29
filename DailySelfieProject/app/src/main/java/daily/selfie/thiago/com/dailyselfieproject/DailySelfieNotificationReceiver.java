package daily.selfie.thiago.com.dailyselfieproject;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailySelfieNotificationReceiver extends BroadcastReceiver {

    private final long[] mVibratePattern = { 0, 300, 100, 300 };
    private final int NOTIFICATION_ID = 1;

    private Intent mNotificationSelfieReminderIntent;
    private PendingIntent mContentSelfieReminderIntent;

    @Override
    public void onReceive(Context context, Intent intent) {

        mNotificationSelfieReminderIntent = new Intent(context,MainActivity.class);
        mContentSelfieReminderIntent = PendingIntent.getActivity(context, 0,
                mNotificationSelfieReminderIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification.Builder notificationBuilder = new Notification.Builder(
                context).setTicker(context.getResources().getString(R.string.ticker_text))
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setAutoCancel(true).setContentTitle(context.getResources().getString(R.string.content_title))
                .setContentText(context.getResources().getString(R.string.content_text)).setContentIntent(mContentSelfieReminderIntent)
                .setVibrate(mVibratePattern)
                .setSmallIcon(android.R.drawable.ic_menu_camera);

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID,
                notificationBuilder.build());

    }
}
