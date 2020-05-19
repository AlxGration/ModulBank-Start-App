using System;
using System.Collections.Generic;
using System.Text;
using Microsoft.Extensions.Options;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using WebApplication.Models;
using WebApplication.DTO;
using WebApplication.Services.Interfeces;
using WorkWithDatabase.Services.Inerfaces;
using WorkWithDatabase.Models;

namespace WebApplication.Services
{
    public class AuthService : IAuthService {

        private IDbService _dbService;
        private AuthOptions _authOptions;

        public AuthService(IDbService dbService, IOptions<AuthOptions> authOptionsAccessor)
        {
            _dbService = dbService;
            _authOptions = authOptionsAccessor.Value;
        }


        public bool IsEmailRegistered(string email)
        {
            // проверка на занятый username
            UserModel user = _dbService.GetUserByEmail(email);
           
            return user != null;
        }

        public bool IsValidUser(string email, string password)
        {
            UserModel user = _dbService.GetUserByEmail(email);
            if (user == null) return false;
            return Password.CheckPassword(password, user.Salt, user.Password);
        }

        public void RegisterUser(Guid id, string username, string email, string password)
        {
            User usr = new User(email, password);// хеширование 
            // и запись в БД
            _dbService.AddUser(new UserModel(id, username, usr.Username, usr.Password, usr.Salt, UserStatus.UNVERIFIED));
        }

        public Ttoken Token(UserCredentials user)
        {
            var authClaims = new[] {
                    new Claim(JwtRegisteredClaimNames.Sub, user.Email),
                    new Claim(JwtRegisteredClaimNames.Jti, Guid.NewGuid().ToString())
                };

            var token = new JwtSecurityToken(
                issuer: _authOptions.Issuer,
                audience: _authOptions.Audience,
                expires: DateTime.Now.AddHours(_authOptions.ExpiresInHours),
                claims: authClaims,
                signingCredentials: new SigningCredentials(
                    new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_authOptions.SecureKey)),
                    SecurityAlgorithms.HmacSha256Signature
                )
            );

            return new Ttoken(
                _dbService.GetUserByEmail(user.Email).Id,
                new JwtSecurityTokenHandler().WriteToken(token),
                token.ValidTo.ToString()
            );
        }

        public bool IsUserExist(Guid id)
        {
            return _dbService.GetUser(id) != null;
        }
    }
}
