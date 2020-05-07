using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using WebApplication.DTO;
using Auth;
using WorkWithDatabase;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace WebApplication.Controllers
{
    [ApiController]
    [Route("api/[controller]")]
    public class RegistrationController : ControllerBase
    {
        private IUserService _service;

        public RegistrationController(IUserService service)
        {
            _service = service;
        }

        [HttpPost]
        public IActionResult Get([FromBody] UserCredentialsReg user)
        {
            if (user.Username == null || user.Email == null || user.Password == null) return Unauthorized(new { message = "Some arguments are missed" });
            if (user.Username.Length < 3 || user.Password.Length < 6) return Unauthorized(new { message = "Incorrect arguments" });
            // если Email не занят и по формату
            if (_service.IsValidEmail(user.Email))
            {

                // подключение к БД и запрос на добавление
                _service.RegisterUser(user.Username, user.Email, user.Password);

                return Ok();
            }
            return Unauthorized(new { message = "Email is incorrect or already registered" });
        }
    }
}