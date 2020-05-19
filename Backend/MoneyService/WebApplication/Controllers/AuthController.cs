using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebApplication.DTO;
using WebApplication.BusinessLogic;
using WebApplication.Models;

namespace WebApplication.Controllers
{
    [ApiController]
    
    public class AuthController : ControllerBase
    {
        private AuthUserRequestHandler _service;

        public AuthController(AuthUserRequestHandler service)
        {
            _service = service;
        }

        [Route("api/login")]
        [HttpPost]
        public IActionResult Login([FromBody] UserCredentials user)
        {
            Ttoken token = _service.LoginRequest(user);
            if (token != null)
            {
                return Ok(token);
            }
            return BadRequest("Invalid email or password");
        }

        [Route("api/registration")]
        [HttpPost]
        public IActionResult Register([FromBody] UserCredentialsReg user)
        {
            if (_service.RegisterRequest(user))
            {
                return Ok();
            }
            return BadRequest("Email is incorrect or already registered");
        }

    }
}
