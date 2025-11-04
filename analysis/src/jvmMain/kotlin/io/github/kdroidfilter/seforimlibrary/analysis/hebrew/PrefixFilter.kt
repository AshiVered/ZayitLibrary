package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.TokenFilter
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute
import org.apache.lucene.analysis.tokenattributes.PositionIncrementAttribute

class PrefixFilter(input: TokenStream) : TokenFilter(input) {
    private val termAttr = addAttribute(CharTermAttribute::class.java)
    private val hebAttr = addAttribute(HebrewTokenTypeAttribute::class.java)
    private val posIncAttr = addAttribute(PositionIncrementAttribute::class.java)
    private val backlog = ArrayDeque<String>()

    private val prefixes: Array<String> = arrayOf(
        "בכ", "וב", "וה", "וכ", "ול", "ומ", "וש",
        "כב", "ככ", "כל", "כמ", "כש", "לכ",
        "מב", "מה", "מכ", "מל", "מש",
        "שב", "שה", "שכ", "של", "שמ",
        "ב", "ה", "ו", "כ", "ל", "מ", "ש"
    )

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
            for (pre in prefixes) {
                if (curr.length >= pre.length + 3 && curr.startsWith(pre)) {
                    backlog.addLast(curr.substring(pre.length))
                }
            }
            if (backlog.isNotEmpty()) {
                clearAttributes()
                termAttr.append(backlog.removeFirst())
                posIncAttr.positionIncrement = 0
                hebAttr.setExact(false)
                return true
            }
        }
        return true
    }
}

