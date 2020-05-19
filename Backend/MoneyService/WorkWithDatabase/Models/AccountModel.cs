using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase.Models
{
    public class AccountModel
    {
        /*
        public AccountModel(Guid userId, long number)
        {
            User_id = userId;
            Number = number;
            Amount = 0;
            Status = AccountStatus.OPENED;
        }

        public AccountModel(Guid userId, long number, decimal amount, AccountStatus status)
        {
            User_id = userId;
            Number = number;
            Amount = amount;
            Status = status;
        }
        */
        public AccountModel(Guid id, Guid user_id, long number, decimal amount, AccountStatus status)
        {
            Id = id;
            User_id = user_id;
            Number = number;
            Amount = amount;
            Status = status;
        }
       

        public Guid Id { get; set; }
        public Guid User_id { get; set; }
        public long Number { get; set; }
        public decimal Amount { get; set; }
        public AccountStatus Status { get; set; }

    }
}
