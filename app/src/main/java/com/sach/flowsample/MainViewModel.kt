package com.sach.flowsample

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.fold
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val Tag: String = "MainViewModel"

    //flowOf(), This is used to create flow from given set of items.
    private val items = flowOf("Apple")


    //This just flow builder which emit the values.
    val sampleFlow = flow<Int> {

        val startingValue = 10
        var currentValue = startingValue
        emit(startingValue)
        while (currentValue > 0) {
            delay(1000L)
            currentValue--
            emit(currentValue)
        }

    }

    init {
        flowOperations()
    }

    //Example of Terminal Operator
    private fun flowOperations() {
        viewModelScope.launch {
            items.collect {
                displayLog("FlowOf Items: $it")
            }

            //Single Terminal Operator
            val single = items.single()
            displayLog("Single Item is $single")
            //First Terminal Operator
            val first = sampleFlow.first()
            displayLog("First item is - $first")

            //Collect Terminal Operator
            sampleFlow.filter { it % 2 == 0 }.map { it + 1 }.collect {
                displayLog("displayCounter: $it")
            }

            //Count Terminal Operator
            val sum = sampleFlow.count()
            displayLog("Count is - $sum")

            //Reduce Terminal Operator
            val reduce = sampleFlow.reduce { accumulator, value ->
                accumulator + value
            }
            displayLog("Reduce - Operation is - $reduce")

            // Fold Terminal Opertor
            val fold = sampleFlow.fold(10) { accumulator, value ->
                accumulator + value
            }
            displayLog("Reduce - Operation is - $fold")


        }
    }

    private fun displayLog(message: String) {
        Log.e(Tag, message)
    }


}