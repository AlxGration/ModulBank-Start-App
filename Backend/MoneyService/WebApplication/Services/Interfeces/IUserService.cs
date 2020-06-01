using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WorkWithDatabase.Models;

namespace WebApplication.Services.Interfeces
{
    public interface IUserService
    {
        List<AccountModel> GetAccounts(Guid userId);
        UserModel GetUser(Guid id);
        AccountModel AddAccount(Guid id, Guid userId, long number);
        void CloseAccount(long number);
        bool IsUserVerifyed(Guid userId);
        bool IsAccountExist(long number);
        bool IsAccountExist(Guid id);
        void CloseAccount(Guid userId, Guid accountId);
        bool HaveUserAccount(Guid userId, long number);
        bool IsAccountBalanceEmpty(long number);
    }
}
