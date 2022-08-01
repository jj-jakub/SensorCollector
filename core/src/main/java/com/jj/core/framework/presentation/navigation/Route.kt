package com.jj.core.framework.presentation.navigation

object Route {
    const val MAIN_GRAPH = "MainGraph"

    const val MAIN_START_ROUTE = "StartRoute"
    const val SETTINGS_ROUTE = "SETTINGS_ROUTE"
    const val CAMERA_ROUTE = "CAMERA_ROUTE"
    const val UI_TESTING_ROUTE = "UI_TESTING_ROUTE"

    fun getItems() = listOf(
        MAIN_START_ROUTE,
        SETTINGS_ROUTE,
        CAMERA_ROUTE,
        UI_TESTING_ROUTE
    )
}