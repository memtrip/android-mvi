package com.consistence.pinyin.domain.study.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.consistence.pinyin.domain.pinyin.Pinyin
import com.consistence.pinyin.domain.pinyin.db.GetPinyin
import com.consistence.pinyin.domain.study.Study
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction

@Entity(tableName = "Study")
data class StudyEntity(
    @ColumnInfo(name = "englishTranslation") val englishTranslation: String,
    @ColumnInfo(name = "chineseSentence") val chineseSentence: String,
    @ColumnInfo(name = "correct") val correct: Int,
    @ColumnInfo(name = "incorrect") val incorrect: Int,
    @PrimaryKey(autoGenerate = true) val uid: Int = 0
)

fun String.listOfUid(): List<Int> = split("-").map {
    Integer.parseInt(it)
}

fun Observable<List<StudyEntity>>.withPinyin(getPinyin: GetPinyin): Single<List<Study>> {
    return flatMap { Observable.fromIterable(it) }
        .concatMap { studyEntity ->
            Observable.zip(
                Observable.just(studyEntity),
                getPinyin.byListOfUid(studyEntity.chineseSentence.listOfUid()).toObservable(),
                BiFunction<StudyEntity, List<Pinyin>, Study> { _, pinyin ->
                    Study(
                        studyEntity.englishTranslation,
                        pinyin,
                        studyEntity.uid
                    )
                })
        }
        .toList()
}
