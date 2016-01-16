package com.dafttech.jasper.util

/**
  * Created by LolHens on 16.01.2016.
  */
class Vertex(val values: Seq[Float]) {
  if (values.length != Vertex.VTX_FLOAT_COUNT) throw new IllegalArgumentException("Vertex size incorrect")
}

object Vertex {
  val VTX_FLOAT_COUNT = 10
}