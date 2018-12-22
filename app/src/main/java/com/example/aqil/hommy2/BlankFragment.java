package com.example.aqil.hommy2;

import android.app.DialogFragment;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.util.ExponentialBackOff;

import java.io.IOException;
import java.util.Arrays;

public class BlankFragment extends DialogFragment implements View.OnClickListener {
    Button buy;
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 54654;
    GoogleAccountCredential mCredential;
    String API_KEY;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_dialog, container, false);
        buy = v.findViewById(R.id.btn_buy);
        buy.setOnClickListener(this);

        // Do all the stuff to initialize your custom view

        return v;
    }

    @Override
    public void onClick(View view) {

        getToken();
        Snackbar.make(view, "Download Dimulai", Snackbar.LENGTH_LONG)
                .show(); // Don’t forget to show!

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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String IMAGE_DOWNLOAD_PATH = "https://www.googleapis.com/drive/v2/files/1rIM9aPs4t6nvp2tSHrTsHUETLfsxS3_DvIwEdeW3uvA/export?mimeType=application/pdf";
            getContext().startService(com.example.aqil.hommy2.DownloadSongService.getDownloadService(getContext(), IMAGE_DOWNLOAD_PATH, com.example.aqil.hommy2.DirectoryHelper.ROOT_DIRECTORY_NAME.concat("/"), s));

        }
    }

    public void getToken() {
        final String PREFS_NAME = "MyPrefs";
        final String USER_KEY = "user";

        SharedPreferences settings = getContext().getSharedPreferences(PREFS_NAME, 0);
        String username = settings.getString(USER_KEY, null);
        final String[] SCOPES = {"https://www.googleapis.com/auth/userinfo.profile"};
        mCredential = GoogleAccountCredential.usingOAuth2(
                getContext(), Arrays.asList(SCOPES))
                .setBackOff(new ExponentialBackOff());
        mCredential.setSelectedAccountName(username);
        new getToken().execute();


    }
}