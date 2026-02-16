package pt.atec.mobile_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MeetingRoom
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.mobile_app.ui.theme.*

/**
 * Modelo de dados que representa uma sala de formação.
 */
data class Sala(val nome: String, val bloco: String, val estaLivre: Boolean)

/**
 * Ecrã que apresenta a listagem das salas de formação e a sua disponibilidade com design moderno.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SalasScreen(onBackClick: () -> Unit) {
    // Lista estática de salas para demonstração
    val listaSalas = listOf(
        Sala("Sala Programação", "Bloco A", true),
        Sala("Sala Redes", "Bloco B", false),
        Sala("Sala Cibersegurança", "Bloco C", true),
        Sala("Sala Informática", "Bloco D", false)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestão de Salas", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AmigoSurface,
                    titleContentColor = AmigoPurple
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AmigoBackground)
                .padding(padding)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(listaSalas) { sala ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = AmigoSurface),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(sala.nome, fontWeight = FontWeight.Bold, color = AmigoPurple) },
                            supportingContent = { Text(sala.bloco, color = AmigoTextSecondary) },
                            leadingContent = {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    color = if (sala.estaLivre) AmigoBlueLight else Color.LightGray.copy(alpha = 0.2f)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            Icons.Default.MeetingRoom, 
                                            contentDescription = null, 
                                            tint = if (sala.estaLivre) AmigoBlue else Color.Gray
                                        )
                                    }
                                }
                            },
                            trailingContent = {
                                // Badge estilizado para indicar disponibilidade
                                Surface(
                                    color = if (sala.estaLivre) AmigoBlue else Color.Gray.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(8.dp)
                                ) {
                                    Text(
                                        text = if (sala.estaLivre) "LIVRE" else "OCUPADA",
                                        color = if (sala.estaLivre) Color.White else Color.Gray,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            },
                            colors = ListItemDefaults.colors(containerColor = Color.Transparent)
                        )
                    }
                }
            }
        }
    }
}
