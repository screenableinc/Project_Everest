package reply_cirlce.screenable.project_everest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class BootCompletedReciever extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("INTENT_REC","recieved intent");

        launchTestService(context);
    }
    public void launchTestService(Context context) {
        Toast.makeText(context,"Network available",Toast.LENGTH_LONG).show();
        // Construct our Intent specifying the Service
//        context.registerReceiver(new ConnectivityStateReciever())
//        Intent i = new Intent(, Misc.class);kjhjbkgjbkjjljknbgcvh,klgdc bhhfjjvfgsnnhfccgxchjvhbd gviftdhfd5689tgde46tuygf
//        grow up sharonjguoctcmc,tdxcvkierrtyghoyy6cfgklctxhlgl;kjhcxzrtyijkjhtre kjgd
//        // Add extras to the bundle
//        i.putExtra("foo", "bar");
//        // Start the service
//        startService(i);
    }
}
