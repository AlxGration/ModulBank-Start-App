using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase.Models
{
    public class UserModel
    {
        public UserModel(string username, string email, string password, string salt, UserStatus status)
        {
            Username = username;
            Email = email;
            Password = password;
            Salt = salt;
            Status = status;
        }

        public UserModel(Guid id, string username, string email, string password, string salt, UserStatus status, string photo)
        {
            Id = id;
            Username = username;
            Email = email;
            Password = password;
            Salt = salt;
            Status = status;
            Photo = photo;
        }

        public Guid Id { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string Salt { get; set; }
        public UserStatus Status { get; set; }
        public string Photo { get; set; }
    }
}
