package com.example.koininjectionwork.di.module

import com.example.koininjectionwork.classes.Server
import com.example.koininjectionwork.data.api.apiViewModel.ApiViewModel
import com.example.koininjectionwork.data.api.retrofitBuilder.provideApiService
import com.example.koininjectionwork.data.api.retrofitBuilder.provideMoshi
import com.example.koininjectionwork.data.local.provideDao
import com.example.koininjectionwork.data.local.providesDatabase
import com.example.koininjectionwork.utils.DEBUG_SERVER
import com.example.koininjectionwork.utils.DEBUG_SERVER_NAME
import com.example.koininjectionwork.utils.PRODUCTION_SERVER
import com.example.koininjectionwork.utils.PRODUCTION_SERVER_NAME
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = module{
    // API
    single { provideMoshi() }
    single { provideApiService(get()) }

    // roomDB
    single {providesDatabase(get())}
    single { provideDao(get()) }

    // Example of Qualifier
    // Define Database for testing
    single(named(DEBUG_SERVER)) { Server(DEBUG_SERVER_NAME) }
    // Define Database for production
    single(named(PRODUCTION_SERVER)) { Server(PRODUCTION_SERVER_NAME) }

    viewModel { ApiViewModel(get(), get()) }
}