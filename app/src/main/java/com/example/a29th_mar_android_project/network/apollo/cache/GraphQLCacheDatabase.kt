package com.example.a29th_mar_android_project.network.apollo.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GraphQLCacheEntity::class], version = 1, exportSchema = false)
abstract class GraphQLCacheDatabase : RoomDatabase() {
    abstract fun graphQLCacheDao(): GraphQLCacheDao
}

