package io.github.kdroidfilter.seforimlibrary.analysis.hebrew

class StopLetterReplacer(private val stopLetters: CharArray, private val minLength: Int) {
    fun filterStopLetters(input: String): String {
        if (input.length <= minLength) return input
        val maxRemove = input.length - minLength
        var removed = 0
        val out = StringBuilder()
        for (i in input.indices) {
            val c = input[i]
            val isInterior = i != 0 && i != input.lastIndex
            if (isInterior && stopLetters.contains(c) && removed < maxRemove) {
                removed++
                continue
            }
            out.append(c)
        }
        return out.toString()
    }
}

