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
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.example.myapplication.KunciPesanan
import com.example.myapplication.MenuMakanan
import com.example.myapplication.R
import java.text.NumberFormat
import java.util.Locale



class MainActivity : ComponentActivity() {

        private val daftarMenu = listOf(
            MenuMakanan("Nasi Goreng", 15_000),
            MenuMakanan("Mie Goreng", 13_000),
            MenuMakanan("Ayam Geprek", 18_000),
            MenuMakanan("Soto Ayam", 16_000),
            MenuMakanan("Bakso", 14_000),
            MenuMakanan("Sate Ayam", 20_000),
            MenuMakanan("Ikan Bakar", 25_000),
            MenuMakanan("Capcay", 17_000),
            MenuMakanan("Rendang", 22_000),
            MenuMakanan("Gado-Gado", 19_000),   
            MenuMakanan("Iga Bakar", 28_000)
        )

        private var posisiPilihan = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        val tvMenuDipilih =
            findViewById<TextView>(R.id.tvMenuDipilih)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etJumlah = findViewById<EditText>(R.id.etJumlah)

        val btnPesan = findViewById<Button>(R.id.btnPesan)
        val btnReset = findViewById<Button>(R.id.btnReset)

        val rupiah = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
            .apply {
                maximumFractionDigits = 0
            }

        // Ubah data menu menjadi teks untuk setiap baris.
        val teksMenu = daftarMenu.map { menu ->
            "${menu.nama} - ${rupiah.format(menu.harga)}"
        }

        // Hubungkan data dengan layout pilihan tunggal bawaan.
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_list_item_single_choice,
            teksMenu
        )

        lvMenu.adapter = adapter

        // Pulihkan pilihan jika Activity dibuat ulang.
        posisiPilihan = savedInstanceState
            ?.getInt("posisi_pilihan", -1) ?: -1

        if (posisiPilihan in daftarMenu.indices) {
            lvMenu.setItemChecked(posisiPilihan, true)

            val menu = daftarMenu[posisiPilihan]
            tvMenuDipilih.text =
                "Dipilih: ${menu.nama} " +
                        "(${rupiah.format(menu.harga)})"
        }

        // Tangani klik pada menu.
        lvMenu.setOnItemClickListener { _, _, position, _ ->
            posisiPilihan = position

            val menu = daftarMenu[position]

            tvMenuDipilih.text =
                "Dipilih: ${menu.nama} " +
                        "(${rupiah.format(menu.harga)})"
        }

        btnPesan.setOnClickListener {
            etNama.error = null
            etJumlah.error = null

            // Periksa apakah pengguna sudah memilih menu.
            if (posisiPilihan !in daftarMenu.indices) {
                Toast.makeText(
                    this,
                    "Pilih menu terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val nama = etNama.text.toString().trim()
            val jumlah = etJumlah.text.toString()
                .trim()
                .toIntOrNull()

            if (nama.isBlank()) {
                etNama.error = "Masukkan nama pelanggan"
                etNama.requestFocus()
                return@setOnClickListener
            }

            if (jumlah == null || jumlah !in 1..100) {
                etJumlah.error = "Jumlah harus 1–100 porsi"
                etJumlah.requestFocus()
                return@setOnClickListener
            }

            val menu = daftarMenu[posisiPilihan]

            val tujuan = Intent(
                this,
                RingkasanActivity::class.java
            )

            tujuan.putExtra(KunciPesanan.NAMA, nama)
            tujuan.putExtra(KunciPesanan.JUMLAH, jumlah)
            tujuan.putExtra(KunciPesanan.MENU, menu.nama)
            tujuan.putExtra(KunciPesanan.HARGA, menu.harga)

            startActivity(tujuan)
        }

        btnReset.setOnClickListener {
            etNama.text.clear()
            etJumlah.text.clear()

            etNama.error = null
            etJumlah.error = null

            posisiPilihan = -1
            lvMenu.clearChoices()

            // Perbarui tampilan tanda pilihan.
            adapter.notifyDataSetChanged()

            tvMenuDipilih.text = "Menu belum dipilih"
            etNama.requestFocus()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("posisi_pilihan", posisiPilihan)
        super.onSaveInstanceState(outState)
    }


    override fun onStart() {
        super.onStart()
        Log.d("PesanMakan", "HALAMAN FORM: onStart — form mulai terlihat")
    }

    override fun onResume() {
        super.onResume()
        Log.d("PesanMakan", "HALAMAN FORM: onResume — form siap digunakan")
    }

    override fun onPause() {
        super.onPause()
        Log.d("PesanMakan", "HALAMAN FORM: onPause — form kehilangan posisi aktif")
    }

    override fun onStop() {
        super.onStop()
        Log.d("PesanMakan", "HALAMAN FORM: onStop — form tidak terlihat")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("PesanMakan", "HALAMAN FORM: onRestart — form akan tampil kembali")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("PesanMakan", "HALAMAN FORM: onDestroy — instance form diakhiri")
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