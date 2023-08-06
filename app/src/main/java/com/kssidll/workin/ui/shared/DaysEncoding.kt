package com.kssidll.workin.ui.shared

import android.content.*
import com.kssidll.workin.*
import kotlin.experimental.*


// TODO rework based on AddSessionViewModel translation implementation
object EncodedDays {
    const val MONDAY: Byte = (1 shl 0).toByte()
    const val TUESDAY: Byte = (1 shl 1).toByte()
    const val WEDNESDAY: Byte = (1 shl 2).toByte()
    const val THURSDAY: Byte = (1 shl 3).toByte()
    const val FRIDAY: Byte = (1 shl 4).toByte()
    const val SATURDAY: Byte = (1 shl 5).toByte()
    const val SUNDAY: Byte = (1 shl 6).toByte()
}

fun decodeDaysToString(
    encodedDays: Byte,
    context: Context
): List<String> {
    val daysOfWeek = listOf(
        R.string.monday,
        R.string.tuesday,
        R.string.wednesday,
        R.string.thursday,
        R.string.friday,
        R.string.saturday,
        R.string.sunday
    )

    val decodedDays = mutableListOf<String>()
    var dayFlag: Byte = 1

    for (i in daysOfWeek.indices) {
        if (encodedDays and dayFlag == dayFlag) {
            val dayName = context.getString(daysOfWeek[i])
            decodedDays.add(dayName)
        }

        // why can't you be normal kotlin, why?
        dayFlag = (dayFlag.toInt() shl 1).toByte()
    }

    return decodedDays
}

class EncodedDaysBuilder {
    private var value: Int = 0

    private var addedMonday: Boolean = false
    private var addedTuesday: Boolean = false
    private var addedWednesday: Boolean = false
    private var addedThursday: Boolean = false
    private var addedFriday: Boolean = false
    private var addedSaturday: Boolean = false
    private var addedSunday: Boolean = false

    fun build(): Byte {
        return value.toByte()
    }

    fun addMonday(): EncodedDaysBuilder {
        // why is it impossible to write normal code in this language
        // literally just let me value += EncodedDays.Monday * !addedMonday
        // i don't want your branches
        // why can't we make android apps in normal languages
        if (addedMonday) return this

        value += EncodedDays.MONDAY
        addedMonday = true
        return this
    }

    fun addTuesday(): EncodedDaysBuilder {
        if (addedTuesday) return this

        value += EncodedDays.TUESDAY
        addedTuesday = true
        return this
    }

    fun addWednesday(): EncodedDaysBuilder {
        if (addedWednesday) return this

        value += EncodedDays.WEDNESDAY
        addedWednesday = true
        return this
    }

    fun addThursday(): EncodedDaysBuilder {
        if (addedThursday) return this

        value += EncodedDays.THURSDAY
        addedThursday = true
        return this
    }

    fun addFriday(): EncodedDaysBuilder {
        if (addedFriday) return this

        value += EncodedDays.FRIDAY
        addedFriday = true
        return this
    }

    fun addSaturday(): EncodedDaysBuilder {
        if (addedSaturday) return this

        value += EncodedDays.SATURDAY
        addedSaturday = true
        return this
    }

    fun addSunday(): EncodedDaysBuilder {
        if (addedSunday) return this

        value += EncodedDays.SUNDAY
        addedSunday = true
        return this
    }

}