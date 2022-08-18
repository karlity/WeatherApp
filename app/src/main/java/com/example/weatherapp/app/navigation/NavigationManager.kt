package com.example.weatherapp.app.navigation

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow

// This class is instantiated as a Singleton and will be able to be injected into any viewModel
// The [commands] are collected in the NavigationHandler.kt
class NavigationManager {

    // Navigation command that will be collected in the NavigationHandler. To be used when navigating to
    // other destinations in the app aside from the bottom nav bar
    private val _command = MutableSharedFlow<NavigationCommand>(extraBufferCapacity = 10)
    val commands: SharedFlow<NavigationCommand> = _command

    // To be used if back navigation in the top navigation bar is to be intercepted with an alternate command
    // (outside of simply popping the backstack)
    private val _backNavAction = MutableSharedFlow<(() -> Unit)?>(extraBufferCapacity = 10)
    val backNavAction: SharedFlow<(() -> Unit)?> = _backNavAction

    // To be used when the back navigation icon is to be displayed at the top navigation bar
    private val _showBackNavigation = MutableSharedFlow<Boolean>(extraBufferCapacity = 10)
    val showBackNavigation: SharedFlow<Boolean> = _showBackNavigation

    // To be used when the back navigation icon is to be displayed at the top navigation bar
    private val _hideTopAppBar = MutableSharedFlow<Boolean>(extraBufferCapacity = 10)
    val hideTopAppBar: SharedFlow<Boolean> = _hideTopAppBar

    private val _navigateBack = MutableSharedFlow<Boolean>(extraBufferCapacity = 10)
    val navigateBack: SharedFlow<Boolean> = _navigateBack

    // To toggle visibility for the share location icon
    private val _showShareLocation = MutableSharedFlow<Boolean>(extraBufferCapacity = 10)
    val showShareLocation: SharedFlow<Boolean> = _showShareLocation

    private val _setTopAppBarName = MutableSharedFlow<String>(extraBufferCapacity = 10)
    val setTopAppBarName: SharedFlow<String> = _setTopAppBarName

    fun navigate(
        directions: NavigationCommand
    ) {
        _command.tryEmit(directions)
    }

    fun replaceTopAppBarNavAction(
        topBarNav: (() -> Unit)?
    ) {
        _backNavAction.tryEmit(topBarNav)
    }

    fun popBackstack(
        navigateBack: Boolean = true
    ) {
        _navigateBack.tryEmit(navigateBack)
    }

    fun showBackNavigation(
        showBackNavigation: Boolean
    ) {

        _showBackNavigation.tryEmit(showBackNavigation)
    }

    fun hideTopAppBar(
        hideTopAppBar: Boolean
    ) {
        _hideTopAppBar.tryEmit(hideTopAppBar)
    }

    fun showShareLocation(
        showShareLocation: Boolean
    ) {
        _showShareLocation.tryEmit(showShareLocation)
    }

    fun setTopAppBarName(
        name: String
    ) {
        _setTopAppBarName.tryEmit(name)
    }
}
