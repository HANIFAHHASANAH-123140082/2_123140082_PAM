package org.example.project

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import org.example.project.ui.ProfileTextField
import org.example.project.viewmodel.ProfileViewModel
import androidx.compose.runtime.remember
// =============================================
// THEME
// =============================================
private val LightColors = lightColorScheme(
    primary = Color(0xFF1565C0),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFD6E4FF),
    secondary = Color(0xFF455A64),
    background = Color(0xFFF8F9FA),
    surface = Color(0xFFFFFFFF),
    onBackground = Color(0xFF1A1A2E),
    onSurface = Color(0xFF1A1A2E),
    surfaceVariant = Color(0xFFEEEEEE)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFF90CAF9),
    onPrimary = Color(0xFF003258),
    primaryContainer = Color(0xFF1565C0),
    secondary = Color(0xFF90A4AE),
    background = Color(0xFF121212),
    surface = Color(0xFF1E1E2E),
    onBackground = Color(0xFFE8EAF6),
    onSurface = Color(0xFFE8EAF6),
    surfaceVariant = Color(0xFF2D2D3E)
)

// =============================================
// APP ENTRY POINT
// =============================================
@Composable
fun App() {
    val viewModel: ProfileViewModel = remember { ProfileViewModel() }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MaterialTheme(
        colorScheme = if (uiState.isDarkMode) DarkColors else LightColors
    ) {
        AnimatedContent(
            targetState = uiState.isEditing,
            transitionSpec = { fadeIn(tween(300)) togetherWith fadeOut(tween(300)) }
        ) { isEditing ->
            if (isEditing) {
                EditProfileScreen(
                    editName = uiState.editName,
                    editBio = uiState.editBio,
                    editEmail = uiState.editEmail,
                    editPhone = uiState.editPhone,
                    editLocation = uiState.editLocation,
                    isDarkMode = uiState.isDarkMode,
                    onNameChange = viewModel::onNameChange,
                    onBioChange = viewModel::onBioChange,
                    onEmailChange = viewModel::onEmailChange,
                    onPhoneChange = viewModel::onPhoneChange,
                    onLocationChange = viewModel::onLocationChange,
                    onSave = viewModel::saveProfile,
                    onCancel = viewModel::cancelEditing,
                    onToggleDarkMode = viewModel::toggleDarkMode
                )
            } else {
                ProfileScreen(
                    name = uiState.profile.name,
                    bio = uiState.profile.bio,
                    email = uiState.profile.email,
                    phone = uiState.profile.phone,
                    location = uiState.profile.location,
                    isDarkMode = uiState.isDarkMode,
                    onEditClick = viewModel::startEditing,
                    onToggleDarkMode = viewModel::toggleDarkMode
                )
            }
        }
    }
}

// =============================================
// PROFILE VIEW SCREEN
// =============================================
@Composable
fun ProfileScreen(
    name: String,
    bio: String,
    email: String,
    phone: String,
    location: String,
    isDarkMode: Boolean,
    onEditClick: () -> Unit,
    onToggleDarkMode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Header dark mode toggle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "My Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (isDarkMode) "🌙" else "☀️", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onToggleDarkMode() }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Avatar
        Box(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name.first().uppercaseChar().toString(),
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = bio,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Info card
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                InfoRow(icon = Icons.Default.Email, label = "Email", value = email)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(icon = Icons.Default.Phone, label = "Phone", value = phone)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                InfoRow(icon = Icons.Default.LocationOn, label = "Location", value = location)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(Icons.Default.Edit, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Edit Profile", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = label, fontSize = 11.sp, color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
            Text(text = value, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
        }
    }
}

// =============================================
// EDIT PROFILE SCREEN
// =============================================
@Composable
fun EditProfileScreen(
    editName: String,
    editBio: String,
    editEmail: String,
    editPhone: String,
    editLocation: String,
    isDarkMode: Boolean,
    onNameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPhoneChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit,
    onToggleDarkMode: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Edit Profile",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = if (isDarkMode) "🌙" else "☀️", fontSize = 18.sp)
                Spacer(modifier = Modifier.width(6.dp))
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onToggleDarkMode() }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Semua TextField pakai ProfileTextField (State Hoisting)
        ProfileTextField(label = "Nama Lengkap", value = editName, onValueChange = onNameChange)
        ProfileTextField(label = "Bio", value = editBio, onValueChange = onBioChange, singleLine = false)
        ProfileTextField(label = "Email", value = editEmail, onValueChange = onEmailChange)
        ProfileTextField(label = "Phone", value = editPhone, onValueChange = onPhoneChange)
        ProfileTextField(label = "Location", value = editLocation, onValueChange = onLocationChange)

        Spacer(modifier = Modifier.height(28.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.weight(1f).height(52.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Batal", fontSize = 16.sp)
            }

            Button(
                onClick = onSave,
                modifier = Modifier.weight(1f).height(52.dp),
                shape = RoundedCornerShape(12.dp),
                enabled = editName.isNotBlank()
            ) {
                Text("Simpan", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}