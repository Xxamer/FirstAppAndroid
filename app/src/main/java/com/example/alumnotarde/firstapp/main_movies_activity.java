package com.example.alumnotarde.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class main_movies_activity extends AppCompatActivity {
    Button ButtonOneMovie;
    Button ButtonAllMovie;
    TextView SeeText;
    Button ButtonFind;
    TextView NameText1;
    TextView DescriptionText1;
    TextView ReleaseDateText1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_movies);
        ButtonOneMovie = (Button) findViewById(R.id.OneMovieButton);
        ButtonAllMovie = (Button) findViewById(R.id.AllMoviesButton);
        ButtonFind =(Button) findViewById(R.id.OpenID);
        SeeText = (TextView) findViewById(R.id.SeeText);
        SeeText.setMovementMethod(new ScrollingMovementMethod());
        NameText1 =(TextView) findViewById(R.id.nametext1);
        DescriptionText1=(TextView) findViewById(R.id.descriptiontext1);
        DescriptionText1.setMovementMethod(new ScrollingMovementMethod());
        ReleaseDateText1=(TextView)findViewById(R.id.releasedatetext1);
        ButtonOneMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestfirstmovie();
            }
        });
        ButtonAllMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestallmovie();
            }
        });
        ButtonFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenIDActivity();
            }
        });
    }

    private void OpenIDActivity() {
        Intent intent = new Intent(getApplicationContext(), find_id_activity.class);
        startActivity(intent);
    }

    private void requestallmovie() {
        String url = "http://192.168.201.90:40000/api/all-movies"; //The url of your node server
        RequestQueue RequestQueue;
        JsonArrayRequest  JsonArrayRequest = new  JsonArrayRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONArray>(){

                    @Override
                    public void onResponse(JSONArray response) {
                        NameText1.setText("");
                        DescriptionText1.setText("");
                        ReleaseDateText1.setText("");
                        SeeText.setText("Response: " + response.toString() +"\n");
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error, this server is not responding", Toast.LENGTH_LONG).show();

                    }
                });
        //Crear una cola de peticiones
        RequestQueue = Volley.newRequestQueue(this);
        RequestQueue.add( JsonArrayRequest);
    }



    public void requestfirstmovie() {
        String url = "http://192.168.201.90:40000/api/one-movie"; //The url of your node server
        RequestQueue RequestQueue;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            NameText1.setText(response.getString("name"));
                            DescriptionText1.setText(response.getString("description"));
                            ReleaseDateText1.setText(response.getString("ageofrelease"));
                            SeeText.setText("");
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