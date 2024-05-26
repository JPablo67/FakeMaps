package co.edu.eam.fakemaps.activities

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import co.edu.eam.fakemaps.R
import co.edu.eam.fakemaps.bd.LocalStorage
import co.edu.eam.fakemaps.databinding.ActivityMainBinding
import co.edu.eam.fakemaps.fragmentos.CuentaFragment
import co.edu.eam.fakemaps.fragmentos.FavoritosFragment
import co.edu.eam.fakemaps.fragmentos.InicioFragment
import co.edu.eam.fakemaps.utils.Idioma
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding: ActivityMainBinding
    private var MENU_INICIO = "inicio"
    private var MENU_FAVORITOS = "favoritos"
    private var MENU_MICUENTA = "mi cuenta"

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
                R.id.menu_inicio -> reemplazarFragmento(1, MENU_INICIO)
                R.id.menu_favoritos -> reemplazarFragmento(2, MENU_FAVORITOS)
                R.id.menu_mi_cuenta -> reemplazarFragmento(3, MENU_MICUENTA)
            }
            true
        }

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(this)





    }

    fun reemplazarFragmento(valor: Int, nombre:String) {

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
                .addToBackStack(nombre).commit()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val count = supportFragmentManager.backStackEntryCount

        if (count > 0) {
            val nombre = supportFragmentManager.getBackStackEntryAt(count - 1).name
            when (nombre) {
                MENU_INICIO -> binding.barraInferior.menu.getItem(0).isChecked = true
                MENU_FAVORITOS -> binding.barraInferior.menu.getItem(1).isChecked = true
                else -> binding.barraInferior.menu.getItem(2).isChecked = true
            }
        } else {
            // No fragments in back stack, so select the default menu item
            binding.barraInferior.menu.getItem(0).isChecked = true
        }
    }


    fun irALogin(v:View){

        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


    override fun onDestroy() {
        super.onDestroy()


    }

    fun mostrarBarraNav(){
        binding.main.openDrawer(GravityCompat.START)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cambiar_idioma -> {
//                cambiarIdioma()
            }
        }
        item.isChecked = true
        findViewById<DrawerLayout>(R.id.main).closeDrawer(GravityCompat.START)
        return true // Indicate the event was handled
    }

//    fun cambiarIdioma(){
//        Idioma.selecionarIdioma(this)
//
//        val intent = intent
//        if (intent != null) {
//            intent.flags = (Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
//            this.finish()
//            this.startActivity(intent)
//        }
//    }
//    override fun attachBaseContext(newBase: Context?) {
//        val localeUpdatedContext: ContextWrapper? = Idioma.cambiarIdioma(newBase!!)
//        super.attachBaseContext(localeUpdatedContext)
//    }










}