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
        void UpdateUser(UserModel user);
        void AddAccount(AccountModel account);
        AccountModel GetAccount(Guid id);
        AccountModel GetAccountByNumber(long number);
        List<AccountModel> GetAccountsByUserId(Guid userId);
        void UpdateAccount(AccountModel account);
        void LogOperation(long number, decimal amount, DateTime date);
        List<TransactionOperation> GetTransactions(long number);
    }
}
