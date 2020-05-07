﻿using System;
using WorkWithDatabase.Postgre;

namespace WorkWithDatabase
{

    public interface IDatabaseService
    {
        void AddUser(UserModel user);
        UserModel GetUser(int id);
        UserModel GetUserByEmail(string email);
        //void UpdateUser(UserModel user);
        //void DeleteUser(int id);
        void AddAccount(AccountModel account);
        AccountModel GetAccountByNumber(long number);
        void UpdateAccount(AccountModel account);
        //void MakeDeposit(ulong number, decimal amount);
       

    }
    public class DatabaseController : IDatabaseService
    {

        private IDatabaseService _db;

        public DatabaseController()
        {
            _db = new PostgreDB();
        }


        public void AddAccount(AccountModel account)
        {
            _db.AddAccount(account);
        }

        public void AddUser(UserModel user)
        {
            _db.AddUser(user);
        }

        public UserModel GetUser(int id)
        {
            return _db.GetUser(id);
        }

        public UserModel GetUserByEmail(string email)
        {
            return _db.GetUserByEmail(email);
        }

        public AccountModel GetAccountByNumber(long number)
        {
            return _db.GetAccountByNumber(number);
        }

        public void UpdateAccount(AccountModel account)
        {
            _db.UpdateAccount(account);
        }

    }
}
