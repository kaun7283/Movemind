package com.movemind.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.movemind.app.ui.components.*
import com.movemind.app.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
) {
    var email     by remember { mutableStateOf("") }
    var password  by remember { mutableStateOf("") }
    var showPass  by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var emailErr  by remember { mutableStateOf(false) }
    var passErr   by remember { mutableStateOf(false) }

    // Entrada animada dos elementos
    var visible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { visible = true }

    val contentAlpha by animateFloatAsState(
        targetValue   = if (visible) 1f else 0f,
        animationSpec = tween(600, delayMillis = 100),
        label         = "alpha",
    )
    val contentOffset by animateIntAsState(
        targetValue   = if (visible) 0 else 40,
        animationSpec = tween(600, delayMillis = 100, easing = EaseOutCubic),
        label         = "offset",
    )

    fun doLogin() {
        emailErr = email.isBlank() || !email.contains("@")
        passErr  = password.length < 4
        if (emailErr || passErr) return
        isLoading = true
    }

    // Simula loading e navega — em prod, chame seu AuthRepository aqui
    LaunchedEffect(isLoading) {
        if (isLoading) {
            delay(1_400)
            isLoading = false
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark),
    ) {
        // Detalhe decorativo — barra laranja no topo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(3.dp)
                .background(OrangeVibrant)
        )

        // Gradiente sutil de fundo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            OrangeVibrant.copy(alpha = 0.04f),
                            NavyDark,
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp)
                .alpha(contentAlpha)
                .offset(y = contentOffset.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Spacer(Modifier.height(80.dp))

            // Logo
            MoveMindLogo()

            Spacer(Modifier.height(4.dp))

            Text(
                text  = "Diário de Treino & Foco",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
            )

            Spacer(Modifier.height(48.dp))

            OrangeAccentBar()

            Spacer(Modifier.height(20.dp))

            Text(
                text  = "Bem-vindo de volta",
                style = MaterialTheme.typography.headlineMedium,
                color = TextPrimary,
            )
            Text(
                text  = "Entre para continuar sua jornada.",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
            )

            Spacer(Modifier.height(32.dp))

            // Campo e-mail
            MoveMindTextField(
                value         = email,
                onValueChange = { email = it; emailErr = false },
                label         = "E-MAIL",
                leadingIcon   = Icons.Outlined.Email,
                isError       = emailErr,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction    = ImeAction.Next,
                ),
            )

            if (emailErr) {
                Text(
                    text  = "Informe um e-mail válido",
                    style = MaterialTheme.typography.labelSmall,
                    color = ErrorRed,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                )
            }

            Spacer(Modifier.height(16.dp))

            // Campo senha
            MoveMindTextField(
                value         = password,
                onValueChange = { password = it; passErr = false },
                label         = "SENHA",
                leadingIcon   = Icons.Outlined.Lock,
                isError       = passErr,
                visualTransformation = if (showPass)
                    VisualTransformation.None
                else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction    = ImeAction.Done,
                ),
                trailingIcon = {
                    IconButton(onClick = { showPass = !showPass }) {
                        Icon(
                            imageVector = if (showPass)
                                Icons.Outlined.VisibilityOff
                            else
                                Icons.Outlined.Visibility,
                            contentDescription = if (showPass) "Ocultar senha" else "Ver senha",
                            tint     = TextHint,
                            modifier = Modifier.size(20.dp),
                        )
                    }
                },
            )

            if (passErr) {
                Text(
                    text  = "Senha muito curta",
                    style = MaterialTheme.typography.labelSmall,
                    color = ErrorRed,
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                )
            }

            Spacer(Modifier.height(8.dp))

            // Esqueci a senha
            Text(
                text  = "Esqueci minha senha",
                style = MaterialTheme.typography.labelLarge,
                color = OrangeVibrant,
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { /* TODO: navegar para recuperação */ }
                    .padding(vertical = 8.dp),
            )

            Spacer(Modifier.height(28.dp))

            // Botão principal
            MoveMindButton(
                text      = "ENTRAR",
                onClick   = { doLogin() },
                isLoading = isLoading,
            )

            Spacer(Modifier.height(24.dp))

            // Divisor
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Divider(Modifier.weight(1f), color = SlateMid)
                Text(
                    text     = "  ou  ",
                    style    = MaterialTheme.typography.bodyMedium,
                    color    = TextHint,
                )
                Divider(Modifier.weight(1f), color = SlateMid)
            }

            Spacer(Modifier.height(24.dp))

            // Link cadastro
            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(color = TextSecondary)) { append("Ainda não tem conta? ") }
                    withStyle(SpanStyle(color = OrangeVibrant))  { append("Crie agora") }
                },
                style    = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable { /* TODO: navegar para cadastro */ },
                textAlign = TextAlign.Center,
            )

            Spacer(Modifier.height(40.dp))
        }
    }
}
