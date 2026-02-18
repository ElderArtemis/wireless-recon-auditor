package com.example.osintlab

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(32, 32, 32, 32)
        }

        val btnScan = Button(this).apply { text = "SCAN SPECTRUM" }
        val logView = TextView(this).apply {
            text = "Wireless Auditor v1.0\nReady for signal analysis..."
            textSize = 14f
        }
        val scroll = ScrollView(this).apply { addView(logView) }

        layout.addView(btnScan)
        layout.addView(scroll)
        setContentView(layout)

        val scanner = ScannerManager(this)

        btnScan.setOnClickListener {
            logView.text = "Initializing scan..."
            scanner.startWifiScan { resultado -> logView.text = resultado }
        }

        logView.setOnClickListener {
            try {
                val texto = logView.text.toString()
                val regexMac = "([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})".toRegex()
                val mac = regexMac.find(texto)?.value ?: return@setOnClickListener

                val intent = Intent(this, AuditActivity::class.java).apply {
                    putExtra("BSSID", mac.uppercase())
                    putExtra("WPS", texto.contains("WPS", ignoreCase = true))
                }
                startActivity(intent)
            } catch (e: Exception) { }
        }
    }
}