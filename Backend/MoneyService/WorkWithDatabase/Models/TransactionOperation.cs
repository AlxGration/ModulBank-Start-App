using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase.Models
{
    public class TransactionOperation
    {
        public long Id { get; set; }
        public long Number { get; set; }
        public decimal Amount { get; set; }
        public DateTime Oper_date { get; set; }
    }
}
