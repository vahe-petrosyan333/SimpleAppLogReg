package com.example.simpleapplogreg.view.validatorEditText

interface OnValidListener {
    fun validate(): Boolean
    fun showDefaultState()
    fun showErrorState(messageText: String)
}