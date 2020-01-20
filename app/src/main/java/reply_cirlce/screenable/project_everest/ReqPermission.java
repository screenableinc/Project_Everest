package reply_cirlce.screenable.project_everest;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class ReqPermission {
    Activity context;
    String permission;
    public ReqPermission(Activity context, String permission){
     this.context=context;
     this.permission=permission;

 }

    public boolean Check(){
        if (ContextCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    100);
        }
        return false;
    }
}
