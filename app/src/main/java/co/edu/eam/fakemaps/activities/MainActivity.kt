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

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)

        val correo = sp.getString("correo_usuario","")
        val tipo = sp.getString("tipo_usuario","")

        LocalStorage.User = null;

        if (correo!!.isNotEmpty() && tipo!!.isNotEmpty() && tipo != "usuario"){

            when(tipo){

                "admin" -> {
                    val i = Intent(this,AdminActivity::class.java)
                    startActivity(i)
                    finish()
                }
            }

        }else{
            binding = ActivityMainBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(binding.contenidoPrincipal.id, InicioFragment())
                    .commit()
            }

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

    fun reemplazarFragmento(valor: Int) {

        val sp = getSharedPreferences("sesion", Context.MODE_PRIVATE)
        val correo = sp.getString("correo_usuario", "")

        var fragmento: Fragment? = null

        when (valor) {
            1 -> fragmento = InicioFragment()
            2 -> fragmento = FavoritosFragment()
            else -> {
                if (correo.isNullOrEmpty()) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    return // Return early to avoid executing the fragment transaction
                } else {
                    fragmento = CuentaFragment()
                }
            }
        }

        fragmento?.let {
            supportFragmentManager.beginTransaction().replace(binding.contenidoPrincipal.id, it)
                .addToBackStack("fragmento $valor").commit()
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