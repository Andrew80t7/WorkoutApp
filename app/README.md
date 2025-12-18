# TrainingApp

# Android-приложение для тренировок и учёта прогресса с регистрацией (Firebase Auth — Google / Email), локальным хранением профиля и тренировок в Room и DI через Koin.

---

## Содержание

* [Что внутри](#что-внутри)
* [Требования](#требования)
* [Быстрая настройка и запуск](#быстрая-настройка-и-запуск)
* [Настройка Firebase (обязательно для Google Sign-In)](#настройка-firebase-обязательно-для-google-sign-in)
* [Структура проекта](#структура-проекта)
* [Локальная база данных — Room (проверка и отладка)](#локальная-база-данных---room-проверка-и-отладка)
* [DI — Koin (важные моменты)](#di---koin-важные-моменты)
* [Git (игнорирование google-services.json)](#git-игнорирование-google-servicesjson)
* [Частые проблемы и их решения](#частые-проблемы-и-их-решения)
* [Как вносить изменения / вклад разработчика](#как-вносить-изменения--вклад-разработчика)
* [Лицензия](#лицензия)

---

## Что внутри

* UI — Jetpack Compose экраны: `FirstScreen`, `RegisterScreen`, `LoginScreen`, `ProfileSetupScreen`.
* Навигация — простой навигатор в `navigation/Navigation.kt`.
* Аутентификация — Firebase (Google Sign-In и Email) в `auth/AuthViewModel.kt`.
* Локальные данные — Room (`AppDatabase`, `dao`, `entities`) в `data/local`.
* Репозитории — реализация доступа к данным в `data/repository`.
* DI — Koin в `di/AppModule.kt`.
* ViewModel'ы для UI — в `ui/main`.

---

## Требования

* Android Studio Flamingo+ (рекомендуется последняя стабильная версия).
* JDK 11 или 17 (проверяй `build.gradle.kts`).
* Эмулятор или устройство Android API >= minSdk (в проекте minSdk = 24).
* Наличие интернета для скачивания зависимостей при первой сборке.

---

## Быстрая настройка и запуск

1. Клонируй репозиторий:

```bash
git clone <url>
cd TrainingApp
```

2. Установи `google-services.json` (см. раздел ниже).
3. Синхронизируй Gradle и собери:

```bash
./gradlew --stop
./gradlew clean assembleDebug --refresh-dependencies
```

4. Запусти приложение на эмуляторе (Debug).

---

## Настройка Firebase (обязательно для Google Sign-In)

1. На [Firebase Console](https://console.firebase.google.com/) создай проект.
2. Добавь Android-приложение с `package name = com.example.treningapp`.
3. Скачай `google-services.json` и положи в папку `app/` (путь `app/google-services.json`).
4. В `res/values/strings.xml` добавь строку `default_web_client_id` (берётся из OAuth 2.0 Client ID — он доступен в Credentials в Google Cloud, либо из Firebase). Пример:

```xml
<string name="default_web_client_id">YOUR_OAUTH_CLIENT_ID.apps.googleusercontent.com</string>
```

5. Включи в консоли Firebase Authentication: Email/Password и Google Provider (OAuth client).

> **Важно:** не коммить `google-services.json` в публичные репозитории — добавьте правило в `.gitignore`.

---


---

## Локальная база данных — Room (проверка и отладка)

**Имя файла базы:** `treningapp-db` (хранится в `/data/data/<package>/databases/`).

**Проверить содержимое:**

* Через Android Studio: *App Inspection* / *Database Inspector* (запустить app в Debug → открыть App Inspection → выбрать процесс → Databases → `treningapp-db` → таблица `user_profile`).
* Через ADB (эмулятор):

```bash
# скопировать все файлы (db, wal, shm) локально и открыть
adb exec-out run-as com.example.treningapp tar -C /data/data/com.example.treningapp/databases -c treningapp-db treningapp-db-wal treningapp-db-shm > treningapp-db.tar
tar -xf treningapp-db.tar
# затем открыть в DB Browser for SQLite
```


## DI — Koin (важные моменты)

* Регистрируй `UserPreferences(androidContext())` **до** `UserRepository`, потому что репозиторий зависит от `UserPreferences`.
* ViewModel’ы с параметрами регистрируй через `viewModel { ProfileViewModel(get()) }` и в Compose получай их через `getViewModel()` (Koin) или `viewModel(factory = ...)` (если без Koin).

Пример регистрации:

```kotlin
single { UserPreferences(androidContext()) }
single { Room.databaseBuilder(androidContext(), AppDatabase::class.java, "treningapp-db").build() }
single { get<AppDatabase>().userDao() }
single<UserRepository> { UserRepositoryImpl(get(), get()) }
viewModel { ProfileViewModel(get()) }
```

---

## Git: игнорируем `google-services.json`

Добавь в `.gitignore`:

```
/app/google-services.json
/**/google-services.json
```

Если `google-services.json` уже в репозитории — удалить из индекса (но оставить локально):

```bash
git rm --cached app/google-services.json
git add .gitignore
git commit -m "Remove google-services.json and add to .gitignore"
git push
```

## Как проверять сохранение профиля (рекомендованный flow)

1. На `ProfileSetupScreen` при нажатии "Save" вызывайте `profileViewModel.saveProfile(user)`.
2. ViewModel выполняет `userRepository.saveUserProfile(user)` внутри `viewModelScope.launch {}` и выставляет `uiState.saved = true` при успехе.
3. В Compose ждите `uiState.saved` и навигируйте дальше только после `true`.

---

## Рекомендации по дальнейшему развитию

* Перейти на Navigation Compose для более гибкой маршрутизации.
* Добавить unit tests для ViewModel'ей и интеграционные тесты с in-memory Room.
* Добавить централизованный обработчик ошибок и ретроспективы аналитики.

---

## Контакты / помощь

vysotnik3@gmail.com