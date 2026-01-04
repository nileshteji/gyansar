package com.jetbrains.kmpapp.feature.student.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jetbrains.kmpapp.feature.student.data.local.entity.FlashcardEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentActivityEntity
import com.jetbrains.kmpapp.feature.student.data.local.entity.StudentProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentDao {
    @Query("SELECT * FROM student_profiles WHERE isActive = 1 LIMIT 1")
    fun observeActiveStudent(): Flow<StudentProfileEntity?>

    @Query("UPDATE student_profiles SET isActive = 0")
    suspend fun clearActive()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertStudent(profile: StudentProfileEntity)

    @Query("SELECT * FROM student_activities WHERE studentId = :studentId ORDER BY occurredAt DESC")
    fun observeActivities(studentId: String): Flow<List<StudentActivityEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertActivity(activity: StudentActivityEntity)

    @Query("SELECT * FROM flashcards WHERE studentId = :studentId ORDER BY createdAt DESC")
    fun observeFlashcards(studentId: String): Flow<List<FlashcardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertFlashcards(cards: List<FlashcardEntity>)
}
