package com.github.vivchar.network

import com.github.vivchar.domain.entities.Fork
import com.github.vivchar.domain.entities.User

interface EventCenter {
	fun onStargazersReceived(page: Int, stargazers: List<User>)
	fun onStargazersFailed(page: Int)
	fun onForksReceived(forks: List<Fork>)
	fun onForksFailed(message: String)
}