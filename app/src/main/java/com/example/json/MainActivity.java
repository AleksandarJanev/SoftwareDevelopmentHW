package com.example.json;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewResult;
    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextViewResult = findViewById(R.id.text_view_result);
        Button buttonParse = findViewById(R.id.button_parse);

        mQueue = Volley.newRequestQueue(this);
        buttonParse.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                jsonParse();
            }
        });
    }

    private void jsonParse(){
        String url = "http://www.objgen.com/json/models/QgUc";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            //If it is successfull, the following is called:
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("weatherData");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject weatherData = jsonArray.getJSONObject(i);

                        String temp = weatherData.getString("temp");
                        int min = weatherData.getInt("min");
                        int max = weatherData.getInt("max");
                        String weather = weatherData.getString("weather");
                        int id = weatherData.getInt("id");
                        String condition = weatherData.getString("condition");
                        String description = weatherData.getString("description");
                        int pressure = weatherData.getInt("pressure");
                        int humidity = weatherData.getInt("humidity");

                        mTextViewResult.append(temp + ", " + String.valueOf(min));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            //If it is NOT successfull, the following is called:
            @Override
            public void onErrorResponse(VolleyError error) {
            error.printStackTrace();
            }
        });

        mQueue.add(request);
    }
}