package com.github.vivchar.data;


import com.github.vivchar.data.raw.GithubForkRaw;
import com.github.vivchar.data.raw.GithubUserRaw;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */
interface GithubAPI {

	@GET("repos/vivchar/RendererRecyclerViewAdapter/stargazers")
	Observable<List<GithubUserRaw>> getStargazers(@Query("page") int page);

	@GET("repos/vivchar/RendererRecyclerViewAdapter/forks")
	Observable<List<GithubForkRaw>> getForks();
}
