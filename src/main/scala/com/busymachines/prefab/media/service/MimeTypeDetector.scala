package com.busymachines.prefab.media.service

import com.busymachines.commons.domain.Media
import com.busymachines.commons.domain.MimeType

trait MimeTypeDetector {
	def mimeTypeOf(name:Option[String],data:Option[Array[Byte]]):Option[MimeType]
}