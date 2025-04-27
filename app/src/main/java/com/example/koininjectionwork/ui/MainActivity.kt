package com.example.koininjectionwork.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koininjectionwork.BuildConfig
import com.example.koininjectionwork.R
import com.example.koininjectionwork.classes.AirPlaneSession
import com.example.koininjectionwork.classes.HeadOfDepartment
import com.example.koininjectionwork.classes.Server
import com.example.koininjectionwork.classes.SessionManager
import com.example.koininjectionwork.classes.Teacher
import com.example.koininjectionwork.data.api.apiViewModel.ApiViewModel
import com.example.koininjectionwork.di.component.Component
import com.example.koininjectionwork.utils.DEBUG_SERVER
import com.example.koininjectionwork.utils.PRODUCTION_SERVER
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class MainActivity : AppCompatActivity() {
    private val sessionManager : SessionManager by inject()
    private val airPlaneSessionManager : AirPlaneSession by inject()
    private val mainViewModel : MainViewModel by viewModel()
    private val apiViewModel : ApiViewModel by viewModel()
    private lateinit var teacher: Teacher
    private lateinit var hod: HeadOfDepartment
    private val debugServer: Server by  lazy { get(named(DEBUG_SERVER)) }
    private val releaseServer: Server by  lazy { get(named(PRODUCTION_SERVER)) }
    private val component = Component()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        outPuts()
    }

    private fun outPuts() {
        sessionManager.createSession("MainActivitySession_1296")
        airPlaneSessionManager.getAirPlaneSession()
        mainViewModel.getUserName()
        mainViewModel.getStudentInfo()
        val apiResult = apiViewModel.getPost()
        Log.d("apiResponse", "onCreate: API_RESULT -> $apiResult")

        teacher = get(parameters = { parametersOf(
            "CodeWithTeacher", "Full Stack Development", 5,"Software Engineering", 500000)
        })

        hod = get(parameters = { parametersOf(HeadOfDepartmentInfo(
            "CodeWithHOD", "CS/Software Engineering", 1000000))
        })

        teacher.getTeacherInfo()
        hod.getHODInfo()

        // As the component is instantiated, the eagerly, airPlane2 is created immediately.
        // on other hand, lazily will be created when it will be needed.
        component.airPlane.fly()

        if(BuildConfig.DEBUG){
            debugServer.connect()
        } else {
            releaseServer.connect()
        }

        // calling the classes of the interface implementations
        component.callSingleInterface.callInterface()
        component.callAllInterface.callAllInterfaces()
    }

    // when the parameters are more than 2 or 3, its better to use the data class and pass that in the parameters
    data class HeadOfDepartmentInfo(
        val name:String,
        val department: String,
        val salary : Int
    )
}