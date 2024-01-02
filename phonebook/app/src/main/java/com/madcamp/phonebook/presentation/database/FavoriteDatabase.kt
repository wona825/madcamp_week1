package com.madcamp.phonebook.presentation.database

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Entity
data class Favorites(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var name: String,
    val image: Uri?,
    var love: Boolean,
    var description: String,
    var valid: Boolean,
    var dateTime: String?
)

class Converters {
    @TypeConverter
    fun fromUri(uri: Uri?): String? = uri?.toString()

    @TypeConverter
    fun toUri(uriString: String?): Uri? = uriString?.let { Uri.parse(it) }

}

@Dao
interface FavoritesDao {
    @Query("SELECT * FROM favorites")
    fun getAll(): List<Favorites>

    @Insert
    fun insert(favorite: Favorites)

    @Delete
    fun delete(favorite: Favorites)

    @Query("SELECT * FROM Favorites")
    fun getAllFavorites(): List<Favorites>

    @Query("SELECT * FROM Favorites WHERE dateTime = :dateTime")
    fun findFavoritesByDate(dateTime: String): List<Favorites>
}

@Database(entities = [Favorites::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesDao(): FavoritesDao
}

class FavoriteViewModel : ViewModel() {
    var database: AppDatabase? = null

    fun createDatabase(context: Context) {
        Log.e("FavoriteViewModel", "Database creation Start")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, "FavoriteDatabase"
                ).build()
                Log.e("FavoriteViewModel", "Database creation Succeed")
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Database creation failed", e)
            }
        }
    }

    fun addFavorite(favorite: Favorites) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                database?.favoritesDao()?.insert(favorite)
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error adding favorite", e)
            }
        }
    }

    fun getAllFavorites(callback: (List<Favorites>) -> Unit) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val favorites = database?.favoritesDao()?.getAll()
                favorites?.let {
                    withContext(Dispatchers.Main) {
                        callback(it)
                    }
                }
            } catch (e: Exception) {
                Log.e("FavoriteViewModel", "Error retrieving favorites", e)
            }
        }
    }

//    fun findFavoritesByDate(dateTime: String, callback: (List<Favorites>)->Unit) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val favorites = database.favoritesDao().findFavoritesByDate(dateTime)
//                withContext(Dispatchers.Main) {
//                    callback(favorites)
//                }
//            } catch (e: Exception) {
//                Log.e("FavoriteViewModel", "Error finding favorites by date", e)
//            }
//        }
//    }
}