package com.apollo.timeflow.module.moduleNavHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.apollo.timeflow.component.HiddenBarEffect

@Composable
fun TimeFlowNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavHostRouteConfig.Launch.ROUTE,
        modifier = Modifier.fillMaxSize(),
        enterTransition = { fadeIn(tween(0)) },
        exitTransition = { fadeOut(tween(0)) }
    ) {
        with(ModuleGraph) {
            launchGraph(navController)
            homeGraph(navController)
            settingsGraph(navController)
        }
    }
    HiddenBarEffect()
}
