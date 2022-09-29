package com.example.firestore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Alter : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alter)

        val db = Firebase.firestore

        val nome:String = intent.getStringExtra("Nome").toString()

        val btnVoltar : Button = findViewById(R.id.btnVoltar2)
        val btnSalvar : Button = findViewById(R.id.btnSalvar2)

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtCep: EditText = findViewById(R.id.edtCep)

        val docAlt = db.collection("cadastro").document(nome)

        docAlt.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    edtNome.setText("${document.data?.get("Nome")}")
                    edtEndereco.setText("${document.data?.get("Endereco")}")
                    edtBairro.setText("${document.data?.get("bairro")}")
                    edtCep.setText("${document.data?.get("CEP")}")
                } else {
                    Toast.makeText(this, "Falhou :(", Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Falhou :(", Toast.LENGTH_SHORT).show()
            }

        btnVoltar.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        btnSalvar.setOnClickListener {
            val pessoa = hashMapOf(
                "Nome" to edtNome.text.toString(),
                "Endereco" to edtEndereco.text.toString(),
                "bairro" to edtBairro.text.toString(),
                "CEP" to edtCep.text.toString()
            )

            db.collection("cadastro").document(edtNome.text.toString())
                .set(pessoa)
                .addOnSuccessListener {
                    Toast.makeText(this, "Cadastrado com sucesso", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Cadastrado falhou :(", Toast.LENGTH_SHORT).show()
                }
        }
    }
}