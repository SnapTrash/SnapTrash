package com.snaptrash.snaptrash.model.data

data class LocationInfo(
    var id: String,
    var country: String,
    var region: String,
    var municipality: String,
    var authorizedAssociation: Association?
)
