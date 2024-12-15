package com.karim.hackathon

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform