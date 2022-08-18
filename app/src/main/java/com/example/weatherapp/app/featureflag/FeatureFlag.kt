package com.example.weatherapp.app.featureflag

enum class FeatureFlag(
    val id: String,
    val displayName: String,
    val forceRestart: Boolean = true,
    val dependencies: List<FeatureFlag> = listOf()
) {
    CHAT(
        id = "chat",
        displayName = "Chat",
        forceRestart = true
    )
}
