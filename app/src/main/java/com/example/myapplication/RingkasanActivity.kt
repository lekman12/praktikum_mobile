package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import java.text.NumberFormat
import java.util.Locale
import androidx.activity.ComponentActivity
import android.util.Log


class RingkasanActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_ringkasan)

        Log.d("PesanMakan", "RINGKASAN: onCreate — menyiapkan form")


        val tvNama = findViewById<TextView>(R.id.tvNama)
        val tvJumlah = findViewById<TextView>(R.id.tvJumlah)
        val tvTotal = findViewById<TextView>(R.id.tvTotal)
        val btnEdit = findViewById<Button>(R.id.btnEdit)

        val nama = intent.getStringExtra(KunciPesanan.NAMA) ?: ""
        val jumlah = intent.getIntExtra(KunciPesanan.JUMLAH, 0)

        if (nama.isBlank() || jumlah !in 1..100) {
            finish()
            return
        }

        val total = jumlah * 15_000

        val rupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID")
        ).apply {
                maximumFractionDigits = 0
            }
        tvNama.text = nama
        tvJumlah.text = "$jumlah Porsi"
        tvTotal.text = "Total: ${rupiah.format(total)}"

        btnEdit.setOnClickListener {
            finish()
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("PesanMakan", "RINGKASAN: onStart — form mulai terlihat")
    }

    override fun onResume() {
        super.onResume()
        Log.d("PesanMakan", "RINGKASAN: onResume — form siap digunakan")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PesanMakan", "RINGKASAN: onPause — form kehilangan posisi aktif")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PesanMakan", "RINGKASAN: onStop — form tidak terlihat")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("PesanMakan", "RINGKASAN: onRestart — form akan tampil kembali")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PesanMakan", "RINGKASAN: onDestroy — instance form diakhiri")
    }
}