package com.himitsu.localizacep.network

import com.himitsu.localizacep.data.Endereco
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("{cep}/json/")
    fun getEndereco( @Path("cep") cep: String): Call<Endereco>

}