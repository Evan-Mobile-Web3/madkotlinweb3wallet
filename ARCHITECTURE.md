# Architecture Documentation

## Overview

The application follows the **Clean Architecture** principles combined with **MVVM (Model-View-ViewModel)** or **MVI (Model-View-Intent)** patterns.

## Layers

### 1. Presentation Layer (`ui`)
Contains the UI components (Composables) and ViewModels. ViewModels interact with the Domain layer using Use Cases.

### 2. Domain Layer (`domain`)
The most stable layer. Contains:
- **Models**: Business entities.
- **Repositories**: Interface definitions for data operations.
- **Use Cases**: Specific business logic units.

### 3. Data Layer (`data`)
Responsible for data operations. Contains:
- **Repositories**: Implementations of the domain repository interfaces.
- **Data Sources**: Logic for fetching data from Network (Remote) or Database (Local).
- **Mappers**: Converts Data models to Domain models and vice versa.

## Dependency Flow
`Presentation` -> `Domain` <- `Data`
