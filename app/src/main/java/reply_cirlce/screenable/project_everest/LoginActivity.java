package reply_cirlce.screenable.project_everest;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import com.google.android.material.snackbar.Snackbar;
import androidx.emoji.text.EmojiCompat;
import androidx.emoji.text.FontRequestEmojiCompatConfig;
import androidx.core.provider.FontRequest;
import androidx.appcompat.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.hbb20.CountryCodePicker;
//import com.vanniktech.emoji.EmojiManager;
//import com.vanniktech.emoji.ios.IosEmojiProvider;

import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;
    private Globals globals  = new Globals();


    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private ProgressBar progressBar;
    private Button mEmailSignInButton;
    ImageView logo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Emoji support

        FontRequest fontRequest = new FontRequest(
                "com.google.android.gms.fonts",
                "com.google.android.gms",
                "Noto Color Emoji Compat",
                R.array.com_google_android_gms_fonts_certs);

        EmojiCompat.Config config = new FontRequestEmojiCompatConfig(this,fontRequest);
        EmojiCompat.init(config);

//        startActivity(new Intent(LoginActivity.this,MessageThread.class));
//        finish();


        String status = getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE).getString("stage","");

        if(status.equals("verification")){
            startActivity(new Intent(LoginActivity.this, Verification.class));

        }else if(status.equals("setup")){
            startActivity(new Intent(LoginActivity.this, SetUp.class));


        }else if(status.equals("loggedin")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();

        }
        setContentView(R.layout.activity_login);

        logo=findViewById(R.id.logo);
//        final FontRequest fontRequest = new FontRequest(
//                "com.google.android.gms.fonts",
//                "com.google.android.gms",
//                "Noto Color Emoji Compat",
//                R.array.com_google_android_gms_fonts_certs);
//        final EmojiCompat.Config config = new
//                FontRequestEmojiCompatConfig(getApplicationContext(), fontRequest);
//        EmojiCompat.init(config);


        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);

//        startActivity(new Intent(LoginActivity.this,MainActivity.class));

//        ccp
        final CountryCodePicker ccp = (CountryCodePicker) findViewById(R.id.country);
        EditText number = (EditText) findViewById(R.id.number);

        ccp.registerCarrierNumberEditText(number);

        progressBar = (ProgressBar) findViewById(R.id.progress);
        mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin(ccp.getFullNumber());
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(String number) {
        String gencode = genVerificationCode();
        String credentials = "{\"number\":"+number+",\"verificationCode\":"+gencode+"}";
        new UserLoginTask().execute(credentials,number,gencode);
        


    }
    private String genVerificationCode(){
        SecureRandom random = new SecureRandom();
        int num = random.nextInt(100000);
        String formatted = String.format("%05d", num);
        return formatted;

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

//        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<String, Integer, JSONObject> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mEmailSignInButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject response=new JSONObject();
            try{
//                TODO:
                Log.w(globals.TAG,"starting");
                String accessApi = new AccessApi().sendPost(globals.urlJoin,params[0]);
                response = new JSONObject(accessApi);
                if(response.getInt("code")==1){
//                    successful join, store number in sharedpref and go to verify screen
                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(Globals.SHARED_PREF_LOGIN,MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("stage","verification");
                    editor.putString("number",params[1]);

                    editor.putString("verification_code",params[2]);
                    editor.commit();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this, logo, getResources().getResourceName(R.string.logo_trans_name));

                            startActivity(new Intent(LoginActivity.this, Verification.class),options.toBundle());


                        }
                    });



                }else if(response.getInt("code")==0){

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEmailSignInButton.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
//                            show server error
                            Toast.makeText(getApplicationContext(),"Server Error",Toast.LENGTH_LONG).show();
                        }
                    });

                }
                Log.w(globals.TAG,accessApi);
            }catch (Exception e){
//                handle network/connection  errors
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEmailSignInButton.setVisibility(View.VISIBLE);
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Check network connection",Toast.LENGTH_LONG).show();
                    }
                });


            }

            return response;
        }


    }
}

