package org.example.project

import androidx.compose.animation.AnimatedVisibility
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

// =============================================
// COMPOSABLE 1: ProfileHeader
// =============================================
@Composable
fun ProfileHeader(name: String, title: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto profil circular menggunakan Box + Icon
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "Foto Profil",
                tint = Color.White,
                modifier = Modifier.size(60.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Nama
        Text(
            text = name,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Title/jabatan
        Text(
            text = title,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// =============================================
// COMPOSABLE 2: InfoItem
// =============================================
@Composable
fun InfoItem(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icon
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Label dan Value
        Column {
            Text(
                text = label,
                fontSize = 12.sp,
                color = Color.Gray
            )
            Text(
                text = value,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

// =============================================
// COMPOSABLE 3: ProfileCard
// =============================================
@Composable
fun ProfileCard(title: String, content: @Composable () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )
            HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
            Spacer(modifier = Modifier.height(4.dp))
            content()
        }
    }
}

// =============================================
// MAIN APP
// =============================================
@Composable
fun App() {
    MaterialTheme {
        // State untuk AnimatedVisibility (bonus +10%)
        var showContact by remember { mutableStateOf(false) }
        var showBio by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ---- HEADER ----
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
            ) {
                ProfileHeader(
                    name = "Hanifah Hasanah",
                    title = "Mahasiswa Teknik Informatika - ITERA"
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // ---- CARD BIO ----
            ProfileCard(title = "Tentang Saya") {
                // Tombol show/hide bio (AnimatedVisibility)
                AnimatedVisibility(visible = showBio) {
                    Text(
                        text = "Saya adalah mahasiswa Teknik Informatika di Institut Teknologi Sumatera. " +
                                "Saya tertarik dengan pengembangan aplikasi mobile menggunakan Kotlin " +
                                "dan Compose Multiplatform.",
                        modifier = Modifier.padding(16.dp),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Justify
                    )
                }

                Button(
                    onClick = { showBio = !showBio },
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 4.dp)
                        .fillMaxWidth()
                ) {
                    Text(if (showBio) "Sembunyikan Bio" else "Tampilkan Bio")
                }
            }

            // ---- CARD INFORMASI ----
            ProfileCard(title = "Informasi") {
                InfoItem(
                    icon = Icons.Default.Email,
                    label = "Email",
                    value = "hanifah.123140082@student.itera.ac.id"
                )
                InfoItem(
                    icon = Icons.Default.Phone,
                    label = "Phone",
                    value = "+62 812-3456-7890"
                )
                InfoItem(
                    icon = Icons.Default.LocationOn,
                    label = "Location",
                    value = "Lampung Selatan, Indonesia"
                )
            }

            // ---- CARD KONTAK (AnimatedVisibility bonus) ----
            Button(
                onClick = { showContact = !showContact },
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Icon(Icons.Default.Share, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (showContact) "Sembunyikan Kontak" else "Lihat Kontak Sosial")
            }

            AnimatedVisibility(visible = showContact) {
                ProfileCard(title = "Kontak Sosial") {
                    InfoItem(
                        icon = Icons.Default.AccountCircle,
                        label = "GitHub",
                        value = "github.com/HANIFAHHASANAH-123140082"
                    )
                    InfoItem(
                        icon = Icons.Default.Person,
                        label = "LinkedIn",
                        value = "linkedin.com/in/hanifahhasanah"
                    )
                    InfoItem(
                        icon = Icons.Default.Star,
                        label = "NIM",
                        value = "123140082"
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}