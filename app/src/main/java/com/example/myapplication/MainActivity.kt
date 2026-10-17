package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import android.content.Intent
import android.util.Log

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        Log.d("PesanMakan", "FORM: onCreate — menyiapkan form")

        val etNama = findViewById<EditText>(R.id.etNama)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)

        val btnPesan = findViewById<Button>(R.id.btnPesan)
        val btnReset = findViewById<Button>(R.id.btnReset)

        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        btnPesan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val jumlahPesanan = etJumlah.text.toString().trim()

            etNama.error = null
            etJumlah.error = null

            if (nama.isBlank()){
                etNama.error = "Masukkan nama"
                etNama.requestFocus()
                return@setOnClickListener
            }

            val jumlah = jumlahPesanan.toIntOrNull()

            if (jumlah == null){
                etJumlah.error = "Masukkan jumlah pesanan"
                etJumlah.requestFocus()
                return@setOnClickListener
            }
            if (jumlah !in 1..100){
                etJumlah.error = "jumlah pesanan dibatasi 100 porsi"
                etJumlah.requestFocus()
                return@setOnClickListener
            }

//            tentukan activity tujuan
            val tujuan = Intent(this, RingkasanActivity::class.java)

//            kirim data pesanan
            tujuan.putExtra(KunciPesanan.NAMA, nama)
            tujuan.putExtra(KunciPesanan.JUMLAH, jumlah)

//            buka layar ringkasan
            startActivity(tujuan)


            val harga = 15_000
            val total = jumlah*harga

            val formatRupiah = java.text.NumberFormat
                .getCurrencyInstance(java.util.Locale("id", "ID"))
                .apply {
                    maximumFractionDigits
                }

        }

        btnReset.setOnClickListener {
            etNama.text.clear()
            etJumlah.text.clear()

            etNama.error = null
            etJumlah.error = null

            tvHasil.text = "Hasil pesanan akan tampil disini"
            etNama.requestFocus()
        }
    }
    override fun onStart() {
        super.onStart()
        Log.d("PesanMakan", "FORM: onStart — form mulai terlihat")
    }

    override fun onResume() {
        super.onResume()
        Log.d("PesanMakan", "FORM: onResume — form siap digunakan")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PesanMakan", "FORM: onPause — form kehilangan posisi aktif")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PesanMakan", "FORM: onStop — form tidak terlihat")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("PesanMakan", "FORM: onRestart — form akan tampil kembali")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PesanMakan", "FORM: onDestroy — instance form diakhiri")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}