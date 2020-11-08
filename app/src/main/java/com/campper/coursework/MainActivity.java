package com.campper.coursework;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        requestQueue = Volley.newRequestQueue(this);

        final TextView textView = findViewById(R.id.activity_main_text_view);
        button = findViewById(R.id.activity_main__btn_start);

        ParallelTask parallelTask = new ParallelTask(this);
        parallelTask.doInBackground(button);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://jsonplaceholder.typicode.com/todos/1", null,
                listener -> {
                    Log.d("JSON: ",""+listener);
                }, error -> {
                    Log.d("JSON error: ", error.getMessage());
        });

        requestQueue.add(jsonObjectRequest);
    }
}