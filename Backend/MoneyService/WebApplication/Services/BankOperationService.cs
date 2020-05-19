using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication.Services.Interfeces;
using WorkWithDatabase.Models;
using WorkWithDatabase.Services.Inerfaces;

namespace WebApplication.Services
{
    public class BankOperationService : IBankOperationService
    {
        private IDbService _dbService;
        public BankOperationService(IDbService dbService)
        {
            _dbService = dbService;
        }

        public bool AccountHaveAmount(long number, decimal amount)
        {
            var account = _dbService.GetAccountByNumber(number);
            if (account == null || account.Amount < amount)
                return false;

            return true;
        }

        public bool HaveUserAccount(Guid userId, long number)
        {
            // checking accaount access for userId
            AccountModel account = _dbService.GetAccountByNumber(number);
            return account.User_id.Equals(userId);
        }

        public bool IsAccountOpen(long number)
        {
            var account = _dbService.GetAccountByNumber(number);
            if (account == null || account.Status == AccountStatus.CLOSED)
                return false;

            return true;
        }

        public void MakeDeposit(long number, decimal amount)
        {
            var account = _dbService.GetAccountByNumber(number);
            account.Amount += amount;
            _dbService.UpdateAccount(account);
        }

        public void MakeWithdrawal(long number, decimal amount)
        {
            var account = _dbService.GetAccountByNumber(number);
            account.Amount -= amount;
            _dbService.UpdateAccount(account);
        }
    }
}
