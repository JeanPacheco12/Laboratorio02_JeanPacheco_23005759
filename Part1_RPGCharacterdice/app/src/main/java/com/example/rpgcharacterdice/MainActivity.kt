package com.example.rpgcharacterdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
// Imports para los layouts (Columnas).
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
// Imports para las componentes visuales (Botones).
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
// Imports para la logica y los estados (Remember, State).
import androidx.compose.runtime.*
import androidx.compose.runtime.Composable
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
            // Este es un pequeño contenedor para manejar el tema y el scaffold correctamente.
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

@Composable
fun CharacterGenerator() {
    // 1. STATE (El Estado): Aqui guardo los datos.
    // Uso var porque el valor va a cambiar al presionar el boton.
    // La funcion remember le dice a la app: "No olvides este número aunque la pantalla parpadee".
    var vitality by remember { mutableIntStateOf(0) }
    var dexterity by remember { mutableIntStateOf(0) }
    var wisdom by remember { mutableIntStateOf(0) }

    // 2. LAYOUT (El Diseño): Una Columna que apila cosas una sobre otra.
    Column(modifier = Modifier.padding(16.dp)) {

        // Nombre generico de mi app, luego le pondre un mejor nombre.
        Text(text = "Character Creator (D&D)", fontSize = 24.sp)

        // Espacio de separacion
        Text(text = "-----------------")

        // 3. UI DISPLAY: Aqui se muestran las var que almacene arriba.
        Text(text = "Vitality: $vitality", fontSize = 18.sp)
        Text(text = "Dexterity: $dexterity", fontSize = 18.sp)
        Text(text = "Wisdom: $wisdom", fontSize = 18.sp)

        // Espacio vacío
        Text(text = "\n")

        // 1. CALCULO AUTOMATICO
        // Como 'vitality', 'dexterity' y 'wisdom'. son estados, Compose
        // va a recalcular este total automaticamente cada vez que cambien.
        val total = vitality + dexterity + wisdom

        Text(text = "-----------------")

        // 2. MOSTRAR EL TOTAL
        // Use la funcion FontWeight.Bold que me dio Gemini para que resalte el texto.
        Text(text = "Total Score: $total", fontSize = 20.sp, fontWeight = FontWeight.Bold)

        // 3. LOGICA DE VALIDACION (se menciona en el slide 45 y la pude agregar).
        if (total > 0) { // Solo se muestra el mensaje si ya tiraste los dados
            if (total < 30) {
                Text(
                    text = "⚠️ Re-roll recommended!",
                    color = Color.Red,
                    fontSize = 16.sp
                )
            } else if (total >= 40) { // Lo baje a 40 para esta prueba, ya que asi podia comprobar que salia el GODLIKE
                Text(
                    text = "✨ GODLIKE! ✨",
                    color = Color(0xFFFFD700), // Color Dorado Hexadecimal
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            } else {
                Text(text = "Average Hero", color = Color.Gray)
            }
        }

        Text(text = "\n")

        // 4. INTERACTION (Interaccion): Este es el boton que al presionarlo "tira el dado".
        Button(onClick = {
            // Aqui aplique la "Dice Methodology" (logica del dado).
            vitality = (3..18).random()     // Rango típico de D&D, entre 3 y 18.
            dexterity = (3..18).random()
            wisdom = (3..18).random()
        }) {
            Text(text = "Generate Stats")
        }
    }
}