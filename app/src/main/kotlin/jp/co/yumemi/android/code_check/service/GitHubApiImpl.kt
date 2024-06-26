package jp.co.yumemi.android.code_check.service

import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse
import org.json.JSONObject

class GitHubApiImpl(private val client: HttpClient) : GitHubApi {
    override suspend fun search(query: String): Result<JSONObject> {
        return runCatching {
            val response = client.get<HttpResponse>("https://api.github.com/search/repositories") {
                header("Accept", "application/vnd.github.v3+json")
                parameter("q", query)
            }
            val jsonBody = JSONObject(response.receive<String>())
            Result.success(jsonBody)
        }.getOrElse {
            Result.failure(it)
        }
    }
}
