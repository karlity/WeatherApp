package com.example.weatherapp.app.navigation

import androidx.navigation.NamedNavArgument

// Interface for the navigation command that will get passed to the NavigationHandler.kt
interface NavigationCommand {

    val arguments: List<NamedNavArgument>

    val destination: String

    val navIcon: Int?
}
