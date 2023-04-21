package com.david.archetype.fundaments.base

interface DTStateListener<S : DTState> {

    fun onStateChanged(state: S)
}