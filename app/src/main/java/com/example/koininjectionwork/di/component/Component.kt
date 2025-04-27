package com.example.koininjectionwork.di.component

import com.example.koininjectionwork.classes.Airplane
import com.example.koininjectionwork.interfaces.AllInterfaceImpl
import com.example.koininjectionwork.interfaces.CallAllInterface
import com.example.koininjectionwork.interfaces.CallSingleInterface
import com.example.koininjectionwork.interfaces.SingleInterfaceImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject


class Component : KoinComponent {
    // lazily "don't create unless actually needed."
    val airPlane : Airplane by inject()
    val callSingleInterface : CallSingleInterface by inject()
    val callAllInterface : CallAllInterface by inject()

    // Eagerly "create right now."
    val airplane2 : Airplane = get()
}

/**
 * This is the component class
 * its particularly useful in classes that are not directly managed by Koin's lifecycle mechanisms
 * (like Activities, Fragments, or ViewModels when using the viewModel() extension). This could include:
 *     [Custom Android Views]: If a custom view needs a specific service.
 *     [Utility Classes]: Helper classes that perform operations and require dependencies.
 *     [Data Sources or Managers]: Classes that handle data fetching or management but aren't necessarily ViewModels
 *     or Repositories directly injected into them.
 *     [Workers or Services]: Background tasks that need access to your application's dependencies.
 **/

