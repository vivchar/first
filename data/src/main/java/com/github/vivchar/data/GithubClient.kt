package com.github.vivchar.data

import com.github.vivchar.domain.entities.Fork
import com.github.vivchar.domain.entities.User
import io.reactivex.rxjava3.core.Observable
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Vivchar Vitaly on 12.10.17.
 */
class GithubClient(apiUrl: String, private val protocolMapper: ProtocolMapper) {

	/* move outside if needed */
	private val githubAPI = Retrofit.Builder()
		.baseUrl(apiUrl)
		.addConverterFactory(GsonConverterFactory.create())
		.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
		.build()
		.create(GithubAPI::class.java)

	fun fetchStargazers(page: Int): Observable<List<User>> {
		return githubAPI.getStargazers(page)
			.map { list -> list.map { protocolMapper.mapUser(it) } }
			.onErrorReturn { emptyList() }
	}

	fun fetchForks(): Observable<List<Fork>> {
		return githubAPI.forks
			.map { list -> list.map { protocolMapper.mapFork(it) } }
			.onErrorReturn { emptyList() }
	}
}