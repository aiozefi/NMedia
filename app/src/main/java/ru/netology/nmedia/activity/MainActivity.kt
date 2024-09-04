package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import android.util.Log
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewmodel.PostViewModel


class MainActivity : AppCompatActivity() {
    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.author
                published.text = post.published
                content.text = post.content
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like_icon)
                numLikes.text = formatNumber(post.likes)
                numReposts.text = formatNumber(post.reposts)
            }
        }

        with(binding) {
            like.setOnClickListener {
                viewModel.onLikeClicked()
            }

            repost.setOnClickListener {
                viewModel.onRepostClicked()
            }
        }
    }

    // Функция форматирования чисел
    private fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> "${number / 1_000_000}.${(number % 1_000_000) / 100_000}M"
            number >= 10_000 -> "${number / 1_000}K"
            number >= 1_000 -> "${number / 1_000}.${(number % 1_000) / 100}K"
            else -> number.toString()
        }
    }
}