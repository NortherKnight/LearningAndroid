package com.example.dominik.learningandroid.basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dominik.learningandroid.modules.retrofitGET.GitHubClient;
import com.example.dominik.learningandroid.modules.retrofitGET.GitHubRepo;
import com.example.dominik.learningandroid.modules.retrofitGET.GitHubRepoAdapter;
import com.example.dominik.learningandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{
    private Spinner spinner;
    private String[] modules = {"Retrofit GET request"};

    public static final String DEBUG = "LEARNING";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner_moduleSelect);
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this,
                           R.layout.support_simple_spinner_dropdown_item, modules));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                Log.d(DEBUG, modules[position]);
                Toast.makeText(MainActivity.this, modules[position], Toast.LENGTH_SHORT);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }
}
