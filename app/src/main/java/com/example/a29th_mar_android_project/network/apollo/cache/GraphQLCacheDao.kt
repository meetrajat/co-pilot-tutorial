package com.example.a29th_mar_android_project.network.apollo.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Delete

@Dao
interface GraphQLCacheDao {
    @Query("SELECT * FROM graphql_cache WHERE requestKey = :requestKey LIMIT 1")
    suspend fun getCacheByKey(requestKey: String): GraphQLCacheEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateCache(entity: GraphQLCacheEntity)

    @Query("SELECT * FROM graphql_cache ORDER BY timestamp ASC")
    suspend fun getAllCacheOrderedByOldest(): List<GraphQLCacheEntity>

    @Query("DELETE FROM graphql_cache WHERE requestKey = :requestKey")
    suspend fun deleteCacheByKey(requestKey: String)

    @Query("DELETE FROM graphql_cache WHERE requestKey NOT IN (SELECT requestKey FROM graphql_cache ORDER BY timestamp DESC LIMIT 5)")
    suspend fun pruneToLast5()
}

