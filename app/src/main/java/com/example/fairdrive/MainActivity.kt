package com.example.fairdrive


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fairdrive.databinding.ActivityMainBinding
class MainActivity : AppCompatActivity()
{
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra("USER_NAME").toString()
        binding.txtName.text = name

        binding.lBook.setOnClickListener {
            var i = Intent(this@MainActivity, BookARideActivity::class.java)
            startActivity(i)
        }
        binding.lBhistory.setOnClickListener{
            var i = Intent(this@MainActivity, RideHistoryActivity::class.java)
            startActivity(i)
        }
    }
}