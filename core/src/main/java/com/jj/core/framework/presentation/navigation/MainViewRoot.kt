package com.jj.core.framework.presentation.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.jj.core.framework.presentation.navigation.Route.CAMERA_ROUTE
import com.jj.core.framework.presentation.navigation.Route.MAIN_GRAPH
import com.jj.core.framework.presentation.navigation.Route.MAIN_START_ROUTE
import com.jj.core.framework.presentation.navigation.Route.SETTINGS_ROUTE
import com.jj.core.framework.presentation.screens.CameraScreen
import com.jj.core.framework.presentation.screens.MainScreen
import com.jj.core.framework.presentation.screens.SettingsScreen

@Composable
fun MainViewRoot(
    startDestination: String = MAIN_GRAPH
) {
    val navController = rememberNavController()
    MaterialTheme {
        Scaffold(
            topBar = {},
            bottomBar = { BottomNavigationBar(navController = navController) }) { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)
            ) {
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
}

@Preview
@Composable
fun PreviewMainViewRoot() {
    MainViewRoot()
}


fun NavGraphBuilder.mainGraph(navController: NavController) {
    navigation(startDestination = MAIN_START_ROUTE, route = MAIN_GRAPH) {
        composable(route = MAIN_START_ROUTE) {
            MainScreen(navController)
        }
        composable(route = SETTINGS_ROUTE) {
            SettingsScreen()
        }
        composable(route = CAMERA_ROUTE) {
            CameraScreen()
        }
    }
}

