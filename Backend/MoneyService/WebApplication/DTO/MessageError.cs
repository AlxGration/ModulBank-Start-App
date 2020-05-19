using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.DTO
{
    public class MessageError
    {
        public MessageError(string message)
        {
            errorMessage = message;
        }
        public string errorMessage { get; set; }
    }
}
