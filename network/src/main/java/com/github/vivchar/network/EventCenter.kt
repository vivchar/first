package com.github.vivchar.network

import com.github.vivchar.network.models.GithubForkRaw
import com.github.vivchar.network.models.GithubUserRaw

interface EventCenter {
	fun onStargazersReceived(page: Int, stargazers: List<GithubUserRaw>)
	fun onStargazersFailed(page: Int)
	fun onForksReceived(forks: List<GithubForkRaw>)
	fun onForksFailed(message: String)
}