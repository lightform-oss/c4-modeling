#!/usr/bin/env amm

import $file.^.c4
import c4._
import $file.`big-bank-plc`
import `big-bank-plc`._

render(
  PersonalBankingCustomer-"views balances using"-->InternetBankingSystem,
  InternetBankingSystem-"gets account info from"-->MainframeBankingSystem,
  InternetBankingSystem-"sends email using"-->EMailSystem,
  EMailSystem-"sends email to"-->PersonalBankingCustomer
)