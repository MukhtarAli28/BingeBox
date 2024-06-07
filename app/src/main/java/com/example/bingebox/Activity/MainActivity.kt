package com.example.bingebox.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.bingebox.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mBinding.clTVShows.setOnClickListener {
            showComingSoonDialog()
        }
        mBinding.clRomenticComedy.setOnClickListener {
            startActivity(Intent(this, MovieListingActivity::class.java))
        }
    }
    private fun showComingSoonDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Coming Soon!")
        builder.setMessage("This feature is currently under development.")
        builder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = builder.create()
        dialog.show()
    }
}

