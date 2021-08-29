package my.routes.house
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

import android.app.Activity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import kotlinx.android.synthetic.main.activity_login.*
import my.routes.house.dataclass.User
import my.routes.house.service.login.Login_UserReplace

// loj.rus@gmail.com => routes-list.web.app
// keytool -list -v -keystore svoboda_key.jks
// svoboda
// SHA1: DD:12:B4:63:5A:E9:7C:FB:31:8B:FB:D7:07:13:C4:37:35:B8:97:F6
// SHA256: 01:30:81:C7:CF:60:95:E0:AC:FF:AF:AE:AE:F2:D9:7C:AE:9A:90:F8:E5:F3:43:4C:20:75:39:DF:C4:13:05:C4
class LoginActivity : AppCompatActivity() {
    lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var auth: FirebaseAuth
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_login)
        auth = Firebase.auth

        enter_with_google.setOnClickListener { signIn(); }
        if(auth.currentUser != null) { updateUI(); }

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

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)!!
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    Toast.makeText(this, "Error resultlauncher comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken(getString(R.string.default_web_client_id)).requestEmail().build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
        val signInIntent = mGoogleSignInClient.signInIntent
        resultLauncher.launch(signInIntent)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val name = user!!.displayName.toString()
                    val email = user!!.email.toString()
                    val photoUrl = user!!.photoUrl.toString()
                    val database = Firebase.database
                    val emailReplaced = Login_UserReplace.executeMyReplaces(email)
                    val myRef = database.getReference("users/$emailReplaced")
                    myRef.setValue(User(name, email, photoUrl)).addOnCompleteListener { taskIt ->
                        if(taskIt.isSuccessful){
                            updateUI()
                        }
                    }
                } else {
                    Toast.makeText(this, "Error firebaseAuthWithGoogle comment to Alexei Suzdalenko", Toast.LENGTH_LONG).show()
                }
            }
    }

    fun updateUI(){
        Toast.makeText(this, "\uD83D\uDE0A " + auth.currentUser!!.displayName, Toast.LENGTH_LONG).show()
        startActivity(Intent(this, ListRoutesActivity::class.java))
        finish()
    }
}