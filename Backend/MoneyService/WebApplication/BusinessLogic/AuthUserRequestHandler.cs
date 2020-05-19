using System;
using WebApplication.DTO;
using WebApplication.Models;
using System.Net.Mail;
using System.Diagnostics;
using WebApplication.Services.Interfeces;

namespace WebApplication.BusinessLogic
{
   
    public class AuthUserRequestHandler
    {
        private IAuthService _authService;

        public AuthUserRequestHandler(IAuthService authService)
        {
            _authService = authService;
        }

        public Object LoginRequest(UserCredentials user)
        {
            if (user.Password == null || user.Email == null) return new MessageError("Some arguments are missed");
            if (_authService.IsValidUser(user.Email, user.Password))
            { // если пользователь есть в БД, то даем ему токен
                return _authService.Token(user);
            }

            return new MessageError("Email or password is incorrect");
        }

        public Object RegisterRequest(UserCredentialsReg user)
        {
            if (user.Username == null || user.Email == null || user.Password == null) return new MessageError("Some arguments are missed" );
            if (user.Username.Length < 3 || user.Password.Length < 6) return new MessageError("Incorrect arguments");
            if ( ! IsValidEmail(user.Email)) return new MessageError("Incorrect email" );
            // если Email не занят
            if ( ! _authService.IsEmailRegistered(user.Email))
            {
                Guid id = Guid.NewGuid();
                while ( _authService.IsUserExist(id))
                {
                    id = Guid.NewGuid();
                }

                // подключение к БД и запрос на добавление
                _authService.RegisterUser(id, user.Username, user.Email, user.Password);

                return new { result = true};
            }
            return new MessageError("Email is incorrect or already registered" );
        }
        public bool IsValidEmail(string email)
        {
            try  // валидация на email pattern
            {
                MailAddress mail = new MailAddress(email);
            }
            catch (Exception e)
            {
                Debug.WriteLine(e);
                return false;
            }
            return true;
        }
    }
}
