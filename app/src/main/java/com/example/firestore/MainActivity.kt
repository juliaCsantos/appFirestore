package com.example.firestore

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = Firebase.firestore

        val btnCadastrar : Button = findViewById(R.id.btnCadastrar)
        val btnDeletar : Button = findViewById(R.id.btnExcluir)
        val btnAlterar : Button = findViewById(R.id.btnAlterar)

        btnAlterar.setOnClickListener {
            val editNomeAlt : EditText = findViewById(R.id.Change)
            val nomeAlterar = editNomeAlt.text.toString()

            if (nomeAlterar.length != 0){
                val i = Intent(this, Alter::class.java)
                i.putExtra("Nome", nomeAlterar)
                startActivity(i)
            }
            else{
                Toast.makeText(this, "Campo nulo inválido", Toast.LENGTH_SHORT).show()
            }
        }

        btnCadastrar.setOnClickListener {
            val i = Intent(this, Register::class.java)
            startActivity(i)
        }

        btnDeletar.setOnClickListener {
            val editNomeExcluir : EditText = findViewById(R.id.Change)
            val nomeExcluir = editNomeExcluir.text.toString()

            if(nomeExcluir.length != 0){
                db.collection("cadastro").document(nomeExcluir)
                    .delete()
                    .addOnSuccessListener { Toast.makeText(this, "Cadastro excluído com sucesso!", Toast.LENGTH_SHORT).show()}
                    .addOnFailureListener { Toast.makeText(this, "Falhou :(", Toast.LENGTH_SHORT).show() }

                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
            else{
                Toast.makeText(this, "Campo nulo inválido", Toast.LENGTH_SHORT).show()
            }
        }

        val txt: TextView = findViewById(R.id.textView)
        db.collection("cadastro")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    val textoAtual = txt.text.toString()
                    val novoTexto = textoAtual + "Nome: ${document.data["Nome"]}\nEndereço: ${document.data["Endereco"]}\nBairro: ${document.data["bairro"]}\nCEP: ${document.data["CEP"]}\n\n"
                    txt.text = novoTexto
                }
            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

    }
}