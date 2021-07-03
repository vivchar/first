package com.github.vivchar.network

import com.github.vivchar.network.models.GithubFork
import com.github.vivchar.network.models.GithubUser

interface EventCenter {
	fun onStargazersReceived(page: Int, stargazers: List<GithubUser>)
	fun onStargazersFailed(page: Int)
	fun onForksReceived(forks: List<GithubFork>)
	fun onForksFailed(message: String)
}