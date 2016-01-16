package com.dafttech.jasper.model

import com.dafttech.jasper.model
import com.dafttech.jasper.render.VertexBuffer

import scala.collection.mutable

/**
  * A scene
  */
class Scene {
  val vertexBuffer: VertexBuffer = new VertexBuffer()
  val models = new mutable.MutableList[model.RObject]()

  def addObject(model: RObject) = {
    model.vbLoc = vertexBuffer.allocate(model.tesselator.getVtxCount, model.tesselator.getIdxCount)
    model.tesselator.tesselate(model)

    models += model
  }
}
