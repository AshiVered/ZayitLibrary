package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute

class PluralFilter(input: TokenStream) : TokenFilter(input) {
    private val termAttr = addAttribute(CharTermAttribute::class.java)
    private val hebAttr = addAttribute(HebrewTokenTypeAttribute::class.java)
    private val posIncAttr = addAttribute(PositionIncrementAttribute::class.java)

    private val backlog = ArrayDeque<String>()
    private val replacer = PluralReplacer()

    override fun incrementToken(): Boolean {
        if (backlog.isNotEmpty()) {
            clearAttributes()
            termAttr.append(backlog.removeFirst())
            posIncAttr.positionIncrement = 0
            hebAttr.setExact(false)
            return true
        }

        var nextTok: String? = null
        while (nextTok == null) {
            if (!input.incrementToken()) return false
            val s = termAttr.toString().trim()
            if (s.isNotEmpty()) nextTok = s
        }

        backlog.addLast(replacer.filterPlural(nextTok))
        clearAttributes()
        termAttr.append(nextTok).append('$')
        posIncAttr.positionIncrement = 1
        hebAttr.setExact(true)
        return true
    }
}

