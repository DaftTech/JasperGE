package com.dafttech.jasper.scene

import com.dafttech.jasper.scene.model.Model
import org.joml.Matrix4f

class PlacedModel(val model: Model, transformation: Matrix4f) extends RenderingEntity(transformation) {
  override def getVertices = model.getVertices

  override def getIndices = model.getIndices
}
