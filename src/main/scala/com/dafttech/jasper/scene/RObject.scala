package com.dafttech.jasper.scene

import com.dafttech.jasper.render.{ObjectRenderer, Tesselator, VertexBufferLocation}
import com.dafttech.jasper.scene.model.Model

import scala.collection.mutable

class RObject {
  val objectRenderer: ObjectRenderer = ObjectRenderer.Triangles
  val tesselator: Tesselator = Tesselator.Triangles

  val models = new mutable.MutableList[Model]()

  var vbLoc: VertexBufferLocation = null

  def addModel(model: Model) = models += model
}
