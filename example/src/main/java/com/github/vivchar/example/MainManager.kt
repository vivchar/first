package com.github.vivchar.example

import android.app.Application
import com.github.vivchar.data.EventCenter
import com.github.vivchar.data.GithubClient
import com.github.vivchar.data.ProtocolMapper
import com.github.vivchar.data.repositories.ForksRepository
import com.github.vivchar.data.repositories.StargazersRepository
import com.github.vivchar.domain.entities.Fork
import com.github.vivchar.domain.entities.User

/**
 * Created by Vivchar Vitaly on 08.10.17.
 */
class MainManager(private val context: Application) : EventCenter {

	private val protocolMapper = ProtocolMapper()
	private val githubClient = GithubClient(API_URL, protocolMapper, this)

	val stargazersRepository = StargazersRepository(githubClient)
	val forksRepository = ForksRepository(githubClient)

	private fun onApplicationStarted() {
		stargazersRepository.onApplicationStarted()
		forksRepository.onApplicationStarted()
	}

	override fun onStargazersReceived(page: Int, stargazers: List<User>) {
		stargazersRepository.onStargazersReceived(page, stargazers)
	}

	override fun onStargazersFailed(page: Int) {
		stargazersRepository.onStargazersFailed(page)
	}

	override fun onForksReceived(forks: List<Fork>) {
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