package busymachines.core

import busymachines.core.Anomaly.Parameters

/**
  *
  * @author Lorand Szakacs, lsz@lorandszakacs.com, lorand.szakacs@busymachines.com
  * @since 26 Dec 2017
  *
  */
trait ConflictAnomaly extends Anomaly with MeaningfulAnomalies.Conflict with Product with Serializable

object ConflictAnomaly extends AnomalyConstructors[ConflictAnomaly] {
  override def apply(id: AnomalyID): ConflictAnomaly = ConflictAnomalyImpl(id = id)

  override def apply(message: String): ConflictAnomaly = ConflictAnomalyImpl(message = message)

  override def apply(parameters: Parameters): ConflictAnomaly = ConflictAnomalyImpl(parameters = parameters)

  override def apply(id: AnomalyID, message: String): ConflictAnomaly =
    ConflictAnomalyImpl(id = id, message = message)

  override def apply(id: AnomalyID, parameters: Parameters): ConflictAnomaly =
    ConflictAnomalyImpl(id = id, parameters = parameters)

  override def apply(message: String, parameters: Parameters): ConflictAnomaly =
    ConflictAnomalyImpl(message = message, parameters = parameters)

  override def apply(id: AnomalyID, message: String, parameters: Parameters): ConflictAnomaly =
    ConflictAnomalyImpl(id = id, message = message, parameters = parameters)

  override def apply(a: Anomaly): ConflictAnomaly =
    ConflictAnomalyImpl(id = a.id, message = a.message, parameters = a.parameters)
}

private[core] final case class ConflictAnomalyImpl(
  override val id:         AnomalyID  = ConflictAnomalyID,
  override val message:    String     = MeaningfulAnomalies.`Conflict`,
  override val parameters: Parameters = Parameters.empty
) extends ConflictAnomaly with Product with Serializable {

  override def asThrowable: Throwable = ConflictFailureImpl(id, message, parameters)
}

//=============================================================================
//=============================================================================
//=============================================================================

abstract class ConflictFailure(
  override val message: String,
  causedBy:             Option[Throwable] = None,
) extends AnomalousFailure(message, causedBy) with ConflictAnomaly with Product with Serializable {
  override def id: AnomalyID = ConflictAnomalyID
}

object ConflictFailure extends FailureConstructors[ConflictFailure] {
  override def apply(causedBy: Throwable): ConflictFailure = ConflictFailureImpl(causedBy = Option(causedBy))

  override def apply(id: AnomalyID, message: String, causedBy: Throwable): ConflictFailure =
    ConflictFailureImpl(id = id, message = message, causedBy = Option(causedBy))

  override def apply(id: AnomalyID, parameters: Parameters, causedBy: Throwable): ConflictFailure =
    ConflictFailureImpl(id = id, parameters = parameters, causedBy = Option(causedBy))

  override def apply(message: String, parameters: Parameters, causedBy: Throwable): ConflictFailure =
    ConflictFailureImpl(message = message, parameters = parameters, causedBy = Option(causedBy))

  override def apply(id: AnomalyID, message: String, parameters: Parameters, causedBy: Throwable): ConflictFailure =
    ConflictFailureImpl(id = id, message = message, parameters = parameters, causedBy = Option(causedBy))

  override def apply(a: Anomaly, causedBy: Throwable): ConflictFailure =
    ConflictFailureImpl(id = a.id, message = a.message, parameters = a.parameters, causedBy = Option(causedBy))

  override def apply(id: AnomalyID): ConflictFailure =
    ConflictFailureImpl(id = id)

  override def apply(message: String): ConflictFailure =
    ConflictFailureImpl(message = message)

  override def apply(parameters: Parameters): ConflictFailure =
    ConflictFailureImpl(parameters = parameters)

  override def apply(id: AnomalyID, message: String): ConflictFailure =
    ConflictFailureImpl(id = id, message = message)

  override def apply(id: AnomalyID, parameters: Parameters): ConflictFailure =
    ConflictFailureImpl(id = id, parameters = parameters)

  override def apply(message: String, parameters: Parameters): ConflictFailure =
    ConflictFailureImpl(message = message, parameters = parameters)

  override def apply(id: AnomalyID, message: String, parameters: Parameters): ConflictFailure =
    ConflictFailureImpl(id = id, message = message, parameters = parameters)

  override def apply(a: Anomaly): ConflictFailure =
    ConflictFailureImpl(id = a.id, message = a.message, parameters = a.parameters)
}

private[core] final case class ConflictFailureImpl(
  override val id:         AnomalyID         = ConflictAnomalyID,
  override val message:    String            = MeaningfulAnomalies.`Conflict`,
  override val parameters: Parameters        = Parameters.empty,
  causedBy:                Option[Throwable] = None,
) extends ConflictFailure(message, causedBy) with Product with Serializable
