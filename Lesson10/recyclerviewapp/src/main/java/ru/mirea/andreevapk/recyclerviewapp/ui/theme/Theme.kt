package ru.mirea.andreevapk.recyclerviewapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import ru.mirea.andreevapk.recyclerviewapp.Event
import ru.mirea.andreevapk.recyclerviewapp.EventList
import ru.mirea.andreevapk.recyclerviewapp.R

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),
    onPrimary = Color.White,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    background = Color(0xFFF0F0F0),
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        content = content
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        EventList(
            events = listOf(
                Event(
                    "Sample Event 1",
                    "This is a description.",
                    R.drawable.ic_launcher_foreground
                ),
                Event(
                    "Sample Event 2",
                    "This is another description.",
                    R.drawable.ic_launcher_background
                )
            )
        )
    }
}