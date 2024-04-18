package com.monaser.nstore.ui.utils

import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private const val INPUT_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    private const val OUTPUT_PATTERN = "MMM dd, yyyy 'at' hh:mm a"

    fun formatDateTime(dateTimeString: String): String {
        return try {
            val inputFormat = SimpleDateFormat(INPUT_PATTERN, Locale.getDefault())
            val outputFormat = SimpleDateFormat(OUTPUT_PATTERN, Locale.getDefault())

            inputFormat.timeZone = TimeZone.getTimeZone("UTC")
            val dateTime = inputFormat.parse(dateTimeString)

            outputFormat.format(dateTime ?: Date())
        } catch (e: Exception) {
            "Invalid Date"
        }
    }
}
