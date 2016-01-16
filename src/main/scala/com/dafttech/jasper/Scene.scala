package com.dafttech.jasper

import scala.collection.mutable

/**
  * A scene
  */
class Scene {
  val vertexBuffer: VertexBuffer = new VertexBuffer()
  val models = new mutable.MutableList[Object]()

  def addModel(model: Object) = {
    model.vbLoc = vertexBuffer.allocate(model.tesselator.getVtxCount, model.tesselator.getIdxCount)
    model.tesselator.tesselate(model)

    models += model
  }
}
