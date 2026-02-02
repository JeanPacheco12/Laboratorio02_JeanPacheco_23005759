# Laboratorio 2 - Parte 1: RPG Character Creator 

**Estudiante:** Jean Pacheco 
**Carnet:** 23005759

---

## Descripción
Esta aplicación es un generador de stats para personajes de rol (tipo D&D). Que permite al usuario generar valores random para atributos clave (Vitality, Dexterity, Wisdom) de forma independiente y calcula un puntaje total acompañado de un apartado visual donde visualiza si el personaje es malo, intermedio o buen personaje.

## Funcionalidades Principales
* **Generación Independiente:** Cada atributo tiene su propio botón "🎲" que genera un número entre 3 y 18.
* **Cálculo Automático:** La suma total (Total Score) se actualiza instantáneamente gracias al manejo de estados en Compose.
* **Validación Visual:**
    *  **Cursed Roll (< 30):** Muestra advertencia en rojo de que no es un buen personaje.
    *  **Average Hero (30-49):** Muestra texto estándar en gris de que es un personaje estándar.
    *  **Legendary Hero (>= 50):** Muestra mensaje dorado y destacado de que es un buen personaje.
* **UI:** Diseño "Dark Mode" con gradientes, tarjetas elevadas y visualizadores de dados para darle ese aspecto y ambientacion de juego de D&D.

## Conceptos Técnicos Aplicados
* **Jetpack Compose:** Usé `Column`, `Row`, `Card`, `Box`, `Surface`.
* **State Hoisting:** Usé `remember { mutableIntStateOf(0) }` para la gestión reactiva de los datos.
* **Refactoring:** Para la creación del Composable reutilizable `StatRow` para mantener el código limpio como se nos indico en las instrucciones.
* **Kotlin Logic:** Rangos de `(3..18).random()` y estructuras de control ya vistas en anteriores cursos de CC `if/else`.

## Video del Proyecto
* https://youtu.be/H7L01hRW3uM 

---
*Desarrollado en Android Studio con Kotlin y Jetpack Compose.*