package com.example.firestore

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.RestrictionEntry.TYPE_NULL
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class Register : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val db = Firebase.firestore

        val edtNome: EditText = findViewById(R.id.edtNome)
        val edtEndereco: EditText = findViewById(R.id.edtEndereco)
        val edtBairro: EditText = findViewById(R.id.edtBairro)
        val edtCep: EditText = findViewById(R.id.edtCep)

        val btnSalvar : Button = findViewById(R.id.btnSalvar)
        val btnVoltar : Button = findViewById(R.id.btnVoltar)

        edtNome.isEnabled = false

        btnVoltar.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }

        btnSalvar.setOnClickListener {
            // Create a new user with a first and last name
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

            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
        }
    }
}