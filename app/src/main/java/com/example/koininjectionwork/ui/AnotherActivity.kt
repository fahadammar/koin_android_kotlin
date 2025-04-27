package com.example.koininjectionwork.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.koininjectionwork.R
import com.example.koininjectionwork.classes.TeacherSession
import org.koin.android.ext.android.get
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class AnotherActivity : AppCompatActivity() , AndroidScopeComponent{
    override val scope : Scope by lazy { getKoin().createScope("TeacherSessionScope", named("TeacherSessionScope")) }
    private val teacherSession: TeacherSession by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_another)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        teacherSession.getTeacherSession()

        // Create the scope manually - we can do like this - but not a good approach - not recommended
        // scope = GlobalContext.get().createScope("TeacherSessionScope", named("TeacherSessionScope"))
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}