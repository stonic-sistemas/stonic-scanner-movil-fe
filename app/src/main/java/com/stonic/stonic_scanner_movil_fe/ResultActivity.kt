package com.stonic.stonic_scanner_movil_fe

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.stonic.stonic_scanner_movil_fe.adapter.OptionsAdapter
import java.util.Date
import java.text.SimpleDateFormat

class ResultActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Sección de resultados
        val textViewResult = findViewById<TextView>(R.id.textViewResultCode)
        val barcodeString = intent.getStringExtra("result_string")

        if (!barcodeString.isNullOrEmpty()) {
            textViewResult.text = getString(R.string.scanner_string)+ ": " + barcodeString
        } else {
            textViewResult.text = getString(R.string.scanner_string_noresult)
        }

        val textViewResultDate = findViewById<TextView>(R.id.textViewResultDate)
        textViewResultDate.text = getString(R.string.scanner_date)+ ": " + getCurrentDateTime()

        // Sección de acciones del sistema
        val recyclerView = findViewById<RecyclerView>(R.id.recview_result_options)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val systemOptions = listOf(
            Option(R.drawable.ic_copy, getString(R.string.option_system_copy)) {
                copyToClipboard(barcodeString)
            },
            Option(R.drawable.ic_share, getString(R.string.option_system_share)) {
                shareText(barcodeString)
            }
            // impimir en alguna impresora, eliminar
        )
        recyclerView.adapter = OptionsAdapter(systemOptions)

        // Sección de Búsqueda
        // Buscar en algun buscador, en algun aplicación de pedidos, etc
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentDateTime(): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
        return sdf.format(Date())
    }

    private fun copyToClipboard(text: String?) {
        text?.let {
            val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Barcode", it)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, getString(R.string.scanner_string_copied), Toast.LENGTH_SHORT).show()
        }
    }

    private fun shareText(text: String?) {
        text?.let {
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, it)
            }
            startActivity(Intent.createChooser(intent, "Compartir con"))
        }
    }
}