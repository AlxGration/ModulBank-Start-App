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
        AccountModel AddAccount(Guid id, Guid userId, long number);
        void CloseAccount(long number);
        bool IsUserVerifyed(Guid userId);
        bool IsAccountExist(long number);
        bool IsAccountExist(Guid id);

    }
}
