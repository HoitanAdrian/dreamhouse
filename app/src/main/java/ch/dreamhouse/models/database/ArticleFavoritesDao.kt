package ch.dreamhouse.models.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query

@Dao
interface ArticleFavoritesDao {
    @Query("SELECT * from favorites")
    fun getAll(): List<ArticleFavorites>

    @Insert(onConflict = REPLACE)
    fun insert(favoriteData: ArticleFavorites)

    @Query("DELETE FROM favorites WHERE id = :id")
    fun delete(id: Long)
}