using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase
{
    public class UserModel
    {

        public enum Statuses
        {
            VERIFIED,
            UNVERIFIED
        }

        public UserModel(string username, string email, string password, string salt, Statuses status)
        {
            Username = username;
            Email = email;
            Password = password;
            Salt = salt;
            Status = status;
        }

        public UserModel(int id, string username, string email, string password, string salt, Statuses status)
        {
            ID = id;
            Username = username;
            Email = email;
            Password = password;
            Salt = salt;
            Status = status;
        }

        public int ID { get; set; }
        public string Username { get; set; }
        public string Email { get; set; }
        public string Password { get; set; }
        public string Salt { get; set; }
        public Statuses Status { get; set; }
    }
}
