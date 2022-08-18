package com.example.weatherapp.app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.spring
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.weatherapp.ui.HomeView
import com.example.weatherapp.ui.SampleHomeView
import com.example.weatherapp.ui.SampleHomeView2
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun NavigationHandler(navController: NavHostController, navigationManager: NavigationManager) {

    // appointmentDetailNavigation is added since the navigation handler recomposes when the scaffolding states change and since the AppointmentDetailsView
    // isn't included in the navgraph, when the navhandler recomposes, we get sent back to schedule view.
    // This flag will only get set to true when we actually trigger a navigation event
    var appointmentDetailNavigation by remember { mutableStateOf(false) }

    val backstack = navController.currentBackStackEntryAsState()

    LaunchedEffect(Unit) {
        navigationManager.commands.collect {
            appointmentDetailNavigation = true
            navController.navigate(it.destination) {

                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                    inclusive = true
                }
                // Avoid multiple copies of the same destination when
                // reselecting the same item
                launchSingleTop = true

                // Restore state when reselecting a previously selected item
                restoreState = true
            }
        }
    }

    LaunchedEffect(Unit) {
        navigationManager.navigateBack.collect {
            navController.popBackStack()
        }
    }

    val chatNavAnimationEnter =
        if (backstack.value?.destination?.route == BottomNavigationDirections.sample.destination) {
            slideInHorizontally(
                initialOffsetX = { 1000 }, animationSpec = spring()
            )
        } else {
            slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = spring())
        }

    val chatNavAnimationExit =
        if (backstack.value?.destination?.route == BottomNavigationDirections.sample.destination) {
            slideOutHorizontally(
                targetOffsetX = { 1000 }, animationSpec = spring()
            )
        } else {
            slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = spring())
        }

    AnimatedNavHost(
        navController, startDestination = BottomNavigationDirections.sample.destination
    ) {

        composable(
            route = BottomNavigationDirections.sample.destination, enterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = spring())
            }, exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }, animationSpec = spring())
            }, popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }, animationSpec = spring())
            }, popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }, animationSpec = spring())
            }

        ) { backstack ->

            HomeView()
        }

        composable(
            route = BottomNavigationDirections.sample2.destination,
            enterTransition = { chatNavAnimationEnter },
            exitTransition = { chatNavAnimationExit },
            popExitTransition = { chatNavAnimationExit },
            popEnterTransition = { chatNavAnimationEnter }
        ) {
            SampleHomeView()
        }

        composable(
            route = BottomNavigationDirections.sample3.destination,
            enterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 }, animationSpec = spring()
                )
            },
            exitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = spring()
                )
            },
            popExitTransition = {
                slideOutHorizontally(
                    targetOffsetX = { 1000 }, animationSpec = spring()
                )
            },
            popEnterTransition = {
                slideInHorizontally(
                    initialOffsetX = { 1000 }, animationSpec = spring()
                )
            }
        ) {
            SampleHomeView2()
        }
    }
}
