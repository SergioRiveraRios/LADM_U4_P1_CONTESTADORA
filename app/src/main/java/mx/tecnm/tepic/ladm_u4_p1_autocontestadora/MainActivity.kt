package mx.tecnm.tepic.ladm_u4_p1_autocontestadora

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.telephony.TelephonyManager
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var mTelephonyManager: TelephonyManager? = null
    var baseFirebase=FirebaseFirestore.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        asignarAccesos()

        actividadMensaje.setOnClickListener {
            agregarMensaje()
            finish()
        }
        actividadContacto.setOnClickListener {
            agregarContacto()
            finish()
        }


    }
    private fun obtenerLlamadas(){
        baseFirebase.collection("")
    }

    private fun agregarMensaje(){
        var intent = Intent(this, mensaje::class.java)
        //intent.putExtra("dato", dato)
        startActivity(intent)
    }
    private fun agregarContacto(){
        var intent = Intent(this, llamadas::class.java)
        //intent.putExtra("dato", dato)
        startActivity(intent)
    }
    private fun asignarAccesos(){
        val permission = ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_PHONE_STATE), 1)
        }
        if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.SEND_SMS)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.SEND_SMS),1)
        }
        if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CALL_LOG),1)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1){

        }

    }

}