package com.github.vivchar.network

import android.app.Application
import com.github.vivchar.network.models.GithubFork
import com.github.vivchar.network.models.GithubUser
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Vivchar Vitaly on 08.10.17.
 */
class MainManager(private val context: Application) : EventCenter {

	private val retrofit = Retrofit.Builder().baseUrl(API_URL).addConverterFactory(GsonConverterFactory.create()).build()
	private val githubAPI = retrofit.create(GithubAPI::class.java)
	private val githubClient = GithubClient(githubAPI, this)

	val stargazersRepository = StargazersRepository(githubClient)
	val forksRepository = ForksRepository(githubClient)

	private fun onApplicationStarted() {
		stargazersRepository.onApplicationStarted()
		forksRepository.onApplicationStarted()
	}

	override fun onStargazersReceived(page: Int, stargazers: List<GithubUser>) {
		stargazersRepository.onStargazersReceived(page, stargazers)
	}

	override fun onStargazersFailed(page: Int) {
		stargazersRepository.onStargazersFailed(page)
	}

	override fun onForksReceived(forks: List<GithubFork>) {
		forksRepository.onForksReceived(forks)
	}

	override fun onForksFailed(message: String) {
		forksRepository.onForksFailed(message)
	}

	companion object {
		const val API_URL = "https://api.github.com/"
		private var sMainManager: MainManager? = null
		fun init(application: Application) {
			if (sMainManager == null) {
				sMainManager = MainManager(application)
				sMainManager!!.onApplicationStarted()
			}
		}

		@JvmStatic
		val instance: MainManager
			get() = sMainManager!!
	}
}