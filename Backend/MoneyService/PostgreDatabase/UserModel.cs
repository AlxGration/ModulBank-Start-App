﻿using System;
using System.Collections.Generic;
using System.Text;

namespace PostgreDatabase
{
    public class UserModel
    {
        public int ID { get; set; }
        public string Username { get; set; }
        public string Password { get; set; }
        public string Salt { get; set; }
    }
}
