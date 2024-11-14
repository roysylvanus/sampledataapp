package com.techadive.sampledataapp.di

import android.app.Application
import android.content.Context
import android.content.res.AssetManager
import androidx.room.Room
import com.techadive.sampledataapp.data.AppPreferences
import com.techadive.sampledataapp.data.JsonDataProvider
import com.techadive.sampledataapp.data.JsonDataProviderImpl
import com.techadive.sampledataapp.data.localdatabase.AppDatabase
import com.techadive.sampledataapp.data.localdatabase.ProductDao
import com.techadive.sampledataapp.data.repositories.ProductRepository
import com.techadive.sampledataapp.data.repositories.ProductRepositoryImpl
import com.techadive.sampledataapp.domain.use_cases.GetProductsUseCase
import com.techadive.sampledataapp.domain.use_cases.LoadSampleDataUseCase
import com.techadive.sampledataapp.domain.use_cases.UpdateProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideAssetManager(@ApplicationContext context: Context): AssetManager =
        context.assets

    @Provides
    fun provideJsonDataProvider(assetManager: AssetManager): JsonDataProvider =
        JsonDataProviderImpl(assetManager)

    @Provides
    fun provideProductRepository(
        dao: ProductDao,
    ): ProductRepository =
        ProductRepositoryImpl(dao)

    @Provides
    fun provideGetProductsUseCase(repository: ProductRepository): GetProductsUseCase {
        return GetProductsUseCase(repository)
    }

    @Provides
    fun provideUpdateProductUseCase(repository: ProductRepository): UpdateProductUseCase =
        UpdateProductUseCase(repository)

    @Provides
    fun provideLoadSampleDataUseCase(
        repository: ProductRepository,
        jsonDataProvider: JsonDataProvider,
        appPreferences: AppPreferences
    ): LoadSampleDataUseCase =
        LoadSampleDataUseCase(repository, jsonDataProvider, appPreferences)

    @Singleton
    @Provides
    fun provideAppPreference(
        @ApplicationContext context: Context
    ): AppPreferences =
        AppPreferences(context)

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "product_database"
        ).build()
    }

    @Provides
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}