using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.DTO
{
    public class Account
    {
        public int ID { get; set; }
        public Guid User_id { get; set; }
        public long Number { get; set; }
        public decimal Amount { get; set; }
    }
}
