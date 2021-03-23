package com.example.gituserdemo.utilities

import kotlinx.coroutines.*

val job = SupervisorJob()
val backgroundscope = CoroutineScope(Dispatchers.IO + job)
val ioscope = CoroutineScope(Dispatchers.IO + job)
val mainscope = CoroutineScope(Dispatchers.Main + job)

fun cancelAllWork() {
    backgroundscope.coroutineContext.cancelChildren()
    ioscope.coroutineContext.cancelChildren()
    mainscope.coroutineContext.cancelChildren()
}

fun doBackgroundTask(backGroundTask : () -> Any, foregroundTask : (Any) -> Unit){
    backgroundscope.launch {
        val returns = backGroundTask()
        mainscope.launch {
            foregroundTask(returns)
        }
    }
}
