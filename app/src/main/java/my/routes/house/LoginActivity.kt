package my.routes.house
import android.R.attr
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import androidx.core.app.ActivityCompat.startActivityForResult
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

import android.R.attr.data
import android.view.Window
import android.view.WindowManager
// loj.rus@gmail.com => routes-list.web.app
// keytool -list -v -keystore svoboda_key.jks
// svoboda
// SHA1: DD:12:B4:63:5A:E9:7C:FB:31:8B:FB:D7:07:13:C4:37:35:B8:97:F6
// SHA256: 01:30:81:C7:CF:60:95:E0:AC:FF:AF:AE:AE:F2:D9:7C:AE:9A:90:F8:E5:F3:43:4C:20:75:39:DF:C4:13:05:C4
class LoginActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
      //  Firebase.auth.signOut()
        auth = Firebase.auth
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        signIn()

      //  val database = Firebase.database
      //  val myRef = database.getReference("message")
      //  myRef.setValue("Hello, World!")

      //  val db = Firebase.firestore
      //  val user = hashMapOf(
      //      "first" to "Alan",
      //      "middle" to "Mathison",
      //      "last" to "Turing",
      //      "born" to 1912
      //  )

      //  db.collection("users")
      //      .add(user)
      //      .addOnSuccessListener { documentReference ->
      //          Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
      //      }
      //      .addOnFailureListener { e ->
      //          Log.w("TAG", "Error adding document", e)
      //      }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 1) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d("tag", "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("tag", "Google sign in failed ++++++++++++++++++++++++++++++++++++++++++++++++", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("tag", "signInWithCredential:success")
                    Toast.makeText(this, "Login APP", Toast.LENGTH_LONG).show()
                    val user = auth.currentUser
                    val name = user!!.displayName
                    val email = user!!.email
                    val photoUrl = user!!.photoUrl
                    Log.d("tag", "user => " + name + email + photoUrl)
                  //  updateUI(user)
                } else {
                    Toast.makeText(this, "Error case", Toast.LENGTH_LONG).show()
                    // If sign in fails, display a message to the user.
                    Log.w("tag", "signInWithCredential:failure", task.exception)
                 //   updateUI(null)
                }
            }
    }
}