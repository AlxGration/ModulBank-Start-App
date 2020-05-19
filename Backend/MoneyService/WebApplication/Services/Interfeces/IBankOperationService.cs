using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Services.Interfeces
{
    public interface IBankOperationService
    {
        public void MakeDeposit(long number, decimal amount);
        public bool IsAccountOpen(long number);
        public bool AccountHaveAmount(long number, decimal amount);
        public void MakeWithdrawal(long number, decimal amount);
        public bool HaveUserAccount(Guid userId, long number);
    }
}
