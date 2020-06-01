using System;
using System.Collections.Generic;
using System.Linq;
using System.Security.Cryptography;
using System.Threading.Tasks;
using WebApplication.DTO;
using WebApplication.Services.Interfeces;
using WorkWithDatabase.Models;

namespace WebApplication.BusinessLogic
{
    public class UserRequestHandler
    {
        IUserService _service;

        public UserRequestHandler(IUserService service)
        {
            _service = service;
        }

        public List<AccountModel> GetAccounts(Guid userId)
        {
            return _service.GetAccounts(userId);
        }

        public object AddAccount(Guid userId)
        {
            if (!_service.IsUserVerifyed(userId)) return new MessageError("Non verified users can not create account");

            // создание номера счета
            var rnd = new Random();
            long number = rnd.Next(0, 1000000000) + 4000000000 ;
            while ( _service.IsAccountExist(number))
            {
                number = rnd.Next(0, 1000000000) + 4000000000;
            }

            // создание id
            Guid id = Guid.NewGuid();
            while (_service.IsAccountExist(id))
            {
                id = Guid.NewGuid();
            }

            return _service.AddAccount(id, userId, number);
        }

        public UserMe GetUser(Guid id)
        {
            UserModel user = _service.GetUser(id);
            return new UserMe(user.Username, user.Photo, user.Status);
        }

        public object CloseAccount(Guid userId, long number)
        {
            if (!_service.IsUserVerifyed(userId)) return new MessageError("Non verified users have not accounts");

            if (!_service.HaveUserAccount(userId, number)) return new MessageError("No accsess to this account");

            if (!_service.IsAccountBalanceEmpty(number)) return new MessageError("Transfer money before closing account");

            _service.CloseAccount(number);

            return new { result = true };
        }

    }
}
