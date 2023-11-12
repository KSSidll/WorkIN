package com.kssidll.workin.domain

import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R
import java.util.*

/**
 * @param encoding: id used to encode this in the datobase, has to be unique,
 */
enum class WeekDays(val encoding: Byte) {
    /**
     * every [encoding] value has to be bit shifted compared to previous highest value, and can never be changed,
     * we could use the ordinal, but i think having that set explicitly is less dangerous
     * because it won't be accidentally sorted
     */
    Sunday(
        1.shl(0)
            .toByte()
    ),
    Monday(
        1.shl(1)
            .toByte()
    ),
    Tuesday(
        1.shl(2)
            .toByte()
    ),
    Wednesday(
        1.shl(3)
            .toByte()
    ),
    Thursday(
        1.shl(4)
            .toByte()
    ),
    Friday(
        1.shl(5)
            .toByte()
    ),
    Saturday(
        1.shl(6)
            .toByte()
    ),

    ;

    companion object {
        private val idMap = entries
            .associateBy { it.encoding }

        fun getByEncoding(encoding: Byte) = idMap[encoding]

        /**
         * @return encoding for current day as [Int]
         */
        fun currentDayEncoding(): Int = (1).shl(
            Calendar.getInstance()
                .get(Calendar.DAY_OF_WEEK) - 1
        )

        /**
         * @return List of WeekDays encoded within encodedDays.
         * The list has decremental ordering (first day in the list is the last day / day with highest number).
         */
        fun decode(encodedDays: Byte): List<WeekDays> {
            var encoded = encodedDays
            val days = mutableListOf<WeekDays>()

            entries
                .sortedByDescending { it.encoding }
                .forEach {
                    if (encoded >= it.encoding) {
                        encoded = encoded.minus(it.encoding)
                            .toByte()

                        when (it) {
                            Saturday -> days.add(Saturday)
                            Friday -> days.add(Friday)
                            Thursday -> days.add(Thursday)
                            Wednesday -> days.add(Wednesday)
                            Tuesday -> days.add(Tuesday)
                            Monday -> days.add(Monday)
                            Sunday -> days.add(Sunday)
                        }
                    }
                }

            return days
        }

        fun getCurrent(): WeekDays {
            val currentDayEncoding = (1).shl(
                Calendar.getInstance()
                    .get(Calendar.DAY_OF_WEEK) - 1
            )

            return WeekDays.getByEncoding(currentDayEncoding.toByte())!!
        }
    }
}

@Composable
fun WeekDays.getTranslation(): String {
    return when (this) {
        WeekDays.Sunday -> stringResource(id = R.string.sunday)
        WeekDays.Monday -> stringResource(id = R.string.monday)
        WeekDays.Tuesday -> stringResource(id = R.string.tuesday)
        WeekDays.Wednesday -> stringResource(id = R.string.wednesday)
        WeekDays.Thursday -> stringResource(id = R.string.thursday)
        WeekDays.Friday -> stringResource(id = R.string.friday)
        WeekDays.Saturday -> stringResource(id = R.string.saturday)
    }
}

class EncodedDaysBuilder {
    private var value: Int = 0

    var addedSunday: Boolean = false
        private set
    var addedMonday: Boolean = false
        private set
    var addedTuesday: Boolean = false
        private set
    var addedWednesday: Boolean = false
        private set
    var addedThursday: Boolean = false
        private set
    var addedFriday: Boolean = false
        private set
    var addedSaturday: Boolean = false
        private set

    fun build(): Byte = value.toByte()

    fun add(encodedDays: Byte): EncodedDaysBuilder {
        WeekDays.decode(encodedDays)
            .forEach {
                when (it) {
                    WeekDays.Sunday -> addSunday()
                    WeekDays.Monday -> addMonday()
                    WeekDays.Tuesday -> addTuesday()
                    WeekDays.Wednesday -> addWednesday()
                    WeekDays.Thursday -> addThursday()
                    WeekDays.Friday -> addFriday()
                    WeekDays.Saturday -> addSaturday()
                }
            }

        return this
    }

    fun addSunday(): EncodedDaysBuilder {
        if (addedSunday) return this

        value += WeekDays.Sunday.encoding
        addedSunday = true
        return this
    }

    fun addMonday(): EncodedDaysBuilder {
        if (addedMonday) return this

        value += WeekDays.Monday.encoding
        addedMonday = true
        return this
    }

    fun addTuesday(): EncodedDaysBuilder {
        if (addedTuesday) return this

        value += WeekDays.Tuesday.encoding
        addedTuesday = true
        return this
    }

    fun addWednesday(): EncodedDaysBuilder {
        if (addedWednesday) return this

        value += WeekDays.Wednesday.encoding
        addedWednesday = true
        return this
    }

    fun addThursday(): EncodedDaysBuilder {
        if (addedThursday) return this

        value += WeekDays.Thursday.encoding
        addedThursday = true
        return this
    }

    fun addFriday(): EncodedDaysBuilder {
        if (addedFriday) return this

        value += WeekDays.Friday.encoding
        addedFriday = true
        return this
    }

    fun addSaturday(): EncodedDaysBuilder {
        if (addedSaturday) return this

        value += WeekDays.Saturday.encoding
        addedSaturday = true
        return this
    }

}