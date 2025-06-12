# SmartChatbotApp

A modern Android chat application built with Clean Architecture principles, featuring a DeepSeek AI-powered chatbot.

## Features

- Real-time chat interface with AI
- Message history persistence
- Clean Architecture implementation
- Modern UI with Jetpack Compose
- Dependency Injection with Hilt
- Local storage with Room
- Network calls with Retrofit

## Tech Stack

- **UI Layer**: Jetpack Compose, Material3
- **Architecture**: Clean Architecture, MVVM
- **Dependency Injection**: Hilt
- **Local Storage**: Room Database
- **Networking**: Retrofit, OkHttp
- **Asynchronous**: Kotlin Coroutines, Flow
- **Serialization**: Moshi
- **Navigation**: Jetpack Navigation Compose

## Project Structure

```
app/src/main/java/com/demo/smartchatbotapp/
├── common/           # Shared utilities and extensions
├── data/            # Data layer
│   ├── local/       # Local data sources (Room)
│   ├── remote/      # Remote data sources (Retrofit)
│   └── repository/  # Repository implementations
├── domain/          # Domain layer
│   ├── model/       # Domain models
│   ├── repository/  # Repository interfaces
│   └── usecase/     # Use cases
├── di/              # Dependency injection modules
└── ui/              # UI layer
    ├── screen/      # Composable screens
    ├── theme/       # UI theme
    └── viewmodel/   # ViewModels
```

## Setup

1. Clone the repository
2. Open the project in Android Studio
3. Add your DeepSeek API key in `local.properties`:
   ```properties
   DEEPSEEK_API_KEY=your_api_key_here
   ```
4. Sync project with Gradle files
5. Run the app

## Architecture

The app follows Clean Architecture principles with three main layers:

1. **Data Layer**
   - Handles data operations
   - Implements repository interfaces
   - Manages local and remote data sources

2. **Domain Layer**
   - Contains business logic
   - Defines repository interfaces
   - Implements use cases

3. **UI Layer**
   - Manages user interface
   - Implements ViewModels
   - Handles user interactions

## Dependencies

Key dependencies are managed through version catalogs in `gradle/libs.versions.toml`.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details. 