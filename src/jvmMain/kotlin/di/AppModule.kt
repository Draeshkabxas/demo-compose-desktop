package di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import authorization.data.model.User
import org.koin.dsl.module
import authorization.data.repository.AppCloseImpl
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import authorization.data.repository.MangodbAuthenticationImpl
import authorization.domain.usecase.IsUserUseCase
import authorization.presentation.login.LoginViewModel

val appModule = module {
     single<Realm> { Realm.open(
          RealmConfiguration.create(schema = setOf(User::class))
     )
     }
     single <AppCloseRepository>{ AppCloseImpl() }
     single <AuthenticationRepository>{ MangodbAuthenticationImpl(get(),get()) }
     single <LoginViewModel>{ LoginViewModel(IsUserUseCase(get())) }
}