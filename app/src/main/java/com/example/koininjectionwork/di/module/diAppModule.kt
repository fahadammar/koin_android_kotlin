package com.example.koininjectionwork.di.module

import com.example.koininjectionwork.classes.AirPlaneSession
import com.example.koininjectionwork.classes.Airplane
import com.example.koininjectionwork.classes.HeadOfDepartment
import com.example.koininjectionwork.classes.Pilot
import com.example.koininjectionwork.classes.SessionManager
import com.example.koininjectionwork.classes.Student
import com.example.koininjectionwork.classes.StudentSession
import com.example.koininjectionwork.classes.Teacher
import com.example.koininjectionwork.classes.TeacherSession
import com.example.koininjectionwork.classes.UserInfo
import com.example.koininjectionwork.classes.Wings
import com.example.koininjectionwork.ui.AnotherActivity
import com.example.koininjectionwork.ui.MainActivity
import com.example.koininjectionwork.ui.MainViewModel
import com.example.koininjectionwork.utils.MAIN_ACTIVITY_SCOPE
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val diAppModule = module {
    // factory { }: New instance every time you ask for it.
    factory { UserInfo() }
    factory { Pilot() }
    factory { Wings() }
    // Dependencies of Pilot and Wings are provided.
    // Koin will automatically understand the required dependencies and resolve them via get() when instantiating Airplane.
    factory { Airplane(get(), get()) }
    // Scope in Koin keeps objects alive only as long as their screen (Activity/Fragment) is alive.
    scope<MainActivity> {
        scoped { SessionManager() }
    }
    scope(named(MAIN_ACTIVITY_SCOPE)){
        scoped { AirPlaneSession() }
    }
    scope(named(MAIN_ACTIVITY_SCOPE)){
        scoped{ StudentSession() }
    }
    scope(named("TeacherSessionScope")){
        scoped { TeacherSession() }
    }
    // ViewModel
    viewModel {
        MainViewModel(get(), get())
    }

    // the injection with different primitive types in the constructor - hardCoded
    factory { Student(name = "developer", gender = "Male", age = 18) }

    // the injection with different primitive types in the constructor - Dynamic
    // order should be same as in the class constructor else the app crash can occur
    factory { (name: String, subject: String, creditHours: Double, department: String, salary: Int) ->
        Teacher(
            name,
            subject,
            creditHours,
            department,
            salary
        )
    }

    single {
        (name: String,department: String, salary: Int) -> HeadOfDepartment(name, department, salary)
    }
}