package ru.mirea.andreevapk.scrollviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.mirea.andreevapk.scrollviewapp.ui.theme.AppTheme
import java.math.BigInteger

class ScrollViewAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ProgressionList()
                }
            }
        }

    }
}

@Composable
fun ProgressionList() {
    val items = List(100) { index ->
        val value = BigInteger.valueOf(2).pow(index)
        "Элемент ${index + 1}: $value"
    }

    LazyColumn {
        items(items.size) { index ->
            Text(
                text = items[index],
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AppTheme {
        ProgressionList()
    }
}