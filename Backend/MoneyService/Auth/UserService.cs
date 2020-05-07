using System;
using System.Collections.Generic;
using System.Text;
using System.Net.Mail;
using WorkWithDatabase;
using System.Diagnostics;
using Npgsql.Logging;

namespace Auth
{
    public interface IUserService {
        bool IsValidUser(string username, string password);
        bool IsValidEmail(string username);
        void RegisterUser(string username, string email, string password);
    }

    public class UserService : IUserService {

        private IDatabaseService _dbService;

        public UserService(IDatabaseService dbService)
        {
            _dbService = dbService;
        }


        public bool IsValidEmail(string email)
        {
            try
            {
                // валидация на email pattern
                MailAddress mail = new MailAddress(email);
                // проверка на занятый username
                UserModel user = _dbService.GetUserByEmail(email);
                if (user != null) return false;
            }
            catch(Exception e)
            {
                Debug.WriteLine(e);
                return false;
            }
            return true;
        }

        public bool IsValidUser(string email, string password)
        {
            UserModel user = _dbService.GetUserByEmail(email);
            if (user == null) return false;
            return Password.CheckPassword(password, user.Salt, user.Password);
        }

        public void RegisterUser(string username, string email, string password)
        {
            User usr = new User(email, password);// хеширование 
            // и запись в БД
            _dbService.AddUser(new UserModel(username, usr.Username, usr.Password, usr.Salt, UserModel.Statuses.UNVERIFIED));
        }
    }
}
