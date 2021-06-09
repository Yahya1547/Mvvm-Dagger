package com.future.mvvm_dagger

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.future.mvvm_dagger.databinding.ActivityMainBinding
import com.future.mvvm_dagger.network.ApiState
import com.future.mvvm_dagger.network.AuthLogin
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = ViewModelProviders.of(this, viewModelFactory)[LoginViewModel::class.java]

        binding.editTextUsername.setOnFocusChangeListener { _, hasFocus ->
            val username = binding.editTextUsername.text.toString()
            if (!hasFocus && username.isEmpty()) {
                binding.tvErrorUsernameRequired.visibility = View.VISIBLE
            }
        }

        binding.editTextPassword.setOnFocusChangeListener { _, hasFocus ->
            val password = binding.editTextPassword.text.toString()
            if (!hasFocus && password.isEmpty()) {
                binding.tvErrorPasswordRequired.visibility = View.VISIBLE
            }
        }

        binding.editTextUsername.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setUsername(text.toString())
        }

        loginViewModel.errorUsernameRequired.observe(this, { error ->
            if (!error) {
                binding.tvErrorUsernameRequired.visibility = View.INVISIBLE
            }
        })

        binding.editTextPassword.doOnTextChanged { text, _, _, _ ->
            loginViewModel.setPassword(text.toString())
        }

        loginViewModel.errorPasswordRequired.observe(this, { error ->
            if (!error) {
                binding.tvErrorPasswordRequired.visibility = View.INVISIBLE
            }
        })

        binding.btnLogin.setOnClickListener {
            if (isFormFilledCorrectly()) {
                login()
            }
        }

        binding.tvRegister.setOnClickListener {
//            openActivityRegister()
        }
    }

    private fun isFormFilledCorrectly(): Boolean {
        var isError = false

        if (loginViewModel.errorUsernameRequired.value == true) {
            binding.tvErrorUsernameRequired.visibility = View.VISIBLE
            isError = isError || true
        } else {
            binding.tvErrorUsernameRequired.visibility = View.INVISIBLE
            isError = isError || false
        }

        if (loginViewModel.errorPasswordRequired.value == true) {
            binding.tvErrorPasswordRequired.visibility = View.VISIBLE
            isError = isError || true
        } else {
            binding.tvErrorPasswordRequired.visibility = View.INVISIBLE
            isError = isError || false
        }

        return !isError
    }

    private fun login() {
        isEnabledButtonAfterClick(false)
        displayErrorLoginMessage(false)

        val username = binding.editTextUsername.text.toString()
        val password = binding.editTextPassword.text.toString()
        val user = AuthLogin(
            username = username,
            password = password,
        )

        loginViewModel.login(user).observe(this, { result ->
            when (result.state) {
                ApiState.SUCCESS -> {
                    displayErrorLoginMessage(false)
                    openActivityHome()
                }
                ApiState.HTTP_ERROR -> {
                    displayErrorLoginMessage(true)
                }
                else -> {
                    showSnackBarError()
                }
            }

            isEnabledButtonAfterClick(true)
        })
    }

    private fun displayErrorLoginMessage(show: Boolean) {
        if (show) {
            binding.tvErrorLogin.visibility = View.VISIBLE
        } else {
            binding.tvErrorLogin.visibility = View.INVISIBLE
        }
    }

    private fun showSnackBarError() {
        Log.d("Login Activity", "Show SnackBar")
        Snackbar.make(
            binding.btnLogin,
            R.string.something_went_wrong_text,
            Snackbar.LENGTH_LONG
        )
            .show()
    }

    private fun isEnabledButtonAfterClick(isEnabled: Boolean) {
        binding.btnLogin.isEnabled = isEnabled
        if (isEnabled) {
            binding.btnLogin.text = "Login"
        } else {
            binding.btnLogin.text = "Loading"
        }
    }

    private fun openActivityHome() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}