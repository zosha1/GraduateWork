# Процедура запуска авто-тестов

1. Установить Docker, скачать образы: MySql, PostgreSQL и Node.js.
2. Командой ```git clone``` склонировать репозиторий [GraduateWork](https://github.com/zosha1/GraduateWork).
3. Командой ```docker-compose -f .\docker-compose.yml up``` 
запустить контейнер с MySql, PostgreSQL и Node.js.
4. Выполнить одно из двух действий:
* для запуска и интеграции приложения с базой MySQL выполнить команду
```java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar```;
* для запуска и интеграции приложения с базой PostgreSQL выполнить команду
```java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar```.
5. Запустить тесты одной из следующих команд соответственно:
* для СУБД MySQL: 
```./gradlew "-Ddatasource.url=jdbc:mysql://localhost:3306/app" clean test allureReport```;
* для СУБД PostgreSQL: 
```./gradlew "-Ddatasource.url=jdbc:postgresql://localhost:5432/app" clean test allureReport```.
6. Для получения отчетов Allure необходимо ввести команду ```./gradlew allureServe``` 
находясь в корневой папке.
7. Для завершения работы контейнеров Docker использовать команду
```docker-compose down``` находясь в корневой папке.