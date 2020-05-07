using System;
using System.Collections.Generic;
using System.Text;
using System.Security.Cryptography;
using System.Security;

namespace Auth {
    public class Password {
        public Password(string password)
        {
            Salt = Guid.NewGuid().ToString().Substring(0, 8);
            using (MD5 md5 = MD5.Create()) {
                byte[] inputBytes = Encoding.ASCII.GetBytes(string.Concat(password, Salt));
                byte[] hashBytes = md5.ComputeHash(inputBytes);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hashBytes.Length; i++) {
                    sb.Append(hashBytes[i].ToString("X2"));
                }
                PasswordHash = sb.ToString();
            }
        }

        public string Salt { get; set; }
        public string PasswordHash { get; set; }

        public static bool CheckPassword(string password, string salt, string hash)
        {

            using (MD5 md5 = MD5.Create()) {
                byte[] inputBytes = Encoding.ASCII.GetBytes(string.Concat(password, salt));
                byte[] hashBytes = md5.ComputeHash(inputBytes);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < hashBytes.Length; i++) {
                    sb.Append(hashBytes[i].ToString("X2"));
                }
                string s = sb.ToString();
                return hash.Equals(s);
            }
        }
    }
}
