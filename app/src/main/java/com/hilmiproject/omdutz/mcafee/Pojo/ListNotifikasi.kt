package com.hilmiproject.omdutz.mcafee.Pojo

/**
 * Created by L on 21/12/17.
 */
class ListNotifikasi {

    private var jenis:String=""
    get() {return field}
    set(value) { field = jenis}

    private var pesan:String= ""

    constructor(jenis: String, pesan: String) {
        this.jenis = jenis
        this.pesan = pesan
    }
}