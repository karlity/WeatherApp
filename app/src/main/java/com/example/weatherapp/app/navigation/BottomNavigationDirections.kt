package com.example.weatherapp.app.navigation

import androidx.navigation.NamedNavArgument

object BottomNavigationDirections {

    val Default = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = ""
        override val navIcon: Int? = null
    }

    val sample = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "sample"
        override val navIcon: Int? = null
    }

    val sample2 = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "sample2"
        override val navIcon: Int? = null
    }

    val sample3 = object : NavigationCommand {
        override val arguments = emptyList<NamedNavArgument>()
        override val destination = "sample3"
        override val navIcon: Int? = null
    }
}
