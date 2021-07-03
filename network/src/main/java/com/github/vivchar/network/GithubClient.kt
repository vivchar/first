package com.github.vivchar.network

import com.github.vivchar.network.models.GithubFork
import com.github.vivchar.network.models.GithubUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
internal class GithubClient(private val githubAPI: GithubAPI, private val center: EventCenter) {

	fun sendStargazersRequest(page: Int) {
		githubAPI.getStargazers(page).enqueue(object : Callback<List<GithubUser>> {
			override fun onResponse(call: Call<List<GithubUser>>, response: Response<List<GithubUser>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					center.onStargazersReceived(page, body)
				} else {
					center.onStargazersFailed(page)
				}
			}

			override fun onFailure(call: Call<List<GithubUser>>, t: Throwable) {
				center.onStargazersFailed(page)
			}
		})
	}

	fun sendForksRequest() {
		githubAPI.forks.enqueue(object : Callback<List<GithubFork>> {
			override fun onResponse(call: Call<List<GithubFork>>, response: Response<List<GithubFork>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					center.onForksReceived(body)
				} else {
					center.onForksFailed("Response is failed")
				}
			}

			override fun onFailure(call: Call<List<GithubFork>>, t: Throwable) {
				center.onForksFailed(t.message!!)
			}
		})
	}

}