package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.TokenStream
import org.apache.lucene.analysis.Tokenizer
import org.apache.lucene.analysis.core.LowerCaseFilter
import org.apache.lucene.analysis.miscellaneous.ASCIIFoldingFilter
import org.apache.lucene.analysis.pattern.PatternReplaceFilter
import org.apache.lucene.analysis.standard.StandardTokenizer
import java.util.regex.Pattern

/**
 * Strict analyzer for NEAR=0: no morphological expansions, only normalization
 * that is lossless for structure: remove diacritics, lowercase, normalize sofits.
 */
class SefariaStrictHebrewAnalyzer : Analyzer() {
    private val diacriticsPattern: Pattern =
        Pattern.compile("[\u0591-\u05BD\u05BF\u05C1\u05C2\u05C4\u05C5\u05C7]")

    override fun createComponents(fieldName: String): TokenStreamComponents {
        val tokenizer: Tokenizer = StandardTokenizer()
        var ts: TokenStream = tokenizer
        ts = PatternReplaceFilter(ts, diacriticsPattern, "", true)
        ts = ASCIIFoldingFilter(ts)
        ts = LowerCaseFilter(ts)
        ts = SofitLetterFilter(ts)
        return TokenStreamComponents(tokenizer, ts)
    }
}

