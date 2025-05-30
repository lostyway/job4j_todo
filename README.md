📝 Todo Web App
Описание

Todo Web App — это веб-приложение для управления задачами. Приложение позволяет создавать, редактировать, просматривать, удалять задачи и отмечать их как выполненные. Реализовано на основе фреймворка Spring MVC, с использованием Thymeleaf для шаблонов и Sql2o для работы с базой данных.
🔧 Стек технологий

    Java 17+

    Spring MVC

    Spring Web

    Thymeleaf

    Sql2o

    PostgreSQL

    Liquibase

    Maven

    Lombok

📦 Установка и запуск
1. Клонировать репозиторий:

git clone https://github.com/lostyway/job4j_todo.git
cd todo

2. Настроить базу данных:

Создайте PostgreSQL-базу данных и пользователя, затем настройте файл db.properties (или application.properties, если используется Spring Boot):

datasource.url=jdbc:postgresql://localhost:5432/todo
datasource.username=your_username
datasource.password=your_password

3. Запустить миграции Liquibase:

mvn liquibase:update

4. Собрать и запустить приложение:

mvn clean package
java -jar target/todo-1.0.jar

(или mvn spring-boot:run, если у тебя Spring Boot)
📌 Функциональность

    Просмотр всех задач

    Создание новой задачи

    Просмотр задачи по ID

    Редактирование задачи

    Удаление задачи

    Отметка задачи как выполненной

    Обработка исключений (404 / ошибки обновления)

📸 Скриншоты

Окно входа
![изображение](https://github.com/user-attachments/assets/b8fc4438-5a1c-44b6-ae8e-2d4599af7186)

Окно регистрации
![изображение](https://github.com/user-attachments/assets/666c250e-633c-406b-a907-f7c0d0ad0e51)


Главная страница
  ![изображение](https://github.com/user-attachments/assets/dc02bbbb-e4d7-43b8-a53a-b8601a49f684)

Добавление задачи
![изображение](https://github.com/user-attachments/assets/375c36f4-f186-4bf6-acc7-f4405d4be2c5)

Окно просмотра задачи
![изображение](https://github.com/user-attachments/assets/393b8d3a-ab6d-4796-b30a-a2df3a76a6a3)

Окно редактора задачи
![изображение](https://github.com/user-attachments/assets/b969df20-730c-488f-ad62-c7aa90606c0e)


