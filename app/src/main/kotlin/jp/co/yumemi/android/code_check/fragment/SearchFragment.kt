package jp.co.yumemi.android.code_check.fragment

import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.adapter.SearchListAdapter
import jp.co.yumemi.android.code_check.databinding.FragmentSearchBinding
import jp.co.yumemi.android.code_check.model.GitHubRepositoryItem
import jp.co.yumemi.android.code_check.repository.GitHubRepositoryImpl
import jp.co.yumemi.android.code_check.service.GitHubApi
import jp.co.yumemi.android.code_check.service.GitHubApiImpl
import jp.co.yumemi.android.code_check.view_model.SearchViewModel

class SearchFragment : Fragment(R.layout.fragment_detail) {

    private val viewModel: SearchViewModel by viewModels {
        val client = HttpClient(Android)
        val api: GitHubApi = GitHubApiImpl(client)
        val repository = GitHubRepositoryImpl(api)
        SearchViewModel.provideFactory(repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentSearchBinding.bind(view)
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = context?.let {
            DividerItemDecoration(it, layoutManager.orientation)
        }
        val adapter = SearchListAdapter(object : SearchListAdapter.OnItemClickListener {
            override fun itemClick(item: GitHubRepositoryItem) {
                gotoRepositoryFragment(item)
            }
        })

        binding.run {
            searchInputText.setOnEditorActionListener { editText, action, _ ->
                if (action == EditorInfo.IME_ACTION_SEARCH) {
                    editText.text.toString().let {
                        viewModel.searchResults(it)
                    }
                    return@setOnEditorActionListener true
                }
                return@setOnEditorActionListener false
            }
            recyclerView.also {
                it.layoutManager = layoutManager
                dividerItemDecoration?.run {
                    it.addItemDecoration(this)
                }
                it.adapter = adapter
            }
        }

        viewModel.repositoryItems.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun gotoRepositoryFragment(item: GitHubRepositoryItem) {
        val action = SearchFragmentDirections
            .actionSearchFragmentToDetailFragment(item = item)
        findNavController().navigate(action)
    }
}
