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

import com.example.dominik.learningandroid.modules.GitHubClient;
import com.example.dominik.learningandroid.modules.GitHubRepo;
import com.example.dominik.learningandroid.modules.GitHubRepoAdapter;
import com.example.dominik.learningandroid.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private ListView list;
    private Spinner spinner;
    private String[] modules = {"Retrofit GET request", "Retrofit POST request"};

    public static final String URL = "http://api.github.com/";
    public static final String DEBUG = "LEARNING";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinner = (Spinner)findViewById(R.id.spinner_moduleSelect);
        spinner.setAdapter(new ArrayAdapter<String>(MainActivity.this,
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

        list = (ListView)findViewById(R.id.listView_repos);

        Retrofit.Builder builder = new Retrofit.Builder().baseUrl(URL)
                                                         .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();
        GitHubClient client = retrofit.create(GitHubClient.class);
        Call<List<GitHubRepo>> call = client.reposForUser("NortherKnight");

        call.enqueue(new Callback<List<GitHubRepo>>()
        {
            @Override
            public void onResponse(Call<List<GitHubRepo>> call, Response<List<GitHubRepo>> response)
            {
                List<GitHubRepo> repos = response.body();

                list.setAdapter(new GitHubRepoAdapter(MainActivity.this, repos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t)
            {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT);
            }
        });
    }
}
