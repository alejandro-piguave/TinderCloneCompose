package com.apiguave.profile_data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.apiguave.profile_data.repository.FakeProfileRepositoryImpl
import com.apiguave.profile_data.repository.ProfileRepositoryImpl
import com.apiguave.profile_data.repository.dataStore
import com.apiguave.profile_data.repository.ProfileFirebaseDataSource
import com.apiguave.profile_domain.repository.ProfileRepository
import org.koin.dsl.module

val profileDataModule = module {
    single<ProfileRepository> { ProfileRepositoryImpl(get(), get()) }
    single<ProfileFirebaseDataSource> { ProfileFirebaseDataSource() }
    single<DataStore<Preferences>> {
        val context: Context = get()
        context.dataStore
    }
}

val fakeProfileDatModule = module {
    single<ProfileRepository> { FakeProfileRepositoryImpl() }
}