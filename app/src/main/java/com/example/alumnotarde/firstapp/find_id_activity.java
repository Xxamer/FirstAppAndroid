package com.example.alumnotarde.firstapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class find_id_activity extends AppCompatActivity {
    EditText TextID;
    Button FindIDButton;
    TextView SeeTextName;
    TextView SeeTextDescription;
    TextView SeeTextRelease;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); //Hide the bar
        setContentView(R.layout.find_id);
        TextID = (EditText) findViewById(R.id.FindIDText);
        FindIDButton = (Button)findViewById(R.id.FindButton);
        SeeTextName = (TextView) findViewById(R.id.nameofid);
        SeeTextDescription = (TextView) findViewById((R.id.SeeDescription));
        SeeTextDescription.setMovementMethod(new ScrollingMovementMethod());
        SeeTextRelease = (TextView) findViewById((R.id.ReleaseText));
        FindIDButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate();
            }
        });
    }
    private void validate() {
        String ID = TextID.getText().toString();
        TextID.setError(null);
        if(TextUtils.isEmpty(ID)){
            TextID.setError(getString(R.string.Error_required_field));
            TextID.requestFocus();
            return;
        }else{
           RequestIDMovie();
        }
    }
    private void RequestIDMovie() {
        String ID = TextID.getText().toString();
        String url = "http://192.168.201.90:40000/api/movies/"+ID; //The url of your node server
        RequestQueue RequestQueue;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SeeTextName.setText(response.getString("name"));
                            SeeTextDescription.setText(response.getString("description"));
                            SeeTextRelease.setText(response.getString("ageofrelease"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error, this server is not responding", Toast.LENGTH_LONG).show();
                    }
                });
        //Crear una cola de peticiones
        RequestQueue = Volley.newRequestQueue(this);
        RequestQueue.add(jsonObjectRequest);
    }
}
