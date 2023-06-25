package di

import androidx.compose.ui.window.ApplicationScope
import org.koin.core.Koin
import org.koin.core.context.GlobalContext.startKoin

private lateinit var koin: Koin

fun ApplicationScope.koinConfiguration(){
    if (!::koin.isInitialized) {
        startKoin {
            modules(appModule)
        }.koin.let { koin = it }
    }
}