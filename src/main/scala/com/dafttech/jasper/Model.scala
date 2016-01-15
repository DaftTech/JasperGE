package com.dafttech.jasper

/**
  * Represents a abstract model as well as its representation in the Scene VertexBuffer
  */
class Model {
  val modelRenderer: ModelRenderer = ModelRenderer.Triangle
  val modelTesselator: ModelTesselator = ModelTesselator.Triangle

  var vbLoc: VertexBufferLocation = null
}
