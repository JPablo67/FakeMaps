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
import co.edu.eam.fakemaps.databinding.ActivityOnlySessionBinding

class SessionOnlyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnlySessionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityOnlySessionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainView = binding.root.findViewById<View>(R.id.only_session)

        ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.txtBusqueda.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val busqueda = binding.txtBusqueda.text.toString()
                if (busqueda.isNotEmpty()) {
                    val intent = Intent(baseContext, ResultadoBusquedaActivity::class.java)
                    intent.putExtra("texto", busqueda)
                    startActivity(intent)
                    Log.e("SessionOnlyActivity", binding.txtBusqueda.text.toString())
                    return@setOnEditorActionListener true
                }
            }
            false
        }


    }

    fun irALogin(v: View) {
        Toast.makeText(this, "Se dio click al botón de log in", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    fun irACrear(v: View) {
        val intent = Intent(this, CrearLugarActivity::class.java)
        startActivity(intent)
    }

    fun  irProfile(v: View){
        val intent = Intent(this, ClienteActivity::class.java)
        startActivity(intent)
    }

    fun irAMain(v:View){
        val sharedPreferences = this.getSharedPreferences("sesion", Context.MODE_PRIVATE).edit()
        sharedPreferences.clear()
        sharedPreferences.apply()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
