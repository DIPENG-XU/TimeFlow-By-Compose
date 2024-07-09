package com.apollo.timeflow

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.apollo.timeflow.module.moduleNavHost.TimeFlowNavHost
import com.apollo.timeflow.theme.TimeFlowTheme
import com.apollo.timeflow.viewmodel.HostActivityViewModel

@Composable
fun TimeHostComponent(
    onDestinationChangedListener: NavController.OnDestinationChangedListener,
    viewModelStoreOwner: ViewModelStoreOwner,
) {
    val snackBarHostState = hiltViewModel<HostActivityViewModel>().snackBarHostState
    TimeFlowTheme {
        Scaffold(
            snackbarHost = {
                Box(modifier = Modifier.fillMaxSize()) {
                    SnackbarHost(
                        hostState = snackBarHostState,
                        modifier = Modifier.align(Alignment.TopCenter),
                    )
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .padding(paddingValues)
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