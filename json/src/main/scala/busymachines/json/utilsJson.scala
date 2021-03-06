/**
  * Copyright (c) 2017-2018 BusyMachines
  *
  * See company homepage at: https://www.busymachines.com/
  *
  * Licensed under the Apache License, Version 2.0 (the "License");
  * you may not use this file except in compliance with the License.
  * You may obtain a copy of the License at
  *
  *     http://www.apache.org/licenses/LICENSE-2.0
  *
  * Unless required by applicable law or agreed to in writing, software
  * distributed under the License is distributed on an "AS IS" BASIS,
  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  * See the License for the specific language governing permissions and
  * limitations under the License.
  */
package busymachines.json

import busymachines.core._
import busymachines.effects.sync._

import io.circe.parser._
import io.circe.Printer

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 10 Aug 2017
  *
  */
object JsonDecoding {

  def decodeAs[A](json: Json)(implicit decoder: Decoder[A]): Result[A] = {
    val r: io.circe.Decoder.Result[A] = decoder.decodeJson(json)
    r.left.map(df => JsonDecodingFailure(df.getMessage))
  }

  def decodeAs[A](json: String)(implicit decoder: Decoder[A]): Result[A] = {
    val je = JsonParsing.parseString(json)
    je.flatMap(json => this.decodeAs(json))
  }

  def unsafeDecodeAs[A](json: Json)(implicit decoder: Decoder[A]): A = {
    this.decodeAs[A](json)(decoder).unsafeGet
  }

  def unsafeDecodeAs[A](json: String)(implicit decoder: Decoder[A]): A = {
    JsonDecoding.decodeAs(json).unsafeGet
  }
}

final case class JsonDecodingFailure(msg: String) extends InvalidInputFailure(msg) {
  override def id: AnomalyID = JsonAnomalyIDs.JsonDecodingAnomalyID
}

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 10 Aug 2017
  */
object JsonParsing {

  def parseString(input: String): Result[Json] = {
    parse(input).left.map(pf => JsonParsingFailure(pf.message))
  }

  def unsafeParseString(input: String): Json = {
    JsonParsing.parseString(input).unsafeGet
  }

}

object PrettyJson {
  val noSpacesNoNulls: Printer = Printer.noSpaces.copy(dropNullValues = true)
  val spaces2NoNulls:  Printer = Printer.spaces2.copy(dropNullValues  = true)
  val spaces4NoNulls:  Printer = Printer.spaces4.copy(dropNullValues  = true)

  val noSpaces: Printer = Printer.noSpaces
  val spaces2:  Printer = Printer.spaces2
  val spaces4:  Printer = Printer.spaces4

}

final case class JsonParsingFailure(msg: String) extends InvalidInputFailure(msg) {
  override def id: AnomalyID = JsonAnomalyIDs.JsonParsingAnomalyID
}

/**
  *
  */
object JsonAnomalyIDs {

  case object JsonParsingAnomalyID extends AnomalyID {
    override def name: String = "json_01"
  }

  case object JsonDecodingAnomalyID extends AnomalyID {
    override def name: String = "json_02"
  }

}
