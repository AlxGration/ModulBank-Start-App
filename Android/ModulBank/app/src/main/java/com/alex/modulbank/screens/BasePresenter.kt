package com.alex.modulbank.screens


open class BasePresenter <V> {
    protected var view: V? = null
        private set

    var isAttached = view != null

    fun attach(view: V) {
        this.view = view
    }

    fun detach() {
        this.view = null
    }
}