package com.allantl.value.entry

import com.allantl.value.entry.ValueEntry.{LowerCase, SnakeCase, WithPrefix}
import org.scalatest.{MustMatchers, WordSpec}

class ValueEntrySpec extends WordSpec with MustMatchers {

  "ValueEntry" should {
    "test uppercase" in {

      sealed trait Fruit extends ValueEntry with ValueEntry.UpperCase
      case object Apple extends Fruit

      Apple.value mustBe "APPLE"
    }

    "test lowercase" in {
      sealed trait Fruit extends ValueEntry with ValueEntry.LowerCase
      case object Apple extends Fruit

      Apple.value mustBe "apple"
    }

    "test uncapitalized" in {
      sealed trait Fruit extends ValueEntry with ValueEntry.Uncapitalized
      case object Apple extends Fruit

      Apple.value mustBe "apple"
    }

    "test snakecase" in {
      sealed trait Fruit extends ValueEntry with ValueEntry.SnakeCase
      case object RedApple extends Fruit

      RedApple.value mustBe "Red_Apple"
    }

    "test prefix" in {
      trait PrefixPlus extends WithPrefix {
        override val prefix: String = "+"
      }

      sealed trait Fruit extends ValueEntry with PrefixPlus
      case object Apple extends Fruit

      Apple.value mustBe "+Apple"
    }

    "test combinations" in {
      sealed trait Fruit extends ValueEntry with SnakeCase with LowerCase
      case object RedApple extends Fruit

      RedApple.value mustBe "red_apple"
    }
  }
}
