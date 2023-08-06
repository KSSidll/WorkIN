package com.kssidll.workin.data.data

import androidx.room.*

@Entity(
    indices = [
        Index(
            value = ["name"],
            unique = true,
        )
    ]
)
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val days: Byte,
) {
    constructor(
        name: String,
        description: String,
        days: Byte,
    ): this(0, name, description, days)
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["sessionId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.RESTRICT,
        ),
    ],
)
data class SessionWorkout(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(index = true) val sessionId: Long,
    @ColumnInfo(index = true) val workoutId: Long,
    val repetitionCount: Int,
    val repetitionType: Int,
    val weight: Float,
    val weightType: Int,
    val order: Int,
    val restTime: Int,
) {
    constructor(
        sessionId: Long,
        workoutId: Long,
        repetitionCount: Int,
        repetitionType: Int,
        weight: Float,
        weightType: Int,
        order: Int,
        restTime: Int,
    ): this(
        id = 0,
        sessionId = sessionId,
        workoutId = workoutId,
        repetitionCount = repetitionCount,
        repetitionType = repetitionType,
        weight = weight,
        weightType = weightType,
        order = order,
        restTime = restTime,
    )
}

data class SessionWithWorkouts(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId"
    ) val workouts: List<SessionWorkout>
)