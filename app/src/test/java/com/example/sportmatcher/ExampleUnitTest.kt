package com.example.sportmatcher

import com.example.sportmatcher.domain.auth.SignInUseCase
import com.example.sportmatcher.model.authentication.AuthenticatedState
import com.example.sportmatcher.model.authentication.LoginInfo
import com.example.sportmatcher.service.FirebaseAuthService
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun testSignInUseCase(){
        val signInUseCase = SignInUseCase(
            FirebaseAuthService()
        )
        val state = signInUseCase.execute(LoginInfo("mock@mock.com", "mockmock")).blockingGet()
        println(state)
        assert(state is AuthenticatedState)
    }
}
