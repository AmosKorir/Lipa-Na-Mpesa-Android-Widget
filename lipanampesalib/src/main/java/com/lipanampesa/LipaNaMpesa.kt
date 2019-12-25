package com.lipanampesa

import java.io.Serializable

/**
 * Created by Korir on 12/21/19.
 * amoskrr@gmail.com
 */
data class LipaNaMpesa(
  val PAYBILL: String,
  val ACCOUNT: String,
  val AMOUNT: String,
  val PRIMARYCOLOR: Int
) : Serializable