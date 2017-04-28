package com.example.dominik.learningandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private ListView list;

    public static final String URL = "http://api.github.com/";
    public static final String DEBUG = "LEARNING";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
