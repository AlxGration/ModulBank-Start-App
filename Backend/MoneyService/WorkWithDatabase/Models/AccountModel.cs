using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase.Models
{
    public class AccountModel
    {
      
        /*
        public AccountModel(int id, int userId, long number, decimal amount, Statuses status)
        {
            ID = id;
            UserID = userId;
            Number = number;
            Amount = amount;
            Status = status;
        }
       */

        public int ID { get; set; }
        public Guid User_id { get; set; }
        public long Number { get; set; }
        public decimal Amount { get; set; }
        public AccountStatus Status { get; set; }

    }
}
