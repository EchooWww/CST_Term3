package com.example.lecturedtc.data

import androidx.room.*
import androidx.room.RoomDatabase

@Entity(tableName = "users")
data class LocalUser(
    @PrimaryKey(autoGenerate = true) val uid :Int? = null,
    @ColumnInfo(name = "user_name") val userName: String,
    val email: String,
)

@Dao
interface UserDao{
    @Query ("SELECT * FROM users")
    fun getAll(): List<LocalUser>

    @Insert
    fun add (user: LocalUser)
}

@Database(entities = [LocalUser::class], version = 1)
abstract class AppDatabase : RoomDatabase(){
    abstract fun userDao(): UserDao
}
