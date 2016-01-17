package com.dafttech.jasper.scene

import org.joml.{Vector3f, Matrix4f}

class Camera(vpWidth: Int, vpHeight: Int, position: Vector3f, lookAt: Vector3f) {
  val matrix = new Matrix4f().perspective((Math.PI.toFloat/180f) * 75f, vpWidth / vpHeight, 0.001f, 10000f).lookAt(position, lookAt, new Vector3f(0, 1, 0))
}