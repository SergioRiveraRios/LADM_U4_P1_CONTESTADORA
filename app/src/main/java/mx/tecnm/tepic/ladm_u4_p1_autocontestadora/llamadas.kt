package mx.tecnm.tepic.ladm_u4_p1_autocontestadora

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.llamadas.*
import kotlinx.android.synthetic.main.mensaje.*

class llamadas: AppCompatActivity() {
    var baseFirebase=FirebaseFirestore.getInstance()
    var contactosDeseados=ArrayList<String>()
    var contactosNoDeseados=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.llamadas)

        agregarContacto.setOnClickListener {
            agregarContacto()
        }

    }
    fun agregarContacto(){
        var mensaje=Nombre.text.toString()
        if(contactoAgradable.isChecked ){
            contactosDeseados.add(mensaje)
            listaContactosAgradabless.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, contactosDeseados)
            subirFirebase(1)
        }
        if(contactoNoAgradable.isChecked){
            contactosNoDeseados.add(mensaje)
            listaContactosNoAgradabless.adapter = ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, contactosNoDeseados)
            subirFirebase(0)
        }
    }
    private fun subirFirebase(tipo:Int){
        var estado=true
        if(tipo==1){
            estado=true
        }else{estado=false}

        var insertar = hashMapOf(
            "Contacto" to Nombre.text.toString(),
            "Telefono" to "+52"+Telefono.text.toString(),
            "Tipo" to estado
        )
        baseFirebase.collection("Contactos")
            .add(insertar)
            .addOnSuccessListener { documentReference ->
            }
    }
}