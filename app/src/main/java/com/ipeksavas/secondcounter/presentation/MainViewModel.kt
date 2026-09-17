package com.ipeksavas.secondcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel: ViewModel(){
    val countDownFlow = flow<Int>{
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while (currentValue>0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }
    
    
    init{
        collectFlow()
    }

    private fun collectFlow(){
        
        viewModelScope.launch{
            countDownFlow
                .filter { time ->
                    time % 2 == 0
                }
                //map kendisine gelen değeri içine ne yazıldıysa onunla değiştiriyor.
                .map { time ->
                    time * time
                }
                .onEach{ time ->
                    println(time)
                }
                //collect ona ulaşan değeri fırlatıyor
                .collect { time ->
                    println("The current time is $time")
                }
                
            println("The time is $countDownFlow")
        }
    }
}