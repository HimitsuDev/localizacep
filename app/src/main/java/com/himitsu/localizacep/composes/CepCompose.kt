package com.himitsu.localizacep.composes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.himitsu.localizacep.data.Endereco
import com.himitsu.localizacep.viewModel.ViewModelCep

var aviso: String? by mutableStateOf("")
var load by mutableStateOf(false)

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CepCompose(viewModel: ViewModelCep, navController: NavController) {
    var text by remember { mutableStateOf("") }
    val campoAlterado by viewModel.endereco.collectAsState()

    Column(horizontalAlignment = Alignment.CenterHorizontally)
    {
        dropMenu(navController)

        Text(text = "Consulta de CEP", fontSize = 40.sp,
            style = TextStyle(fontFamily = FontFamily.Serif),
            modifier = Modifier
                .padding(top = 20.dp),

            textAlign = TextAlign.Center)

        OutlinedTextField(modifier = Modifier
            .padding(top = 15.dp),
            label = { Text(text = "CEP", fontSize = 20.sp) } ,
            value = text,
            onValueChange ={


                text = if(text.length < 8) {
                    it.filter { char -> char.isDigit() }

                } else{
                    text.substring(0, text.length -1)

                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            maxLines = 1
        )

        Button(onClick = {
            aviso = null
            aviso = viewModel.respostVerific(text)
            viewModel.getCep(text)


            Log.d("botaoCep", "$campoAlterado")
            text = ""


        },  modifier = Modifier.padding(top = 20.dp)) {
            Text(text = "Pesquisar")

        }
        aviso?.let { Text(text = it, fontSize = 30.sp,
            color = Color.Red,
            modifier = Modifier
                .padding(start = 30.dp),) }

        if (campoAlterado.localidade == null){
            aviso = "Endereço não encontrado, por favor verifique o CEP e tente novamente."
            viewModel.endereco.value.localidade = ""
        } else {


            LazyColumn{

                item { ViewEndereco(campoAlterado) }

            }
        }

    }

}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ViewEndereco(endereco: Endereco){
    val keyboardController = LocalSoftwareKeyboardController.current
    keyboardController?.hide()

    if(load){

        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()) {

            Text(
                text = "Aguarde..",  style = TextStyle(color = MaterialTheme.colorScheme.tertiary ),
                fontSize = 30.sp, modifier = Modifier
                    .padding(start = 15.dp, end = 10.dp, bottom = 10.dp)
            )
            Icon(
                Icons.Outlined.Refresh,
                contentDescription = null,
                modifier = Modifier.padding(top = 10.dp)
            )
        }

       }



    Column {





        val campos = listOf(
            "Localidade:" to endereco.localidade,
            "Logradouro:" to endereco.logradouro,
            "CEP:" to endereco.cep,
            "Complemento:" to endereco.complemento,
            "N° IBGE:" to endereco.ibge,
            "GIA:" to endereco.gia,
            "DDD:" to endereco.ddd,
            "SIAFI:" to endereco.siafi
        )

        campos.forEach { (titulo, valor) ->

            if (valor.isNotBlank()) {
                ElevatedCard(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 70.dp
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp)
                ){




                Text(
                    text = "$titulo $valor",  style = TextStyle(color = MaterialTheme.colorScheme.tertiary ),
                    fontSize = 30.sp, modifier = Modifier
                        .padding(start = 15.dp, end = 10.dp, bottom = 10.dp)
                )
                }
            }
        }

    }

}