package co.edu.eam.fakemaps.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LocalStorage.User = null;

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val correo = sp.getString("correo_usuario","")
        val tipo = sp.getString("tipo_usuario","")

        if (correo!!.isNotEmpty() && tipo!!.isNotEmpty()){

            when(tipo){
                "usuario" -> startActivity(Intent(this,SessionOnlyActivity::class.java))
                "admin" -> startActivity(Intent(this,AdminActivity::class.java))
            }
        }else{
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            binding.txtBusqueda.setOnEditorActionListener{textView,i,keyEvent ->
                if(i == EditorInfo.IME_ACTION_SEARCH){

                    val busqueda = binding.txtBusqueda.text.toString()
                    if(busqueda.isNotEmpty()){
                        val intent = Intent(baseContext,ResultadoBusquedaActivity::class.java)
                        intent.putExtra("texto", busqueda)
                        startActivity(intent)
                        Log.e("MainActivity",binding.txtBusqueda.text.toString())

                    }


                }
                true
            }
        }




    }

    fun irALogin(v:View){

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()


    }



}