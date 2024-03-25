import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import data.Database
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.core.context.startKoin
import org.koin.dsl.module
import ui.screens.HomeScreen
import ui.screens.HomeViewModel
import ui.screens.TaskViewModel

@Composable
@Preview
fun App() {
    initializeKoin()
    MaterialTheme {
        Navigator(HomeScreen()) {
            SlideTransition(it)
        }
    }
}

val databaseModule = module {
    single { Database() }
    factory { HomeViewModel(get()) }
    factory { TaskViewModel(get()) }
}

fun initializeKoin() {
    startKoin {
        modules(databaseModule)
    }
}