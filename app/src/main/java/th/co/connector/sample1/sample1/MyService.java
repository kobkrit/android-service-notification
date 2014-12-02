package th.co.connector.sample1.sample1;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by kobkrit on 12/2/14.
 */
public class MyService extends Service {
    private Handler mHandler;
    private Runnable run;
    private int count = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        mHandler = new Handler(Looper.getMainLooper());
        Toast.makeText(this, "Congrats! MyService Created", Toast.LENGTH_SHORT).show();
        run = new Runnable() {
            public void run() {
                makeNotification();
                mHandler.postDelayed(this, 1000);
            }
        };
    }

    void makeNotification(){
        //TODO: Load information from DB

        //TODO: Check whether need to notify something?

        //TODO: Notify it.


        ++count;
        Toast.makeText(this, "Congrats! MyService Notify "+count, Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_launcher)
                        .setContentTitle("My Notification Title "+count)
                        .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                        .setContentText("Something interesting happened "+count);
        int NOTIFICATION_ID = 12345;

        //Add sound in notification here.
        Intent targetIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nManager.notify(NOTIFICATION_ID, builder.build());
    }


    @Override
    public void onStart(Intent intent, int startId) {
        Toast.makeText(this, "My Service Started", Toast.LENGTH_SHORT).show();
        mHandler.post(run);

    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "MyService Stopped", Toast.LENGTH_SHORT).show();
        mHandler.removeCallbacks(run);
    }
}
