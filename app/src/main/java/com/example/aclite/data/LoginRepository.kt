package com.example.aclite.data

import com.example.aclite.R
import com.example.aclite.ui.MainViewModel
import kotlinx.coroutines.delay

class LoginRepository(
    private val loginRemoteDataSource: LoginRemoteDataSource = LoginRemoteDataSource()
) {

    suspend fun tryLogin(user : String, pass: String) : LoginResult =
        loginRemoteDataSource.tryLogin(user, pass)
}