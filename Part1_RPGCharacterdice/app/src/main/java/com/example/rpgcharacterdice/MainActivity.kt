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
import androidx.compose.foundation.layout.height // <--- VISUAL AGREGADO: Para espacios verticales exactos.
// Imports de Disenyo y graficos
import androidx.compose.foundation.background // <--- VISUAL AGREGADO: Para el fondo de color.
import androidx.compose.foundation.shape.RoundedCornerShape // <--- VISUAL AGREGADO: Para bordes redondeados.
import androidx.compose.foundation.layout.Box // <--- VISUAL AGREGADO: Contenedor para encimar fondos.
import androidx.compose.foundation.layout.Spacer
// Imports para las componentes visuales (Botones, Tarjetas).
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults // <--- VISUAL AGREGADO: Para cambiar color de botones.
import androidx.compose.material3.Card // <--- AGREGADO: Componente visual para agrupar datos.
import androidx.compose.material3.CardDefaults // <--- AGREGADO: Para efectos de sombra en las tarjetas.
import androidx.compose.material3.HorizontalDivider // <--- VISUAL AGREGADO: Linea divisora elegante.
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface // <--- VISUAL AGREGADO: Para crear el cuadrito del dado.
import androidx.compose.material3.Text
// Imports para la logica y los estados (Remember, State).
import androidx.compose.runtime.*
// Imports para la alineación y modificadores
import androidx.compose.ui.Alignment // <--- AGREGADO: Para centrar elementos verticalmente.
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush // <--- VISUAL AGREGADO: Para el gradiente (degradado).
import androidx.compose.ui.tooling.preview.Preview
// Imports para las unidades de medida (dp, sp).
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rpgcharacterdice.ui.theme.RPGCharacterDiceTheme
// Imports para la visualizacion de colores en los textos (rojo, dorado).
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign // <--- VISUAL AGREGADO: Para centrar textos.

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            // Este es un pequenyo contenedor para manejar el tema y el scaffold correctamente.
            RPGCharacterDiceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Paso el padding al contenido para que no lo tape la barra de estado.
                    // VISUAL: Pasamos el modifier directo para que el fondo cubra todo.
                    CharacterGenerator(modifier = Modifier.padding(innerPadding))
                }
            }
        }
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
            .padding(vertical = 6.dp), // Espacio vertical entre tarjetas (Aumentado un poco para estetica).
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp), // Sombra suave.
        colors = CardDefaults.cardColors(containerColor = Color(0xFF2A2A2A)) // <--- VISUAL AGREGADO: Tarjeta oscura.
    ) {
        // Uso row para poner texto a la izquierda y boton a la derecha.
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween, // Empuja el contenido a los extremos.
            verticalAlignment = Alignment.CenterVertically // Centra el boton con el texto.
        ) {
            // Columna interna para el nombre.
            // VISUAL: Quite el valor de aqui para ponerlo en un "visualizer" mas bonito.
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White // <--- VISUAL AGREGADO: Texto blanco para contraste.
            )

            // <--- VISUAL AGREGADO: Fila interna para el valor (Dado) y el boton.
            Row(verticalAlignment = Alignment.CenterVertically) {
                // El "Visualizer" del dado (Un cuadrito con el numero).
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color(0xFF444444), // Gris oscuro fondo del número.
                    modifier = Modifier.padding(end = 12.dp)
                ) {
                    Text(
                        text = "$value",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFFFFD700), // Numero Dorado.
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                    )
                }

                // Boton individual que ejecuta la funcion 'onRoll' que le paso.
                Button(
                    onClick = onRoll,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF8B0000)) // <--- VISUAL AGREGADO: Boton color rojo sangre.
                ) {
                    Text("🎲") // Icono de dado en vez de texto "Roll".
                }
            }
        }
    }
}

@Composable
fun CharacterGenerator(modifier: Modifier = Modifier) {
    // 1. STATE (El Estado): Aqui guardo los datos.
    var vitality by remember { mutableIntStateOf(0) }
    var dexterity by remember { mutableIntStateOf(0) }
    var wisdom by remember { mutableIntStateOf(0) }

    // 1. CALCULO AUTOMATICO (Lo movi arriba para usarlo en el UI si fuera necesario antes).
    val total = vitality + dexterity + wisdom

    // <--- VISUAL AGREGADO: Box permite poner cosas una encima de otra (Fondo + Contenido).
    Box(
        modifier = modifier
            .fillMaxSize()
            // Gradiente: Negro -> Rojo Oscuro -> Negro (Estilo Dungeon).
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF101010), Color(0xFF3E0505), Color(0xFF101010))
                )
            )
    ) {
        // 2. LAYOUT (El Disenyo): Una columna que apila cosas una sobre otra.
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally, // <--- VISUAL AGREGADO: Centrar todo.
            verticalArrangement = Arrangement.Center // <--- VISUAL AGREGADO: Centrar verticalmente.
        ) {

            // Nombre oficial de mi app.
            Text(
                text = "⚔️ Dungeon Master 🐉\nDice Roller",
                fontSize = 28.sp,
                fontWeight = FontWeight.Black,
                textAlign = TextAlign.Center, // <--- VISUAL AGREGADO.
                lineHeight = 36.sp,
                color = Color(0xFFE0E0E0) // Blanco humo.
            )

            // Espacio de separacion (La funcion spacer es mejor que \n para control exacto).
            Spacer(modifier = Modifier.height(24.dp))

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

            // Espacio vacio
            Spacer(modifier = Modifier.height(24.dp))

            // SEPARADOR VISUAL (Reemplaza al texto de guiones).
            HorizontalDivider(thickness = 2.dp, color = Color.Gray)

            Spacer(modifier = Modifier.height(16.dp))

            // 2. MOSTRAR EL TOTAL
            // <--- VISUAL AGREGADO: Tarjeta especial para el Total Score.
            Card(
                colors = CardDefaults.cardColors(containerColor = Color.Black),
                border = androidx.compose.foundation.BorderStroke(2.dp, Color(0xFFFFD700)) // Borde Dorado.
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "TOTAL SCORE", color = Color.Gray, fontSize = 14.sp)
                    Text(text = "$total", fontSize = 48.sp, fontWeight = FontWeight.Black, color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. LOGICA DE VALIDACION
            if (total > 0) {
                if (total < 30) {
                    Text(
                        text = "☠️ CURSED ROLL! ☠️\nRe-roll recommended", // Texto actualizado a tematica de D&D.
                        color = Color(0xFFFF4444), // Rojo brillante.
                        fontSize = 18.sp, // Aumente un poco el tamanyo para que se lea mejor.
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                } else if (total >= 50) { // Regrese a 50 como la regla original.
                    Text(
                        text = "✨ LEGENDARY HERO! ✨\nGodlike Stats",
                        color = Color(0xFFFFD700), // Color Dorado Hexadecimal.
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                } else {
                    Text(text = "Standard Adventurer", color = Color.Gray)
                }
            }

            // <--- ELIMINACION DE BUTTON onClick: Ya no necesito el boton general porque ya cada fila de stats tiene su propio boton.
        }
    }
}

// Elimine las funciones Greeting y CharacterGeneratorWrapper porque ya no las usamos y no son relevantes para esta parte.

@Preview(showBackground = true)
@Composable
fun CharacterGeneratorPreview() {
    RPGCharacterDiceTheme {
        CharacterGenerator()
    }
}