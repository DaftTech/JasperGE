package com.dafttech.jasper.scene

import com.dafttech.jasper.render.{ObjectRenderer, Tesselator, VertexBufferLocation}
import com.dafttech.jasper.scene.model.Model
import org.joml.Matrix4f

import scala.collection.mutable

class PlacedModel(val model: Model, val transformation: Matrix4f) {
  val objectRenderer: ObjectRenderer = ObjectRenderer.Triangles
  val tesselator: Tesselator = Tesselator.Triangles

  var vbLoc: VertexBufferLocation = null
}
