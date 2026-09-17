package com.ipeksavas.secondcounter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.ipeksavas.secondcounter.presentation.MainViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ipeksavas.secondcounter.presentation.FlowViewModel
import com.ipeksavas.secondcounter.presentation.StateFlowViewModel
import com.ipeksavas.secondcounter.ui.theme.SecondCounterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContent{
            SecondCounterTheme{
                val viewModel = viewModel<StateFlowViewModel>()
                val time = viewModel.stateFlow.collectAsState(initial = 0)
                Box(modifier = Modifier.fillMaxSize()){
                    Button(
                        onClick = { viewModel.incrementCounter() },
                        modifier = Modifier.align(Alignment.Center)
                    ) {
                        Text(
                            text = " Time is ${time.value}",
                            fontSize = 30.sp)
                    }
                }
            }
        }
    }
}

