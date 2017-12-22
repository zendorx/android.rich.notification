package com.furry.utils.rich;


import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class FurryPushBuilderService extends IntentService {

    private static String TAG = "FurryPushBuilderService";
    public FurryPushBuilderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {

            Bundle extras = intent.getExtras();
            Context context = this;
            String packageName = context.getPackageName();
            Intent notificationIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            notificationIntent.putExtras(extras);
            Random rnd = new Random(System.currentTimeMillis());

            PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

            Log.w(TAG, "EXTRAS: " + extras.toString());

            String backImageID = "";
            int backImage = 0;
            String title = extras.getString("gcm.notification.title");
            String message = extras.getString("gcm.notification.body");
            String rich_back = extras.getString("rich_back");

            if (rich_back != null && !rich_back.isEmpty())
            {
                backImageID = rich_back;
                backImage = context.getResources().getIdentifier(backImageID, "drawable", context.getPackageName());

                if (backImage == 0)
                {
                    backImageID = "";
                }
            }

            List<String> imagesList = new ArrayList<>();
            if (backImageID.isEmpty())
            {
                try {
                    ApplicationInfo ai = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
                    Bundle bundle = ai.metaData;
                    if (bundle != null) {

                        String idBase = "furry.rich.";
                        int count = 1;
                        while (true)
                        {
                            String value = bundle.getString(idBase + String.valueOf(count));
                            if (value != null)
                            {
                                imagesList.add(value);
                            }
                            else
                            {
                                break;
                            }
                            count++;
                        }

                        if (imagesList.size() > 0)
                        {
                            backImageID = imagesList.get(rnd.nextInt(imagesList.size()));
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    FurryPushReceiver.completeWakefulIntent(intent);
                    return;
                }
            }

            int smallIcon = context.getResources().getIdentifier("ic_launcher", "drawable", context.getPackageName());
            if (smallIcon == 0)
            {
               // context.getResources().getIdentifier("ic_launcher", "mipmap", context.getPackageName());//TODO fix maybe
            }

            if (smallIcon == 0 || backImage == 0) {
                Log.e(TAG, "Push configuration error. SmallIcon or BackImage not found");
                FurryPushReceiver.completeWakefulIntent(intent);
                return;
            }

            RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.rich_notification);
            contentView.setInt(R.id.rich_layout, "setBackgroundResource", backImage);
            contentView.setImageViewResource(R.id.image, smallIcon);
            contentView.setTextViewText(R.id.title, title);
            contentView.setTextViewText(R.id.text, message);

            Uri notificationSound = RingtoneManager.getActualDefaultRingtoneUri(context, RingtoneManager.TYPE_NOTIFICATION);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(smallIcon)
                    .setContentIntent(contentIntent)
                    .setContent(contentView)
                    .setTicker(message)
                    .setAutoCancel(true)
                    .setPriority(99999)
                    .setSound(notificationSound)
                    .setVibrate(new long[] {500,1000})
                    .setContentTitle(title);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(UUID.randomUUID().hashCode(), notificationBuilder.build());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            FurryPushReceiver.completeWakefulIntent(intent);
        }
    }
}
