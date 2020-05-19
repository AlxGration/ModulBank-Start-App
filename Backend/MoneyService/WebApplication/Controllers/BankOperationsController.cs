using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Authorization;
using WebApplication.DTO;
using WebApplication.BusinessLogic;

namespace WebApplication.Controllers
{
    [Authorize]
    [ApiController]
    public class BankOperationsController : ControllerBase
    {
        private const string V = "ID";
        private BankOperationRequestHandler _bankOperation;

        public BankOperationsController(BankOperationRequestHandler bankOperation)
        {
            _bankOperation = bankOperation;
        }

        [Route("api/makedepo")]
        [HttpPost]
        public IActionResult MakeDeposit([FromBody]Transaction transaction)
        {
            // положить на счет
            if (_bankOperation.MakeDeposit(transaction)) {                 
                return Ok();
            }
            return BadRequest( "Invalid bank account or amount" );
        }


        [Route("api/maketransfer")]
        [HttpPost]
        public IActionResult MakeTransfer([FromBody]Transaction transaction)
        {
            var headers = Request.Headers;
            Microsoft.Extensions.Primitives.StringValues userId;
            if (headers.ContainsKey("ID")){
                headers.TryGetValue(V, out userId);
            }
            else
            {
                Unauthorized();// no user ID in query
            }

            if (_bankOperation.MakeTransfer(new Guid(userId), transaction)){
                return Ok();
            }
            return BadRequest("Invalid bank accounts or amount" );
        }

    }
}