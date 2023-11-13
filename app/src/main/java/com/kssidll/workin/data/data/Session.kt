package com.kssidll.workin.data.data

import androidx.compose.runtime.*
import androidx.compose.ui.res.*
import androidx.room.*
import com.kssidll.workin.R
import java.util.*

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

    constructor(
        sessionId: Long,
        workoutId: Long,
        repetitionCount: Int,
        repetitionType: RepetitionTypes,
        weight: Float,
        weightType: WeightTypes,
        order: Int,
        restTime: Int,
    ): this(
        sessionId = sessionId,
        workoutId = workoutId,
        repetitionCount = repetitionCount,
        repetitionType = repetitionType.id,
        weight = weight,
        weightType = weightType.id,
        order = order,
        restTime = restTime,
    )
}

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Workout::class,
            parentColumns = ["id"],
            childColumns = ["workoutId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.RESTRICT,
        ),
    ],
)
data class SessionWorkoutLog(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(index = true) val workoutId: Long,
    val repetitionCount: Int,
    val repetitionType: Int,
    val weight: Float,
    val weightType: Int,
    val datetime: Long,
) {
    constructor(
        workoutId: Long,
        repetitionCount: Int,
        repetitionType: RepetitionTypes,
        weight: Float,
        weightType: WeightTypes
    ): this(
        id = 0,
        workoutId = workoutId,
        repetitionCount = repetitionCount,
        repetitionType = repetitionType.id,
        weight = weight,
        weightType = weightType.id,
        datetime = Calendar.getInstance().timeInMillis,
    )
}

/**
 * SessionWorkout with the workout itself instead of just the id
 */
data class FullSessionWorkout(
    val id: Long,
    val sessionId: Long,
    val workout: Workout,
    var repetitionCount: Int,
    var repetitionType: Int,
    var weight: Float,
    var weightType: Int,
    val order: Int,
    var restTime: Int,
) {
    constructor(
        sessionId: Long,
        workout: Workout,
        repetitionCount: Int,
        repetitionType: RepetitionTypes,
        weight: Float,
        weightType: WeightTypes,
        order: Int,
        restTime: Int,
    ): this(
        id = 0,
        sessionId = sessionId,
        workout = workout,
        repetitionCount = repetitionCount,
        repetitionType = repetitionType.id,
        weight = weight,
        weightType = weightType.id,
        order = order,
        restTime = restTime,
    )
}

data class SessionWithWorkouts(
    val session: Session,
    val workouts: List<FullSessionWorkout>
)

/**
 * @param id: id used to encode this in the datobase, has to be unique, and can never be changed,
 *            we could use the ordinal, but i think having that set explicitly is less dangerous
 *            because it won't be accidentally sorted
 */
enum class RepetitionTypes(val id: Int) {
    Repetitions(0),
    Seconds(1),
    RiR(2)

    ;

    companion object {
        private val idMap = entries.associateBy { it.id }

        fun getById(id: Int) = idMap[id]
    }
}

@Composable
fun RepetitionTypes.getTranslation(): String {
    return when (this) {
        RepetitionTypes.Repetitions -> stringResource(id = R.string.repetitions)
        RepetitionTypes.Seconds -> stringResource(id = R.string.seconds)
        RepetitionTypes.RiR -> stringResource(id = R.string.rir)
    }
}

/**
 * @param id: id used to encode this in the datobase, has to be unique, and can never be changed,
 *            we could use the ordinal, but i think having that set explicitly is less dangerous
 *            because it won't be accidentally sorted
 */
enum class WeightTypes(
    val id: Int,
    val hideWeight: Boolean = false
) {
    KG(0),
    LB(1),
    BodyMass(2, true),
    KGBodyMass(3),
    LBBodyMass(4),

    ;

    companion object {
        private val idMap = entries.associateBy { it.id }

        fun getById(id: Int) = idMap[id]
    }
}

@Composable
fun WeightTypes.getTranslation(): String {
    return when (this) {
        WeightTypes.KG -> "KG"
        WeightTypes.LB -> "LB"
        WeightTypes.BodyMass -> stringResource(id = R.string.body_mass)
        WeightTypes.KGBodyMass -> "KG + ${stringResource(id = R.string.body_mass)}"
        WeightTypes.LBBodyMass -> "LB + ${stringResource(id = R.string.body_mass)}"
    }
}
