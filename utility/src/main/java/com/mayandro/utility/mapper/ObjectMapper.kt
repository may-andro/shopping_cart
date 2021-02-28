package com.mayandro.utility.mapper

abstract class ObjectMapper<in M, out E> {
    abstract fun mapFromOriginalObject(originalObject: M): E
}