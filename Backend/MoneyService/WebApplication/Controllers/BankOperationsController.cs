using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using WebApplication.DTO;
using WebApplication.BusinessLogic;
using Newtonsoft.Json.Linq;
using WorkWithDatabase.Models;

namespace WebApplication.Controllers
{
    [Authorize]
    [ApiController]
    public class BankOperationsController : ControllerBase
    {

        private BankOperationRequestHandler _bankOperation;

        public BankOperationsController(BankOperationRequestHandler bankOperation)
        {
            _bankOperation = bankOperation;
        }

        [Route("api/makedepo")]
        [HttpPost]
        public IActionResult MakeDeposit([FromBody]Transaction transaction)
        {
            JObject json = (JObject)JToken.FromObject(_bankOperation.MakeDeposit(transaction));
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }               
            return Ok();
          
        }


        [Route("api/maketransfer")]
        [HttpPost]
        public IActionResult MakeTransfer([FromBody]Transaction transaction)
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID")){
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized(new MessageError("No user ID in header"));
            }

            JObject json = (JObject)JToken.FromObject(_bankOperation.MakeTransfer(new Guid(userId), transaction));
            if (json.ContainsKey("errorMessage"))
            {
                return BadRequest(json.ToObject<MessageError>());
            }
            return Ok();
        }


        [Route("api/transactions")]
        [HttpGet]
        public IActionResult GetOperationsHistory([FromBody]Account account)
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID"))
            {
                headers.TryGetValue("ID", out userId);
            }
            else
            {
                Unauthorized(new MessageError("No user ID in header"));
            }

            List<TransactionOperation> transactions = _bankOperation.GetTransactions(new Guid(userId), account.Number);
            return Ok(transactions);
        }
    }
}