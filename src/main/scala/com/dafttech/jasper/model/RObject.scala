package com.dafttech.jasper.model

import com.dafttech.jasper.render.{ObjectRenderer, Tesselator, VertexBufferLocation}

import scala.collection.mutable

/**
  * Represents a abstract model as well as its representation in the Scene VertexBuffer
  */
class RObject {
  val objectRenderer: ObjectRenderer = ObjectRenderer.Triangles
  val tesselator: Tesselator = Tesselator.Triangles

  val models = new mutable.MutableList[Model]()

  var vbLoc: VertexBufferLocation = null

  def addModel(model: Model) = models += model
}