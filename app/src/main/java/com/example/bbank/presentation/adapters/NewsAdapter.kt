package com.example.bbank.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bbank.databinding.ItemNewsRvBinding
import com.example.bbank.domain.models.News

internal class NewsAdapter(
    private var news: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(binding: ItemNewsRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvNewsDate: TextView = binding.tvNewsDate
        val tvNewsLink: TextView = binding.tvNewsLink
        val tvNewsTitle: TextView = binding.tvNewsTitle
        val ivThumbnail: ImageView = binding.ivThumbnail
        val newsCardView: CardView = binding.newsCardView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int = news.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val chosenNews = news[position]
        with(holder) {
            tvNewsDate.text = chosenNews.startDate
            tvNewsLink.text = chosenNews.link
            tvNewsTitle.text = chosenNews.nameRu
            newsSetOnClickListener(newsCardView, position)
            loadImageIntoView(ivThumbnail, chosenNews.img)
        }
    }

    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) =
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)

    private fun newsSetOnClickListener(newsCardView: CardView, position: Int) =
        newsCardView.setOnClickListener {
            onClick(news[position])
        }

    internal fun updateNewsAdapterData(newNews: List<News>) {
        if (news != newNews) {
            news = newNews
            notifyDataSetChanged()
        }
    }
}