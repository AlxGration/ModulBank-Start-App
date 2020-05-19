using System;
using System.Collections.Generic;
using System.Linq;
using Dapper;
using Npgsql;
using WorkWithDatabase.Models;
using WorkWithDatabase.Services.Inerfaces;

namespace WorkWithDatabase.Services
{
    public class PostgreDbService : IDbService
    {
        private static string _dbConnInfo = $"server=localhost;port=5432;database=postgres;userid=postgres;password=admin;Pooling=false";
        private static bool flagNeedUpdate = false;

        public PostgreDbService()
        {
            if (flagNeedUpdate) { 
                DropTables();
                flagNeedUpdate = false;
            }
            CreateTables();
        }

        private void CreateTables()
        {
            using (var conn = new NpgsqlConnection(_dbConnInfo))
            {
                conn.Open();

                conn.Execute(@"CREATE TABLE IF NOT EXISTS users (
                              id uuid PRIMARY KEY UNIQUE,
                              username TEXT NOT NULL,
                              email TEXT NOT NULL,
                              password TEXT NOT null,
                              salt TEXT NOT NULL,
                              status INT NOT NULL
                            );
                            CREATE TABLE IF NOT EXISTS accounts (
                              id uuid PRIMARY KEY UNIQUE,
                              user_id uuid NOT NULL REFERENCES users (id) 
                              ON DELETE SET NULL,
                              number BIGINT NOT NULL UNIQUE,
                              amount MONEY NOT NULL,
                              status INT NOT NULL
                            );"
                );
            }
        }

        private void DropTables()
        {
            using (var conn = new NpgsqlConnection(_dbConnInfo))
            {
                conn.Open();

                conn.Execute(@"DROP TABLE IF EXISTS accounts; 
                               DROP TABLE IF EXISTS users;"
                );
            }
        }

        private NpgsqlConnection CreateConnection()
        {
            var connection = new NpgsqlConnection(_dbConnInfo);

            return connection;
        }

        public void AddUser(UserModel user)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                conn.Execute("INSERT INTO users (id, username, email, password, salt, status) VALUES(@id, @username, @email, @password, @salt, @status);",
                    new
                    {
                        id = user.Id,
                        username = user.Username,
                        email = user.Email,
                        password = user.Password,
                        salt = user.Salt,
                        status = user.Status
                    }); 
            }
        }

        public UserModel GetUser(Guid id)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.QuerySingleOrDefault<UserModel>("SELECT id, username, email, password, salt, status FROM users WHERE id = @id;",
                    new
                    {
                        id,
                    });

                return result;
            }
        }

        public void AddAccount(AccountModel account)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                conn.Execute("INSERT INTO accounts (id, user_id, number, amount, status) VALUES(@id, @user_id, @number, @amount, @status);",
                    new
                    {
                        id = account.Id,
                        user_id = account.User_id,
                        number = account.Number,
                        amount = account.Amount,
                        status = account.Status
                    }); 
            }
        }

        public void UpdateAccount(AccountModel account)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                conn.Execute("UPDATE accounts SET user_id = @user_id, number = @number, amount = @amount, status = @status WHERE id = @id;",
                     new
                     {
                         id = account.Id,
                         user_id = account.User_id,
                         number = account.Number,
                         amount = account.Amount,
                         status = account.Status
                     });
            }
        }

        public UserModel GetUserByEmail(string email)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.QuerySingleOrDefault<UserModel>("SELECT id, username, email, password, salt, status FROM users WHERE email = @email;",
                    new
                    {
                        email,
                    });

                return result;
            }
        }

        public AccountModel GetAccountByNumber(long number)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.QuerySingleOrDefault<AccountModel>("SELECT id, user_id, number, amount, status FROM accounts WHERE number = @number;",
                    new
                    {
                        number,
                    });

                return result;
            }
        }

        
        public void UpdateUser(UserModel user)
        {
          using (var conn = CreateConnection())
          {
              conn.Open();

              conn.Execute("UPDATE users SET username = @username, email = @email, password = @password, salt = @salt, status = status WHERE id = @id;",
                  new
                  {
                      id = user.Id,
                      username = user.Username,
                      email = user.Email,
                      password = user.Password,
                      salt = user.Salt,
                      status = user.Status
                  });
          }
    }

        public List<AccountModel> GetAccountsByUserId(Guid userId)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.Query<AccountModel>("SELECT id, user_id, number, amount, status FROM accounts WHERE user_id = @id AND status = 0;",
                    new
                    {
                        id = userId,
                    });

                return result.ToList();
            }
        }

        public AccountModel GetAccount(Guid id)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.QuerySingleOrDefault<AccountModel>("SELECT id, user_id, number, amount, status FROM accounts WHERE id = @id;",
                    new
                    {
                        id,
                    });

                return result;
            }
        }
    }
}
