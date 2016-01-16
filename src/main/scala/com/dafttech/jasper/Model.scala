package com.dafttech.jasper

/**
  * Represents a abstract model as well as its representation in the Scene VertexBuffer
  */
class Model {
  val modelRenderer: ModelRenderer = ModelRenderer.Quads
  val modelTesselator: ModelTesselator = ModelTesselator.Quads

  var vbLoc: VertexBufferLocation = null
}
