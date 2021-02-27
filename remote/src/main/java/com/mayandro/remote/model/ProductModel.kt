package com.mayandro.remote.model

import com.google.gson.annotations.SerializedName

data class ProductResponse (
	@SerializedName("list") val list : List<ProductItem>,
	@SerializedName("page") val page : Int,
	@SerializedName("pageSize") val pageSize : Int,
	@SerializedName("size") val size : Int,
	@SerializedName("_link") val _link : String,
	@SerializedName("_type") val _type : String,
	@SerializedName("_next") val _next : String
)


data class ProductItem (
	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("brand") val brand : String,
	@SerializedName("price") val price : Int,
	@SerializedName("currency") val currency : String,
	@SerializedName("image") val image : String,
	@SerializedName("_link") val _link : String,
	@SerializedName("_type") val _type : String
)

data class ProductDetail (
	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("description") val description : String,
	@SerializedName("brand") val brand : String,
	@SerializedName("price") val price : Int,
	@SerializedName("currency") val currency : String,
	@SerializedName("discountPercentage") val discountPercentage : Int,
	@SerializedName("image") val image : String,
	@SerializedName("stock") val stock : Int,
	@SerializedName("_link") val _link : String,
	@SerializedName("_type") val _type : String
)