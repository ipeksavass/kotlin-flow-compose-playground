package com.ipeksavas.secondcounter

import android.R.attr.text
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.ipeksavas.secondcounter.presentation.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipeksavas.secondcounter.presentation.FlowViewModel
import com.ipeksavas.secondcounter.presentation.SharedFlowViewModel
import com.ipeksavas.secondcounter.presentation.StateFlowViewModel
import com.ipeksavas.secondcounter.ui.theme.SecondCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            SecondCounterTheme{
                val viewModel = viewModel<SharedFlowViewModel>()
                
                val snackbarHostState = remember { SnackbarHostState() }//snackbar durumunu yönetecek nesne
                
                //SharedFlowu Compose içerisinde güvenli bir şekilde dinleme alanı.
                LaunchedEffect(key1 = true){
                    viewModel.sharedFlow.collect{ squaredNumber->
                        snackbarHostState.showSnackbar(
                            message = "Squared number: $squaredNumber"
                        )
                    }
                }
                
                Scaffold(
                    snackbarHost = { SnackbarHost(hostState = snackbarHostState)}
                ){ innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        contentAlignment = Alignment.Center
                    ) {
                        Button(
                            onClick = {
                                viewModel.squaredNumber(3)
                            }
                        ) {
                            Text(
                                text = "Squared Number Result",
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

