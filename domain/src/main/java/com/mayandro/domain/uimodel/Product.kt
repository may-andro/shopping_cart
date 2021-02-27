package com.mayandro.domain.uimodel

data class ProductUIItem(
    val id : Int,
    val name : String,
    val brand : String,
    val price : Int,
    val currency : String,
    val image : String,
    val link : String,
    val type : String,
    val cardBackground: Int,
    var width: Int,
    var height: Int
)

data class ProductDetailUIItem(
    val id : Int,
    val name : String,
    val description : String,
    val brand : String,
    val price : Int,
    val currency : String,
    val discountPercentage : Int,
    val image : String,
    val stock : Int,
    val link : String,
    val type : String,
    val cardBackground: Int,
    var width: Int,
    var height: Int
)