using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Models
{
    public class Ttoken
    {
        public Ttoken(Guid userId, string token, string expiration)
        {
            UserId = userId;
            Token = token;
            Expiration = expiration;
        }
        public string Token { get; set; }
        public string Expiration { get; set; }
        public Guid UserId { get; set; }
    }
}
