package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute

class StopLetterFilter(input: TokenStream, stopLetters: CharArray) : TokenFilter(input) {
    private val termAttr = addAttribute(CharTermAttribute::class.java)
    private val hebAttr = addAttribute(HebrewTokenTypeAttribute::class.java)
    private val posIncAttr = addAttribute(PositionIncrementAttribute::class.java)
    private val replacer = StopLetterReplacer(stopLetters, 3)

    override fun incrementToken(): Boolean {
        if (!input.incrementToken()) return false
        val curr = termAttr.toString().trim()
        if (!hebAttr.isExact()) {
            clearAttributes()
            termAttr.append(replacer.filterStopLetters(curr))
            posIncAttr.positionIncrement = 1
            hebAttr.setExact(false)
        }
        return true
    }
}

