package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class EditPostResultContract : ActivityResultContract<String?, String?>() {

    override fun createIntent(context: Context, input: String?): Intent =
        Intent(context, EditPostActivity::class.java).apply {
            putExtra(Intent.EXTRA_TEXT, input) // Передаем текст поста для редактирования
        }

    override fun parseResult(resultCode: Int, intent: Intent?): String? =
        if (resultCode == Activity.RESULT_OK) {
            intent?.getStringExtra(Intent.EXTRA_TEXT) // Возвращаем измененный текст
        } else {
            null
        }
}
