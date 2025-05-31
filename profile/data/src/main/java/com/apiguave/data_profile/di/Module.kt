package com.apiguave.data_profile.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.apiguave.data_profile.repository.ProfileRemoteDataSource
import com.apiguave.data_profile.repository.ProfileRepositoryImpl
import com.apiguave.data_profile.repository.dataStore
import com.apiguave.data_profile.source.ProfileRemoteDataSourceImpl
import com.apiguave.data_profile.source.ProfileRemoteDataSourceMockImpl
import com.apiguave.domain_profile.repository.ProfileRepository
import org.koin.dsl.module

val dataProfileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceImpl() }
    single<DataStore<Preferences>> {
        val context: Context = get()
        context.dataStore
    }
}

val mockDataProfileModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<ProfileRemoteDataSource> { ProfileRemoteDataSourceMockImpl() }
}