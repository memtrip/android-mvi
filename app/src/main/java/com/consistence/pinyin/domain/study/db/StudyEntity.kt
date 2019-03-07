package com.consistence.pinyin.domain.study.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Study")
data class StudyEntity(
    @ColumnInfo(name = "englishTranslation") val englishTranslation: String,
    @ColumnInfo(name = "chineseSentence") val chineseSentence: String,
    @ColumnInfo(name = "correct") val correct: Int,
    @ColumnInfo(name = "incorrect") val incorrect: Int,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)