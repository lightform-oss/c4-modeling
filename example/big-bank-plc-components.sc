#!/usr/bin/env amm

import $file.^.c4
import c4._
import $file.`big-bank-plc`
import `big-bank-plc`._

  render(
  InternetBankingSystem.SinglePageApplication-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.SignInComponent,
  InternetBankingSystem.SinglePageApplication-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.PasswordResetController,
  InternetBankingSystem.SinglePageApplication-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.AccountsSummaryController,

  InternetBankingSystem.MobileApp-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.SignInComponent,
  InternetBankingSystem.MobileApp-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.PasswordResetController,
  InternetBankingSystem.MobileApp-("makes api calls", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.AccountsSummaryController,

  InternetBankingSystem.APIApplication.SecurityComponent-("r/w", "JDBC")-->InternetBankingSystem.Database,

  InternetBankingSystem.APIApplication.EMailComponent-"sends email using"-->EMailSystem,

  InternetBankingSystem.APIApplication.MainframeBankingSystemFacade-("makes api calls to", "XML/HTTPS")-->MainframeBankingSystem,

  InternetBankingSystem.APIApplication.SignInComponent-"uses"-->InternetBankingSystem.APIApplication.SecurityComponent,

  InternetBankingSystem.APIApplication.PasswordResetController-"uses"-->InternetBankingSystem.APIApplication.SecurityComponent,
  InternetBankingSystem.APIApplication.PasswordResetController-"uses"-->InternetBankingSystem.APIApplication.EMailComponent,

  InternetBankingSystem.APIApplication.AccountsSummaryController-("uses", "JSON/HTTPS")-->InternetBankingSystem.APIApplication.MainframeBankingSystemFacade,
)