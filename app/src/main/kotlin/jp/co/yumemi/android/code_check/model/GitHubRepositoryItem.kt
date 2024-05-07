package jp.co.yumemi.android.code_check.model

data class GitHubRepositoryItem(
    val fullName: String,
    val avatarUrl: String,
    val language: String,
    val stargazersCount: Long,
    val watchersCount: Long,
    val forksCount: Long,
    val openIssuesCount: Long,
)
