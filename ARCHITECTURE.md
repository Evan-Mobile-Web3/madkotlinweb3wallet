# Architecture Documentation

## Overview

The application follows the **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)** or **MVI (Model-View-Intent)** patterns.

## Layers

### 1. Presentation Layer (Android `app/ui`)
Contains the UI components (Composables) and ViewModels. ViewModels interact with the Domain layer using Use Cases.

### 2. Domain Layer (Android `app/domain`)
The most stable layer. Contains:
- **Models**: Business entities.
- **Repositories**: Interface definitions for data operations.
- **Use Cases**: Specific business logic units.

### 3. Data Layer (Android `app/data`)
Responsible for data operations. Contains:
- **Repositories**: Implementations of the domain repository interfaces.
- **Data Sources**: Logic for fetching data from Network (Remote) or Database (Local).
- **Mappers**: Converts Data models to Domain models and vice versa.

### 4. Backend Layer (`server/`)
A lightweight Node.js service (Express) acting as a middleware between the Mobile App and the Web3 world.
- **Role**: Data indexing, metadata management, and administrative control (e.g., user access management).
- **Communication**: REST API over HTTP.

## Dependency Flow
`Mobile App (Presentation -> Domain <- Data)` <---HTTP---> `Backend (Node.js)`
