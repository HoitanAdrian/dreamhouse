package ch.dreamhouse.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ch.dreamhouse.R
import ch.dreamhouse.databinding.LayoutListArticleBinding
import ch.dreamhouse.models.Article
import ch.dreamhouse.viewmodels.ArticleViewModel

class ArticleListAdapter: RecyclerView.Adapter<ArticleListAdapter.ViewHolder>() {
    private lateinit var articleList:List<Article>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleListAdapter.ViewHolder {
        val binding: LayoutListArticleBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.layout_list_article, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ArticleListAdapter.ViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return if(::articleList.isInitialized) articleList.size else 0
    }

    fun updatePostList(postList:List<Article>){
        this.articleList = postList
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: LayoutListArticleBinding): RecyclerView.ViewHolder(binding.root){
        private val articleViewModel = ArticleViewModel()

        fun bind(post:Article){
            articleViewModel.bind(post)
            binding.viewModel = articleViewModel
            binding.listArticleViewPager.adapter = ArticleListImagesAdapter(articleViewModel)
            binding.listArticleDotIndicator.setupWithViewPager(binding.listArticleViewPager)
        }
    }
}