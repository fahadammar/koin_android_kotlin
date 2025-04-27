package com.example.koininjectionwork.di.module

import com.example.koininjectionwork.interfaces.AllInterfaceImpl
import com.example.koininjectionwork.interfaces.CallAllInterface
import com.example.koininjectionwork.interfaces.CallSingleInterface
import com.example.koininjectionwork.interfaces.FirstInterface
import com.example.koininjectionwork.interfaces.SecondInterface
import com.example.koininjectionwork.interfaces.SingleInterfaceImpl
import org.koin.dsl.bind
import org.koin.dsl.binds
import org.koin.dsl.module

val interfaceModule = module {
    // It can be done this way
    //factory { SingleInterfaceImpl() as FirstInterface}

    // Also it can be done this way
    //factory<FirstInterface>{ SingleInterfaceImpl() }

    // More recommend way is to use the bind
    factory { SingleInterfaceImpl() } bind FirstInterface::class
    factory { CallSingleInterface(get()) }

    // When two or more interfaces are involved than we shall do this
    factory { AllInterfaceImpl() } binds arrayOf(FirstInterface::class, SecondInterface::class)
    factory { CallAllInterface(get(), get()) }
}