package ch.dreamhouse.models.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "favorites")
data class ArticleFavorites(@PrimaryKey(autoGenerate = true) var id: Long?,
                            @ColumnInfo(name = "expire_date") var expireDate: Long) {
    constructor():this(null,0)
}