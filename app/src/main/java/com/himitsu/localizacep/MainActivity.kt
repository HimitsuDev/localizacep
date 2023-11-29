package com.himitsu.localizacep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.himitsu.localizacep.composes.CepCompose
import com.himitsu.localizacep.composes.PoliticasPrivacidade
import com.himitsu.localizacep.ui.theme.LOCALIZACEPTheme
import com.himitsu.localizacep.viewModel.ViewModelCep


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContent {
            LOCALIZACEPTheme {

                val viewModel by viewModels<ViewModelCep>()

                val navController = rememberNavController()
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                            MyAppHost(navController, viewModel)

                }
            }
        }

    }

    @Composable
    fun MyAppHost(
                  navController: NavHostController = rememberNavController(),
                  viewModelCep: ViewModelCep,
                  startDestination: String = "CepCompose"
    ){

        NavHost(navController = navController, startDestination = startDestination){
            composable("CepCompose") { CepCompose(viewModelCep, navController)}
            composable("PoliticasPrivacidade") { PoliticasPrivacidade(navController)}

        }
    }

}
