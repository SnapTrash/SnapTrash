package com.snaptrash.snaptrash.model.data

import java.util.*

data class Association(
    var id: String,
    var name : String,
    var phone_number : String,
    var email : String,
    var type : String,
    var country : String,
    var city : String,
    var zip_code : String,
    var street_number : String,
    var areas: List<LocationInfo>,
)
