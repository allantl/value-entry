package com.allantl.value.entry

trait ValueEntry {

  private[this] lazy val valueEntryName: String = toString

  def value: String = valueEntryName
}

object ValueEntry {

  trait LowerCase extends ValueEntry {
    private[this] lazy val valueEntryName: String = super.value.toLowerCase

    override def value: String = valueEntryName
  }

  trait UpperCase extends ValueEntry {
    private[this] lazy val valueEntryName: String = super.value.toUpperCase

    override def value: String = valueEntryName
  }

  trait Uncapitalized extends ValueEntry {
    private[this] lazy val valueEntryName: String = uncapitalized(super.value)

    override def value: String = valueEntryName

    private def uncapitalized(s: String): String =
      s.head.toLower + s.tail
  }

  /**
    * Transforms string to snake case.
    * Example: HelloWorld -> Hello_World
    *
    * If you want to make all words lower case, extends `Lowercase`
    */
  trait SnakeCase extends ValueEntry {
    private[this] lazy val valueEntryName: String =
      super.value
        .replaceAll("([A-Z]+)([A-Z][a-z])", "$1_$2")
        .replaceAll("([a-z\\d])([A-Z])", "$1_$2")

    override def value: String = valueEntryName
  }

  trait WithPrefix extends ValueEntry {
    private[this] lazy val valueEntryName: String = prefix + super.value

    def prefix: String

    override def value: String = valueEntryName
  }
}
