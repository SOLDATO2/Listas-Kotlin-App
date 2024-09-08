package com.example.listas

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listas.ui.theme.ListasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            LayoutLista()
        }
    }
}

@Composable
fun LayoutLista(){

    //variaveis q vao interagir com a interface
    var nome by remember { mutableStateOf("") }
    var fone by remember { mutableStateOf("") }
    // lista de contatos
    var contatos by remember { mutableStateOf(listOf<Contato>())}
    val context = LocalContext.current

    Column(modifier = Modifier.fillMaxSize().padding(18.dp)){

        Text(text = "Agenda Telefônica", modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(28.dp))

        //TextField com nome
        TextField(modifier = Modifier.fillMaxWidth().padding(18.dp),
            value = nome,
            onValueChange = {nome = it},
            label = {Text(text = "Nome do contato:")})
        //TextField com fone
        TextField(modifier = Modifier.fillMaxWidth().padding(18.dp),
            value = fone,
            onValueChange = {fone = it},
            label = {Text(text = "Fone do contato:")})

        Button(modifier = Modifier.fillMaxWidth().padding(80.dp),
            onClick =  {
                if(nome.isNotBlank() && fone.isNotBlank()){

                    contatos = contatos + Contato(nome,fone)
                    nome = ""
                    fone = ""

                }else{
                    Toast.makeText(context, "Preencha todos os campos",
                        Toast.LENGTH_SHORT).show()
                }
            }) {
            Text(text = "Salvar Contato")
        }


        //LazyColumn = lista dinamica de elementos
        LazyColumn(modifier = Modifier.fillMaxWidth()){
            // como se fosse um lambda, para cada contato dentro da lista "contatos"
            // iremos passar o contato atual para a função ContatoItem
            items(contatos){ contato -> ContatoItem(
                contato = contato,
                onDeleteClick = {
                    contatos = contatos.filter { it != contato }
                    Toast.makeText(context, "Contato Excluido", Toast.LENGTH_SHORT).show()
                })}
        }
    }


}

@Composable
fun ContatoItem(contato: Contato, onDeleteClick: () -> Unit){

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)){


        Row(modifier = Modifier.fillMaxWidth()){
            Text(text = "${contato.nome}. ${contato.fone}", fontSize = 20.sp)
            Button(onClick = onDeleteClick){
                Text(text = "X")
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun Preview() {
    LayoutLista()
}