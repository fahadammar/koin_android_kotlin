package com.example.koininjectionwork.data.api.retrofitBuilder

import com.example.koininjectionwork.data.api.service.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

fun provideMoshi():Moshi = Moshi
    .Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

fun provideApiService(moshi: Moshi):ApiService =
    Retrofit
        .Builder()
        .run {
            baseUrl(ApiService.BASE_URL)
            addConverterFactory(MoshiConverterFactory.create(moshi))
            build()
        }.create(ApiService::class.java)