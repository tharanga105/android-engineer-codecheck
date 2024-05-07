package jp.co.yumemi.android.code_check.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import jp.co.yumemi.android.code_check.model.GitHubRepositoryItem
import jp.co.yumemi.android.code_check.repository.GitHubRepository
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GitHubRepository) : ViewModel() {
    private val _repositoryItems: MutableLiveData<List<GitHubRepositoryItem>> = MutableLiveData()
    val repositoryItems: LiveData<List<GitHubRepositoryItem>> = _repositoryItems

    companion object {
        fun provideFactory(repository: GitHubRepository) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return SearchViewModel(repository) as T
                }
            }
    }

    fun searchResults(inputText: String) {
        viewModelScope.launch {
            repository.searchRepository(inputText).fold(
                onSuccess = {
                    _repositoryItems.value = it
                },
                onFailure = {
                    Log.d(this@SearchViewModel.javaClass.simpleName, it.stackTraceToString())
                },
            )
        }
    }



}