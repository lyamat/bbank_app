package com.example.news.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.core.domain.news.News
import com.example.core.domain.news.NewsLink
import com.example.news.presentation.databinding.ItemNewsRvBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

internal class NewsAdapter(
    private var news: List<News>,
    private val onClick: (NewsLink) -> Unit
) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

    private val currentDate: Date = Calendar.getInstance().time
    private lateinit var context: Context

    internal inner class NewsViewHolder(binding: ItemNewsRvBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvTimeSincePublication: TextView = binding.tvTimeSincePublication
        val tvNewsTitle: TextView = binding.tvNewsTitle
        val ivThumbnail: ImageView = binding.ivThumbnail
        val newsCardView: LinearLayout = binding.newsElement
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = ItemNewsRvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        context = parent.context
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val newsItem = news[position]
        with(holder) {
            tvTimeSincePublication.text = getTimeSincePublication(newsItem.startDate)
            tvNewsTitle.text = newsItem.nameRu
            newsCardView.setOnClickListener { onClick(news[position].link) }
            loadImageIntoView(ivThumbnail, newsItem.img)
        }
    }

    private fun loadImageIntoView(imageView: ImageView, imageUrl: String) =
        Glide.with(imageView.context)
            .load(imageUrl)
            .into(imageView) // TODO:  placeholder, onError

    internal fun updateNews(newNews: List<News>) {
        news = newNews
        notifyDataSetChanged()
    }

    private fun getTimeSincePublication(startDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = sdf.parse(startDate) ?: return startDate
        val difference = (currentDate.time - date.time) / (1000 * 60 * 60 * 24)

        return when {
            difference == 0L -> context.getString(R.string.today)
            difference < 7 -> context.getString(R.string.days_since, difference)
            difference < 30 -> context.getString(R.string.weeks_since, difference / 7)
            difference < 365 -> context.getString(R.string.months_since, difference / 30)
            else -> context.getString(R.string.years_since, difference / 365)
        }
    }

    override fun getItemCount(): Int = news.size
}