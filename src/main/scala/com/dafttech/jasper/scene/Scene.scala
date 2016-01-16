package com.dafttech.jasper.scene

import com.dafttech.jasper.render.VertexBuffer

import scala.collection.mutable

class Scene {
  val vertexBuffer: VertexBuffer = new VertexBuffer()
  val models = new mutable.MutableList[PlacedModel]()

  def addObject(model: PlacedModel) = {
    model.vbLoc = vertexBuffer.allocate(model.tesselator.getVtxCount, model.tesselator.getIdxCount)
    model.tesselator.tesselate(model)

    models += model

    model
  }
}
