package com.future.mvvm_dagger.network

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = true)
data class NetworkResponse<T>(
    val status: String,
    val code: Int,
    val data: T,
)


@Parcelize
@JsonClass(generateAdapter = true)
data class AuthLogin(
    val username: String,
    val password: String,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class UserRegister(
    val username: String,
    val displayName: String,
    val email: String,
    val phoneNo: String = "",
    val role: String = "user",
    val password: String,
) : Parcelable

@Parcelize
@JsonClass(generateAdapter = true)
data class UserAuthenticated(
    val id: String,
    val displayName: String,
    val image: String?,
    val email: String,
    val phoneNo: String,
    val role: String,
    val token: String,
) : Parcelable