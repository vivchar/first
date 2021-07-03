package com.github.vivchar.network

import android.util.Log
import com.github.vivchar.network.models.GithubUserRaw
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * Created by Vivchar Vitaly on 09.10.17.
 */
class StargazersRepository internal constructor(private val client: GithubClient) {

	private val githubUsersSubject = ReplaySubject.createWithSize<List<GithubUserRaw>?>(1)
	private var currentPage = 1
	private var lastLoadedPage = 0
	private var hasMore = false
	private var isReloading = false
	private val originalUsers: MutableList<GithubUserRaw> = ArrayList()

	fun sendReloadRequest() {
		Log.d(TAG, "sendReloadRequest")
		/* vivchar: to avoid the API rate limit of the github https://developer.github.com/v3/#rate-limiting */
		if (githubUsersSubject.value!!.isEmpty()) {
			isReloading = true
			currentPage = 1
			sendPageLoadRequest(currentPage)
		} else {
			githubUsersSubject.onNext(ArrayList(originalUsers)) //temporary workaround
		}
	}

	fun sendLoadMoreRequest() {
		if (hasMore) {
			sendPageLoadRequest(lastLoadedPage + 1)
		}
	}

	private fun sendPageLoadRequest(page: Int) {
		Log.d(TAG, "sendPageLoadRequest: $page")
		client.sendStargazersRequest(page)
	}

	fun onApplicationStarted() {
		sendPageLoadRequest(currentPage)
	}

	fun onStargazersReceived(page: Int, list: List<GithubUserRaw>) {
		Log.d(TAG, "onStargazersReceived: " + page + " list: " + list.size)
		lastLoadedPage = page
		hasMore = list.isNotEmpty()
		val newList = ArrayList<GithubUserRaw>()
		if (isReloading) {
			isReloading = false
		} else if (githubUsersSubject.value != null) { //load more response
			newList.addAll(0, githubUsersSubject.value!!)
		}
		newList.addAll(list)
		originalUsers.addAll(ArrayList(newList))
		githubUsersSubject.onNext(newList)
	}

	fun onStargazersFailed(page: Int) {
		Log.e(TAG, "onStargazersFailed: $page")
		githubUsersSubject.onNext(ArrayList())
	}

	val all: Observable<List<GithubUserRaw>> get() = githubUsersSubject.hide()

	val top10: Observable<List<GithubUserRaw>> get() = githubUsersSubject.hide().map { ArrayList(it.subList(0, it.size.coerceAtMost(10))) }

	companion object {
		private val TAG = StargazersRepository::class.java.simpleName
	}
}