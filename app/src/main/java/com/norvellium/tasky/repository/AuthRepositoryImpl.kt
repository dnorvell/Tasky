package com.norvellium.tasky.repository

import com.norvellium.tasky.web.TaskyApi

class AuthRepositoryImpl(
    val api: TaskyApi
) : AuthRepository {
    override fun checkAuthentication() {
        
    }
}