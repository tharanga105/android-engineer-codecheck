package jp.co.yumemi.android.code_check.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import jp.co.yumemi.android.code_check.R
import jp.co.yumemi.android.code_check.model.GitHubRepositoryItem

class SearchListAdapter(
    private val itemClickListener: OnItemClickListener,
) : ListAdapter<GitHubRepositoryItem, SearchListAdapter.ViewHolder>(DiffCallback) {

    private lateinit var repoName: TextView
    class ViewHolder(
        itemView: View,
        private val itemClickListener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(itemView) {
        private val repositoryImageView: ImageView = itemView.findViewById(R.id.ivrepoCV)
        private val repositoryNameView: TextView = itemView.findViewById(R.id.tvRepoNameCV)


        init {
            // Apply border to repositoryImageView
            repositoryImageView.setBackgroundResource(R.drawable.image_border)
        }

        fun bind(item: GitHubRepositoryItem) {
            // Load image using Glide or Picasso
            Glide.with(itemView)
                .load(item.avatarUrl)
                .placeholder(R.drawable.placeholder_image) // Placeholder image while loading
                .error(R.drawable.error_image) // Image to display if loading fails
                .centerCrop()
                .into(repositoryImageView)

            repositoryNameView.text = item.fullName
            itemView.setOnClickListener {
                itemClickListener.itemClick(item)
            }
        }
    }




    /*class ViewHolder(
        private val binding: LayoutItemBinding,
        private val itemClickListener: OnItemClickListener,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: GitHubRepositoryItem) {
            binding.repositoryNameView.run {
                text = item.fullName
                setOnClickListener {
                    itemClickListener.itemClick(item)
                }
            }
        }
    }*/

    interface OnItemClickListener {
        fun itemClick(item: GitHubRepositoryItem)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_card_view, parent, false)
        return ViewHolder(view, itemClickListener)
    }

    /*override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //val view = LayoutInflater.from(parent.context).inflate(R.layout.repo_card_view,parent,false)
        val binding = LayoutItemBinding.inflate(view, parent, false)
        return ViewHolder(binding, itemClickListener)
    }*/

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffCallback : DiffUtil.ItemCallback<GitHubRepositoryItem>() {
        override fun areItemsTheSame(
            oldItem: GitHubRepositoryItem,
            newItem: GitHubRepositoryItem
        ): Boolean {
            return oldItem.fullName == newItem.fullName
        }

        override fun areContentsTheSame(
            oldItem: GitHubRepositoryItem,
            newItem: GitHubRepositoryItem
        ): Boolean {
            return oldItem == newItem
        }
    }
}
