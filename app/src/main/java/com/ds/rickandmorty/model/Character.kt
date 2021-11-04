package com.ds.rickandmorty.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Character : Serializable {
    @SerializedName("info")
    @Expose
    var info: Info? = null

    @SerializedName("results")
    @Expose
    var results: List<Result>? = null
}