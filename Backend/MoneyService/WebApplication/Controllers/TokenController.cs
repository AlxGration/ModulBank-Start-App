using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using Microsoft.Extensions.Options;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Auth;
using WebApplication.DTO;


namespace WebApplication.Controllers
{
    [ApiController]
    [Route("api/login")]
    public class TokenController : ControllerBase
    {
        private IUserService _service;
        private AuthOptions _authOptions;

        public TokenController(IUserService service, IOptions<AuthOptions> authOptionsAccessor)
        {
            _service = service;
            _authOptions = authOptionsAccessor.Value;
        }

        [HttpPost]
        public IActionResult Get([FromBody] UserCredentials user)
        {
            if (user.Password == null || user.Email == null) return Unauthorized(new { message="Some arguments are missed" });

            if (_service.IsValidUser(user.Email, user.Password)) { // если пользователь есть в БД, то даем ему токен
                var authClaims = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, user.Email),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
                };

                var token = new JwtSecurityToken(
                    issuer: _authOptions.Issuer,
                    audience: _authOptions.Audience,
                    expires: DateTime.Now.AddMinutes(_authOptions.ExpiresInMenutes),
                    claims: authClaims,
                    signingCredentials: new SigningCredentials(
                        new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_authOptions.SecureKey)),
                        SecurityAlgorithms.HmacSha256Signature
                    )
                );

                return Ok(new {
                    token = new JwtSecurityTokenHandler().WriteToken(token),
                    expiration = token.ValidTo
                });
            }
            return Unauthorized(new { message = "Email or password is incorrect" });
        }
        
    }
}
