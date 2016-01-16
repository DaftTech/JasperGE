package com.dafttech.jasper

import scala.collection.mutable

/**
  * Represents a abstract model as well as its representation in the Scene VertexBuffer
  */
class Object {
  val objectRenderer: ObjectRenderer = ObjectRenderer.Triangles
  val tesselator: Tesselator = Tesselator.Triangles

  val models = new mutable.MutableList[Model]()

  var vbLoc: VertexBufferLocation = null

  def addModel(model: Model) = models += model
}
