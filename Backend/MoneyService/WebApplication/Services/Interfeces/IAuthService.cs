using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication.DTO;
using WebApplication.Models;

namespace WebApplication.Services.Interfeces
{
    public interface IAuthService
    {
        bool IsValidUser(string username, string password);
        bool IsEmailRegistered(string email);
        void RegisterUser(Guid id, string username, string email, string password);
        Ttoken Token(UserCredentials user);
        bool IsUserExist(Guid id);
    }
}
