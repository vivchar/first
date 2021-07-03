package com.github.vivchar.data.repositories

import com.github.vivchar.data.GithubClient
import com.github.vivchar.domain.entities.Fork
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
class ForksRepository internal constructor(private val client: GithubClient) {

	private val githubForksSubject = ReplaySubject.createWithSize<List<Fork>>(1)

	fun onApplicationStarted() {
		client.sendForksRequest()
	}

	fun onForksReceived(forks: List<Fork>) {
		githubForksSubject.onNext(forks)
	}

	fun onForksFailed(message: String) {
		githubForksSubject.onNext(ArrayList())
	}

	val githubForks: Observable<List<Fork>> get() = githubForksSubject.hide()
}