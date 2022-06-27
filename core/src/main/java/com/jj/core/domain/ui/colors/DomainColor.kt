package com.jj.core.domain.ui.colors

sealed class DomainColor {
    object Red: DomainColor()
    object Yellow: DomainColor()
    object Orange: DomainColor()
    object Green: DomainColor()
    object Default: DomainColor()
}
