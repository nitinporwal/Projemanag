package com.example.projemanage

import android.os.Bundle
import com.example.projemanage.activities.BaseActivity

class TaskListActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
    }
}