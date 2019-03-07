package com.consistence.pinyin.domain.study.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudyDao {

    @Query("SELECT * FROM Study ORDER BY uid DESC LIMIT 0, 50")
    fun studyOrderByDesc(): List<StudyEntity>

    @Query("SELECT * FROM Study ORDER BY correct ASC LIMIT 0, 50")
    fun studyOrderByCorrect(): List<StudyEntity>

    @Query("SELECT * FROM Study ORDER BY incorrect ASC LIMIT 0, 50")
    fun studyOrderByIncorrect(): List<StudyEntity>

    @Insert
    fun insert(study: StudyEntity)

    @Query("UPDATE Study SET englishTranslation = :englishTranslation, chineseSentence = :chineseSentence WHERE uid = :uid")
    fun update(englishTranslation: String, chineseSentence: String, uid: Int)

    @Query("UPDATE Study SET englishTranslation = :correct WHERE uid = :uid")
    fun updateCorrect(correct: Int, uid: Int)

    @Query("UPDATE Study SET englishTranslation = :incorrect WHERE uid = :uid")
    fun updateIncorrect(incorrect: Int, uid: Int)

    @Query("SELECT COUNT(*) FROM Study")
    fun count(): Int
}