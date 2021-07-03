package com.github.vivchar.data.repositories

import com.github.vivchar.data.GithubClient
import com.github.vivchar.domain.entities.Fork
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
class ForksRepository(private val client: GithubClient) {

	private val forksSubject = ReplaySubject.createWithSize<List<Fork>>(1)
	val forks: Observable<List<Fork>> get() = forksSubject.hide()

	fun onApplicationStarted() {
		/* move to presenter/interactor */
		fetchForks().subscribe()
	}

	fun fetchForks(): Observable<List<Fork>> {
		return client.fetchForks().doOnNext { onForksReceived(it) }
	}

	private fun onForksReceived(forks: List<Fork>) {
		forksSubject.onNext(forks)
	}

}