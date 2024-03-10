package com.drednoot.smartcalc.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.drednoot.calculator.ui.Calculator
import com.drednoot.circles.ui.Circles

enum class NavigationTarget(val target: String) {
    MAIN_MENU("main_menu"),
    CALCULATOR("calculator"),
    CIRCLES("circles"),
}

fun NavHostController.navigate(target: NavigationTarget) {
    navigate(target.target)
}

@Composable
fun SetupNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavigationTarget.MAIN_MENU.target,
    ) {
        composable(NavigationTarget.MAIN_MENU.target) { MainMenu(navController) }
        composable(NavigationTarget.CALCULATOR.target) { Calculator().Screen() }
        composable(NavigationTarget.CIRCLES.target) { Circles().Screen() }
    }
}
