package mx.tecnm.tepic.ladm_u4_p1_autocontestadora

import android.R
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.PhoneStateListener
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore


class recibeLlamadas:BroadcastReceiver() {
    private var telefonoManager: TelephonyManager? = null
    var baseFirebase = FirebaseFirestore.getInstance()
    var mensajeRandom= arrayListOf<String>()
    var status=false
    var itemInfo=true

    override fun onReceive(context: Context, intent: Intent?) {
        telefonoManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val listenerTelefono: PhoneStateListener = object : PhoneStateListener() {

            override fun onCallStateChanged(state: Int, incomingNumber: String){
                super.onCallStateChanged(state, incomingNumber)
                status=false
                when (state) {
                    TelephonyManager.CALL_STATE_RINGING-> {


                        baseFirebase.collection("Contactos")
                                .whereEqualTo("Telefono", incomingNumber)
                                .get()
                                .addOnSuccessListener { documents ->
                                    for (document in documents) {
                                        itemInfo = document.getBoolean("Tipo")!!
                                    }
                                    if (itemInfo) {

                                        mensajeRandom.clear()
                                        baseFirebase.collection("Mensajes")
                                                .whereEqualTo("Tipo", true)
                                                .get()
                                                .addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        mensajeRandom.add("${document.get("Mensaje")}")
                                                    }
                                                    System.out.println(mensajeRandom.size)
                                                    val rnds = (0..mensajeRandom.size-1).random()
                                                    SmsManager.getDefault().sendTextMessage(incomingNumber, null, mensajeRandom.get(rnds), null, null)
                                                    Toast.makeText(context, "Mensaje Enviado", Toast.LENGTH_LONG)
                                                            .show()
                                                }
                                    }
                                    if (!itemInfo) {
                                        mensajeRandom.clear()
                                        baseFirebase.collection("Mensajes")
                                                .whereEqualTo("Tipo", false)
                                                .get()
                                                .addOnSuccessListener { documents ->
                                                    for (document in documents) {
                                                        mensajeRandom.add("${document.get("Mensaje")}")
                                                    }
                                                    val rnds = (0..mensajeRandom.size).random()
                                                    SmsManager.getDefault().sendTextMessage(incomingNumber, null, mensajeRandom.get(rnds), null, null)
                                                    Toast.makeText(context, "Mensaje Enviado", Toast.LENGTH_LONG)
                                                            .show()
                                                }
                                    }
                                }

                    }
                }
            }

        }

        if (!isListening) {
            telefonoManager!!.listen(listenerTelefono, PhoneStateListener.LISTEN_CALL_STATE)
            isListening = true
        }
    }
    companion object {
        var isListening = false
    }

}



