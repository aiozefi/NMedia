package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.util.Log
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 1,
            author = "Нетология. Университет интернет-профессий будущего",
            content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            published = "21 мая в 18:36",
            likedByMe = false,
            repostByMe = false,
            likes = 999,
            reposts = 0

        )
        with(binding) {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            if (post.likedByMe) {
                like?.setImageResource(R.drawable.ic_liked)
            }
            numLikes?.text = formatNumber(post.likes)
            numReposts?.text = formatNumber(post.reposts)

            root.setOnClickListener {
                Log.d("stuff", "stuff")
            }

            avatar.setOnClickListener {
                Log.d("stuff", "avatar")
            }

            like?.setOnClickListener {
                Log.d("stuff", "like")
                post.likedByMe = !post.likedByMe
                if (post.likedByMe) post.likes++ else post.likes-- // Увеличение или уменьшение количества лайков
                like.setImageResource(if (post.likedByMe) R.drawable.ic_liked else R.drawable.ic_like_icon) // Замена иконки
                numLikes?.text = formatNumber(post.likes) // Обновление числа лайков
            }

            repost?.setOnClickListener { // Кнопка репоста
                Log.d("stuff", "repost")
                post.reposts++ // Увеличение количества репостов при каждом нажатии
                numReposts?.text = formatNumber(post.reposts) // Обновление числа репостов
            }
        }
    }

    // Функция форматирования чисел
    private fun formatNumber(number: Int): String {
        return when {
            number >= 1_000_000 -> String.format("%.1fM", number / 1_000_000.0)
            number >= 10_000 -> String.format("%dK", number / 1_000)
            number >= 1_100 -> String.format("%.1fK", number / 1_000.0)
            number >= 1_000 -> "1K"
            else -> number.toString()
        }
    }
}