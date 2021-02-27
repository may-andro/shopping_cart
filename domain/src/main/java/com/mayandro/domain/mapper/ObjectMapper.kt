package com.mayandro.domain.mapper

interface ObjectMapper<in M, out E> {
    fun mapFromOriginalObject(originalObject: M): E
}