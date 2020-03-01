package com.example.sportmatcher.domain

interface NoInputUseCase<OUTPUT>{
    fun execute():OUTPUT
}