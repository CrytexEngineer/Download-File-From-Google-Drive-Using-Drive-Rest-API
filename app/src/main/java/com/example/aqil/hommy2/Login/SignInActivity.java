package com.example.aqil.hommy2.Login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aqil.hommy2.CustHomeActivity;
import com.example.aqil.hommy2.CustomerActivity;
import com.example.aqil.hommy2.Entity.UserAccount;
import com.example.aqil.hommy2.R;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;
import com.synnapps.carouselview.ViewListener;

import java.io.IOException;
import java.util.Arrays;

/**
 * Activity to demonstrate basic retrieval of the Google user's ID, email address, and basic
 * profile.
 */
public class SignInActivity extends AppCompatActivity implements
        View.OnClickListener {
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String TAG = "SignInActivity";
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;
    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private TextView mStatusTextView;
    final int LOG_IN_CUST = 2;
    final int LOG_IN_ARCH = 1;
    int loginType = 0;
    GoogleAccountCredential mCredential;
    CarouselView carouselView;
    int NUMBER_OF_PAGES = 2;
    String[] sampleImages = {"http://s1.picswalls.com/wallpapers/2014/08/08/beautiful-house-wallpaper_015635431_148.jpg", "http://www.hdnicewallpapers.com/Walls/Big/House%20and%20Bungalow/Beautiful_Luxury_House_with_Swiming_Pool_Wallpapers_for_Desktop.jpg"};
    ImageListener imageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Button listeners
        findViewById(R.id.sign_in_arch_button).setOnClickListener(this);
        findViewById(R.id.sign_in_cust_button).setOnClickListener(this);

        carouselView = (CarouselView) findViewById(R.id.carouselView);
        carouselView.setPageCount(NUMBER_OF_PAGES);
        carouselView.setImageListener(imageListener);

        ViewListener viewListener = new ViewListener() {

            @Override
            public View setViewForPosition(int position) {
                View customView = getLayoutInflater().inflate(R.layout.item_carrousel, null);
                ImageView imageView = customView.findViewById(R.id.item_layout_thumnail);
                switch (position) {
                    case 0:
                        Glide.with(SignInActivity.this).load(sampleImages[position]).apply(new RequestOptions().centerCrop().override(1050, 480)).into(imageView);
                        break;
                    case 1:
                        Glide.with(SignInActivity.this).load(sampleImages[position]).apply(new RequestOptions().centerCrop().override(1050, 480)).into(imageView);
                        break;
                    //set view attributes here
                }
                return customView;
            }
        };

        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Glide.with(SignInActivity.this).load(sampleImages[position]).apply(new RequestOptions().centerCrop().override(1050, 480)).into(imageView);
            }
        };

        carouselView.setViewListener(viewListener);

        // [START configure_signin]
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        Scope scope = new Scope("https://www.googleapis.com/auth/drive.readonly");
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestScopes(scope)
                .requestEmail()
                .build();

        final String[] SCOPES = {"https://www.googleapis.com/auth/userinfo.profile"};
        mCredential = GoogleAccountCredential.usingOAuth2(
                getApplicationContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());


        // [END configure_signin]

        // [START build_client]
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END build_client]

        // [START customize_button]
        // Set the dimensions of the sign-in button.
        // [END customize_button]
        Boolean reqOut = getIntent().getBooleanExtra("REQ_OUT", false);
        if (reqOut) {
            signOut();

        }


    }

    @Override
    public void onStart() {
        super.onStart();

        // [START on_start_sign_in]
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            Log.d(TAG, "onStart: " + "non null");

            updateUI(account);
        }
        if (account == null) {
            Log.d(TAG, "onStart: " + " null");
        }

        // [END on_start_sign_in]
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }

    }
    // [END onActivityResult]

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            Log.d(TAG, "handleSignInResult:  success");
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    // [END handleSignInResult]

    // [START signIn]
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    // [START signOut]
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END signOut]

    // [START revokeAccess]
    private void revokeAccess() {
        mGoogleSignInClient.revokeAccess()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }
    // [END revokeAccess]

    private void updateUI(@Nullable GoogleSignInAccount account) {
        if (account != null) {
            Intent intent;
            switch (loginType) {
                case LOG_IN_ARCH:
                    intent = new Intent(this, CustHomeActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case LOG_IN_CUST:

                    mCredential.setSelectedAccountName(account.getEmail());
                    new getToken().execute();
                     final String PREFS_NAME = "MyPrefs";
                     final String USER_KEY = "user";
                    SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                    String username = account.getEmail();
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(USER_KEY, username);
                    editor.commit();
                    intent = new Intent(this, CustomerActivity.class);
                    UserAccount userAccount = new UserAccount(account.getDisplayName(), account.getPhotoUrl().toString(), account.getEmail());
                    Log.d(TAG, "updateUI: " + account.getPhotoUrl().toString());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("USER ACCOUNT", userAccount);
                    startActivityForResult(intent, LOG_IN_CUST);

                    break;

            }

        } else {


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_arch_button:
                loginType = LOG_IN_ARCH;
                signIn();
                break;
            case R.id.sign_in_cust_button:
                loginType = LOG_IN_CUST;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                }
                com.example.aqil.hommy2.DirectoryHelper.createDirectory(this);
                signIn();
                break;

        }
    }


    public class getToken extends AsyncTask<Void, Integer, String> {
        @Override
        protected String doInBackground(Void... voids) {
            try {


                Log.d("TAG", "doInBackground: " + mCredential.getToken());
                return mCredential.getToken();
            } catch (UserRecoverableAuthIOException e) {
                e.printStackTrace();

                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (GoogleAuthException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
                com.example.aqil.hommy2.DirectoryHelper.createDirectory(this);
            else {   if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
            }}
        }
    }
}
