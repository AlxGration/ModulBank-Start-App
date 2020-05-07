using System;
using System.Collections.Generic;
using System.Text;

namespace WorkWithDatabase
{
    public class AccountModel
    {
        public int ID { get; set; }
        public int UserID { get; set; }
        public string Number { get; set; }
        public string Amount { get; set; }
        public int Status { get; set; }
    }
}
