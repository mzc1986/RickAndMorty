package com.ds.rickandmorty.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import javax.annotation.Generated

@Generated("jsonschema2pojo")
class Info : Serializable {
    @SerializedName("count")
    @Expose
    var count: Int? = null

    @SerializedName("pages")
    @Expose
    var pages: Int? = null

    @SerializedName("next")
    @Expose
    var next: String? = null

    @SerializedName("prev")
    @Expose
    var prev: Any? = null
}