package com.example.a29th_mar_android_project.network.apollo.cache

import android.content.Context
import androidx.room.Room

object GraphQLCacheDatabaseProvider {
    @Volatile
    private var INSTANCE: GraphQLCacheDatabase? = null

    fun getInstance(context: Context): GraphQLCacheDatabase {
        return INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                GraphQLCacheDatabase::class.java,
                "graphql_cache_db"
            ).build().also { INSTANCE = it }
        }
    }
}

