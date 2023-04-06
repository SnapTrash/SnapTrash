package com.snaptrash.snaptrash.model.data

import java.util.*

data class Report(
    var id: String,
    var date: Date,
    var reporterId: String,
    var reportedId: String,
    var message: String?,
    var status: ReportStatus
)
