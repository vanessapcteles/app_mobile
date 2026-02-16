package pt.atec.mobile_app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.mobile_app.ui.theme.*

/**
 * Ecrã principal de Dashboard após o login.
 * Apresenta as opções de navegação principais com um design moderno e limpo.
 */
@Composable
fun DashboardScreen(
    nome: String,
    onNavigate: (String) -> Unit,
    onBackClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AmigoBackground) // Fundo cinza suave para profundidade
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Cabeçalho com Botão de Logout
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextButton(
                onClick = onBackClick,
                colors = ButtonDefaults.textButtonColors(contentColor = AmigoPurple)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Sair")
                Spacer(modifier = Modifier.width(8.dp))
                Text("Terminar Sessão", fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Logótipo centralizado
        Image(
            painter = painterResource(id = R.drawable.logo_amigo_app_mobile_sem_fundo),
            contentDescription = "Logo AMIGO",
            modifier = Modifier.size(220.dp)
        )

        // Boas-vindas com tipografia refinada
        Text(
            text = "Olá, $nome!",
            style = MaterialTheme.typography.headlineMedium,
            color = AmigoPurple,
            fontWeight = FontWeight.ExtraBold
        )
        Text(
            text = "Bem-vindo ao Dashboard",
            style = MaterialTheme.typography.bodyLarge,
            color = AmigoTextSecondary
        )

        Spacer(modifier = Modifier.height(40.dp))

        // Grelha de navegação com cartões modernos
        Column(verticalArrangement = Arrangement.spacedBy(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                DashboardCard(
                    titulo = "Cursos",
                    icone = Icons.Default.MenuBook,
                    corStart = AmigoPurple,
                    corEnd = AmigoPurpleDark,
                    modifier = Modifier.weight(1f)
                ) { onNavigate("cursos") }
                
                DashboardCard(
                    titulo = "Formandos",
                    icone = Icons.Default.People,
                    corStart = AmigoBlue,
                    corEnd = AmigoBlueDark,
                    modifier = Modifier.weight(1f)
                ) { onNavigate("formandos") }
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                DashboardCard(
                    titulo = "Formadores",
                    icone = Icons.Default.Badge,
                    corStart = AmigoBlue,
                    corEnd = AmigoBlueDark,
                    modifier = Modifier.weight(1f)
                ) { onNavigate("formadores") }
                
                DashboardCard(
                    titulo = "Salas",
                    icone = Icons.Default.MeetingRoom,
                    corStart = AmigoPurple,
                    corEnd = AmigoPurpleDark,
                    modifier = Modifier.weight(1f)
                ) { onNavigate("salas") }
            }
        }
    }
}

/**
 * Componente de cartão individual do Dashboard com gradiente e sombras suaves.
 */
@Composable
fun DashboardCard(
    titulo: String,
    icone: ImageVector,
    corStart: Color,
    corEnd: Color,
    modifier: Modifier,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = modifier.height(130.dp),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(corStart, corEnd)))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = icone,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(36.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = titulo,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    letterSpacing = 0.5.sp
                )
            }
        }
    }
}
