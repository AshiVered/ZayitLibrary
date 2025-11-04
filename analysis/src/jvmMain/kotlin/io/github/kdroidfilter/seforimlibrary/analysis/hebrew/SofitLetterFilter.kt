package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute

class SofitLetterFilter(input: TokenStream) : TokenFilter(input) {
    private val termAttr = addAttribute(CharTermAttribute::class.java)
    private val hebAttr = addAttribute(HebrewTokenTypeAttribute::class.java)

    override fun incrementToken(): Boolean {
        if (!input.incrementToken()) return false
        if (!hebAttr.isExact()) {
            val s = termAttr.toString()
            val replaced = s
                .replace('\u05DA', '\u05DB')
                .replace('\u05DD', '\u05DE')
                .replace('\u05DF', '\u05E0')
                .replace('\u05E3', '\u05E4')
                .replace('\u05E5', '\u05E6')
            if (replaced != s) {
                clearAttributes()
                termAttr.append(replaced)
            }
        }
        return true
    }
}

