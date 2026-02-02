package com.example.trafficlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Implementacion IMPORTANTE: Esta librería permite hacer las pausas de tiempo.
import kotlinx.coroutines.delay
import com.example.trafficlight.ui.theme.TrafficLightTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TrafficLightTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TrafficLightScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// 1. Aqui defino los 3 estados posibles.
enum class TrafficState {
    RED,
    YELLOW,
    GREEN
}

@Composable
fun TrafficLightScreen(modifier: Modifier = Modifier) {
    // 2. STATE: Guardo en que luz estoy actualmente (Empieza en ROJO).
    var currentState by remember { mutableStateOf(TrafficState.RED) }

    // Texto aux para ver que ocurre.
    var statusText by remember { mutableStateOf("Stop!") }

    // 3. CEREBRO DEL SEMAFORO (LaunchedEffect).
    // El "Unit" hace que esto arranque una sola vez cuando se abre la app.
    LaunchedEffect(Unit) {
        while (true) { // Ciclo infinito.
            // -- ROJO --
            currentState = TrafficState.RED
            statusText = "Stop!"
            delay(4000) // Espera 4 segundos (4000 milisegundos).

            // -- VERDE --
            currentState = TrafficState.GREEN
            statusText = "Go!"
            delay(3000) // Espera 3 segundos (3000 milisegundos).

            // -- AMARILLO --
            currentState = TrafficState.YELLOW
            statusText = "Caution!"
            delay(1500) // Espera 1.5 segundos (1,500 milisegundos).

            // Y vuelve a empezar el while.
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Traffic Light Simulator 🚦",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        // Texto que cambia segun el estado.
        Text(
            text = statusText,
            fontSize = 20.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp, top = 8.dp)
        )

        // Cuerpo del semaforo.
        Column(
            modifier = Modifier
                .width(120.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // LUZ ROJA: Se enciende solo si currentState es RED.
            TrafficLightCircle(
                color = Color.Red,
                isOn = currentState == TrafficState.RED
            )

            // LUZ AMARILLA: Se enciende solo si currentState es YELLOW.
            TrafficLightCircle(
                color = Color.Yellow,
                isOn = currentState == TrafficState.YELLOW
            )

            // LUZ VERDE: Se enciende solo si currentState es GREEN.
            TrafficLightCircle(
                color = Color.Green,
                isOn = currentState == TrafficState.GREEN
            )
        }
    }
}

// Componente visual de la luz.
@Composable
fun TrafficLightCircle(color: Color, isOn: Boolean) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape)
            // LÓGICA DE COLOR: Si está encendido (isOn), usa el color normal.
            // Si está apagado, usa el mismo color pero transparente (alpha 0.2).
            .background(if (isOn) color else color.copy(alpha = 0.2f))
            .border(2.dp, Color.Black, CircleShape)
    )
}

@Preview(showBackground = true)
@Composable
fun TrafficLightPreview() {
    TrafficLightTheme {
        TrafficLightScreen()
    }
}