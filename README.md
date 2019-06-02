# Value Entry

[![Build Status](https://travis-ci.org/allantl/value-entry.svg?branch=master)](https://travis-ci.org/allantl/value-entry)

Value entry for Scala/ScalaJS

```scala
libraryDependencies ++= Seq(
    "com.github.allantl" %%% "value-entry" % "0.1.0"
)
```

## Quick Start

```scala
import com.allantl.value.entry.ValueEntry

sealed trait Fruit extends ValueEntry
case object Apple extends Fruit

Apple.value // Apple: String
```

#### Uncapitalized
```scala
import com.allantl.value.entry.ValueEntry

sealed trait Fruit extends ValueEntry.Uncapitalized
case object GreenApple extends Fruit

GreenApple.value // greenApple: String
```

#### SnakeCase
```scala
import com.allantl.value.entry.ValueEntry

sealed trait Fruit extends ValueEntry.SnakeCase with ValueEntry.LowerCase
case object RedApple extends Fruit

RedApple.value // red_apple: String
```
