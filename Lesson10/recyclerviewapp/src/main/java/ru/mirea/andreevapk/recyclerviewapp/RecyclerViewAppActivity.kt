package ru.mirea.andreevapk.recyclerviewapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import ru.mirea.andreevapk.recyclerviewapp.ui.EventItem
import ru.mirea.andreevapk.recyclerviewapp.ui.theme.MyApplicationTheme

class RecyclerViewAppActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                val events = listOf(
                    Event(
                        "Падение Римской империи",
                        "Конец Римской империи в 476 году, когда последний император Ромул Августул был свергнут варваром Одоакром.",
                        R.drawable.rome
                    ),
                    Event(
                        "Крестовые походы",
                        "Серия религиозных войн в Средние века (1096–1271), организованных христианами для освобождения Святой Земли от мусульман.",
                        R.drawable.crusader
                    ),
                    Event(
                        "Открытие Америки Христофором Колумбом",
                        "12 октября 1492 года Колумб, снарядив экспедицию, открыл Новый Свет для европейцев.",
                        R.drawable.columb
                    ),
                    Event(
                        "Великая французская революция",
                        "Революционные события 1789–1799 годов, в ходе которых была свергнута монархия и провозглашена Республика.",
                        R.drawable.france
                    ),
                    Event(
                        "Первая мировая война",
                        "Военный конфликт 1914–1918 годов, в котором участвовали многие великие державы.",
                        R.drawable.firstww
                    ),
                    Event(
                        "Октябрьская революция",
                        "Революция 1917 года, которая привела к власти большевиков во главе с Владимиром Лениным и основанию Советской России.",
                        R.drawable.october
                    ),
                    Event(
                        "Великая депрессия",
                        "Глобальный экономический кризис, начавшийся в 1929 году и затронувший почти все страны мира.",
                        R.drawable.depression
                    ),
                    Event(
                        "Вторая мировая война",
                        "Мировой конфликт 1939–1945 годов, в котором принимали участие большинство стран мира, включая все великие державы.",
                        R.drawable.secondww
                    )
                )

                EventList(events)
            }
        }
    }
}

@Composable
fun EventList(events: List<Event>) {
    LazyColumn {
        items(events) { event ->
            EventItem(event)
        }
    }
}