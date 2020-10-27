## Model system architecure in code

```scala
object PersonalBankingCustomer extends Person("personal customer of the bank")

object EMailSystem extends System("microsoft exchange", external = true)
object MainframeBankingSystem extends System("old", external = true)
object InternetBankingSystem extends System {

  object WebApplication extends Container(
    "Java and Spring MVC", "delivers the static content"
  )

  object SinglePageApplication extends Container("Javascript and Angular")
  object MobileApp extends Container("Xamarin", "subset of web functionality")
  object Database extends Container("Oracle", "user info", true)

  object APIApplication extends Container("Java and Spring MVC") {

    object PasswordResetController extends Component("Spring MVC Rest controller")

    object SignInComponent extends Component(
      "Spring MVC Rest controller", "lets users sign in"
    )

    object AccountsSummaryController extends Component(
      "Spring MVC Rest controller", 
      "gives customers summaries of their accounts"
    )

    object SecurityComponent extends Component(
      "Spring Bean", "functions related to sign in, password change, etc"
    )

    object EMailComponent extends Component(
      "Spring Bean", "sends emails to users"
    )

    object MainframeBankingSystemFacade extends Component(
      "Spring Bean", "facade to mainframe bank system"
    )
  }
}
```

## Create diagrams by describing relationships in the model

```scala
render(
  PersonalBankingCustomer-
    "visits website"-->InternetBankingSystem.WebApplication,

  PersonalBankingCustomer-
    "views account balances"-->InternetBankingSystem.SinglePageApplication,

  PersonalBankingCustomer-
    "views account balances"-->InternetBankingSystem.MobileApp,

  InternetBankingSystem.WebApplication-
    "delivers to browser"-->InternetBankingSystem.SinglePageApplication,

  InternetBankingSystem.MobileApp-
    "makes api calls"-->InternetBankingSystem.APIApplication,

  InternetBankingSystem.SinglePageApplication-
    "makes api calls"-->InternetBankingSystem.APIApplication,

  InternetBankingSystem.APIApplication-
    "r/w"-->InternetBankingSystem.Database,

  InternetBankingSystem.APIApplication-
    "sends email using"-->EMailSystem,

  InternetBankingSystem.APIApplication-
    "makes api calls to"-->MainframeBankingSystem
)
```

## Render diagrams using [plantUML](https://plantuml.com)

### [From the command line](https://plantuml.com)
```sh
$ ./big-bank-plc-containers.sc | java -jar plantuml.jar -pipe > big-bank-plc-containers.png
```

### [Or from many other applications](https://plantuml.com/running)

```sh
$ ./big-bank-plc-containers.sc > big-bank-plc-containers.puml
```

![](http://www.plantuml.com/plantuml/svg/nLOzRnin39thNy5cQe3sl7JgbCJ9aA9kZGJzs0AUZhQ5IDH1ekm4HVzxgTYN3wE32ml8PEhnnFT87kaxIO9HidUZjvQDontbw60fqgTFut74TREmiinjJXHDO26MnWG_lh86OnU-Mkh9ZISVFymSidoVVXbxJ49HePj9y7tWSgCl13EAAV0xz3wm5Mhcrb7JsaNZBUVK8jzkhDwPPlCikZD5BIycEKdm5D_3qU21hGi6tv4w-y5fjY24ESYIe4G_EXvTtwisVtDn9_l9k9YYTPjB6lq2oX4svqBdhOaXXRa0tPabyeAUECv2vWxZ_Nv4bomKcMIRr2FbW0_P3kJ7y7iq2InecU815CrFQa_xtbc3OWChemBm32bfViOL0d87rtqi6gO_9WNkoDaLnVHGu2Jwk87jAw9bU5D5tkdiygKy1VQHzvp_l3r4mJaAjfX8YGQpa7wBQDo3LIO3BCz3jQIdeTKxcgL6tzYWXyBr2pr6oyLCkKqavVrUKmlppARK0vsL-sf2hjLqDCC5lMp21eVY-5SpaecsbuUMdF8YEuogwCz_3zyKjQeHFUqEnQDZTohK5rodP5Va3jmp5KTgPPELLDgalrH3NFsMhYoj4w0n8RD0YmxPKAgYjS8SxA6lJglg5_PfIGPeOrWdYeFg6dMfk5eztb82x2qOT6weRUs2lBxY_UDN-TU8u_Kh2DlzTad4NGBo2a5E6hc-faDM-audGSBnw4Gbby_6lm00)

# Install

1. Install [ammonite](https://ammonite.io/#ScalaScripts)
2. Download the modeling script `wget https://raw.githubusercontent.com/kag0/c4-modeling/main/c4.sc`
3. Reference from your modeling and diagram code
    ```scala
    import $file.^.c4
    import c4._
    ```

# Example

See example code [here](example)

![](http://www.plantuml.com/plantuml/svg/xLZFJnix43u__efvd1uIRtFfgIUIGDLK1292gzxGh7UoiV2FbJqceAh_U-qicuHLXK345OVUx1dix_iydyTiJZoZuw3LmR_I21Kg2ax1ahdn7mS3XwkYbhmCPV3aX3LCXWjXzU1A2dILlP3Ka1gCF_m_KsZuw_HyeD4pkHYw6LlTM9DMD0bW_X0J-kRidlz3hQsHJCL2AYfAMHTQck1BDBVjg56YE9kYLEsYOpWyWpI5TduO0reAPxrTCD2zMAAfwV3erHXnJxDmg6aKmzBK6xXD0XupEyXMLLkeaGscGlVmCho9ONA6k0_NnVjeH_3ZO6mDHpRaze5L36UJOTCeAP2bDH4w1c0dqa9sbTiVKZ53xrVMLLVaYPCcPvKY5nbq6LYdO9jBXprlN3gFwRTnIZE8RVReu9ySH7_lYKyeiA9dd07jN-nm7X6QD5si4y0Mz6PjuWL-lJ2NZhcipSHiTawyOWGc1ZQnvmyuPHMnXtJvFVYqMffST8T2s63OpuFMqUDF7DBbu37vSe_4R2tlo8C8dgrUwrZl8MFCBe2N91tWu_xPIa4YECaFkyNeOhrgzAmJZ4WxU72aaAa2jbqXZg7fBaxR_uw1M6IIq7RZBV-k8J_BtfEfF916gJomRNqK2VwC5BkcTbg-XUGfCfReAO9rmmHywL2ezIX1WZGB6s6te6-1dDeoheb74tTlnn0d2Uix9b-roaFfYPF1LbH2LqLKiTvx8pAFGqKphEbfGs_Za1Amaqcqlk0TUk5amu2cWg6fWqAthiWLgGnaCdOpZRVa0Hi90fNoITtyycBm-VfwDe-Aykl9yXo-0rrPsdX-PPjxcTzr-JMyPwVbrvBVNHaUzBsyOcwmIiHFH-EMSiO7_FNVO3lFEGGVWNeKC__d_QOFkfuxW6rwiAVdkVpHxnjPhHSy-LP1zgxnLyaBxbPFHlRMzwTkO9BLxwidPAhqwyul)