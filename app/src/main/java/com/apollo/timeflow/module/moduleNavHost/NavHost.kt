package com.apollo.timeflow.module.moduleNavHost

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.apollo.timeflow.module.settings.ui.ConfirmDialog
import com.apollo.timeflow.module.settings.ui.TimeFlowSettings
import com.apollo.timeflow.module.homefeed.ui.card.CardHomeFeed

@Composable
fun TimeFlowNavHost(
    navController: NavHostController,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    NavHost(
        navController = navController,
        startDestination = NavHostRouteConfig.NAV_HOST_ROUTE_FOR_HOMEFEED,
        modifier = Modifier.fillMaxSize(),
        enterTransition = {
            fadeIn(tween(0))
        }, exitTransition = {
            fadeOut(tween(0))
        }
    ) {
        composable(route = NavHostRouteConfig.NAV_HOST_ROUTE_FOR_HOMEFEED) {
            CardHomeFeed(
                viewModelStoreOwner,
                navController,
            )
        }

        composable(route = NavHostRouteConfig.NAV_HOST_ROUTE_FOR_SETTINGS) {
            TimeFlowSettings(navController, viewModelStoreOwner)
        }

        composable(route = NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.DATE_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(route = NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.TIME_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }

        composable(route = NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE) {
            ConfirmDialog(
                navController = navController,
                route = NavHostRouteConfig.THEME_FORMAT_DIALOG_ROUTE,
                viewModelStoreOwner = viewModelStoreOwner,
            )
        }
    }
}