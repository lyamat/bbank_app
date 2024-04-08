package com.example.bbank.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bbank.databinding.NewsRvItemBinding
import com.example.bbank.domain.models.News

internal class NewsAdapter(
    private val context: Context,
    private var news: List<News>,
    private val onClick: (News) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(binding: NewsRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val tvNewsDate: TextView = binding.tvNewsDate
        val tvNewsLink: TextView = binding.tvNewsLink
        val tvNewsTitle: TextView = binding.tvNewsTitle
        val ivThumbnail: ImageView = binding.ivThumbnail
        val newsCardView: CardView = binding.newsCardView

        init {
            binding.root.setOnClickListener {
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

        loadImageIntoView(holder.ivThumbnail, chosenNews.img)
        holder.tvNewsDate.text = chosenNews.startDate
        holder.tvNewsLink.text = chosenNews.link
        holder.tvNewsTitle.text = chosenNews.nameRu

        newsSetOnClickListener(holder.newsCardView, position)
    }

    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) {
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView)
    }

    private fun newsSetOnClickListener(newsCardView: CardView, position: Int) {
        newsCardView.setOnClickListener {
            onClick(news[position])
        }
    }

    internal fun updateNewsAdapterData(newNews: List<News>) {
        news = newNews
        notifyDataSetChanged()
    }
}