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

@Database(entities = arrayOf(PinyinEntity::class), version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pinyinDao(): PinyinDao
}

@Entity(tableName = "Pinyin")
data class PinyinEntity(
        @ColumnInfo(name = "sourceUrl") val sourceUrl: String,
        @ColumnInfo(name = "phoneticScriptText") val phoneticScriptText: String,
        @ColumnInfo(name = "romanLetterText") val romanLetterText: String,
        @ColumnInfo(name = "audioSrc") val audioSrc: String?,
        @ColumnInfo(name = "englishTranslationText") val englishTranslationText: String,
        @ColumnInfo(name = "chineseCharacters") val chineseCharacters: String,
        @ColumnInfo(name = "characterImageSrc") val characterImageSrc: String,
        @PrimaryKey(autoGenerate = true) val uid: Int = 0)

@Dao
interface PinyinDao {

    @Query("SELECT * FROM Pinyin ORDER BY romanLetterText ASC LIMIT :skip, :limit")
    fun get(skip: Int, limit: Int): Single<List<PinyinEntity>>

    @Query("SELECT * FROM Pinyin WHERE romanLetterText LIKE :terms ORDER BY romanLetterText ASC LIMIT 0, 100")
    fun phoneticSearch(terms: String): Single<List<PinyinEntity>>

    @Query("SELECT * FROM Pinyin WHERE chineseCharacters LIKE :terms ORDER BY chineseCharacters ASC LIMIT 0, 100")
    fun characterSearch(terms: String): Single<List<PinyinEntity>>

    @Query("SELECT * FROM Pinyin WHERE englishTranslationText LIKE :terms ORDER BY englishTranslationText ASC LIMIT 0, 100")
    fun englishSearch(terms: String): Single<List<PinyinEntity>>

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
                    it.chineseCharacters,
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

class GetPinyin @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun get(skip: Int, limit: Int, pinyin: Consumer<List<PinyinEntity>>, error: Consumer<Throwable>): Disposable =
            pinyinDao.get(skip, limit)
                    .observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.thread())
                    .subscribe(pinyin, error)
}

class PhoneticSearch @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun search(terms: String, pinyin: Consumer<List<PinyinEntity>>, error: Consumer<Throwable>): Disposable =
            pinyinDao.phoneticSearch( terms+"%")
                    .observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.thread())
                    .subscribe(pinyin, error)
}

class CharacterSearch @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun search(terms: String, pinyin: Consumer<List<PinyinEntity>>, error: Consumer<Throwable>): Disposable =
            pinyinDao.characterSearch(  terms+"%")
                    .observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.thread())
                    .subscribe(pinyin, error)
}

class EnglishSearch @Inject internal constructor(
        private val pinyinDao: PinyinDao,
        private val schedulerProvider: SchedulerProvider) {

    fun search(terms: String, pinyin: Consumer<List<PinyinEntity>>, error: Consumer<Throwable>): Disposable =
            pinyinDao.englishSearch( "%" + terms + "%")
                    .observeOn(schedulerProvider.main())
                    .subscribeOn(schedulerProvider.thread())
                    .subscribe(pinyin, error)
}