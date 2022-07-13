package com.jj.core.framework.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.jj.core.R

@Composable
fun BottomNavigationBar(
    navController: NavController
) {
    val items = remember {
        listOf(
            Routes.MAIN_START_ROUTE,
            Routes.SETTINGS_ROUTE,
        )
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    BottomNavigation {
        items.forEach {
            BottomNavigationItem(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentHeight(),
                icon = @Composable { Icon(painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = "") },
                label = @Composable { Text(it) },
                alwaysShowLabel = true,
                selectedContentColor = Color.Red,
                unselectedContentColor = Color.Gray,
                selected = currentRoute == it,
                onClick = {
                    navController.navigate(it) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewBottomNavigationBar() {
    BottomNavigationBar(NavController(LocalContext.current))
}