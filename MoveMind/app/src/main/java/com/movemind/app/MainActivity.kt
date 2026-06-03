package com.movemind.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.runtime.*
import com.movemind.app.ui.screens.HomeScreen
import com.movemind.app.ui.screens.LoginScreen
import com.movemind.app.ui.theme.MoveMindTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MoveMindTheme {
                MoveMindApp()
            }
        }
    }
}

// ── Navegação simples por estado ───────────────────────────────────────────
// Para projetos maiores, substitua por Navigation Compose (NavHost).
enum class Screen { LOGIN, HOME }

@Composable
fun MoveMindApp() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

    AnimatedContent(
        targetState   = currentScreen,
        transitionSpec = {
            if (targetState == Screen.HOME) {
                // Login → Home: slide para cima
                slideInVertically(
                    initialOffsetY = { it },
                    animationSpec  = tween(400),
                ) togetherWith slideOutVertically(
                    targetOffsetY = { -it / 4 },
                    animationSpec = tween(400),
                )
            } else {
                // Home → Login: fade simples
                fadeIn(tween(300)) togetherWith fadeOut(tween(300))
            }
        },
        label = "screenTransition",
    ) { screen ->
        when (screen) {
            Screen.LOGIN -> LoginScreen(
                onLoginSuccess = { currentScreen = Screen.HOME },
            )
            Screen.HOME  -> HomeScreen(
                userName = "Kauã",
                onLogout = { currentScreen = Screen.LOGIN },
            )
        }
    }
}
