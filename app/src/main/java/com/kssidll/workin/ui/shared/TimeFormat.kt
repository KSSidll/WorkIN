package com.kssidll.workin.ui.shared

/**
 * @param time: time in seconds
 * @return String in format MM:SS
 */
fun formatTime(time: Int): String {
    val minutes = time.div(60)
    val seconds = time.mod(60)

    return buildString {
        append(minutes.toString())
        append(":")
        if (seconds < 10) {
            append("0")
        }
        append(seconds.toString())
    }
}

/**
 * @param time: time in seconds
 * @return String in format MM:SS
 */
fun formatTime(time: Long): String {
    val minutes = time.div(60)
    val seconds = time.mod(60)

    return buildString {
        append(minutes.toString())
        append(":")
        if (seconds < 10) {
            append("0")
        }
        append(seconds.toString())
    }
}
