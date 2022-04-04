package com.jj.sensorcollector.playground1.domain.ui

sealed class DomainColor {
    object Red: DomainColor()
    object Yellow: DomainColor()
    object Green: DomainColor()
    object Default: DomainColor()
}
