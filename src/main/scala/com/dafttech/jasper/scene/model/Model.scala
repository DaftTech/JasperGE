package com.dafttech.jasper.scene.model

import com.dafttech.jasper.util.Vertex

case class Point3f(x: Float, y: Float, z: Float)

case class Normal3f(x: Float, y: Float, z: Float)

case class TexCoord(u: Float, v: Float)

abstract class Model {
  def getVertices: Seq[Vertex]

  def getIndices: Seq[Int]
}