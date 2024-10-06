package com.example.tugas6

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas6.databinding.ActivityTaskListBinding

class TaskListActivity : AppCompatActivity() {
    private val binding by lazy { ActivityTaskListBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val title = intent.getStringExtra("EXTRA_TITLE")
        val repeat = intent.getStringExtra("EXTRA_REPEAT")
        val date = intent.getStringExtra("EXTRA_DATE")
        val time = intent.getStringExtra("EXTRA_TIME")

        with(binding) {
            taskTitle.text = title
            taskRepeat.text = repeat
            taskDate.text = date
            taskTime.text = time
        }
    }

    fun addTask(view: View) {
        finish()
    }
}