package com.apiguave.tinderclonecompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.apiguave.tinderclonecompose.navigation.NavigationGraph
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import org.koin.core.context.loadKoinModules
import org.koin.dsl.module

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityModule = module {
            single<GoogleSignInClient> {
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
                GoogleSignIn.getClient(this@MainActivity, gso)
            }
        }

        loadKoinModules(activityModule)
        setContent { NavigationGraph() }
    }
}
