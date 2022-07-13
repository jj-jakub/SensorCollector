package com.jj.core.framework.presentation.navigation

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jj.core.framework.presentation.navigation.Routes.MAIN_GRAPH
import com.jj.core.framework.presentation.navigation.Routes.MAIN_START_ROUTE
import com.jj.core.framework.presentation.screens.MainScreen

@Composable
fun MainViewRoot(
    startDestination: String = MAIN_GRAPH
) {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(topBar = {}) {
            NavHost(
                navController = navController,
                startDestination = startDestination,
            ) {
                mainGraph(
                    navController = navController
                )
            }
        }
    }
}

fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = MAIN_START_ROUTE, route = MAIN_GRAPH) {
        composable(route = MAIN_START_ROUTE) {
            MainScreen(navController)
        }
    }
}

