package com.example.aclite.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.DisposableHandle
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class LoginRemoteDataSource {

    suspend fun tryLogin(user : String, pass: String) : LoginResult = withContext(Dispatchers.IO){
        delay(2000)
        val userError = !user.contains("@")
        val passError = pass.length < 5

        LoginResult(
            userError, passError
        )
    }
}