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

        public Object AddAccount(Guid userId)
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

    }
}
