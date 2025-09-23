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
import com.apollo.timeflow.module.moduleNavHost.TimeFlowNavHost
import com.apollo.timeflow.theme.TimeFlowTheme
import com.apollo.timeflow.viewmodel.HostActivityViewModel

@Composable
fun TimeHostComponent() {
    TimeFlowTheme {
        Scaffold(
            snackbarHost = {
                Box(modifier = Modifier.fillMaxSize()) {
                    SnackbarHost(
                        hostState = hiltViewModel<HostActivityViewModel>().snackBarHostState,
                        modifier = Modifier.align(Alignment.Center),
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
                TimeFlowNavHost()
            }
        }
    }
}