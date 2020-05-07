using System;
using Dapper;
using Npgsql;

namespace WorkWithDatabase.Postgre
{
    public class PostgreDB : IDatabaseService
    {
        private static string _dbConnInfo = $"server=localhost;port=5432;database=postgres;userid=postgres;password=admin;Pooling=false";
        private static bool flagNeedUpdate = false;

        public PostgreDB()
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
                              id SERIAL PRIMARY KEY,
                              username TEXT NOT NULL,
                              email TEXT NOT NULL,
                              password TEXT NOT null,
                              salt TEXT NOT NULL,
                              status INT NOT NULL
                            );
                            CREATE TABLE IF NOT EXISTS accounts (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL REFERENCES users (id) 
                              ON DELETE SET NULL,
                              number BIGINT NOT NULL UNIQUE,
                              amount MONEY NOT NULL,
                              status INT NOT NULL
                            );
");
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

                conn.Execute("INSERT INTO users (username, email, password, salt, status) VALUES(@username, @email, @password, @salt, @status);",
                    new
                    {
                        username = user.Username,
                        email = user.Email,
                        password = user.Password,
                        salt = user.Salt,
                        status = user.Status
                    }); ;
            }
        }

        public UserModel GetUser(int id)
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

                conn.Execute("INSERT INTO accounts (user_id, number, amount, status) VALUES(@user_id, @number, @amount, @status);",
                    new
                    {
                        user_id = account.User_id,
                        number = account.Number,
                        amount = account.Amount,
                        status = account.Status
                    }); ;
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
                         id = account.ID,
                         user_id = account.User_id,
                         number = account.Number,
                         amount = account.Amount,
                         status = account.Status
                     }); ;
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
                conn.Open();//id, user_id, number, amount, status

                var result = conn.QuerySingleOrDefault<AccountModel>("SELECT * FROM accounts WHERE number = @number;",
                    new
                    {
                        number,
                    });

                return result;
            }
        }

        /*
public void UpdateUser(UserModel user)
{
  using (var conn = CreateConnection())
  {
      conn.Open();

      conn.Execute("UPDATE users SET username = @username, password = @password, salt = @salt WHERE id = @id;",
          new
          {
              id = user.ID,
              username = user.Username,
              password = user.Password,
              salt = user.Salt,
          });
  }
}

public void DeleteUser(int id)
{
  using (var conn = CreateConnection())
  {
      conn.Open();

      conn.Execute("DELETE FROM users WHERE id = @id;",
          new
          {
              id = id,
          });
  }
}
*/
    }
}
