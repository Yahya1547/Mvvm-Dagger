package com.future.mvvm_dagger

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.future.mvvm_dagger.network.ApiResult
import com.future.mvvm_dagger.network.AuthLogin
import com.future.mvvm_dagger.network.AuthService
import com.future.mvvm_dagger.network.UserAuthenticated
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {
    private val mCompositeDisposable = CompositeDisposable()

    private var _username: MutableLiveData<String> = MutableLiveData("")
    val username: LiveData<String> = _username

    val errorUsernameRequired = Transformations.map(username) {
        it.isNullOrEmpty()
    }

    private var _password: MutableLiveData<String> = MutableLiveData("")
    val password: LiveData<String> = _password

    val errorPasswordRequired = Transformations.map(password) {
        it.isNullOrEmpty()
    }

    fun login(user: AuthLogin): LiveData<ApiResult<UserAuthenticated>> {
        val result = MutableLiveData<ApiResult<UserAuthenticated>>()

        Log.d("Login View Model", user.toString())
        mCompositeDisposable.add(
            authService.login(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    result.postValue(ApiResult.success(response.data))
                }, {
                    it.printStackTrace()
                    val error = handleError(it)
                    result.postValue(error)
                })
        )
        return result
    }

    private fun handleError(throwable: Throwable): ApiResult<UserAuthenticated> {
        return when (throwable) {
            is HttpException -> ApiResult.failureHttp("You have entered wrong Username / Password !")
            is IOException -> ApiResult.failureNetwork("Something went wrong. Please try again later.")
            else -> ApiResult.failureUnknown("Something went wrong. Please try again later.")
        }
    }


    fun setUsername(username: String) {
        _username.value = username
    }

    fun setPassword(password: String) {
        _password.value = password
    }

}