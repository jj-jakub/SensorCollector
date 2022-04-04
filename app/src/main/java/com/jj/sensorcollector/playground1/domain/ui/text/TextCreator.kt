package com.jj.sensorcollector.playground1.domain.ui.text

interface TextCreator<T> {

    fun buildColoredString(vararg textComponents: TextComponent): T
}