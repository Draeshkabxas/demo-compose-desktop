package di

import realmdb.firstRealmMigrate
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
import features.contracts.data.model.RealmContract
import features.contracts.data.repository.RealmContractImpl
import features.contracts.domain.repository.ContractRepository
import features.contracts.domain.usecases.AddContract
import features.contracts.domain.usecases.GetAllContracts
import features.contracts.domain.usecases.RemoveAllContracts
import features.contracts.presentation.add_contracts.AddContractViewModel
import features.contracts.presentation.contracts.ContractsScreenViewModel
import features.courses.data.model.JustificationCourse
import features.courses.data.model.ProcedureCourse
import features.courses.data.model.RealmCourse
import features.courses.data.repository.RealmCourseImpl
import features.courses.domain.repository.CoursesRepository
import features.courses.domain.usecases.AddCourse
import features.courses.domain.usecases.GetAllCourses
import features.courses.presentation.add_courses.AddCourseViewModel
import features.courses.presentation.courses.CoursesScreenViewModel
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
    single<Realm> {
        Realm.open(
            RealmConfiguration.Builder(
                schema = setOf(
                    UserRealm::class,
                    RealmPerson::class,
                    Justification::class,
                    Procedure::class,
                    RealmContract::class,
                    RealmCourse::class,
                    JustificationCourse::class,
                    ProcedureCourse::class
                )
            ).schemaVersion(2)
                .migration(firstRealmMigrate())
                .build()
        )
    }
    single<AppCloseRepository> { AppCloseImpl() }
    single<AuthenticationRepository> { MangodbAuthenticationImpl(get(), get()) }
    single<LoginViewModel> { LoginViewModel(LoginUseCase(get())) }
    single<RegisterViewModel> {
        RegisterViewModel(
            SignupUseCase(get()),
            ValidateUsername(get()),
            closeApplication = CloseApplication(get())
        )
    }
    single<PersonRepository> { RealmPersonImpl(get()) }
    single<PersonXlsxRepository> { PersonXlsxImpl() }
    single<PrintPersonsListToXlsxFile> { PrintPersonsListToXlsxFile(get()) }
    single<GetAllPeople> { GetAllPeople(get()) }
    single<AddSonsOfOfficersViewModel> { AddSonsOfOfficersViewModel(addPerson = AddPerson(get())) }
    factory<SonsOfOfficersScreenViewModel> { SonsOfOfficersScreenViewModel(get(), get()) }

    //Contract Di
    single<ContractRepository> { RealmContractImpl(get()) }
    single<GetAllContracts> { GetAllContracts(get()) }
    single<RemoveAllContracts> { RemoveAllContracts(get()) }
    factory<ContractsScreenViewModel> { ContractsScreenViewModel(allContracts = get()) }
    //Add Contract Di
    single<AddContract> { AddContract(get()) }
    single<AddContractViewModel> { AddContractViewModel(addContract = get()) }


    //Courses Di
    single<CoursesRepository> { RealmCourseImpl(get()) }
    single<GetAllCourses> { GetAllCourses(get()) }
    //single<RemoveAllC> { RemoveAllContracts(get()) }
    factory<CoursesScreenViewModel> { CoursesScreenViewModel(getAllCourses = get()) }
    //Add Courses Di
    single<AddCourse> { AddCourse(get()) }
    single<AddCourseViewModel> { AddCourseViewModel(addCourse = get()) }


}