# Дипломный проект Яндекс.Практикума - Поиск вакансий

[![CI Status](https://github.com/DoNatPanic/practicum-android-diploma/actions/workflows/pr_checks.yml/badge.svg)](https://github.com/DoNatPanic/practicum-android-diploma/actions)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg)](https://kotlinlang.org/)
[![Android](https://img.shields.io/badge/Android-API_26+-green.svg)](https://developer.android.com)
[![Retrofit](https://img.shields.io/badge/Retrofit-2.11.0-orange.svg?logo=square)](https://square.github.io/retrofit/)
[![Room](https://img.shields.io/badge/Room-2.6.1-blue.svg?logo=android)](https://developer.android.com/training/data-storage/room)
[![Koin](https://img.shields.io/badge/Koin-3.3.0-blueviolet.svg)](https://insert-koin.io)
[![Coroutines](https://img.shields.io/badge/Coroutines-1.7.3-green.svg?logo=kotlin)](https://github.com/Kotlin/kotlinx.coroutines)
[![Glide](https://img.shields.io/badge/Glide-4.16.0-yellow.svg)](https://bumptech.github.io/glide/)
[![Detekt](https://img.shields.io/badge/Detekt-1.23.3-blue.svg)](https://detekt.dev)

## Описание проекта

Это дипломный проект для курса ["Android-разработчик" Яндекс.Практикума](https://practicum.yandex.ru/android-developer/). Приложение предоставляет функциональность для поиска вакансий через API HeadHunter с возможностью фильтрации, просмотра деталей вакансий и добавления их в избранное.

## 📱 Основные функции

-   🔍 Поиск вакансий по ключевым словам
-   ⚙️ Фильтрация вакансий по региону, отрасли и уровню зарплаты
-   💼 Просмотр детальной информации о вакансии
-   ❤️ Добавление вакансий в избранное для оффлайн-доступа
-   🌓 Оформление в тёмной и светлой теме в зависмости от настроек устройства

## 🛠 Технологический стек

| Категория              | Технологии                   |
| ---------------------- | ---------------------------- |
| Язык                   | Kotlin                       |
| Минимальный SDK        | Android API 29               |
| Архитектура            | MVVM + Clean Architecture    |
| Локальная БД           | Room                         |
| DI                     | Koin                         |
| Сетевые запросы        | Retrofit 2                   |
| Асинхронность          | Kotlin Coroutines            |
| UI                     | XML-разметка                 |
| Навигация              | Android Navigation Component |
| Работа с изображениями | Glide                        |
| Стиль кода             | Detekt                       |

## 💻 Demo

![Демонстрация работы приложения](./images/demo.gif)

## 🚀 Инструкция по запуску

1. **Склонируйте репозиторий**:

    ```bash
    git clone https://github.com/DoNatPanic/practicum-android-diploma.git
    ```

2. **Добавьте API-ключ**:

    - Создайте файл `develop.properties` в корне проекта
    - Добавьте строку:
        ```properties
        hhAccessToken=токен_для_доступа_к_api_headhunter
        ```

3. **Запустите в Android Studio**:
    - Откройте проект
    - Дождитесь синхронизации Gradle
    - Нажмите `Run` для сборки и запуска

> _Приложение требует Android SDK 26+ (Android 8.0+) и работает только в портретной ориентации_
