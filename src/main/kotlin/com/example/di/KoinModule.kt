package com.example.di

import com.example.repositories.HeroRepositoryImpl
import com.example.repositories.IHeroRepository
import com.example.data.HeroDataSource
import com.example.repositories.alternative.HeroRepositoryAlternative
import com.example.repositories.alternative.HeroRepositoryAlternativeImpl
import org.koin.core.scope.get
import org.koin.dsl.module

val koinModule = module {
    single<HeroDataSource> { HeroDataSource() }
    single<IHeroRepository> { HeroRepositoryImpl(service = get()) }
    single<HeroRepositoryAlternative> { HeroRepositoryAlternativeImpl(dataSource = get()) }
}