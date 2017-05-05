package com.example.dominik.learningandroid.basic;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dominik.learningandroid.modules.GitHubClient;
import com.example.dominik.learningandroid.modules.GitHubRepo;
import com.example.dominik.learningandroid.modules.GitHubRepoAdapter;
import com.example.dominik.learningandroid.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
{

    private ListView list;
    private ExpandableListView sectionList;
    private ExpandableListAdapter adapter;

    private List<String> titleList;
    private HashMap<String, List<String>> detailHash;

    public static final String URL = "http://api.github.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sectionList = (ExpandableListView)findViewById(R.id.expandableListView_sectionList);

        titleList = new ArrayList<>();
        titleList.add("Modules");
        detailHash = new HashMap<>();
        List<String> modules = new ArrayList<>();
        modules.add("Retrofit GET request");
        detailHash.put(titleList.get(0), modules);

        adapter = new CustomExpandableListAdapter(this, titleList, detailHash);
        sectionList.setAdapter(adapter);
        sectionList.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition,
                                        int childPosition, long id)
            {
                Toast.makeText(getApplicationContext(), detailHash.get(titleList.get(groupPosition))
                              .get(childPosition), Toast.LENGTH_SHORT);
                return false;
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
