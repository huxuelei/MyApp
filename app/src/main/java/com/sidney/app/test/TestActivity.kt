package com.sidney.app.test

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.sidney.app.R

class TestActivity : Activity() {

    lateinit var btnTest: Button

    fun launch(context: Context) {
        val intent = Intent(context, TestActivity::class.java)
        context.startActivity(intent)
    }

    class TestActivity(a: String) {

    }

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        btnTest = findViewById(R.id.btn_test)

        btnTest.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "kotlin", Toast.LENGTH_LONG).show()
        })
    }

    fun test() {
        var a: Int
        var items = listOf("111", "22222")
        for (item in items) {
            print(item)
        }

        for (index in items.indices) {
            try {
                print(items[index])
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun describe(obj: Any): String =
            when (obj) {
                1 -> "the"
                "test" -> "ool"
                else -> "thoho"
            }
}
