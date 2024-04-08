package com.example.bbank.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bbank.databinding.NewsRvItemBinding
import com.example.bbank.domain.models.News

internal class NewsAdapter(
    private val context: Context,
    private var news: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(var view: NewsRvItemBinding) : RecyclerView.ViewHolder(view.root) {
        init {
            view.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val chosenNews = news[position]
                    onClick(chosenNews)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return NewsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return news.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val chosenNews = news[position]

        val view = holder.view

        loadImageIntoView(holder.view.ivThumbnail, chosenNews.img)
        view.tvNewsDate.text = chosenNews.startDate
        view.tvNewsLink.text = chosenNews.link
        view.tvNewsTitle.text = chosenNews.nameRu
    }

    internal fun updateNewsAdapterData(newNews: List<News>) {
        news = newNews
        notifyDataSetChanged()
    }

    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }
}