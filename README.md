# Проект для ModulBank Start!

**Backend:** 
			
 - Auth - аутентификация, регистрация, проверка данных
 - 	WorkWithDatabase - работа с БД
 - 	WebApplication - АПИ


# Текущее API

основной адрес: https://localhost:44314/api

>*RegistrationController* 

 1. **POST /registration - регистриция**
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

	- "Some arguments are missed" 
	- "Incorrect arguments" 
	- "Email is incorrect or already registered" 

>*TokenController*

 1. **POST /login - аутентификация**
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
				"expiration": "2020-05-07T02:04:41Z"
			}
		http 200 ОК
	Возможные ответы:

	- "Some arguments are missed" 
	- "Email or password is incorrect"

> BankOperationController
 2. **POST /makedepo - пополнение счета**
	
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

	- "Invalid bank account or amount"

 4. **POST /maketransfer - перевод между счетами**
	
	Обязательные параметры запроса:
		
		Bearer Token
		- NumberFrom: long		| счет отправителя
		- NumberTo:   long 		| счет получателя
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

	- "Invalid sender bank account or amount, or not enought money"
	- "Invalid beneficiary's bank account or amount"



## 
	 Винов Александр

