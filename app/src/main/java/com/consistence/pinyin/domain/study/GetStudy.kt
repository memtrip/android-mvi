package com.consistence.pinyin.domain.study

import com.consistence.pinyin.domain.study.db.GetStudyOrderedByDesc
import io.reactivex.Single
import javax.inject.Inject

class GetStudy @Inject internal constructor(
    private val getStudyOrderedByDesc: GetStudyOrderedByDesc
) {

    fun get(): Single<List<Study>> {
        return getStudyOrderedByDesc.get().map { studyItems ->
            studyItems.map {
                Study(
                    it.uid,
                    it.englishTranslation,
                    listOf()
                )
            }
        }
    }
}