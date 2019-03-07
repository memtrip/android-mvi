package com.consistence.pinyin.domain.study.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudyDao {

    @Query("SELECT * FROM Study ORDER BY uid DESC LIMIT 0, 50")
    fun studyOrderByDesc(): List<StudyEntity>

    @Query("SELECT * FROM Study ORDER BY RANDOM() LIMIT :limit")
    fun randomStudy(limit: Int): List<StudyEntity>

    @Insert
    fun insert(study: StudyEntity)

    @Query("UPDATE Study SET englishTranslation = :englishTranslation, chineseSentence = :chineseSentence WHERE uid = :uid")
    fun update(englishTranslation: String, chineseSentence: String, uid: Int)

    @Query("DELETE FROM Study WHERE uid = :uid")
    fun deleteStudy(uid: Int)

    @Query("SELECT COUNT(*) FROM Study")
    fun count(): Int
}