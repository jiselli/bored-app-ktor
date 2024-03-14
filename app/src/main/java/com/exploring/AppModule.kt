package com.exploring

import com.exploring.screen.MainViewModel
import com.exploring.source.repository.ActivityRepository
import com.exploring.source.webservice.ApiClient
import io.ktor.client.engine.cio.CIO
import org.koin.dsl.module

val appModule = module {
    single<MainViewModel> {
        MainViewModel(get())
    }
    single {
        ApiClient(CIO.create())
    }
    single<ActivityRepository> {
        ActivityRepository(get())
    }
}