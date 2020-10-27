import $file.^.`c4`
import `c4`._

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
