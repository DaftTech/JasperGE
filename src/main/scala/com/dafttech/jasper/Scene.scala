package com.dafttech.jasper

import scala.collection.mutable

/**
  * A scene
  */
class Scene {
  val vertexBuffer: VertexBuffer = new VertexBuffer()
  val models = new mutable.MutableList[Model]()

  def addModel(model: Model) = {
    model.vbLoc = vertexBuffer.allocate(model.modelTesselator.getSize)
    model.modelTesselator.tesselate(model)

    models += model
  }
}
