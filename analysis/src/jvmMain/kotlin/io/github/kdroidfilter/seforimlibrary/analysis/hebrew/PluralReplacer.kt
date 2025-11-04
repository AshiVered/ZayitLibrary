package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

class PluralReplacer {
    private val exceptions: Set<String> = setOf("שמים", "ירושלים", "אפרים", "אלוהים", "אלהים")
    private val suffixRegex = Regex("(.{2,})(ים|ות)$")

    fun filterPlural(input: String): String {
        if (input in exceptions) return input
        return input.replace(suffixRegex, "$1")
    }
}

