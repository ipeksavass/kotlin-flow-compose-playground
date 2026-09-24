package com.ipeksavas.secondcounter.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SharedFlowViewModel: ViewModel() {
    private val _sharedFlow = MutableSharedFlow<Int>()
    val sharedFlow = _sharedFlow.asSharedFlow()
    
    init{
        viewModelScope.launch{
            sharedFlow.collect{
                delay(2000L)
                println("FIRST FLOW: The received number is $it")
            }
        }
        viewModelScope.launch{
            sharedFlow.collect{
                delay(3000L)
                println("SECOND FLOW: The received number is $it")
            }
        }
        squaredNumber(3)
        //ilk başta emit çalışırsa ve bir collect yani dinleyici aktif değilse yapılan işlem hiçbir işe yaramaz.
        //ama ilk başta suspend fonk olan collect aktif olur ve dinleyiciler yerleştirilirse daha sonra yapılan işlemler (emit gibi) logcat ekranında kendini gösterebilir.
    }
    
    fun squaredNumber(number: Int){
        viewModelScope.launch{
            _sharedFlow.emit(number * number)
            
        }
    }
    
}