package jp.co.yumemi.android.code_check.service

import org.json.JSONObject

interface GitHubApi {
    suspend fun search(query: String): Result<JSONObject>
}