package com.apiguave.tinderclonecompose.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.text.isDigitsOnly
import com.apiguave.tinderclonecompose.R

@Composable
fun GenerateProfilesDialog(onDismissRequest: () -> Unit, onGenerate: (Int) -> Unit){
    var text by remember { mutableStateOf(TextFieldValue("5")) }
    Dialog(
        onDismissRequest = onDismissRequest,
    ){
        Surface(modifier = Modifier
            .fillMaxWidth(),
            elevation = 4.dp) {
          Column(
              modifier = Modifier
                  .fillMaxWidth()
                  .padding(top = 16.dp, bottom = 8.dp),
              verticalArrangement = Arrangement.Center,
              horizontalAlignment = Alignment.CenterHorizontally) {

              Text(stringResource(id = R.string.generate_profiles), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
              Spacer(Modifier.height(8.dp))
              TextField(
                  value = text,
                  keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                  onValueChange = {
                      if(it.text.isDigitsOnly()){
                          text = it
                      }
                  }
              )
              Button(onClick = { onGenerate(text.text.toInt()) }){
                  Text("Generate")
              }
          }
        }
    }
}