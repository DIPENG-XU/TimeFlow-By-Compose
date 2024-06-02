package com.apollo.timeflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.apollo.timeflow.module.homefeed.ui.theme.TimeFlowTheme
import com.apollo.timeflow.module.moduleNavHost.TimeFlowNavHost

@Composable
fun TimeHostComponent(
    snackbarHostState: SnackbarHostState,
    onDestinationChangedListener: NavController.OnDestinationChangedListener,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    TimeFlowTheme {
        Scaffold(
            snackbarHost = {
                Box(modifier = Modifier.fillMaxSize()) {
                    SnackbarHost(
                        hostState = snackbarHostState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }
        ) { paddingValues ->
            paddingValues.calculateTopPadding()
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxSize()
            ) {
                val navController = rememberNavController().also {
                    it.removeOnDestinationChangedListener(onDestinationChangedListener)
                    it.addOnDestinationChangedListener(onDestinationChangedListener)
                }

                TimeFlowNavHost(
                    viewModelStoreOwner = viewModelStoreOwner,
                    navController = navController,
                )
            }
        }
    }
}