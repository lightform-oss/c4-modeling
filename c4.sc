sealed trait Abstraction {
  def db: Boolean
  def external: Boolean
  def renderFun: String
  def renderParams: Seq[String]
  def render(children: Iterable[String]) = {
    val fun = {
      val ext = if(external) "_Ext" else ""
      val database = if(db) "Db" else ""
      s"$renderFun$database$ext"
    }
    if(children.isEmpty) {
      val params = (id +: s""""$name"""" +: renderParams).mkString(", ")
      s"""$fun($params)"""
    } else {
      val params = (id +: s""""$name"""" +: Nil).mkString(", ")
      s"""${fun}_Boundary($params) {${children.mkString("\n", "\n\t", "\n")}}"""
    }
  }

  def nameParts =
    getClass
      .getName
      .split('$')
      .flatMap(_.split('.'))
      .filter(_.nonEmpty)

  def id = nameParts.mkString(".")
  def name = {
    def addSpaceToCamelCase(str: Seq[Char]): Seq[Char] = str match {
      case c1 +: c2 +: cs
        if Character.isUpperCase(c1) && !Character.isUpperCase(c2) =>
        ' ' +: c1 +: c2 +: addSpaceToCamelCase(cs)
      case c +: cs => c +: addSpaceToCamelCase(cs)
      case cs if cs.isEmpty => Nil
    }

    addSpaceToCamelCase(nameParts.last.toList).mkString.trim
  }

  def -(description: String) = PartialRelation(this, description)

  def -(descriptionAndTechnology: (String, String)) =
    PartialRelation(this, descriptionAndTechnology._1, descriptionAndTechnology._2)
}

sealed trait ContextLevelAbstraction extends Abstraction

abstract class Person(description: String = "", val external: Boolean = false) extends ContextLevelAbstraction {
  val db = false
  val renderFun = "Person"
  val renderParams =
    if(description.isEmpty) Nil
    else Seq(s""""$description"""")
}

abstract class System(description: String = "", val db: Boolean = false, val external: Boolean = false)
  extends ContextLevelAbstraction { sys =>
  val renderFun = "System"
  val renderParams =
    if(description.isEmpty) Nil
    else Seq(s""""$description"""")

  abstract class Container(
      technology: String,
      description: String = "",
      val db: Boolean = false
    ) extends Abstraction { ctr =>
    val external = false
    val system = sys
    val renderFun = "Container"
    val renderParams = s""""$technology"""" +: (
      if(description.isEmpty) Nil
      else Seq(s""""$description"""")
      )

    abstract class Component(
      technology: String,
      description: String = "",
      val db: Boolean = false
    ) extends Abstraction {
      val external = false
      val container = ctr
      val renderFun = "Component"
      val renderParams = s""""$technology"""" +: (
        if(description.isEmpty) Nil
        else Seq(s""""$description"""")
        )
    }
  }
}

type Container = System#Container
type Component = Container#Component

case class PartialRelation(
  a: Abstraction,
  description: String,
  technology: String = ""
) {
  def -->(b: Abstraction) = Relation(a, b, description, technology)
}

case class Relation(
 a: Abstraction, b: Abstraction,
 description: String,
 technology: String = ""
) {
  def render = {
    val tech =
      if (technology.isEmpty) technology
      else s""", "$technology""""
    s"Rel(${a.id}, ${b.id}, ${description}$tech)"
  }
}

def diagram(relations: Seq[Relation]) = {
  val abstractions = relations.flatMap(r => Seq(r.a, r.b)).toSet
  val components = abstractions.collect { case c: Component => c }
  val containers =
    components.map(_.container) ++ abstractions.collect{ case c: Container => c }
  val systems =
    containers.map(_.system) ++ abstractions.collect{ case s: ContextLevelAbstraction => s }

  val containerMap = containers
    .map(ctr => ctr -> components.filter(_.container == ctr))
    .toMap

  val systemMap = systems.map {
    case p: Person => p -> Map.empty
    case s: System => s -> containerMap.filterKeys(_.system == s)
  }.toMap

  val pt1 = systemMap.map{ case (s, cs) =>
    s.render(
      cs.map { case (ctr: Container, cpts: Set[Component]) =>
        ctr.render(cpts.map(_.render(Nil)).toSeq)
      }
    )
  }

  val pt2 = relations.map(_.render)

  (pt1 ++ pt2).mkString("\n")
}

def render(relations: Relation*) = {
  println("@startuml")
  println("!includeurl https://raw.githubusercontent.com/RicardoNiepel/C4-PlantUML/master/C4_Component.puml")
  println(diagram(relations))
  println("@enduml")
}