package com.norvellium.tasky.repository

interface AuthRepository {
    fun authenticate(token: String)
}