using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication.DTO;
using WebApplication.Services.Interfeces;
using WorkWithDatabase.Models;
using WorkWithDatabase.Services.Inerfaces;

namespace WebApplication.Services
{
    public class UserService : IUserService
    {
        private IDbService _dbService;
        public UserService(IDbService dbService)
        {
            _dbService = dbService;
        }

        public AccountModel AddAccount(Guid id, Guid userId, long number)
        {

            _dbService.AddAccount(new AccountModel(
                id,
                userId, 
                number, 
                0,
                AccountStatus.OPENED
            ));

            return _dbService.GetAccount(id);
        }

        public void CloseAccount(long number)
        {
            AccountModel account = _dbService.GetAccountByNumber(number);
            account.Status = AccountStatus.CLOSED;
            _dbService.UpdateAccount(account);
        }

        public List<AccountModel> GetAccounts(Guid userId)
        {
            return _dbService.GetAccountsByUserId(userId);
        }

        public bool IsAccountExist(long number)
        {
            return null != _dbService.GetAccountByNumber(number);
        }

        public bool IsAccountExist(Guid id)
        {
            return null != _dbService.GetAccount(id);
        }

        public bool IsUserVerifyed(Guid userId)
        {
            UserModel user = _dbService.GetUser(userId);
            return user != null && user.Status == UserStatus.VERIFIED;
        }
    }
}
