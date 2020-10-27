#!/usr/bin/env amm

import $file.^.c4
import c4._
import $file.`big-bank-plc`
import `big-bank-plc`._

render(
  PersonalBankingCustomer-"visits website"-->InternetBankingSystem.WebApplication,
  PersonalBankingCustomer-"views account balances"-->InternetBankingSystem.SinglePageApplication,
  PersonalBankingCustomer-"views account balances"-->InternetBankingSystem.MobileApp,
  InternetBankingSystem.WebApplication-"delivers to browser"-->InternetBankingSystem.SinglePageApplication,
  InternetBankingSystem.MobileApp-"makes api calls"-->InternetBankingSystem.APIApplication,
  InternetBankingSystem.SinglePageApplication-"makes api calls"-->InternetBankingSystem.APIApplication,
  InternetBankingSystem.APIApplication-"r/w"-->InternetBankingSystem.Database,
  InternetBankingSystem.APIApplication-"sends email using"-->EMailSystem,
  InternetBankingSystem.APIApplication-"makes api calls to"-->MainframeBankingSystem
)