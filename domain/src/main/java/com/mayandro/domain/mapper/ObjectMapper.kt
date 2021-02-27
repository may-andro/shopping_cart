package com.mayandro.domain.mapper

abstract class ObjectMapper<in M, out E> {
    abstract fun mapFromOriginalObject(originalObject: M): E
}