package com.gumlet.insights

interface Observable<TListener> {
    fun subscribe(listener: TListener)
    fun unsubscribe(listener: TListener)
}
