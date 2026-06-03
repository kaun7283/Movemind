package com.movemind.app.ui.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.movemind.app.ui.components.*
import com.movemind.app.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

// ── Data classes locais ────────────────────────────────────────────────────
private data class HabitState(
    val icon:    ImageVector,
    val title:   String,
    val value:   String,
    val percent: Float,
    var checked: Boolean = false,
)

private data class RecentActivity(
    val icon:  ImageVector,
    val label: String,
    val info:  String,
    val tag:   String,
)

// ── Tela Principal ─────────────────────────────────────────────────────────
@Composable
fun HomeScreen(
    userName:  String = "Kauã",
    onLogout:  () -> Unit,
) {
    // Hábitos diários — refletem os cards do slide (Treino, Código, Água)
    var habits by remember {
        mutableStateOf(
            listOf(
                HabitState(Icons.Outlined.FitnessCenter, "Treino / Cardio",    "90%", 0.90f),
                HabitState(Icons.Outlined.Code,          "Código & Estudos",   "80%", 0.80f),
                HabitState(Icons.Outlined.WaterDrop,     "Hidratação Ativa",   "75%", 0.75f),
            )
        )
    }

    val activities = remember {
        listOf(
            RecentActivity(Icons.Outlined.FitnessCenter, "Peito + Ombro", "Hoje · 1h12min",    "Academia"),
            RecentActivity(Icons.Outlined.DirectionsRun, "Corrida 5K",    "Ontem · 28min",      "Cardio"),
            RecentActivity(Icons.Outlined.Code,          "NestJS — Auth", "Ontem · 2h40min",   "Código"),
        )
    }

    val today = remember {
        SimpleDateFormat("EEEE, d MMM", Locale("pt", "BR"))
            .format(Date())
            .replaceFirstChar { it.uppercase() }
    }

    // Consistência média (espelha o slide: 81%)
    val consistency = habits.map { it.percent }.average().let { "%.0f%%".format(it * 100) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(NavyDark),
    ) {
        // Gradiente hero
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            OrangeVibrant.copy(alpha = 0.07f),
                            NavyDark,
                        )
                    )
                )
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            // ── Top bar ────────────────────────────────────────────────
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text  = "Move",
                        style = MaterialTheme.typography.titleLarge,
                        color = TextPrimary,
                    )
                    Text(
                        text  = "Mind",
                        style = MaterialTheme.typography.titleLarge,
                        color = OrangeVibrant,
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    IconButton(onClick = { /* TODO: Notificações */ }) {
                        Icon(
                            Icons.Outlined.Notifications,
                            contentDescription = "Notificações",
                            tint = TextSecondary,
                        )
                    }
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(OrangeVibrant)
                            .clickable { onLogout() },
                        contentAlignment = Alignment.Center,
                    ) {
                        Text(
                            text  = userName.take(1).uppercase(),
                            style = MaterialTheme.typography.labelLarge,
                            color = TextPrimary,
                        )
                    }
                }
            }

            // ── Saudação ───────────────────────────────────────────────
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Text(
                    text  = today,
                    style = MaterialTheme.typography.labelSmall,
                    color = TextHint,
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = buildAnnotatedString {
                        append("Olá, ")
                        withStyle(SpanStyle(color = OrangeVibrant)) { append(userName) }
                        append(" 👊")
                    },
                    style = MaterialTheme.typography.headlineLarge,
                    color = TextPrimary,
                )
                Text(
                    text  = "Pronto para superar o ontem?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary,
                )
            }

            Spacer(Modifier.height(28.dp))

            // ── Card de consistência ───────────────────────────────────
            ConsistencyBanner(
                consistency = consistency,
                modifier    = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
            )

            Spacer(Modifier.height(28.dp))

            // ── Seção hábitos ──────────────────────────────────────────
            SectionHeader(
                title    = "Checklist de Hábitos",
                subtitle = "hoje",
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(Modifier.height(12.dp))

            // Grid 2 colunas (terceiro abaixo)
            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    habits.take(2).forEachIndexed { i, habit ->
                        HabitCard(
                            icon     = habit.icon,
                            title    = habit.title,
                            value    = habit.value,
                            percent  = habit.percent,
                            modifier = Modifier.weight(1f),
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                HabitCard(
                    icon     = habits[2].icon,
                    title    = habits[2].title,
                    value    = habits[2].value,
                    percent  = habits[2].percent,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            Spacer(Modifier.height(28.dp))

            // ── Atividades recentes ────────────────────────────────────
            SectionHeader(
                title    = "Atividade Recente",
                subtitle = "ver tudo",
                modifier = Modifier.padding(horizontal = 24.dp),
            )

            Spacer(Modifier.height(12.dp))

            Column(
                modifier              = Modifier.padding(horizontal = 24.dp),
                verticalArrangement   = Arrangement.spacedBy(10.dp),
            ) {
                activities.forEach { act ->
                    ActivityRow(activity = act)
                }
            }

            Spacer(Modifier.height(32.dp))

            // ── FAB area ── registro rápido ────────────────────────────
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp),
            ) {
                MoveMindButton(
                    text    = "+ REGISTRAR TREINO",
                    onClick = { /* TODO: navegar para registro */ },
                )
            }

            Spacer(Modifier.height(40.dp))
        }
    }
}

// ── Composables auxiliares da Home ─────────────────────────────────────────

@Composable
private fun ConsistencyBanner(
    consistency: String,
    modifier:    Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(
                Brush.horizontalGradient(
                    listOf(OrangeVibrant.copy(alpha = 0.18f), NavyMid)
                )
            )
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column {
            Text(
                text  = "Consistência semanal",
                style = MaterialTheme.typography.bodyMedium,
                color = TextSecondary,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text  = "Você está indo muito bem!",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary,
            )
        }
        Text(
            text  = consistency,
            style = MaterialTheme.typography.displayLarge,
            color = OrangeVibrant,
        )
    }
}

@Composable
private fun SectionHeader(
    title:    String,
    subtitle: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier              = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically,
    ) {
        Text(
            text  = title,
            style = MaterialTheme.typography.titleMedium,
            color = TextPrimary,
        )
        Text(
            text  = subtitle,
            style = MaterialTheme.typography.labelLarge,
            color = OrangeVibrant,
        )
    }
}

@Composable
private fun ActivityRow(activity: RecentActivity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(SurfaceCard)
            .padding(horizontal = 14.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(OrangeVibrant.copy(alpha = 0.12f)),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = activity.icon,
                contentDescription = null,
                tint     = OrangeVibrant,
                modifier = Modifier.size(20.dp),
            )
        }

        Spacer(Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text  = activity.label,
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary,
            )
            Text(
                text  = activity.info,
                style = MaterialTheme.typography.bodyMedium,
                color = TextHint,
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(OrangeVibrant.copy(alpha = 0.15f))
                .padding(horizontal = 10.dp, vertical = 4.dp),
        ) {
            Text(
                text  = activity.tag,
                style = MaterialTheme.typography.labelSmall,
                color = OrangeVibrant,
            )
        }
    }
}
