package di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import authorization.data.model.User
import org.koin.dsl.module
import authorization.data.repository.AppCloseImpl
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import authorization.data.repository.MangodbAuthenticationImpl
import authorization.domain.usecase.LoginUseCase
import authorization.domain.usecase.SignupUseCase
import authorization.presentation.login.LoginViewModel
import authorization.presentation.register.RegisterViewModel

val appModule = module {
     single<Realm> { Realm.open(
          RealmConfiguration.create(schema = setOf(User::class))
     )
     }
     single <AppCloseRepository>{ AppCloseImpl() }
     single <AuthenticationRepository>{ MangodbAuthenticationImpl(get(),get()) }
     single <LoginViewModel>{ LoginViewModel(LoginUseCase(get())) }
     single <RegisterViewModel>{ RegisterViewModel(SignupUseCase(get())) }
}