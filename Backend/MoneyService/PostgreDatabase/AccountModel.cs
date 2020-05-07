using System;
using System.Collections.Generic;
using System.Text;

namespace PostgreDatabase
{
    public class AccountModel
    {
        public int ID { get; set; }
        public int UserID { get; set; }
        public string Number { get; set; }
        public string Amount { get; set; }
        public string Status { get; set; }
    }
}
