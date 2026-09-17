package com.ipeksavas.secondcounter.presentation

import android.graphics.Color.red
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.reduce
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
    
//    val squaredData = countDownFlow
//        .filter{
//            it % 2 == 0
//        }
//        .map{
//            it * it
//        }

    
    init{
        collectFlow()
    }

    private fun collectFlow(){
//      Bu şekilde launchIn kullanınca collect otomatik çalışıyor.
//        countDownFlow.onEach {
//            println(it)
//        }.launchIn(viewModelScope)
        
        viewModelScope.launch{
            // kendi ve kendinden sonraki değer için verilen koşulu gerçekleştirir.
            var reduceResult = countDownFlow
                .reduce { accumulator, value ->
                    accumulator + value
                }
                
//                .filter { time ->
//                    time % 2 == 0
//                }
//                //map kendisine gelen değeri içine ne yazıldıysa onunla değiştiriyor.
//                .map { time ->
//                    time * time
//                }
//                .onEach{ time ->
//                    println(time)
//                }
//                //collect ona ulaşan değeri fırlatıyor
//                .collect { time ->
//                    println("The current time is $time")
//                }
                
            println("The result is $reduceResult")
        }
    }
}