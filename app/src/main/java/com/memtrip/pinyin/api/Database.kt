package com.memtrip.pinyin.api

import android.app.Application
import android.arch.persistence.room.*
import dagger.Module
import dagger.Provides
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import javax.inject.Inject
import javax.inject.Singleton

@Database(entities = arrayOf(PinyinEntity::class), version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinyinDao(): PinyinDao
}

@Entity(tableName = "Pinyin")
data class PinyinEntity(
        @ColumnInfo(name = "sourceUrl") val id: String,
        @ColumnInfo(name = "phoneticScriptText") val type: String,
        @ColumnInfo(name = "romanLetterText") val contentId: String,
        @ColumnInfo(name = "audioSrc") val audioSrc: String?,
        @ColumnInfo(name = "englishTranslationText") val englishTranslationText: String,
        @ColumnInfo(name = "characterImageSrc") val characterImageSrc: String,
        @PrimaryKey(autoGenerate = true) val uid: Int = 0)

@Dao
interface PinyinDao {

    @Query("SELECT * FROM Pinyin ORDER BY romanLetterText DESC LIMIT :skip, :limit")
    fun get(skip: Int, limit: Int): Single<List<PinyinEntity>>

    @Insert
    fun insertAll(pinyin: List<PinyinEntity>)

    @Query("SELECT COUNT(*) FROM Pinyin")
    fun count(): Int
}

@Module
class DatabaseModule {

    @Provides @Singleton
    fun appDatabase(application: Application) : AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, "pingyin")
                .fallbackToDestructiveMigration()
                .build()
    }

    @Provides @Singleton
    fun pinyinDao(appDatabase: AppDatabase) : PinyinDao {
        return appDatabase.pinyinDao();
    }
}

class SavePinyin @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun insert(pinyin: List<PinyinJson>,
               success: Action,
               error: Consumer<Throwable>): Disposable = Completable.fromAction({

        pinyinDao.insertAll(pinyin.map {
            PinyinEntity(it.sourceUrl,
                    it.phoneticScriptText,
                    it.romanLetterText,
                    it.audioSrc,
                    it.englishTranslationText,
                    it.characterImageSrc)
        })
    }).observeOn(schedulerProvider.main())
            .subscribeOn(schedulerProvider.thread())
            .subscribe(success, error)
}

class CountPinyin @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun count(hasPinyin: Consumer<Int>, error: Consumer<Throwable>): Disposable =
            Single.fromCallable({ pinyinDao.count() }).observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.thread())
                    .subscribe(hasPinyin, error)
}