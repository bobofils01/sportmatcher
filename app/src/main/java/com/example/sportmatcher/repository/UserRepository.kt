package com.example.sportmatcher.service.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.FirebaseAuthService
import java.util.regex.Pattern

class UserRepository{

    private val authService : FirebaseAuthService = FirebaseAuthService()

    fun login(loginInfo: LoginInfo) :LiveData<String>{
        val errorMessage = MutableLiveData<String>()

        if(isEmailValid(loginInfo.email)){
            if(loginInfo.userPassWord.length<8 && !isPasswordValid(loginInfo.userPassWord)){
                errorMessage.value = "Invalid Password"
            }else{
                authService.signIn(loginInfo.email, loginInfo.userPassWord)
                errorMessage.value = "Successful Login"
            }
        }else{
            errorMessage.value = "Invalid Email"
        }

        return  errorMessage
    }


    fun signup(email: String, password: String, confirmPassword:String) : LiveData<String>{
        val errorMessage = MutableLiveData<String>()

        if(isEmailValid(email)){
            if(password.length<8 && !isPasswordValid(password)){
                errorMessage.value = "Invalid Password"
            }else if(!password.equals(confirmPassword)){
                errorMessage.value = "unmatch password"
            }else{
                authService.register(email, password)
                errorMessage.value = "Successful register"
            }
        }else{
            errorMessage.value = "Invalid Email"
        }
        return  errorMessage
    }
    fun isEmailValid(email: String): Boolean {
        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun isPasswordValid(password: String): Boolean{
        val expression  ="^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#\$%^&+=!])(?=\\\\S+\$).{4,}\$";
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(password)
        return matcher.matches()
    }
}