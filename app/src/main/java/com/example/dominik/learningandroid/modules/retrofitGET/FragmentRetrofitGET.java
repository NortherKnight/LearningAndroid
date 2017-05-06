package com.example.dominik.learningandroid.modules.retrofitGET;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.example.dominik.learningandroid.R;
import com.example.dominik.learningandroid.modules.IModule;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FragmentRetrofitGET extends Fragment implements IModule
{
    private ListView list;
    private ViewGroup container;

    public static final String URL = "http://api.github.com/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState)
    {
        this.container = container;
        return inflater.inflate(R.layout.retrofitGET_fragment, container, false);
    }

    @Override
    public void execute()
    {
        list = (ListView)container.findViewById(R.id.listView_repos);

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
                list.setAdapter(new GitHubRepoAdapter(container.getContext(), repos));
            }

            @Override
            public void onFailure(Call<List<GitHubRepo>> call, Throwable t)
            {
                Toast.makeText(container.getContext(), "error", Toast.LENGTH_SHORT);
            }
        });
    }
}
