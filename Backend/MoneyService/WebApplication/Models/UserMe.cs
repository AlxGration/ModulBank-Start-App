using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication.Models;
using WorkWithDatabase.Models;

namespace WebApplication.DTO
{
    public class UserMe
    {
        public UserMe(string username, string photo, UserStatus status)
        {
            Username = username;
            Photo = photo;
            Status = status;
        }
        public string Username { get; set; }
        public string Photo { get; set; }
        public UserStatus Status { get; set; }
    }
}
