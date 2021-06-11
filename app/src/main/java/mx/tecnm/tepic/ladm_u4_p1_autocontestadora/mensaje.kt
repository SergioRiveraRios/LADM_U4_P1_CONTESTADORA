package mx.tecnm.tepic.ladm_u4_p1_autocontestadora

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.mensaje.*

class mensaje: AppCompatActivity() {
    var baseFirebase =FirebaseFirestore.getInstance()
    var deseados=ArrayList<String>()
    var noDeseados=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mensaje)

        agregarMensaje.setOnClickListener {
            agregarMensaje()
        }
        salirMensaje.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    fun agregarMensaje(){
        var mensaje=mensaje.text.toString()
        if(mensajeAgradable.isChecked ){
            deseados.add(mensaje)
            Agradables.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, deseados)
            subirFirebase(1)
        }
        if(mensajeNoAgradable.isChecked){
            noDeseados.add(mensaje)
            noAgradables.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, noDeseados)
            subirFirebase(0)
        }
    }

    private fun subirFirebase(tipo:Int){
        var estado=true
        if(tipo==1){
            estado=true
        }else{estado=false}

        var insertar = hashMapOf(
            "Mensaje" to mensaje.text.toString(),
            "Tipo" to estado
        )
        baseFirebase.collection("Mensajes")
            .add(insertar)
            .addOnSuccessListener { documentReference ->
            }
    }

    private fun obtenerAgradables(){
        baseFirebase.collection("Mensajes")
            .whereEqualTo("Tipo", true)
            .get()
            .addOnSuccessListener { documents ->
                deseados.clear()
                for (document in documents) {
                    var mensaje="${document.getString("mensaje")}"
                    deseados.add(mensaje)
                }
                Agradables.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, deseados)

            }
            .addOnFailureListener { exception ->
            }

    }
    private fun obtenerNoAgradables(){
        baseFirebase.collection("Mensajes")
            .whereEqualTo("Tipo", false)
            .get()
            .addOnSuccessListener { documents ->
                noDeseados.clear()
                for (document in documents) {
                    var mensaje="${document.getString("mensaje")}"
                    noDeseados.add(mensaje)
                }
                noAgradables.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, noDeseados)

            }
            .addOnFailureListener { exception ->
            }

    }

}