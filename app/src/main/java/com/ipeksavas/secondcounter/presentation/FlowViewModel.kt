package com.ipeksavas.secondcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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
        collectFlow2()
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
    
    private fun collectFlow2(){
        val flow1 = flow {
            delay(250L)
            emit("Appetizer")
            delay(1000L)
            emit("Main Dish")
            delay(100L)
            emit("Dessert")
        }
        viewModelScope.launch{
            flow1
                .onEach {
                    println("FLOW: $it is delivered")
            }
                //.buffer()//zaman kaybı olmasını engelledi, veri kaybı yaşanmadı.
                .conflate()//collect meşgulken gelen eski verileri atlar, aradaki yığılmayı önleyip sadece en son veriyi iletir. Veri kaybı olabilir.
                .collect{
                    println("FLOW: Now eating $it")
                    delay(1500L)
                    println("FLOW: Finished eating $it")
                }
        }
    }
}