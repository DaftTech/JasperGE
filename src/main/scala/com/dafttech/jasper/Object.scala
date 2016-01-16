package com.dafttech.jasper

import scala.collection.mutable

class Object {
  val objectRenderer: ObjectRenderer = ObjectRenderer.Triangles
  val tesselator: Tesselator = Tesselator.Triangles

  val models = new mutable.MutableList[Model]()

  var vbLoc: VertexBufferLocation = null

  def addModel(model: Model) = models += model
}
