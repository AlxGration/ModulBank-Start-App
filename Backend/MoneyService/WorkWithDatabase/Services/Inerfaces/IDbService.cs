using System;
using System.Collections.Generic;
using System.Text;
using WorkWithDatabase.Models;

namespace WorkWithDatabase.Services.Inerfaces
{
    public interface IDbService
    {
        void AddUser(UserModel user);
        UserModel GetUser(Guid id);
        UserModel GetUserByEmail(string email);
        //void UpdateUser(UserModel user);
        //void DeleteUser(int id);
        void AddAccount(AccountModel account);
        AccountModel GetAccountByNumber(long number);
        void UpdateAccount(AccountModel account);
        //void MakeDeposit(ulong number, decimal amount);


    }
}
