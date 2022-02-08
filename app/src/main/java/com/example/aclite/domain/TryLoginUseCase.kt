package com.example.aclite.domain

import com.example.aclite.data.LoginRepository

class TryLoginUseCase(
    private val loginRepository: LoginRepository = LoginRepository()
) {
    suspend operator fun invoke(user: String, pass: String) = loginRepository.tryLogin(user,pass)
}