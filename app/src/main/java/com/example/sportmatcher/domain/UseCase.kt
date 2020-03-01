package com.example.sportmatcher.domain

interface UseCase<INPUT, OUTPUT> {
    fun execute(payload:INPUT):OUTPUT
}