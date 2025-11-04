package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.tokenattributes.KeywordAttribute
import org.apache.lucene.util.AttributeImpl
import org.apache.lucene.util.AttributeReflector

class HebrewTokenTypeAttributeImpl : AttributeImpl(), HebrewTokenTypeAttribute {
    private var type: HebrewTokenTypeAttribute.HebrewType = HebrewTokenTypeAttribute.HebrewType.Unknown
    private var exact: Boolean = false

    override fun setType(type: HebrewTokenTypeAttribute.HebrewType) { this.type = type }
    override fun getType(): HebrewTokenTypeAttribute.HebrewType = type
    override fun isExact(): Boolean = exact
    override fun setExact(isExact: Boolean) { this.exact = isExact }

    override fun clear() {
        type = HebrewTokenTypeAttribute.HebrewType.Unknown
        exact = false
    }

    override fun reflectWith(reflector: AttributeReflector) {
        reflector.reflect(KeywordAttribute::class.java, "isExact", exact)
        reflector.reflect(KeywordAttribute::class.java, "type", type)
    }

    override fun copyTo(target: AttributeImpl) {
        (target as HebrewTokenTypeAttribute).setType(type)
        target.setExact(exact)
    }
}

