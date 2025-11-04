package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.util.Attribute

interface HebrewTokenTypeAttribute : Attribute {
    enum class HebrewType { Unknown }
    fun setType(type: HebrewType)
    fun getType(): HebrewType
    fun isExact(): Boolean
    fun setExact(isExact: Boolean)
}

