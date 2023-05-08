package com.example.projetofiap

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.projetofiap.databinding.ActivityMainBinding
import com.google.android.gms.common.SignInButton
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        window.statusBarColor = Color.parseColor("#912CEE")
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        supportActionBar!!.hide()

        auth = Firebase.auth

        val btnLogin = findViewById<AppCompatButton>(R.id.btEntrar)
        btnLogin.setOnClickListener {
            signIn()
        }

        val textViewCadastro = findViewById<TextView>(R.id.textViewCadastro)

        val texto = "Não tem conta? Cadastre-se."
        val spannable = SpannableString(texto)

        val start = texto.indexOf("Cadastre-se.")
        val end = start + "Cadastre-se.".length

        spannable.setSpan(object : ClickableSpan() {
            override fun onClick(view: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = textViewCadastro.currentTextColor
                ds.isUnderlineText = false //
            }
        }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannable.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        textViewCadastro.text = spannable
        textViewCadastro.movementMethod = LinkMovementMethod.getInstance()
    }

    private fun signIn() {
        val email = binding.editEmail.text.toString()
        val password = binding.editSenha.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, preencha todos os campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Autenticar com email e senha
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Login bem-sucedido
                    navegarTelaPrincipal()
                    val user = auth.currentUser
                    Toast.makeText(this, "Login bem-sucedido", Toast.LENGTH_SHORT).show()
                } else {
                    // Login mal-sucedido
                    Toast.makeText(this, "Erro ao fazer login. Por favor, tente novamente.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun navegarTelaPrincipal(){
        val intent = Intent(this,TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }
}