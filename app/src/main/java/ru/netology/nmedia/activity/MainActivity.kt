package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.edit(post)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onRepost(post: Post) {
                viewModel.repostById(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = posts.size > adapter.currentList.size
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0)
                }
            }
        }

        // Отмена редактирования
        binding.close.setOnClickListener {
            binding.editGroup.visibility = View.GONE
            viewModel.closeEdit()
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            else {
                binding.editablePostText.text = post.content // Текст редактируемого поста
                binding.content.requestFocus()
            }

        }
        // Наблюдение за состоянием редактирования
        viewModel.isEditing.observe(this) { isEditing ->
            if (isEditing) {
                binding.editGroup.visibility = View.VISIBLE // Показываем группу
            } else {
                binding.editGroup.visibility = View.GONE // Скрываем группу
            }
        }

        // Наблюдаем за изменениями в редактируемом посте
        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                binding.editablePostText.text = ""
                binding.content.setText("")
            } else {
                binding.editablePostText.text = post.content
                binding.content.setText(post.content)
                binding.content.requestFocus()
            }
        }

        // Сохранение
        binding.save.setOnClickListener {
            with(binding.content) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text.toString())
                viewModel.save()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }
    }
}