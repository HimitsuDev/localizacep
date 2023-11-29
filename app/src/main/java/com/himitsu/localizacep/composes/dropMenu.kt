package com.himitsu.localizacep.composes

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController


var expanded by mutableStateOf(false)
@Composable
fun dropMenu(navController: NavController){
    val context = LocalContext.current
    Box(contentAlignment = Alignment.TopStart,
        modifier = Modifier
            .fillMaxWidth()

    ) {
        IconButton(modifier = Modifier.align(Alignment.TopStart),onClick = { expanded = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = "Icone superior, dropmenu")
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("Localizar CEP") },
                onClick = {
                    navController.navigate("CepCompose")
                    expanded = false
                },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Home,
                        contentDescription = null
                    )
                })

            DropdownMenuItem(
                text = { Text("Políticas de Privacidade") },
                onClick = {
                    navController.navigate("PoliticasPrivacidade")
                    expanded = false
                          },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = null
                    )
                })
            Divider()
            DropdownMenuItem(
                text = { Text("Sair") },
                onClick = { (context as? Activity)?.finish() },
                leadingIcon = {
                    Icon(
                        Icons.Outlined.ExitToApp,
                        contentDescription = null
                    )
                }
            )
        }
    }

}