import android.content.Context
import com.example.a29th_mar_android_project.network.apollo.cache.GraphQLCacheManager
import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRemoteDataSource
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRepoGQLImpl
import com.example.a29th_mar_android_project.util.AppUtils
import com.example.a29th_mar_android_project.util.BuildJsonObject

class ContinentRepo(
    private val remoteDataSource: ContinentRemoteDataSource,
    private val cacheManager: GraphQLCacheManager
) {
    companion object {
        fun create(context: Context): ContinentRepo {
            val gqlImpl = ContinentRepoGQLImpl()
            val remoteDataSource = ContinentRemoteDataSource(gqlImpl)
            val cacheManager = GraphQLCacheManager(context)
            return ContinentRepo(remoteDataSource, cacheManager)
        }
    }

    suspend fun getContinent(continentCode: String): MutableList<ContinentsServer> {
        val query = ContinentQuery() // If ContinentQuery takes parameters, pass them here
        val networkRequestKey = AppUtils.getNetworkRequestKey(AppUtils.REQUEST_NAME_CONTINENT) // Update requestKey if needed
        val cachedResponse: String =
            cacheManager.getCachedResponse(networkRequestKey)
        if (cachedResponse != null && cachedResponse.isNotEmpty()) {
            try {
                return BuildJsonObject.parseContinentsFromJson(cachedResponse)
            } catch (e: Exception) {
                // handle parse error, fallback to network
            }
        }
        // Not in cache, fetch from network
        val response = remoteDataSource.continentRepoGQLImpl.executeQuery(query)

        // Store the response in database
        response.data?.let { data ->
            val jsonResponse = BuildJsonObject.buildContinentsJson(data.continents)
            cacheManager.cacheResponse(jsonResponse, networkRequestKey)
        }

        val continents = response.data?.continents
        return continents?.mapNotNull {
            if (it != null && it.name != null) ContinentsServer(it.code, it.name) else null
        }?.toMutableList() ?: mutableListOf()
    }
}
