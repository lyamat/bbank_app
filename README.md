# Bbank | Belarusbank Android Client App

An Android app for Belarusbank clients, offering access to departments, news, and live currency conversion.
This app interacts with the Belarusbank API. [API Documentation](https://belarusbank.by/be/33139/forDevelopers/api)

## Stack

- **Language**: Kotlin
- **Architecture**: Single-Activity, MVVM Pattern
- **Concurrency**: Kotlin Coroutines
- **Networking**: OkHttp, Ktor
- **Local Storage**: SharedPreferences, Room Database
- **UI**: Navigation Component, Fragments, (XML)
- **DI**: Hilt
- **Map**: Yandex MapKit

## Features

- **Departments**  
  > Browse the list of Belarusbank departments across Belarus.\
  > Filter departments by city, operating status (open/closed).\
  > View department locations on a map and check available currency rates for each department.

- **News**  
  > View a feed of the latest Belarusbank news.\
  > See the time since each article was published.\
  > News detail view using WebView for easy reading.

- **Currency Converter**  
  > Real-time currency conversion with live rate updates.\
  > Add or remove currencies for customized conversion options.\
  > Option to view or hide specific currencies.

---

<img src="https://github.com/user-attachments/assets/af55e336-1b59-40c1-b9b7-857e8a3ec772" alt="bbank_images" style="width:100%;height:100%;">
<img src="https://github.com/user-attachments/assets/0186423b-01ea-4555-823b-0ace4eaa02ec" alt="bbank_images" style="width:100%;height:100%;">
<img src="https://github.com/user-attachments/assets/e97cf357-1600-4550-b7ab-57aae6499401" alt="bbank_images" style="width:50%;height:100%;">

---
## Modularization

**:converter**\
&emsp;:presentation\
**:core**\
&emsp;:domain\
&emsp;:data\
&emsp;:database\
&emsp;:presentation\
**:department**\
&emsp;:network\
&emsp;:presentation\
**:news**\
&emsp;:data\
&emsp;:network\
&emsp;:presentation\
**:build-logic**\
&emsp;:convention
