package com.recyclerview.using.kotlin.model

data class Product(
    var id: String,
    var image: String,
    var name: String,
    var price: Int,
    var isSelected: Boolean = false,
)