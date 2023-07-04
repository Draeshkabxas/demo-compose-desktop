package di

import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import authorization.data.model.UserRealm
import org.koin.dsl.module
import authorization.data.repository.AppCloseImpl
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import authorization.data.repository.MangodbAuthenticationImpl
import authorization.domain.usecase.CloseApplication
import authorization.domain.usecase.LoginUseCase
import authorization.domain.usecase.SignupUseCase
import authorization.domain.usecase.ValidateUsername
import authorization.presentation.login.LoginViewModel
import authorization.presentation.register.RegisterViewModel
import features.sons_of_officers.data.model.Justification
import features.sons_of_officers.data.model.Procedure
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.data.repository.PersonXlsxImpl
import features.sons_of_officers.data.repository.RealmPersonImpl
import features.sons_of_officers.domain.repository.PersonRepository
import features.sons_of_officers.domain.repository.PersonXlsxRepository
import features.sons_of_officers.domain.usecases.AddPerson
import features.sons_of_officers.domain.usecases.GetAllPeople
import features.sons_of_officers.domain.usecases.PrintPersonsListToXlsxFile
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreenViewModel

val appModule = module {
     single<Realm> { Realm.open(
          RealmConfiguration.create(schema = setOf(UserRealm::class))
     )
     }
     single <AppCloseRepository>{ AppCloseImpl() }
     single <AuthenticationRepository>{ MangodbAuthenticationImpl(get(),get()) }
     single <LoginViewModel>{ LoginViewModel(LoginUseCase(get())) }
     single <RegisterViewModel>{ RegisterViewModel(SignupUseCase(get()), ValidateUsername(get()), closeApplication = CloseApplication(get())) }
     single <PersonRepository> { RealmPersonImpl(
          Realm.open(
               RealmConfiguration.create(schema = setOf(RealmPerson::class,Justification::class, Procedure::class))
          )
     ) }
     single <PersonXlsxRepository> {PersonXlsxImpl()}
     single <PrintPersonsListToXlsxFile>{ PrintPersonsListToXlsxFile(get()) }
     single <GetAllPeople>{ GetAllPeople(get())  }
     single <AddSonsOfOfficersViewModel>{ AddSonsOfOfficersViewModel(addPerson =  AddPerson(get())) }
     single <SonsOfOfficersScreenViewModel> {SonsOfOfficersScreenViewModel(get(),get())  }
}