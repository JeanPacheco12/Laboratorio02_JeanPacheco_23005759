package com.example.rpgcharacterdice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.rpgcharacterdice.ui.theme.RPGCharacterDiceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

        }
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
    // 1. STATE (El Estado): Aquí guardamos los datos.
    // Usamos 'var' porque el valor va a cambiar cuando presiones el botón.
    // 'remember' le dice a la app: "No olvides este número aunque la pantalla parpadee".
    var strength by remember { mutableIntStateOf(0) }
    var dexterity by remember { mutableIntStateOf(0) }
    var intelligence by remember { mutableIntStateOf(0) }

    // 2. LAYOUT (El Diseño): Una Columna (Column) apila cosas una sobre otra.
    Column(modifier = Modifier.padding(16.dp)) {

        Text(text = "Character Creator (D&D)", fontSize = 24.sp)

        // Espacio vacío
        Text(text = "-----------------")

        // 3. UI DISPLAY: Mostramos las variables de arriba
        Text(text = "Strength: $strength", fontSize = 18.sp)
        Text(text = "Dexterity: $dexterity", fontSize = 18.sp)
        Text(text = "Intelligence: $intelligence", fontSize = 18.sp)

        // Espacio vacío
        Text(text = "\n")

        // 4. INTERACTION (Interacción): El botón que "tira los dados"
        Button(onClick = {
            // AQUÍ aplicamos la "Dice Methodology"
            strength = (3..18).random()     // Rango típico de D&D
            dexterity = (3..18).random()
            intelligence = (3..18).random()
        }) {
            Text(text = "Generate Stats")
        }
    }
}