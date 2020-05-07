using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using WorkWithDatabase;
using WebApplication.DTO;

namespace WebApplication.Controllers
{
    [Authorize]
    [ApiController]
    public class BankOperationsController : ControllerBase
    {

        private IDatabaseService _dbService;

        public BankOperationsController(IDatabaseService dbService)
        {
            _dbService = dbService;
        }

        

        [Route("api/makedepo")]
        [HttpPost]
        public IActionResult MakeDeposit([FromBody]Transaction transaction)
        {
            // положить на счет
            if (IsValidOperation(transaction.NumberTo, transaction.Amount, false))
            {
                var account = _dbService.GetAccountByNumber(transaction.NumberTo);
                account.Amount += transaction.Amount;
                _dbService.UpdateAccount(account);
                return Ok();
            }
            return Unauthorized(new { message = "Invalid bank account or amount" });
        }
        [Route("api/maketransfer")]
        [HttpPost]
        public IActionResult MakeTransfer([FromBody]Transaction transaction)
        {
            // положить на счет
            if (! IsValidOperation(transaction.NumberFrom, transaction.Amount, true))
            {
                return Unauthorized(new { message = "Invalid sender bank account or amount, or not enought money" });
            }
            if (IsValidOperation(transaction.NumberTo, transaction.Amount, false))
            {
                var accountF = _dbService.GetAccountByNumber(transaction.NumberFrom);
                accountF.Amount -= transaction.Amount;
                _dbService.UpdateAccount(accountF);
                var accountT = _dbService.GetAccountByNumber(transaction.NumberTo);
                accountT.Amount += transaction.Amount;
                _dbService.UpdateAccount(accountT);
                return Ok();
            }
            return Unauthorized(new { message = "Invalid beneficiary's bank account or amount" });
        }

        private bool IsValidOperation(long number, decimal amount, bool checkAccountAmount)
        {
            if (number < 4000000000 &&
                number > 4999999999 ||
                amount <= 0) 
                return false;
            
            var account = _dbService.GetAccountByNumber(number);
            if (account == null || account.Status == AccountModel.Statuses.CLOSED)
                return false;

            if (checkAccountAmount && account.Amount < amount) return false;
            return true;
        }

    }
}