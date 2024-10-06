package com.example.tugas6

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugas6.databinding.ActivityAddTaskBinding
import java.util.Calendar
import java.util.Date

class AddTaskActivity : AppCompatActivity() {
    private val binding by lazy { ActivityAddTaskBinding.inflate(layoutInflater) }
    var year = Calendar.getInstance().get(Calendar.YEAR)
    var month = Calendar.getInstance().get(Calendar.MONTH)
    var dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val adapter = ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            arrayOf("Once", "Daily", "Weekly", "Monthly")
        )

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.repeatSpinner.adapter = adapter
    }

    fun showDatePicker(view: View) {
        Log.d("AddTask", "showDatePicker: $year")
        DatePickerDialog(this, { _, year, month, dayOfMonth ->
            binding.tvDate.text = "$dayOfMonth/${month + 1}/$year"
            this.year = year
            this.month = month
            this.dayOfMonth = dayOfMonth
        }, this.year, this.month, this.dayOfMonth).show()
    }

    fun addTask(view: View) {
        val alertDialog = AlertDialog.Builder(this).setTitle("Add Task")
            .setMessage("Are you sure you want to add this task?")
            .setPositiveButton("Yes") { dialog, which ->
                val intent = Intent(this, TaskListActivity::class.java)

                val title = binding.etTitle.text.toString()
                val repeat = binding.repeatSpinner.selectedItem.toString()
                val date = binding.tvDate.text.toString()
                val time =
                    String.format("%02d:%02d", binding.timePicker.hour, binding.timePicker.minute)

                intent.putExtra("EXTRA_TITLE", title)
                intent.putExtra("EXTRA_REPEAT", repeat)
                intent.putExtra("EXTRA_DATE", date)
                intent.putExtra("EXTRA_TIME", time)

                startActivity(intent)
            }.setNegativeButton("No") { dialog, which -> }.create()
        alertDialog.show()
    }

    override fun onResume() {
        super.onResume()
        binding.etTitle.text.clear()
        binding.repeatSpinner.setSelection(0)
        binding.tvDate.text = "Select Date"
        this.year = Calendar.getInstance().get(Calendar.YEAR)
        this.month = Calendar.getInstance().get(Calendar.MONTH)
        this.dayOfMonth = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        binding.timePicker.hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        binding.timePicker.minute = Calendar.getInstance().get(Calendar.MINUTE)
    }
}