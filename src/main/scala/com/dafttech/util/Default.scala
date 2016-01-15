package com.dafttech.util

/**
  * Created by LolHens on 02.11.2015.
  */
object Default {
  def apply[T]: T = {
    class Default {
      var value: T = _
    }
    (new Default).value
  }
}
