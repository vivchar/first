package com.github.vivchar.network

import com.github.vivchar.network.models.GithubForkRaw
import com.github.vivchar.network.models.GithubUserRaw
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
internal class GithubClient(private val githubAPI: GithubAPI, private val center: EventCenter) {

	fun sendStargazersRequest(page: Int) {
		githubAPI.getStargazers(page).enqueue(object : Callback<List<GithubUserRaw>> {
			override fun onResponse(call: Call<List<GithubUserRaw>>, response: Response<List<GithubUserRaw>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					center.onStargazersReceived(page, body)
				} else {
					center.onStargazersFailed(page)
				}
			}

			override fun onFailure(call: Call<List<GithubUserRaw>>, t: Throwable) {
				center.onStargazersFailed(page)
			}
		})
	}

	fun sendForksRequest() {
		githubAPI.forks.enqueue(object : Callback<List<GithubForkRaw>> {
			override fun onResponse(call: Call<List<GithubForkRaw>>, response: Response<List<GithubForkRaw>>) {
				val body = response.body()
				if (response.isSuccessful && body != null) {
					center.onForksReceived(body)
				} else {
					center.onForksFailed("Response is failed")
				}
			}

			override fun onFailure(call: Call<List<GithubForkRaw>>, t: Throwable) {
				center.onForksFailed(t.message!!)
			}
		})
	}

}