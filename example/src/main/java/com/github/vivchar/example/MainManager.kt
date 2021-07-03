package com.github.vivchar.example

import android.app.Application
import com.github.vivchar.data.GithubClient
import com.github.vivchar.data.ProtocolMapper
import com.github.vivchar.data.repositories.ForksRepository
import com.github.vivchar.data.repositories.StargazersRepository

/**
 * Created by Vivchar Vitaly on 08.10.17.
 */
class MainManager(private val context: Application) {

	private val protocolMapper = ProtocolMapper()
	private val githubClient = GithubClient(API_URL, protocolMapper)

	val stargazersRepository = StargazersRepository(githubClient)
	val forksRepository = ForksRepository(githubClient)

	private fun onApplicationStarted() {
		stargazersRepository.onApplicationStarted()
		forksRepository.onApplicationStarted()
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