package com.example.koininjectionwork.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.koininjectionwork.R
import com.example.koininjectionwork.classes.PrintInFragment
import com.example.koininjectionwork.utils.logTag
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.fragmentScope
import org.koin.core.scope.Scope
import kotlin.math.log

class JustAFragment : Fragment(), AndroidScopeComponent {
    override val scope: Scope by fragmentScope()
    private val printInFragment : PrintInFragment by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_just_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(logTag, "onViewCreated: Print Fragment Called")
        printInFragment.printFragmentCall()
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.close()
    }
}