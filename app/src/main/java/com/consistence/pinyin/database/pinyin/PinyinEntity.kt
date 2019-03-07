package com.consistence.pinyin.database.pinyin

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Pinyin")
data class PinyinEntity(
    @ColumnInfo(name = "sourceUrl") val sourceUrl: String,
    @ColumnInfo(name = "phoneticScriptText") val phoneticScriptText: String,
    @ColumnInfo(name = "romanLetterText") val romanLetterText: String,
    @ColumnInfo(name = "audioSrc") val audioSrc: String?,
    @ColumnInfo(name = "englishTranslationText") val englishTranslationText: String,
    @ColumnInfo(name = "chineseCharacters") val chineseCharacters: String,
    @ColumnInfo(name = "characterImageSrc") val characterImageSrc: String,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)