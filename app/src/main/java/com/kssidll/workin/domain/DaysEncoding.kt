package com.kssidll.workin.domain

import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import com.kssidll.workin.R

/**
 * @param encoding: id used to encode this in the datobase, has to be unique,
 * every value has to be bit shifted compared to previous highest value, and can never be changed,
 * we could use the ordinal, but i think having that set explicitly is less dangerous
 * because it won't be accidentally sorted
 */
enum class DaysEncoding(val encoding: Byte) {
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
         * @return List of DaysEncoding encoded within encodedDays.
         * The list has decremental ordering (first day in the list is the last day / day with highest number).
         */
        fun decode(encodedDays: Byte): List<DaysEncoding> {
            var encoded = encodedDays
            val days = mutableListOf<DaysEncoding>()

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
    }
}

@Composable
fun DaysEncoding.getTranslation(): String {
    return when (this) {
        DaysEncoding.Sunday -> stringResource(id = R.string.sunday)
        DaysEncoding.Monday -> stringResource(id = R.string.monday)
        DaysEncoding.Tuesday -> stringResource(id = R.string.tuesday)
        DaysEncoding.Wednesday -> stringResource(id = R.string.wednesday)
        DaysEncoding.Thursday -> stringResource(id = R.string.thursday)
        DaysEncoding.Friday -> stringResource(id = R.string.friday)
        DaysEncoding.Saturday -> stringResource(id = R.string.saturday)
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
        DaysEncoding.decode(encodedDays)
            .forEach {
                when (it) {
                    DaysEncoding.Sunday -> addSunday()
                    DaysEncoding.Monday -> addMonday()
                    DaysEncoding.Tuesday -> addTuesday()
                    DaysEncoding.Wednesday -> addWednesday()
                    DaysEncoding.Thursday -> addThursday()
                    DaysEncoding.Friday -> addFriday()
                    DaysEncoding.Saturday -> addSaturday()
                }
            }

        return this
    }

    fun addSunday(): EncodedDaysBuilder {
        if (addedSunday) return this

        value += DaysEncoding.Sunday.encoding
        addedSunday = true
        return this
    }

    fun addMonday(): EncodedDaysBuilder {
        if (addedMonday) return this

        value += DaysEncoding.Monday.encoding
        addedMonday = true
        return this
    }

    fun addTuesday(): EncodedDaysBuilder {
        if (addedTuesday) return this

        value += DaysEncoding.Tuesday.encoding
        addedTuesday = true
        return this
    }

    fun addWednesday(): EncodedDaysBuilder {
        if (addedWednesday) return this

        value += DaysEncoding.Wednesday.encoding
        addedWednesday = true
        return this
    }

    fun addThursday(): EncodedDaysBuilder {
        if (addedThursday) return this

        value += DaysEncoding.Thursday.encoding
        addedThursday = true
        return this
    }

    fun addFriday(): EncodedDaysBuilder {
        if (addedFriday) return this

        value += DaysEncoding.Friday.encoding
        addedFriday = true
        return this
    }

    fun addSaturday(): EncodedDaysBuilder {
        if (addedSaturday) return this

        value += DaysEncoding.Saturday.encoding
        addedSaturday = true
        return this
    }

}