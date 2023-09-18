package com.catnip.solarsystem.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

/**
Written with love by Muhammad Hermas Yuda Pamungkas
Github : https://github.com/hermasyp
 **/
@Parcelize
data class Planet(
    val position : Int,
    val name: String,
    val imgUrl: String,
    val velocity: Long,
    val distanceFromSun: Long,
    val desc: String
) : Parcelable
