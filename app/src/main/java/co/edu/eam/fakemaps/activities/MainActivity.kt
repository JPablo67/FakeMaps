package co.edu.eam.fakemaps.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.databinding.ActivityMainBinding
import co.edu.eam.fakemaps.fragmentos.CuentaFragment
import co.edu.eam.fakemaps.fragmentos.FavoritosFragment
import co.edu.eam.fakemaps.fragmentos.InicioFragment

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
                "usuario" -> {
                    val i = Intent(this,SessionOnlyActivity::class.java)

                    startActivity(i)

                }
                "admin" -> {
                    val i = Intent(this,AdminActivity::class.java)
                    startActivity(i)
                }
            }
            finish()
        }else{
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)


            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }


        }

        binding.barraInferior.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_inicio -> reemplazarFragmento(1)
                R.id.menu_favoritos -> reemplazarFragmento(2)
                R.id.menu_mi_cuenta -> reemplazarFragmento(3)
            }
            true
        }



    }

    fun reemplazarFragmento(valor:Int){

        var fragmento: Fragment

        if (valor == 1){
            fragmento = InicioFragment()
        }else if(valor == 2){
            fragmento = FavoritosFragment()
        }else{
            fragmento = CuentaFragment()
        }
        supportFragmentManager.beginTransaction().replace(binding.contenidoPrincipal.id, fragmento)
            .addToBackStack("fragento $valor").commit()
    }

    fun irALogin(v:View){

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()


    }



}