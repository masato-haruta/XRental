package jp.domus.xrental

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private var mGoogleSignInClient: GoogleSignInClient? = null  //追加

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()  //Email情報取りたい場合
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        val logoutButon: Button = findViewById(R.id.logout_button)
        logoutButon.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            //GoogleからもSignOutする
            mGoogleSignInClient!!.signOut()
                    .addOnCompleteListener { task: Task<Void> ->
                        if(!task.isSuccessful){
                            Log.d("javalog","fail logout")
                            return@addOnCompleteListener
                        }
                        Log.d("javalog", "logout")
                        val intent:Intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                    }
        }
    }
}
