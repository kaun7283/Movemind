package com.movemind.app.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.movemind.app.ui.theme.*

// ── Logo Text ──────────────────────────────────────────────────────────────
@Composable
fun MoveMindLogo(modifier: Modifier = Modifier) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text  = "Move",
            style = MaterialTheme.typography.displayLarge,
            color = TextPrimary,
        )
        Text(
            text  = "Mind",
            style = MaterialTheme.typography.displayLarge,
            color = OrangeVibrant,
        )
    }
}

// ── Orange Accent Divider ──────────────────────────────────────────────────
@Composable
fun OrangeAccentBar(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .width(48.dp)
            .height(3.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(OrangeVibrant)
    )
}

// ── Custom Text Field ──────────────────────────────────────────────────────
@Composable
fun MoveMindTextField(
    value:               String,
    onValueChange:       (String) -> Unit,
    label:               String,
    modifier:            Modifier = Modifier,
    leadingIcon:         ImageVector? = null,
    trailingIcon:        @Composable (() -> Unit)? = null,
    keyboardOptions:     KeyboardOptions = KeyboardOptions.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError:             Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    val borderColor by animateColorAsState(
        targetValue = when {
            isError   -> ErrorRed
            isFocused -> OrangeVibrant
            else      -> SlateMid
        },
        animationSpec = tween(200),
        label = "borderColor",
    )

    Column(modifier = modifier) {
        Text(
            text  = label,
            style = MaterialTheme.typography.labelSmall,
            color = if (isError) ErrorRed else TextSecondary,
            modifier = Modifier.padding(bottom = 6.dp),
        )

        BasicTextField(
            value               = value,
            onValueChange       = onValueChange,
            singleLine          = true,
            textStyle           = MaterialTheme.typography.bodyLarge.copy(color = TextPrimary),
            cursorBrush         = SolidColor(OrangeVibrant),
            keyboardOptions     = keyboardOptions,
            visualTransformation = visualTransformation,
            interactionSource   = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(SurfaceInput)
                .border(1.dp, borderColor, RoundedCornerShape(12.dp))
                .padding(horizontal = 16.dp, vertical = 14.dp),
            decorationBox = { innerTextField ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (leadingIcon != null) {
                        Icon(
                            imageVector = leadingIcon,
                            contentDescription = null,
                            tint     = if (isFocused) OrangeVibrant else TextHint,
                            modifier = Modifier.size(20.dp),
                        )
                        Spacer(Modifier.width(10.dp))
                    }
                    Box(Modifier.weight(1f)) {
                        if (value.isEmpty()) {
                            Text(
                                text  = "Digite aqui...",
                                style = MaterialTheme.typography.bodyLarge,
                                color = TextHint,
                            )
                        }
                        innerTextField()
                    }
                    trailingIcon?.invoke()
                }
            },
        )
    }
}

// ── Primary CTA Button ─────────────────────────────────────────────────────
@Composable
fun MoveMindButton(
    text:      String,
    onClick:   () -> Unit,
    modifier:  Modifier = Modifier,
    enabled:   Boolean = true,
    isLoading: Boolean = false,
) {
    Button(
        onClick  = onClick,
        enabled  = enabled && !isLoading,
        modifier = modifier
            .fillMaxWidth()
            .height(54.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor         = OrangeVibrant,
            contentColor           = TextPrimary,
            disabledContainerColor = OrangeDim,
            disabledContentColor   = TextSecondary,
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                color    = TextPrimary,
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
            )
        } else {
            Text(
                text  = text,
                style = MaterialTheme.typography.labelLarge,
            )
        }
    }
}

// ── Habit Stat Card ────────────────────────────────────────────────────────
@Composable
fun HabitCard(
    icon:    ImageVector,
    title:   String,
    value:   String,
    percent: Float,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(SurfaceCard)
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment     = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(OrangeVibrant.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint     = OrangeVibrant,
                    modifier = Modifier.size(22.dp),
                )
            }
            Text(
                text  = value,
                style = MaterialTheme.typography.titleLarge,
                color = OrangeVibrant,
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            text  = title,
            style = MaterialTheme.typography.bodyMedium,
            color = TextSecondary,
        )

        Spacer(Modifier.height(8.dp))

        // Progress bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp))
                .background(SlateMid)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(fraction = percent.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(2.dp))
                    .background(
                        Brush.horizontalGradient(listOf(OrangeVibrant, OrangeLight))
                    )
            )
        }
    }
}
