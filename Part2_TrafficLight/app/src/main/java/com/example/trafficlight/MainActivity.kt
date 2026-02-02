package com.example.trafficlight

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
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

@Composable
fun TrafficLightScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Traffic Light Simulator",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // En este column hice el "Cuerpo" del semaforo.
        Column(
            modifier = Modifier
                .width(120.dp)
                .background(Color.DarkGray, shape = RoundedCornerShape(16.dp))
                .border(2.dp, Color.Black, shape = RoundedCornerShape(16.dp))
                .padding(vertical = 24.dp), // Padding interno vertical.
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp) // Espacio entre luces.
        ) {
            // LUZ ROJA (De momento apagada, se simula con color tenue).
            TrafficLightCircle(color = Color.Red.copy(alpha = 0.3f))

            // LUZ AMARILLA
            TrafficLightCircle(color = Color.Yellow.copy(alpha = 0.3f))

            // LUZ VERDE
            TrafficLightCircle(color = Color.Green.copy(alpha = 0.3f))
        }
    }
}

// Componente reutilizable para dibujar un circulo.
@Composable
fun TrafficLightCircle(color: Color) {
    Box(
        modifier = Modifier
            .size(80.dp) // Tamanyoo del círculo.
            .clip(CircleShape)
            .background(color)
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