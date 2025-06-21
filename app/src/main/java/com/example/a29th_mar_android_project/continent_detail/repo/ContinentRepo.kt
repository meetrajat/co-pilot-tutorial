import android.content.Context
import com.example.a29th_mar_android_project.network.apollo.cache.GraphQLCacheManager
import com.apollographql.apollo3.api.ApolloResponse
import com.example.a29th_mar_android_project.ContinentQuery
import com.example.a29th_mar_android_project.continent_detail.ContinentsServer
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRemoteDataSource
import com.example.a29th_mar_android_project.continent_detail.network.remote.ContinentRepoGQLImpl

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
        val requestKey = "Continent"/*query::class.simpleName + query.variables().toString()*/
        val cachedResponse: String =
            cacheManager.getCachedResponse(requestKey)
        if (cachedResponse != null ) {
            val continentsList = mutableListOf<ContinentsServer>()
            try {
                val json = org.json.JSONObject(cachedResponse)
                val continentsArray = json.getJSONObject("data").getJSONArray("continents")
                for (i in 0 until continentsArray.length()) {
                    val continentJson = continentsArray.getJSONObject(i)
                    val code = continentJson.optString("code", "")
                    val name = continentJson.optString("name", "")
                    continentsList.add(ContinentsServer(code, name))
                }
                return continentsList
            } catch (e: Exception) {
                // handle parse error, fallback to network
            }
        }
        // Not in cache, fetch from network
        val response = remoteDataSource.continentRepoGQLImpl.executeQuery(query)
        cacheManager.cacheResponse(requestKey)
        val continents = response.data?.continents
        return continents?.mapNotNull {
            if (it != null && it.name != null) ContinentsServer(it.code, it.name) else null
        }?.toMutableList() ?: mutableListOf()
    }
}
