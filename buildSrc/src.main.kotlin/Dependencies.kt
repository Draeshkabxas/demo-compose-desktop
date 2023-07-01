
object Version{

    //Koin
    const val koin_core = "3.4.2"
    const val koin_compose = "3.4.5"


    //Realm Mongodb Database
    const val realm_core = "1.8.0"
    const val realm_coroutines_core="1.6.4"
}

object Koin{
    const val core = "io.insert-koin:koin-core:${Version.koin_core}"
    const val compose ="io.insert-koin:koin-androidx-compose:${Version.koin_compose}"
}

object RealmDB{
    const val base="io.realm.kotlin:library-base:${Version.realm_core}"
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.realm_coroutines_core}"
}
