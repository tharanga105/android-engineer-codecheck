package jp.co.yumemi.android.code_check.repository

import jp.co.yumemi.android.code_check.model.GitHubRepositoryItem
import jp.co.yumemi.android.code_check.service.GitHubApi
import kotlinx.coroutines.runBlocking
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class GitHubRepositoryImplTest {

    @Mock
    private lateinit var mockApi: GitHubApi

    private lateinit var repository: GitHubRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        repository = GitHubRepositoryImpl(mockApi)
    }

    @Test
    fun `test searchRepository success`() = runBlocking {
        // Mock API response
        val mockJsonResponse = """
            {
                "items": [
                    {
                        "full_name": "test/repo",
                        "owner": {
                            "avatar_url": "https://example.com/avatar.png"
                        },
                        "language": "Kotlin",
                        "stargazers_count": 100,
                        "watchers_count": 50,
                        "forks_count": 20,
                        "open_issues_count": 5
                    }
                ]
            }
        """
        val mockJsonObject = JSONObject(mockJsonResponse)

        `when`(mockApi.search("test")).thenReturn(Result.success(mockJsonObject))

        // Execute the method under test
        val result = repository.searchRepository("test")

        // Assert the result
        assertEquals(
            Result.success(
                listOf(
                    GitHubRepositoryItem(
                        fullName = "test/repo",
                        avatarUrl = "https://example.com/avatar.png",
                        language = "Kotlin",
                        stargazersCount = 100,
                        watchersCount = 50,
                        forksCount = 20,
                        openIssuesCount = 5
                    )
                )
            ), result
        )
    }

    @Test
    fun `test searchRepository failure`() = runBlocking {
        // Mock API failure response
        val errorMessage = "Failed to fetch data"
        `when`(mockApi.search("test")).thenReturn(Result.failure(Throwable(errorMessage)))

        // Execute the method under test
        val result = repository.searchRepository("test")

        // Assert the result
        assertTrue(result.isFailure)
        assertEquals(errorMessage, result.exceptionOrNull()?.message)
    }

}
