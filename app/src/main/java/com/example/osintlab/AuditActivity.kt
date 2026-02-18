package com.example.osintlab

import android.os.Bundle
import android.widget.TextView
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity

/**
 * Actividad destinada al análisis de vectores de ataque y vulnerabilidades específicas.
 */
class AuditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 40, 40, 40)
        }

        val scrollView = ScrollView(this)
        val tv = TextView(this)

        val bssid = intent.getStringExtra("BSSID") ?: "00:00:00:00:00:00"
        val hasWps = intent.getBooleanExtra("WPS", false)

        // Cálculo de PIN basado en algoritmo de terminación OUI
        val rawMac = bssid.replace(":", "").uppercase()
        val algoPin = if (rawMac.length >= 6) rawMac.takeLast(6) else "N/A"

        val report = StringBuilder()
        report.append("ANÁLISIS TÉCNICO: $bssid\n")
        report.append("==============================\n\n")

        report.append("VECTORES DE ATAQUE ESTADÍSTICOS:\n")
        report.append("1. PIN Nulo: 00000000\n")
        report.append("2. PIN Estático: 12345670\n")
        report.append("3. PIN Algoritmo (MAC): $algoPin\n\n")

        report.append("NOTAS DE SEGURIDAD:\n")
        if (hasWps) {
            report.append("- El objetivo muestra WPS habilitado. Vulnerabilidad alta.\n")
        } else {
            report.append("- WPS no detectado en el beacon. Se recomienda captura de handshake.\n")
        }

        report.append("\nPROCEDIMIENTO:\n")
        report.append("Verificar la compatibilidad del router con los algoritmos estándar mediante los ajustes de conexión WPS del sistema.")

        tv.text = report.toString()
        tv.textSize = 15f

        scrollView.addView(tv)
        layout.addView(scrollView)
        setContentView(layout)
    }
}