# Laboratorio 2 - Parte 2: Traffic Light Simulator

**Estudiante:** Jean Pacheco
**Carnet:** 23005759

---

##  Descripción
En esta parte 2, la aplicación simula el funcionamiento de un semáforo real. A diferencia de la parte 1, esta aplicación es totalmente **automática al iniciar la app**. Utiliza corrutinas para gestionar tiempos de espera y cambiar los estados de las luces en un ciclo infinito (While True).

##  Funcionalidades Principales
* **Ciclo Automático:** El semáforo cambia solo siguiendo la secuencia estándar:
    1.   **STOP (Rojo):** 4 segundos.
    2.   **GO (Verde):** 3 segundos.
    3.   **CAUTION (Amarillo):** 1.5 segundos.
* **Indicador de Texto:** Un texto en pantalla acompaña el cambio de luces con las palabras: ("Stop!", "Go!", "Caution!").
* **Efecto Visual:** Las luces inactivas reducen su opacidad para simular que estan apagadas, mientras que la luz activa brilla con su color completo.

##  Conceptos Técnicos Aplicados
* **Enum Classes:** Definición de estados con `enum class TrafficState { RED, YELLOW, GREEN }` para un control de flujo seguro y óptimo.
* **LaunchedEffect:** Uso de efectos secundarios de Compose para ejecutar código en segundo plano al iniciar la app.
* **Corrutinas (Delay):** Uso de `kotlinx.coroutines.delay` para manejar los tiempos de espera sin congelar la interfaz del usuario.
* **Reactive UI:** La interfaz se redibuja automáticamente cada vez que cambia el estado (`currentState`).

##  Video del Proyecto
* https://youtu.be/14VZ7c2p9nM

---
*Desarrollado en Android Studio con Kotlin y Jetpack Compose.*