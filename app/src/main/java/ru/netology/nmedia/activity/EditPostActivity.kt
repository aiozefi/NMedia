package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityEditPostBinding

class EditPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityEditPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val content = intent.getStringExtra(Intent.EXTRA_TEXT)
        binding.edit.setText(content) // Устанавливаем исходный текст для редактирования

        binding.ok.setOnClickListener {
            val resultIntent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                setResult(Activity.RESULT_CANCELED, resultIntent)
            } else {
                val updatedContent = binding.edit.text.toString()
                resultIntent.putExtra(Intent.EXTRA_TEXT, updatedContent)
                setResult(Activity.RESULT_OK, resultIntent)
            }
            finish()
        }
    }
}
