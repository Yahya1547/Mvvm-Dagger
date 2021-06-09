package com.future.mvvm_dagger.network

import io.reactivex.rxjava3.core.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("login")
    fun login(@Body user: AuthLogin): Single<NetworkResponse<UserAuthenticated>>

    @POST("register")
    fun register(@Body user: UserRegister): Single<NetworkResponse<UserAuthenticated>>
}