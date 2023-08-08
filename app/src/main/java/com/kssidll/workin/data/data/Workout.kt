package com.kssidll.workin.data.data

import androidx.room.*

// TODO catch non uniques instead of crashing
@Entity(
    indices = [
        Index(
            value = ["name"],
            unique = true,
        )
    ]
)
data class Workout(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var description: String,
) {
    constructor(
        name: String,
        description: String,
    ): this(0, name, description)
}