📘 Descripción de la Aplicación

Esta herramienta es un Auditor de Espectro Inalámbrico diseñado para el análisis técnico de redes Wi-Fi en el entorno cercano. A diferencia de las herramientas de escaneo convencionales, este software se enfoca en la recolección de telemetría crítica y la evaluación de vectores de ataque estadísticos.

Funciones Principales:
Reconocimiento Activo: Escaneo de puntos de acceso (AP) detectando SSIDs ocultos y visibles.

Análisis de Capa Física: Identificación de canales, frecuencias (2.4GHz/5GHz) y niveles de potencia (RSSI).

Huella Digital de Hardware (Fingerprinting): Identificación del fabricante (Vendor) basada en el OUI de la dirección MAC.

Evaluación de Vulnerabilidades: Identificación de configuraciones WPS (Wi-Fi Protected Setup) activas y cálculo de PINs probables mediante algoritmos de generación por hardware.

🛠 Modo de Uso
Para realizar una auditoría completa, sigue estos pasos:

1. Inicialización y Escaneo
   Al abrir la aplicación, pulsa el botón "SCAN SPECTRUM". La app solicitará permisos de ubicación (necesarios en Android para acceder al hardware de red) y comenzará a escuchar los beacons de las redes cercanas.

2. Interpretación de Resultados
   En el reporte principal verás:

SSID: Nombre de la red.

BSSID: Dirección física única del router.

Seguridad: Indica si la red usa cifrado (WPA2/WPA3) y si tiene el vector WPS habilitado.

Canal: Útil para detectar saturación en el espectro.

3. Auditoría de Objetivo Específico
   Si identificas una red con la etiqueta [WPS], puedes profundizar en el análisis:

Selección: Toca directamente sobre la dirección MAC (BSSID) de la red objetivo en la lista.

Cálculo de Vectores: Se abrirá una ventana de auditoría que mostrará:

PIN Nulo/Estático: Configuraciones por defecto comunes en routers domésticos.

PIN Algoritmo (MAC): Un PIN generado dinámicamente basado en los últimos 6 dígitos de la MAC del dispositivo.

4. Verificación de Seguridad
   Como profesional de la seguridad, utiliza estos PINs generados para verificar si el router del cliente (o el propio) permite el acceso no autorizado a través de la función de emparejamiento WPS del sistema operativo. Si la conexión se establece con éxito, se recomienda desactivar el WPS inmediatamente en la configuración del router.

🛠 Stack Tecnológico y Arquitectura (DevOps Focus)

Este proyecto ha sido desarrollado bajo estándares de desarrollo nativo, priorizando el rendimiento y el acceso directo a las APIs de hardware de bajo nivel de Android.

Core Stack:
Language: Kotlin (1.9.x) - Utilizando programación funcional y corrutinas para el manejo de hilos en el escaneo de radio.

Architecture: Patrón de diseño orientado a servicios (Service-Oriented Logic) mediante ScannerManager para desacoplar la lógica de red de la UI.

Asynchronous Processing: Implementación de BroadcastReceiver para el manejo de eventos de sistema asíncronos sin bloquear el hilo principal (Main Thread).

Permissions Model: Gestión dinámica de permisos en tiempo de ejecución para cumplir con los estándares de seguridad de Android 10+ (API 29 a 34).

Infraestructura y Despliegue:
Build System: Gradle (Kotlin DSL) - Gestión de dependencias y automatización de la compilación.

CI/CD Ready: Estructura de proyecto preparada para integración con GitHub Actions para la generación automática de APKs (Build Artifacts).

Static Analysis: Configurado para pasar linters estándar de Kotlin para garantizar la legibilidad y mantenibilidad del código.