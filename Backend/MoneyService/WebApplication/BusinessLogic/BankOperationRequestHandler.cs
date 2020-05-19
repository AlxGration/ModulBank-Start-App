using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using WebApplication.DTO;
using WebApplication.Services.Interfeces;

namespace WebApplication.BusinessLogic
{
    public class BankOperationRequestHandler
    {
        private IBankOperationService _bankService;
        public BankOperationRequestHandler(IBankOperationService bankService)
        {
            _bankService = bankService;
        }

        public bool MakeDeposit(Transaction transaction)
        {
            if (IsValidOperation(transaction.NumberTo, transaction.Amount, false))
            {
                _bankService.MakeDeposit(transaction.NumberTo, transaction.Amount);
                return true;
            }
            return false;
        }

        public bool MakeTransfer(Guid userId, Transaction transaction)
        {
            // проверка, что счет, с которого будет снятие - счет создателя запроса
            if ( ! _bankService.HaveUserAccount(userId, transaction.NumberFrom)) throw new AccessViolationException("No accsess to this account");
            
            if (!IsValidOperation(transaction.NumberFrom, transaction.Amount, true))
            {
                return false;
            }
            if (IsValidOperation(transaction.NumberTo, transaction.Amount, false))
            {
                _bankService.MakeWithdrawal(transaction.NumberFrom, transaction.Amount);
                _bankService.MakeDeposit(transaction.NumberTo, transaction.Amount);

                return true;
            }
            return false;
        }

        private bool IsValidOperation(long number, decimal amount, bool checkAccountAmount)
        {
            if (number < 4000000000 &&
                number > 4999999999 ||
                amount <= 0)
                return false;

            if (! _bankService.IsAccountOpen(number)) return false;
            

            if (checkAccountAmount && !_bankService.AccountHaveAmount(number, amount)) return false;
            return true;
        }
    }
}
