package com.ipeksavas.secondcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class FlowViewModel : ViewModel() {
    
    val flowExample = flow<Int> {
        val startingValue = 10
        var currentValue = startingValue
        emit(currentValue)
        while(currentValue > 0){
            delay(1000L)
            currentValue--
            emit(currentValue)
        }
    }
    
    init{
        collectFlow()
    }
    
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun collectFlow(){
        val flow1 = flow{ // sadece değişken tanımı yapılıyor.
            emit(1)
            delay(500L)
            emit(2)
        }
        viewModelScope.launch{ // collect yapıldığı için launch içinde.
            flow1.flatMapConcat { value ->
                flow {
                    emit(value + 1)
                    delay(500L)
                    emit(value + 2)
                }
            }.collect{ value ->  //collect fonk suspend fonk olduğu için launch ile çağırıyoruz.
                println("The value is $value")
            }
        }
    }
}