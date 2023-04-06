package com.snaptrash.snaptrash.model.data

import java.util.Date

data class PointHistoryEntry(
    var id: String,
    var date: Date,
    var value: Int,
    var topic: String,
    var message: String
)
