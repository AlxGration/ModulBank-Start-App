﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebApplication.Models
{
    public class AuthOptions
    {
  
        public string SecureKey { get; set; }

        public string Issuer { get; set; }

        public string Audience { get; set; }

        public int ExpiresInHours { get; set; }

    }
}
