package di

import realmdb.firstRealmMigrate
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import authorization.data.model.UsersRealm
import org.koin.dsl.module
import authorization.data.repository.AppCloseImpl
import authorization.domain.repository.AppCloseRepository
import authorization.domain.repository.AuthenticationRepository
import authorization.data.repository.MangodbAuthenticationImpl
import authorization.domain.usecase.*
import authorization.presentation.accountsPermissions.AccountPermissionViewModel
import authorization.presentation.login.LoginViewModel
import authorization.presentation.register.RegisterViewModel
import features.contracts.data.model.RealmContract
import features.contracts.data.repository.ContractXlsxImpl
import features.contracts.data.repository.RealmContractImpl
import features.contracts.domain.repository.ContractRepository
import features.contracts.domain.repository.ContractXlsxRepository
import features.contracts.domain.usecases.*
import features.contracts.presentation.add_contracts.AddContractViewModel
import features.contracts.presentation.contracts.ContractsScreenViewModel
import features.courses.data.model.JustificationCourse
import features.courses.data.model.ProcedureCourse
import features.courses.data.model.RealmCourse
import features.courses.data.repository.CoursesXlsxImpl
import features.courses.data.repository.RealmCourseImpl
import features.courses.domain.repository.CourseXlsxRepository
import features.courses.domain.repository.CoursesRepository
import features.courses.domain.usecases.AddCourse
import features.courses.domain.usecases.GetAllCourses
import features.courses.domain.usecases.PrintCoursesListToXlsxFile
import features.courses.domain.usecases.UpdateCourse
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
import features.sons_of_officers.domain.usecases.UpdatePerson
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreenViewModel
import utils.UserAuthSystem
import kotlin.math.sin

val appModule = module {
    single<Realm> {
        Realm.open(
            RealmConfiguration.Builder(
                schema = setOf(
                    UsersRealm::class,
                    RealmPerson::class,
                    Justification::class,
                    Procedure::class,
                    RealmContract::class,
                    RealmCourse::class,
                    JustificationCourse::class,
                    ProcedureCourse::class
                )
            ).schemaVersion(6)
                .migration(firstRealmMigrate())
                .build()
        )
    }
    single<AppCloseRepository> { AppCloseImpl() }
    single<AuthenticationRepository> { MangodbAuthenticationImpl(get(), get()) }

    //UserPermission Di
    single<GetAllUsers> { GetAllUsers(get()) }
    single<UpdateUser> { UpdateUser(get()) }
    single<DeleteUser> { DeleteUser(get()) }
    single<AccountPermissionViewModel> {
        AccountPermissionViewModel(
            updateUser = get(),
            getAllUsers = get(),
            deleteUserUseCase = get()
        )
    }

    //Login
    single<LoginViewModel> { LoginViewModel(LoginUseCase(get())) }

    //UserAuthSystem
    single<UserAuthSystem> { UserAuthSystem() }

    //Register
    single<RegisterViewModel> {
        RegisterViewModel(
            SignupUseCase(get()),
            ValidateUsername(get()),
            closeApplication = CloseApplication(get())
        )
    }

    //Sons of officers
    single<PersonRepository> { RealmPersonImpl(get()) }
    single<PersonXlsxRepository> { PersonXlsxImpl() }
    single<PrintPersonsListToXlsxFile> { PrintPersonsListToXlsxFile(get()) }
    single<GetAllPeople> { GetAllPeople(get()) }
    single<UpdatePerson> { UpdatePerson(get()) }
    factory<AddSonsOfOfficersViewModel> {
        AddSonsOfOfficersViewModel(
            addPerson = AddPerson(get()),
            updatePerson = get()
        )
    }
    factory<SonsOfOfficersScreenViewModel> { SonsOfOfficersScreenViewModel(get(), get()) }

    //Contract Di
    single<ContractRepository> { RealmContractImpl(get()) }
    single<GetAllContracts> { GetAllContracts(get()) }
    single<ContractXlsxRepository> { ContractXlsxImpl() }
    single<PrintContractsListToXlsxFile> { PrintContractsListToXlsxFile(get()) }
    single<RemoveAllContracts> { RemoveAllContracts(get()) }
    factory<ContractsScreenViewModel> {
        ContractsScreenViewModel(
            allContracts = get(),
            printContractsListToXlsxFile = get()
        )
    }
    //Add Contract Di
    single<AddContract> { AddContract(get()) }
    single<UpdateContract> { UpdateContract(get()) }
    factory<AddContractViewModel> { AddContractViewModel(addContract = get(), updateContract = get()) }


    //Courses Di
    single<CoursesRepository> { RealmCourseImpl(get()) }
    single<GetAllCourses> { GetAllCourses(get()) }
    single<CourseXlsxRepository> { CoursesXlsxImpl() }
    single<PrintCoursesListToXlsxFile> { PrintCoursesListToXlsxFile(get()) }
    //single<RemoveAllC> { RemoveAllContracts(get()) }
    factory<CoursesScreenViewModel> {
        CoursesScreenViewModel(
            getAllCourses = get(),
            printCoursesListToXlsxFile = get()
        )
    }
    //Add Courses Di
    single<AddCourse> { AddCourse(get()) }
    single<UpdateCourse> { UpdateCourse(get()) }
    factory<AddCourseViewModel> { AddCourseViewModel(addCourse = get(), updateCourse = get()) }


}