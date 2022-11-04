package com.norvellium.tasky.repository

import com.norvellium.tasky.core.web.TaskyApi

class AuthRepositoryImpl(
    val api: TaskyApi
) : AuthRepository {
    override fun authenticate(token: String) {
//        api.checkAuthentication()
    }
}