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
data class Session(
    @PrimaryKey(autoGenerate = true) val id: Long,
    var name: String,
    var description: String,
    var days: Byte,
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
    @ColumnInfo(index = true) var workoutId: Long,
    var repetitionCount: Int,
    var repetitionType: Int,
    var weight: Float,
    var weightType: Int,
    var order: Int,
    var restTime: Int,
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

/**
 * SessionWorkout with the workout itself instead of just the id
 */
data class FullSessionWorkout(
    @Embedded val sessionWorkout: SessionWorkout,
    @Relation(
        parentColumn = "workoutId",
        entityColumn = "id",
    ) val workout: Workout
)

data class SessionWithSessionWorkouts(
    @Embedded val session: Session,
    @Relation(
        parentColumn = "id",
        entityColumn = "sessionId",
    ) val workouts: List<SessionWorkout>
)

data class SessionWithFullSessionWorkouts(
    val session: Session,
    val workouts: List<FullSessionWorkout>
)

/**
 * Assumes that the FullSessionWorkout list contains the same sessionWorkouts as the SessionWithSessionWorkouts
 */
// This is very not great, but no idea how to do that normally, and for some reason Room doesn't support complex nested structures AFAIK
fun SessionWithSessionWorkouts.merge(fullSessionWorkouts: List<FullSessionWorkout>): SessionWithFullSessionWorkouts {
    return SessionWithFullSessionWorkouts(
        session = this.session,
        workouts = this.workouts.map { sessionWorkout ->
            FullSessionWorkout(
                sessionWorkout = sessionWorkout,
                workout = fullSessionWorkouts.find {
                    sessionWorkout == it.sessionWorkout
                }!!.workout
            )
        }
            .sortedBy { it.sessionWorkout.order }
    )
}