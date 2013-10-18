package com.kentivo.mdm.domain

import com.busymachines.commons.domain.CommonJsonFormats
import com.busymachines.commons.domain.UnitOfMeasure

object DomainJsonFormats extends DomainJsonFormats

trait DomainJsonFormats extends CommonJsonFormats {

  implicit val addressKindFormat = enumFormat(AddressKind)
  implicit val addressFormat = jsonFormat6(Address)
  implicit val phoneNumberKindFormat = enumFormat(PhoneNumberKind)
  implicit val phoneNumberFormat = jsonFormat2(PhoneNumber)
  implicit val emailKindFormat = enumFormat(EmailKind)
  implicit val emailFormat = jsonFormat3(Email)
  implicit val userFormat = jsonFormat10(User)
  implicit val userRoleFormat = jsonFormat3(UserRole)
  implicit val relatedPartyFormat = jsonFormat3(RelatedParty)
  implicit val partyFormat = jsonFormat9(Party)

  implicit val unitOfMeasureFormat = stringJsonFormat("UnitOfMeasure", s => UnitOfMeasure(Nil))
  implicit val mutationFormat = jsonFormat6(Mutation)

  implicit val itemRule = jsonFormat1(ItemRule)
  implicit val propertyRule = jsonFormat2(PropertyRule)
  implicit val rangeCheck = jsonFormat3(RangeCheck)
  implicit val scheduleRepeat = enumFormat(ScheduleRepeat)
  implicit val schedule = jsonFormat2(Schedule)

  implicit val propertyType = enumFormat(PropertyType)
  implicit val propertyScope = enumFormat(PropertyScope)
  implicit val propertyValue = jsonFormat5(PropertyValue)
  implicit val propertyGroup = jsonFormat3(PropertyGroup)
  implicit val property = jsonFormat16(Property)
  implicit val itemDefinitionFormat = jsonFormat8(ItemMetaData)
  implicit val itemFormat = jsonFormat4(Item)

  implicit val validator = jsonFormat1(Validator)
  implicit val streetMatcher = jsonFormat1(StreetMatcher)
  implicit val houseNumberMatcher = jsonFormat1(HouseNumberMatcher)
  implicit val deltaMatcher = jsonFormat3(DeltaMatcher)
  implicit val sourceValidation = jsonFormat5(SourceValidation)
  implicit val matcher = jsonFormat2(Matcher)
  implicit val extractStreetHouseNumber = jsonFormat5(ExtractStreetHouseNumber)
  implicit val defaultMapping = jsonFormat3(DefaultMapping)
  implicit val mappingFormat = jsonFormat5(Mapping)
  implicit val sourceFormat = jsonFormat7(Source)
}