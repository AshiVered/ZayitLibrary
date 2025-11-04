package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute

class AlternateSpellingFilter(input: TokenStream) : TokenFilter(input) {
    private val termAttr = addAttribute(CharTermAttribute::class.java)
    private val hebAttr = addAttribute(HebrewTokenTypeAttribute::class.java)
    private val posIncAttr = addAttribute(PositionIncrementAttribute::class.java)

    private val alts: Map<String, String> = mapOf("היא" to "הוא")
    private val backlog = ArrayDeque<String>()

    override fun incrementToken(): Boolean {
        if (backlog.isNotEmpty()) {
            clearAttributes()
            termAttr.append(backlog.removeFirst())
            posIncAttr.positionIncrement = 0
            hebAttr.setExact(false)
            return true
        }
        if (!input.incrementToken()) return false
        val curr = termAttr.toString().trim()
        if (!hebAttr.isExact()) {
            val alt = alts[curr]
            if (alt != null) backlog.addLast(alt)
        }
        return true
    }
}

