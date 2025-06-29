package com.example.a29th_mar_android_project.network.apollo.cache

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "graphql_cache")
data class GraphQLCacheEntity(
    @PrimaryKey val requestKey: Int, // hash of query+variables
    val response: String, // JSON or stringified response
    val timestamp: Long // for LRU
)

