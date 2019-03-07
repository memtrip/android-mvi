package com.consistence.pinyin.domain.pinyin.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PinyinDao {

    @Query("SELECT * FROM Pinyin WHERE romanLetterText LIKE :terms ORDER BY romanLetterText ASC LIMIT 0, 100")
    fun phoneticSearch(terms: String): List<PinyinEntity>

    @Query("SELECT * FROM Pinyin WHERE chineseCharacters LIKE :terms ORDER BY chineseCharacters ASC LIMIT 0, 100")
    fun characterSearch(terms: String): List<PinyinEntity>

    @Query("SELECT * FROM Pinyin WHERE englishTranslationText LIKE :terms ORDER BY englishTranslationText ASC LIMIT 0, 100")
    fun englishSearch(terms: String): List<PinyinEntity>

    @Insert
    fun insertAll(pinyin: List<PinyinEntity>)

    @Query("SELECT COUNT(*) FROM Pinyin")
    fun count(): Int
}