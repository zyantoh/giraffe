package com.giraffe.canteen.model

enum class TableStatus(val status: String) {
    UNKNOWN(""),
    CONFIDENT_TAKEN("CONFIDENT_TAKEN"),
    MAYBE_TAKEN("MAYBE_TAKEN"),
    CONFIDENT_NOT_TAKEN("CONFIDENT_NOT_TAKEN"),
}