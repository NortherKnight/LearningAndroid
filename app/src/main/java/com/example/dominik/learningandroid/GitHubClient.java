package com.example.dominik.learningandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by HP on 28/04/2017.
 */

public interface GitHubClient
{
    @GET("/user/{user}/repos")
    Call<List<GitHubRepo>> reposForUser(@Path("user") String user);
}
