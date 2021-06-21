# Примеры реактивного программирования с использованием Spring WebFlux:
   
   Примеры выполнены в классе HomeController:

   Обычный (синхронный) метод, возвращает строку (константу);

   http://localhost:8080/test1

   Реактивный метод, возвращает строку (константу);

   http://localhost:8080/test2

   Реактивный метод, вызывает не блокирующий метод сервис (non-blocked);
   
   http://localhost:8080/test3

   Реактивный метод, вызывает блокирующий метод сервис (blocked);

   http://localhost:8080/test4

   Реактивный метод, вызывает блокирующий метод сервис (blocked), при вызове используется Schedulers;

   http://localhost:8080/test5

   Реактивный метод, который внутри вызывает первый метод сервиса, получив ответ от первого, вызывает второй, конкатенирует ответ от первого с ответом второго и возвращает полученную строку.

   http://localhost:8080/test6

   Реактивный метод, который параллельно вызывает 5 методов сервиса, каждый из которых блокирует поток на 5 секунд. Далее, дожидаемся ответа от всех 5, конкатенируем ответы в одну строку и возвращаем. Общее время работы не больше 6 секунд.

   Использование метода Mono.fromCallable()

   http://localhost:8080/test7

   Использование метода Flux.just().publishOn(Schedulers.parallel())

   http://localhost:8080/test8

   Реактивный метод, который внутри себя вызывает через Web Client 2-ой метод этого списка.

   http://localhost:8080/test9

# Обработка ошибок в Spring WebFlux:

   Маршрутизация запросов осуществляется в классе Router. Обработка ошибок выполняется в классе Handler. Правильный вызов контроллера с параметром:

   http://localhost:8080/hello1?name=Bob

   Обработка ошибок с помощью onErrorReturn, при получении исключения от метода sayHello(), возвращается строка по умолчанию.
   
   http://localhost:8080/hello1

   Обработка ошибок с помощью onErrorResume, при получении исключения от метода sayHello(), возвращается строка, состоящая из динамически полученного сообщения об ошибке, добавляемого к строке «Error»

   http://localhost:8080/hello2

   Обработка ошибок с помощью onErrorResume, при получении исключения от метода sayHello(), вызывается резервный служебный метод sayHelloFallback()

   http://localhost:8080/hello3

   Обработка ошибок с помощью onErrorResume, при получении исключения от метода sayHello(), генерируется настраиваемое исключение с сообщением: «username is required». Для выполнения проверки необходимо закомментировать классы: GlobalErrorAttributes и GlobalErrorWebExceptionHandler
  
   http://localhost:8080/hello4

   Обработка ошибок на глобальном уровне

   http://localhost:8080/hello5

# Справочная информация

1. Блокирующие и неблокирующие вызовы;
   
   (См. https://www.baeldung.com/spring-webflux-concurrency)
   
   (См. https://www.baeldung.com/spring-webclient-simultaneous-calls)

2. Обработка ошибок и исключений.
   
   (См. https://www.baeldung.com/spring-webflux-errors)

3. Справочное руководство Reactor 3
   
   (См. https://projectreactor.io/docs/core/release/reference/#flux)

    Дополнительный материал:
   
    (См. https://medium.com/@kirill.sereda/reactive-programming-reactor-%D0%B8-spring-webflux-3f779953ed45)
   
    (См. https://medium.com/@kirill.sereda/reactive-programming-reactor-%D0%B8-spring-webflux-%D1%87%D0%B0%D1%81%D1%82%D1%8C-2-a0273a5d4ebd)
   