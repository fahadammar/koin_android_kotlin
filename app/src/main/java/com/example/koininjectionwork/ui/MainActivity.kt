package com.example.koininjectionwork.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
import com.example.koininjectionwork.utils.logTag
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity(), AndroidScopeComponent{
    override val scope: Scope by activityScope()
    val scope2 : Scope by lazy { getKoin().createScope(getServerName(), named(getServerName())) }
    private val sessionManager : SessionManager by inject()
    private val airPlaneSessionManager : AirPlaneSession by inject()
    private val mainViewModel : MainViewModel by viewModel()
    private val apiViewModel : ApiViewModel by viewModel()
    private lateinit var teacher: Teacher
    private lateinit var hod: HeadOfDepartment
    private val debugServer: Server by  lazy { get(named(DEBUG_SERVER)) }
    private val releaseServer: Server by  lazy { get(named(PRODUCTION_SERVER)) }
    private val server: Server by lazy { scope2.get() }
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
        // Check if the fragment has already been added to avoid multiple instances
        if (savedInstanceState == null) {
            val fragment = JustAFragment()

            // Begin the transaction to add the fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment) // Replace the FrameLayout with the fragment
                .commit()
        }
        clickListener()
        outPuts()
    }

    private fun clickListener() {
        val buttonClick = findViewById<Button>(R.id.button)
        buttonClick.setOnClickListener {
            val intent = Intent(this, AnotherActivity::class.java)
            startActivity(intent)
        }
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
            Log.d(logTag, "outPuts: --Qualifier--")
            debugServer.connect()
        } else {
            releaseServer.connect()
        }
        Log.d(logTag, "outPuts: -- Scope Call below --")
        server.connect()

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

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
        scope2.close()
    }

    fun getServerName():String{
        return if(BuildConfig.DEBUG) DEBUG_SERVER else PRODUCTION_SERVER
    }
}