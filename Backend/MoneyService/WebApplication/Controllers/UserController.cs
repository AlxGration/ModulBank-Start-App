using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Newtonsoft.Json.Linq;
using WebApplication.BusinessLogic;
using WebApplication.DTO;
using WorkWithDatabase.Models;

namespace WebApplication.Controllers
{
    [Authorize]
    [ApiController]
    public class UserController : ControllerBase
    {
        private UserRequestHandler _userRequest;

        public UserController(UserRequestHandler userRequest)
        {
            _userRequest = userRequest;
        }

        [Route("api/accounts")]
        [HttpGet]
        public IActionResult GetAccounts()
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID"))
            {
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized(new MessageError("No user ID in header"));
            }

            List<AccountModel> accounts = _userRequest.GetAccounts(new Guid(userId));
            if (accounts != null && accounts.Count > 0)
            {
                return Ok(accounts);
            }
            return BadRequest(new MessageError("There is no accounts"));
        }

        [Route("api/newaccount")]
        [HttpGet]
        public IActionResult NewAccount()
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID"))
            {
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized(new MessageError("No user ID in header"));
            }

            object obj = _userRequest.AddAccount(new Guid(userId));
            if (obj == null)
            {
                return BadRequest(new MessageError("Server error, try again"));
            }
            JObject json = (JObject)JToken.FromObject(obj);
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }

            AccountModel account = json.ToObject<AccountModel>();          
            return Ok(account);
        }

        [Route("api/me")]
        [HttpGet]
        public IActionResult GetUser()
        {

            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID"))
            {
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized(new MessageError("No user ID in header"));
            }

            UserMe user = _userRequest.GetUser(new Guid(userId));
            if (user != null)
            {
                return Ok(user);
            }
            return BadRequest(new MessageError("User is not found"));
        }

        
        [Route("api/closeaccount")]
        [HttpPost]
        public IActionResult ChangeAccountStatus(Account account)
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID"))
            {
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized("No user ID in header");
            }

            JObject json = (JObject)JToken.FromObject(_userRequest.CloseAccount(new Guid(userId), account.Number));
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }
            
            return Ok();    
        }

    }
}