package reply_cirlce.screenable.project_everest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        supportPostponeEnterTransition();
        Bundle bundle = getIntent().getExtras();
        String pic_url = bundle.getString("profile_pic_url_lg");
        String name=bundle.getString("fullname");
        String username = bundle.getString("UserID");
        TextView _username=findViewById(R.id.username);
        TextView _name = findViewById(R.id.name);
        _name.setText(name);_username.setText(username);
        CircleImageView circleImageView = findViewById(R.id.profile_image);

        Picasso.get()
                .load(pic_url)
                .noFade()
                .into(circleImageView, new Callback() {
                    @Override
                    public void onError(Exception e) {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }


                });

    }
}
