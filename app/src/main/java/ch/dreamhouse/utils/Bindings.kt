package ch.dreamhouse.utils

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.databinding.BindingAdapter
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import ch.dreamhouse.R

@BindingAdapter("loadingIndicator")
fun setLoadingIndicator(view: View, visibility: MutableLiveData<Boolean>?) {
    val parentActivity: AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && visibility != null) {
        visibility.observe(parentActivity, Observer { value -> view.visibility = if (value!!) View.VISIBLE else View.INVISIBLE})
    }
}

@BindingAdapter("mutableText")
fun setMutableText(view: TextView, text: MutableLiveData<String>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && text != null) {
        text.observe(parentActivity, Observer { value -> view.text = value?:""})
    }
}

@BindingAdapter("favoriteVisibility")
fun setFavoriteVisibility(view: ImageButton, favorite: MutableLiveData<Boolean>?) {
    val parentActivity:AppCompatActivity? = view.getParentActivity()
    if(parentActivity != null && favorite != null) {
        favorite.observe(parentActivity, Observer { isFavorite -> view.setImageResource(if (isFavorite!!) R.drawable.ic_article_list_favorite else R.drawable.ic_article_list_not_favorite)})
    }
}