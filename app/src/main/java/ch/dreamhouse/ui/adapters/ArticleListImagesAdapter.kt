package ch.dreamhouse.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ch.dreamhouse.R
import ch.dreamhouse.databinding.LayoutListArticlePictureBinding
import ch.dreamhouse.viewmodels.ArticleViewModel
import com.squareup.picasso.Picasso

class ArticleListImagesAdapter(private val viewModel: ArticleViewModel): PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: LayoutListArticlePictureBinding = DataBindingUtil.inflate(LayoutInflater.from(container.context), R.layout.layout_list_article_picture, container, false)

        // Load image in the background
        Picasso.get().load(viewModel.articlePictures.value!![position]).into(view.articleImage)
        container?.addView(view.root)

        return view.root
    }

    override fun isViewFromObject(p0: View, p1: Any): Boolean {
        return p0 === `p1`
    }

    override fun getCount(): Int {
        return viewModel.articlePictures.value!!.size
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }
}