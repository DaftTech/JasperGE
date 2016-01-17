package com.dafttech.jasper.scene

import com.dafttech.jasper.render.{ObjectRenderer, Tesselator, VertexBufferLocation}
import com.dafttech.jasper.scene.model.Model
import org.joml.Matrix4f

import scala.collection.mutable

class PlacedModel(val model: Model, transformation: Matrix4f) extends RenderingEntity(transformation) {
  override def getVertices = model.getVertices
  override def getIndices = model.getIndices
}
