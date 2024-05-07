package jp.co.yumemi.android.code_check.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.activity.TopActivity.Companion.lastSearchDate
import jp.co.yumemi.android.code_check.databinding.FragmentDetailBinding

class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val args: DetailFragmentArgs by navArgs()

    private lateinit var binding: FragmentDetailBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("検索した日時", lastSearchDate.toString())

        binding = FragmentDetailBinding.bind(view)

        val item = args.item

        binding.run {
            ownerIconView.load(item.avatarUrl)
            nameView.text = item.fullName
            languageView.text = getString(R.string.written_language, item.language)
            starsView.text = "${item.stargazersCount} stars"
            watchersView.text = "${item.watchersCount} watchers"
            forksView.text = "${item.forksCount} forks"
            openIssuesView.text = "${item.openIssuesCount} open issues"
        }
    }
}