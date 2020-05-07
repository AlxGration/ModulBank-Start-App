using System;
using Dapper;
using Npgsql;

namespace PostgreDatabase
{
    public interface IDatabaseService
    {
        void AddUser(UserModel user);
        UserModel GetUser(int id);
        //void UpdateUser(UserModel user);
        //void DeleteUser(int id);
        void AddAccount(AccountModel account);
        void UpdateAccount(AccountModel account);

    }
    public class PostgreController : IDatabaseService
    {
        private static string _dbConnInfo = $"server=localhost;port=5432;database=postgres;userid=postgres;password=admin;Pooling=false";
        private static PostgreController _db;

        public PostgreController()
        {
            CreateTables();
        }

        private void CreateTables()
        {
            using (var conn = new NpgsqlConnection(_dbConnInfo))
            {
                conn.Open();

                conn.Execute(@"CREATE TABLE IF NOT EXISTS users (
                              id SERIAL PRIMARY KEY,
                              username text NOT NULL,
                              password text not null,
                              salt text NOT NULL
                            );
                            CREATE TABLE IF NOT EXISTS accounts (
                              id SERIAL PRIMARY KEY,
                              user_id int Not NULL,
                              number int not NULL,
                              amount MONEY NOT NULL,
                              status TEXT Not NULL,
                              FOREIGN KEY (user_id) REFERENCES users (ID) 
                              ON DELETE SET NULL
                            );");
            }
        }

        private void DropTables()
        {
            using (var conn = new NpgsqlConnection(_dbConnInfo))
            {
                conn.Open();

                conn.Execute(@"DROP TABLE accounts; DROP TABLE users");
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

                conn.Execute("INSERT INTO users (username, password, salt) VALUES(@username, @password, @salt);",
                    new
                    {
                        username = user.Username,
                        password = user.Password,
                        salt = user.Salt,
                    });
            }
        }

        public UserModel GetUser(int id)
        {
            using (var conn = CreateConnection())
            {
                conn.Open();

                var result = conn.QuerySingleOrDefault<UserModel>("SELECT id, username, password, salt FROM users WHERE id = @id;",
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
                        user_id = account.UserID,
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

                conn.Execute("UPDATE users SET user_id = @user_id, number = @number, amount = @amount, status = @status WHERE id = @id;",
                     new
                     {
                         id = account.ID,
                         user_id = account.UserID,
                         number = account.Number,
                         amount = account.Amount,
                         status = account.Status
                     }); ;
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
