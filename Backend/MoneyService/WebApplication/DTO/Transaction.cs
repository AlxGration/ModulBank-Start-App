using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.DTO
{
    public class Transaction
    {
        public long NumberFrom { get; set; }
        public long NumberTo { get; set; }
        public decimal Amount { get; set; }
        
    }
}
