package com.example.rpgcharacterdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// Imports para los layouts (Columnas, Filas, Alineación).
import androidx.compose.foundation.layout.Arrangement // <--- AGREGADO: Para separar elementos (SpaceBetween).
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row // <--- AGREGADO: Para organizar elementos horizontalmente.
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth // <--- AGREGADO: Para que ocupe todo el ancho.
import androidx.compose.foundation.layout.padding
// Imports para las componentes visuales (Botones, Tarjetas).
import androidx.compose.material3.Button
import androidx.compose.material3.Card // <--- AGREGADO: Componente visual para agrupar datos.
import androidx.compose.material3.CardDefaults // <--- AGREGADO: Para efectos de sombra en las tarjetas.
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
// Imports para la logica y los estados (Remember, State).
import androidx.compose.runtime.*
// Imports para la alineación y modificadores
import androidx.compose.ui.Alignment // <--- AGREGADO: Para centrar elementos verticalmente.
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
// Imports para las unidades de medida (dp, sp).
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgcharacterdice.ui.theme.RPGCharacterDiceTheme
// Imports para la visualizacion de colores en los textos (rojo, dorado).
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Este es un pequenyo contenedor para manejar el tema y el scaffold correctamente.
            RPGCharacterDiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Paso el padding al contenido para que no lo tape la barra de estado.
                    CharacterGeneratorWrapper(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

// Un wrapper temporal para aplicar el padding del Scaffold
@Composable
fun CharacterGeneratorWrapper(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        CharacterGenerator()
    }
}

// --- COMPONENTE NUEVO AGREGADO (REFACTORING) ---
// LO AGREGADO: Esta funcion crea una fila individual para cada stat.
// Recibe el nombre (label), el valor (value) y la accion del boton (onRoll).
@Composable
fun StatRow(label: String, value: Int, onRoll: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp), // Espacio vertical entre tarjetas
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Sombra suave.
    ) {
        // Uso row para poner texto a la izquierda y boton a la derecha.
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Empuja el contenido a los extremos.
            verticalAlignment = Alignment.CenterVertically // Centra el boton con el texto.
        ) {
            // Columna interna para el nombre y el valor numerico.
            Column {
                Text(text = label, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = "Value: $value", fontSize = 16.sp)
            }

            // Boton individual que ejecuta la funcion 'onRoll' que le paso.
            Button(onClick = onRoll) {
                Text("Roll")
            }
        }
    }
}

@Composable
fun CharacterGenerator() {
    // 1. STATE (El Estado): Aqui guardo los datos.
    var vitality by remember { mutableIntStateOf(0) }
    var dexterity by remember { mutableIntStateOf(0) }
    var wisdom by remember { mutableIntStateOf(0) }

    // 2. LAYOUT (El Disenyo): Una columna que apila cosas una sobre otra.
    Column(modifier = Modifier.padding(16.dp)) {

        // Nombre generico de mi app, pendiente de cambio.
        Text(text = "Character Creator (D&D)", fontSize = 24.sp, fontWeight = FontWeight.Bold)

        // Espacio de separacion
        Text(text = "\n")

        // <--- CAMBIO IMPORTANTE: Reemplace los textos simples por la componente mencionada en los slides (StatRow).
        // Ahora cada stat tiene su propio boton y logica de random de forma independiente.

        StatRow(label = "Vitality", value = vitality, onRoll = {
            vitality = (3..18).random()
        })

        StatRow(label = "Dexterity", value = dexterity, onRoll = {
            dexterity = (3..18).random()
        })

        StatRow(label = "Wisdom", value = wisdom, onRoll = {
            wisdom = (3..18).random()
        })

        // Espacio vacío
        Text(text = "\n")

        // 1. CALCULO AUTOMATICO
        val total = vitality + dexterity + wisdom

        Text(text = "-----------------")

        // 2. MOSTRAR EL TOTAL
        Text(text = "Total Score: $total", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        // 3. LOGICA DE VALIDACION
        if (total > 0) {
            if (total < 30) {
                Text(
                    text = "⚠️ Re-roll recommended!",
                    color = Color.Red,
                    fontSize = 18.sp, // Aumente un poco el tamanyo para que se lea mejor.
                    fontWeight = FontWeight.Bold
                )
            } else if (total >= 50) { // Regrese a 50 como la regla original, ya que ahora puedo manipular cada stat por sus botones independientes.
                Text(
                    text = "✨ GODLIKE! ✨",
                    color = Color(0xFFFFD700), // Color Dorado Hexadecimal
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(text = "Average Hero", color = Color.Gray)
            }
        }

        // <--- ELIMINACION DE BUTTON onClick: Ya no necesito el boton general porque ya cada fila de stats tiene su propio boton.
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
    RPGCharacterDiceTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterGeneratorPreview() {
    RPGCharacterDiceTheme {
        CharacterGenerator()
    }
}