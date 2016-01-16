package com.dafttech.jasper.scene.model

import com.dafttech.jasper.util.Vertex

abstract class Model {
  def getVertices: Seq[Vertex]

  def getIndices: Seq[Int]
}