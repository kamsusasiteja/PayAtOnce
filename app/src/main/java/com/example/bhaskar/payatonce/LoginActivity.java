package com.example.bhaskar.payatonce;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.VoiceInteractor;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;


public class LoginActivity extends AppCompatActivity {
    String url = "http://200899a0.ngrok.io/WebApplication1/api/Products/";
    EditText pass;
    AutoCompleteTextView ph;
    RequestQueue requestQueue;
    String passwd, bal, el, w, m, tv, ga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);


        pass = (EditText) findViewById(R.id.password);
        ph = (AutoCompleteTextView) findViewById(R.id.phno);
    }

    public void signin(View view) throws IOException {
        String phonno = ph.getText().toString();
        url = url + phonno;
        fetchData();
        if (pass.getText().toString() == passwd) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("bal", bal);
            intent.putExtra("el", el);
            intent.putExtra("w", w);
            intent.putExtra("tv", tv);
            intent.putExtra("ga", ga);

            startActivity(intent);
        } else {
            //Show wrong passwd
        }
    }

    public void fetchData()  {
        StringRequest s1 = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject root= new JSONObject(response);
                    passwd = root.getString("password");
                    bal =root.getString("BankBal");
                    el = root.getString("Electricity");
                    w = root.getString("Water");
                    m=root.getString("Mobile");
                    tv=root.getString("Tv");
                    ga=root.getString("Gas");

                } catch (JSONException e) {


                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(s1);
    }
}
