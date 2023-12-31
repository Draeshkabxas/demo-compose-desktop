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
import features.courses.domain.usecases.*
import features.courses.presentation.add_courses.AddCourseViewModel
import features.courses.presentation.courses.CoursesScreenViewModel
import features.home.data.repository.BackupRealmDBImpl
import features.home.domain.repository.BackupRepository
import features.home.domain.usecases.GetBackupFromLocalRealmDB
import features.home.domain.usecases.SaveBackupOfRealmInDirectory
import features.home.presentation.HomeViewModel
import features.results.data.model.RealmResults
import features.results.data.repository.RealmResultsImpl
import features.results.data.repository.ResultsXlsxImpl
import features.results.domain.repository.ResultsRepository
import features.results.domain.repository.ResultsXlsxRepository
import features.results.domain.usecases.*
import features.results.presentation.add_results.AddResultsViewModel
import features.results.presentation.results.ResultsScreenViewModel
import features.sons_of_officers.data.model.Justification
import features.sons_of_officers.data.model.Procedure
import features.sons_of_officers.data.model.RealmPerson
import features.sons_of_officers.data.repository.PersonXlsxImpl
import features.sons_of_officers.data.repository.RealmPersonImpl
import features.sons_of_officers.domain.repository.PersonRepository
import features.sons_of_officers.domain.repository.PersonXlsxRepository
import features.sons_of_officers.domain.usecases.*
import features.sons_of_officers.presentation.add_sons_of_officers.AddSonsOfOfficersViewModel
import features.sons_of_officers.presentation.sons_of_officers.SonsOfOfficersScreenViewModel
import license.data.repository.LicenseImpl
import license.data.repository.SharedPreferencesImpl
import license.domain.repository.LicenseRepository
import license.domain.repository.SharedPreferencesRepository
import license.domain.usecases.ActivateLicense
import license.domain.usecases.GetExpireDate
import license.presentation.AppLicenseViewModel
import org.koin.core.context.GlobalContext.get
import utils.UserAuthSystem
import utils.getUserAuth


val appModule = module {
    single<Realm> {
        Realm.open(
            RealmConfiguration.Builder(
                schema = setOf(
                    UsersRealm::class,
                    RealmPerson::class,
                    Justification::class,
                    Procedure::class,
                    RealmResults::class,
                    RealmContract::class,
                    RealmCourse::class,
                    JustificationCourse::class,
                    ProcedureCourse::class
                )
            ).schemaVersion(7)
                .migration(firstRealmMigrate())
                .deleteRealmIfMigrationNeeded()
                .build()
        )
    }
    single<AppCloseRepository> { AppCloseImpl() }
    single<AuthenticationRepository> { MangodbAuthenticationImpl(realm =get(),app = get()) }

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
    factory<LoginViewModel> { LoginViewModel(
        LoginUseCase( get()
        )
    ) }

    //UserAuthSystem
    single<UserAuthSystem> { UserAuthSystem() }

    //Register
    factory<RegisterViewModel> {
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
    single<RemoveAllPeople> { RemoveAllPeople(get()) }
    single<RemovePerson> { RemovePerson(get()) }
    single<UpdatePerson> { UpdatePerson(get()) }
    factory<AddSonsOfOfficersViewModel> {
        AddSonsOfOfficersViewModel(
            addPerson = AddPerson(get()),
            updatePerson = get()
        )
    }
    factory<SonsOfOfficersScreenViewModel> {
        SonsOfOfficersScreenViewModel(
            getAllPeople = get(),
            printPersonsListToXlsxFile = get(),
            removeAllPeople = get(),
            removePersonUseCase = get()
        )
    }
//Contract Di
    single<ContractRepository> { RealmContractImpl(get()) }
    single<GetAllContracts> { GetAllContracts(get()) }
    single<ContractXlsxRepository> { ContractXlsxImpl() }
    single<PrintContractsListToXlsxFile> { PrintContractsListToXlsxFile(get()) }
    single<RemoveAllContracts> { RemoveAllContracts(get()) }
    single<RemoveContract> { RemoveContract(get()) }
    single<ImportContractsFromXlsx> { ImportContractsFromXlsx(get()) }
    single<ChangeAllContractsEducationLevel> { ChangeAllContractsEducationLevel() }
    single{ChangeAllContractsCity()}
    single<AddAllContract> { AddAllContract(get()) }
    factory<ContractsScreenViewModel> {
        ContractsScreenViewModel(
            allContracts = get(),
            printContractsListToXlsxFile = get(),
            removeAllContracts = get(),
            removeContractUseCase = get(),
            importContractsFromXlsx = get(),
            changeContractsEducationLevel = get(),
            addAllContract = get(),
            changeAllContractsCity = get()
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
    single<RemoveAllCourses> { RemoveAllCourses(get()) }
    single<RemoveCourse> { RemoveCourse(get()) }
    factory<CoursesScreenViewModel> {
        CoursesScreenViewModel(
            getAllCourses = get(),
            printCoursesListToXlsxFile = get(),
            removeAllCourses = get(),
            removeCourseUseCase = get()
        )
    }
    //Add Courses Di
    single<AddCourse> { AddCourse(get()) }
    single<UpdateCourse> { UpdateCourse(get()) }
    factory<AddCourseViewModel> { AddCourseViewModel(addCourse = get(), updateCourse = get()) }

    //Results Di
    single<ResultsRepository> { RealmResultsImpl(get()) }
    single<GetAllResults> { GetAllResults(get()) }
    single<ResultsXlsxRepository> { ResultsXlsxImpl() }
    single<PrintResultsListToXlsxFile> { PrintResultsListToXlsxFile(get()) }
    single<RemoveAllResults> { RemoveAllResults(get()) }
    single { ImportResultsFromXlsx(resultsXlsxRepository = get()) }
    single { AddAllResults(resultsRepository = get()) }
    single<RemoveResults> { RemoveResults(get()) }
    factory<ResultsScreenViewModel> {
        ResultsScreenViewModel(
            allResults = get(),
            printResultsListToXlsxFile = get(),
            removeAllResults = get(),
            removeResultsUseCase = get(),
            importResultsFromXlsx =  get(),
            addAllResults =  get()
        )
    }
    //Add Results Di
    single<AddResults> { AddResults(get()) }
    single<UpdateResults> { UpdateResults(get()) }
    factory<AddResultsViewModel> { AddResultsViewModel(addResults = get(), updateResults = get()) }


    //Realm Backup Di
    single<BackupRepository> { BackupRealmDBImpl(realm = get()) }
    single<SaveBackupOfRealmInDirectory> { SaveBackupOfRealmInDirectory(backupRepo = get()) }
    single<GetBackupFromLocalRealmDB> { GetBackupFromLocalRealmDB(backupRepo = get()) }
    //Home Di
    single<HomeViewModel> { HomeViewModel(saveBackupOfRealmInDirectory = get(), getBackupFromLocalRealmDB = get()) }

    //AppLicense Di
    single<LicenseRepository> { LicenseImpl() }
    single<SharedPreferencesRepository> { SharedPreferencesImpl() }
    single<ActivateLicense> {
        ActivateLicense(
            licenseRepo = get(),
            sharedPref = get()
        )
    }
    single<GetExpireDate> { GetExpireDate(sharedPref = get()) }
    single<AppLicenseViewModel> { AppLicenseViewModel(getExpireDate = get(),activateLicense =get()) }

}


fun resetAppModule() {
    val currentUser = getUserAuth().currentUser
    get().unloadModules(listOf(appModule))
    get().loadModules(listOf(appModule))
    getUserAuth().currentUser = currentUser
}