package com.example.osintlab

import android.content.Context
import android.net.wifi.WifiManager
import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.net.wifi.ScanResult

/**
 * Gestor encargado de las operaciones de escaneo del espectro inalámbrico.
 */
class ScannerManager(private val context: Context) {

    private val wifiManager = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager

    fun startWifiScan(onResultsFound: (String) -> Unit) {
        val wifiScanReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val results = wifiManager.scanResults
                val report = StringBuilder("--- INFORME DE AUDITORÍA INALÁMBRICA ---\n")
                report.append("Nodos detectados: ${results.size}\n\n")

                for (result in results) {
                    val ssid = if (result.SSID.isEmpty()) "[OCULTA]" else result.SSID
                    val channel = mhzToChannel(result.frequency)
                    val vendor = getVendor(result.BSSID)

                    report.append("📡 SSID: $ssid\n")
                    report.append("🆔 BSSID: ${result.BSSID.uppercase()}\n")
                    report.append("🏗️ Fabricante: $vendor\n")
                    report.append("📶 Señal: ${result.level} dBm\n")
                    report.append("🔒 Seguridad: ${result.capabilities}\n")
                    report.append("🌐 Canal: $channel\n")
                    report.append("--------------------------\n")
                }
                onResultsFound(report.toString())
                context.unregisterReceiver(this)
            }
        }

        context.registerReceiver(wifiScanReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()
    }

    private fun getVendor(bssid: String): String {
        val mac = bssid.uppercase().replace(":", "").take(6)
        return when (mac) {
            "140C76", "50C7BF" -> "TP-Link"
            "ACB313", "DC537C" -> "ZTE / Huawei"
            "E4C722", "9822EF" -> "Xiaomi"
            "00E04C" -> "Realtek"
            else -> "Genérico / Desconocido"
        }
    }

    private fun mhzToChannel(mhz: Int): Int {
        return when {
            mhz in 2412..2472 -> (mhz - 2412) / 5 + 1
            mhz in 5170..5825 -> (mhz - 5170) / 5 + 34
            else -> 0
        }
    }
}