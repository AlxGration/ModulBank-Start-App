using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using WebApplication.DTO;
using WebApplication.BusinessLogic;
using WebApplication.Models;
using Newtonsoft.Json.Linq;

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
            JObject json = (JObject)JToken.FromObject(_service.LoginRequest(user));
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }
            return Ok(json.ToObject<Ttoken>());

        }

        [Route("api/registration")]
        [HttpPost]
        public IActionResult Register([FromBody] UserCredentialsReg user)
        {
            JObject json = (JObject)JToken.FromObject(_service.RegisterRequest(user));
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }
            return Ok();
            
        }

    }
}
