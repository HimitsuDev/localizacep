package com.himitsu.localizacep.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himitsu.localizacep.composes.aviso
import com.himitsu.localizacep.composes.load
import com.himitsu.localizacep.data.Endereco
import com.himitsu.localizacep.network.Endpoint
import com.himitsu.localizacep.network.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


class ViewModelCep: ViewModel() {
        private val _endereco = MutableStateFlow<Endereco>(defaultEnd())
        val endereco = _endereco.asStateFlow()




    fun getCep(nCep: String)= viewModelScope.launch  {
        try {
            val retrofitClient = NetworkUtils.getRetrofitInstance("https://viacep.com.br/ws/")
            val endpoint = retrofitClient.create(Endpoint::class.java)
            _endereco.value = defaultEnd()
            load = true

            withContext(Dispatchers.IO) {
                suspendCoroutine { continuation ->
                    endpoint.getEndereco(nCep).enqueue(object : retrofit2.Callback<Endereco> {
                        override fun onResponse(call: Call<Endereco>, response: Response<Endereco>) {

                            if (response.isSuccessful) {
                                response.body()?.let {

                                    if(it.localidade.isNullOrEmpty()) {
                                        load = false
                                        _endereco.value= defaultEnd()
                                        aviso = "Não encontrado!"
                                    } else {
                                        load = false
                                        _endereco.value = it
                                    }
                                    Log.d("conexao1", "${it}")

                                }
                            } else {

                                Log.d("conexao2", "Resposta não bem-sucedida: ${response.code()}")
                            }

                        }

                        override fun onFailure(call: Call<Endereco>, t: Throwable) {
                            Log.e("conexao3", "Erro durante a chamada à API: ${t.message}", t)
                            continuation.resumeWithException(t)
                        }
                    })
                }

            }

        } catch (e: Exception) {
            Log.e("conexao4", "Erro durante a chamada à API: ${e.message}", e)
        }
    }

    fun respostVerific(texto: String): String{
        var response = ""
        if (texto.length < 8){

            response = "CEP  $texto esta incompleto, digite o restante dos números."
            if (texto.isEmpty()){ response = "Preencha o Campo"}

        }
        return response
    }
}
fun defaultEnd(): Endereco{
    return Endereco("", "", "", "", "", "")
}