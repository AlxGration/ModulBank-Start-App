



# Проект для ModulBank Start!

![one](Android/screens/full.png)

**Backend:** (.net core 3)
 - 	WebApplication - АПИ	
 - 	WorkWithDatabase - работа с БД

**Frontend:** (React, Redux, React-Router, Redux-Saga)
 - 	auth modul - регистрация, аутентификация
 - home modul - личный кабинет
 - accounts modul - список счетов
 - main modul - главный модуль App
 *Информация об авторизованности хранится в localStorage браузера (+валидация токена на пригодность (expiration))

**Android:** (kotlin, dagger 2, retrofit 2)
 - 	скрины экранов в Android/screens
 
# Текущее API

[Регистрация](#registration)

[Аутентификация](#login)

[Информация о себе](#me)
<br>

[Открыть новый счет](#newaccount)

[Список счетов](#accounts)

[Закрыть счет](#closeaccount)
<br>

[Пополнение счета](#makedepo)

[Перевод между счетами](#maketransfer)

[Список транзакций](#transactions)


основной адрес: https://localhost:44314/api/


 1. **POST /<a name="registration">registration</a> - регистрация**
Обязательные параметры запроса:
	
		- username: string
		- email:    string
		- password: string
	Пример запроса:

		{
			"Username":"Алекс",
			"Email":"alex@mail.ru",
			"Password":"1243456"
		}
	Ответ:

		http 200 ОК
		
	Ограничения:
	- Валидный email,
	- Длина Username от 3-ех символов
	- Длина Password от 6-ти символов

	Возможные ответы:
	- { errorMessage: "error cause" }

 2. **POST /<a name="login">login</a> - аутентификация**
	Обязательные параметры запроса:
		
		- email:    string
		- password: string
	Пример запроса:

		{
			"Email":"alex@mail.ru",
			"Password":"1243456"
		}
	Ответ:

		{
			"token": "eyJhbGci...",
			"expiration": "2020-05-07T02:04:41Z",
			"userId": "17f901b1-6bd5-4ebc-ac1d-1ce2dc866adb"
		}
		http 200 ОК
	Возможные ответы:

	- { errorMessage: "error cause" }

 3. **GET /<a name="me">me</a> - информация о себе**
	Обязательные параметры запроса:
		
		- Bearer token
		- ID in header

	Ответ:

		{
			"username": "Alex",
			"status": 0,
			"photo": "http://link"
		}
		http 200 ОК
	Возможные ответы:

	- { errorMessage: "error cause" }

 4. **POST /<a name="makedepo">makedepo</a> - пополнение счета**
	
	Обязательные параметры запроса:
		
		Bearer Token
		- NumberTo: long 	| счет получателя
		- Amount:   decimal	| сумма
	Пример запроса:

		{
			"NumberTo":4000000000,
			"Amount":1000.60
		}
	Ответ:
			
		http 200 ОК
	Ограничения:
	- формат номера счета "4ххххххххх" 
	- сумма > 0
	- номера счета существует и открыт

	Возможные ответы:

	- { errorMessage: "error cause" }

 5. **POST /<a name="maketransfer">maketransfer</a> - перевод между счетами**
	
	Обязательные параметры запроса:
		
		Bearer Token
		- NumberFrom: long	| счет отправителя
		- NumberTo:   long 	| счет получателя
		- Amount:     decimal	| сумма
	Пример запроса:

		{
			"NumberFrom":4000000001,
			"NumberTo":4000000000,
			"Amount":1000.60
		}
	Ответ:
	
		http 200 ОК
		
	Ограничения:
	- формат номера счета "4ххххххххх" 
	- сумма > 0
	- номера счета существует и открыт

	Возможные ответы:

	- { errorMessage: "error cause" }

 6. **GET /<a name="accounts">accounts</a> - список открытых счетов**
	
	Обязательные параметры запроса:
		
		Bearer Token
		ID(пользователя) in header
	
	Ответ:
			
		[
			{
				"id": "07bced39-5f19-4beb-8733-79e8b2463917",
				"user_id": "17f901b1-6bd5-4ebc-ac1d-1ce2dc866adb",
				"number": 4707095033,
				"amount": 2800.90,
				"status": 0
			},
			...
		]

	Возможные ответы:

	- { errorMessage: "error cause" }

 7. **GET /<a name="newaccount">newaccount</a> - создать новый счет**
	
	Обязательные параметры запроса:
		
		Bearer Token
		ID(пользователя) in header
	
	Ответ:
			
		{
			"id": "50b80db1-d3e9-4b28-bb26-a9e049ce7ab4",
			"user_id": "17f901b1-6bd5-4ebc-ac1d-1ce2dc866adb",
			"number": 4713207899,
			"amount": 0.00,
			"status": 0
		}
		http 200 ОК

	Возможные ответы:

	- { errorMessage: "error cause" }

 8. **GET /<a name="closeaccount">closeaccount</a> - закрыть счет**
	
	Обязательные параметры запроса:
		
		Bearer Token
		ID(пользователя) in header
		- number - номер счета
	
	Ответ:
		
		http 200 ОК

	Ограничения:
	
	- на счете не должно быть средств
	
	Возможные ответы:
	
	- { errorMessage: "error cause" }
	
 9. **GET /<a name="transactions">transactions</a> - список операций по счету**
	
	Обязательные параметры запроса:
		
		Bearer Token
		ID(пользователя) in header
		- number - номер счета
	
	Ответ:
		
		[
			{
				"id": 2,
				"number": 4707095033,
				"amount": 700.00,
				"oper_date": "2020-06-01T00:00:00"
			},
			{
				"id": 3,
				"number": 4707095033,
				"amount": -200.30,
				"oper_date": "2020-06-01T00:00:00"
			}
		]
		http 200 ОК

	Возможные ответы:
	
	- { errorMessage: "error cause" }
	
## 
	 Винов Александр
