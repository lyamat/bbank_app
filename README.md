# Android App with Belarusbank API

## Features

- Modern Android development using [Kotlin](https://kotlinlang.org/) and Android Jetpack libraries.
- [Clean Architecture](https://developer.android.com/topic/architecture) with a multi-layered approach.
- Dependency Injection with [Hilt](https://dagger.dev/hilt/).
- Reactive programming with [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/-flow/).
- [MVVM](https://developer.android.com/topic/libraries/architecture/viewmodel) architecture.

<img src="https://github.com/lyamat/bbank_app/assets/65541361/ae062ad0-097f-4eab-ba13-a3fc297d4b16" alt="bbank_images" style="width:100%;height:100%;">

## Belarusbank Server API

API: [Belarusbank Server](https://belarusbank.by/be/33139/forDevelopers/api)

## Architecture Layers

- `data`: Handles data management, including network and database operations.
- `domain`: Contains business logic and use cases.
- `presentation`: Manages UI and view-related logic.
- `di`: Manages dependency injection configurations.

## Patterns and Principles

- Singleton
- Factory
- Facade
- Dependency Injection
- Adapter
- Observer (Flow)
- Repository
- Use Cases
- DTO (Data Transfer Object)

## Libraries and Tools

- Coroutines
- OkHttp
- Retrofit
- Glide
- SharedPreferences
- Room
- Navigation
- Hilt
- Yandex Maps
