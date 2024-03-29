package com.drednoot.smartcalc.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.drednoot.calculator.ui.Calculator

enum class NavigationTarget(val target: String) {
    MAIN_MENU("main_menu"),
    CALCULATOR("calculator"),
}

@Composable
fun NavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationTarget.MAIN_MENU.target,
    ) {
        composable(NavigationTarget.MAIN_MENU.target) { MainMenu(navController) }
        composable(NavigationTarget.CALCULATOR.target) { Calculator() }
    }
}

