package com.github.vivchar.network

import com.github.vivchar.domain.entities.Fork
import com.github.vivchar.domain.entities.User
import com.github.vivchar.network.models.GithubForkRaw
import com.github.vivchar.network.models.GithubUserRaw

class ProtocolMapper {
	fun mapUser(raw: GithubUserRaw) = User(raw.iD, raw.login, raw.avatarUrl, raw.htmlUrl)
	fun mapFork(raw: GithubForkRaw) = Fork(raw.iD, mapUser(raw.owner))
}