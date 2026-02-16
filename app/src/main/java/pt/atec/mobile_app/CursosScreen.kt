package pt.atec.mobile_app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MenuBook
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
 * Modelo de dados que representa um curso da oferta formativa.
 */
data class Curso(
    val nome: String,
    val area: String,
    val duracao: String,
    val modulos: List<String>,
    val corIcone: Color = AmigoPurple
)

/**
 * Lista estática com os cursos disponíveis na aplicação.
 */
val listaCursosCompleta = listOf(
    Curso(
        "Desenvolvimento Mobile",
        "Informática",
        "1200h",
        listOf("Introdução ao Kotlin", "Layouts com Jetpack Compose", "Arquitetura MVVM", "Integração com APIs", "Bases de Dados Locais")
    ),
    Curso(
        "Cibersegurança",
        "Redes",
        "900h",
        listOf("Segurança de Redes", "Ethical Hacking", "Criptografia", "Gestão de Incidentes", "Auditoria de Sistemas")
    )
)

/**
 * Ecrã que apresenta a listagem de todos os cursos disponíveis com design moderno.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CursosScreen(
    onBackClick: () -> Unit,
    onCursoClick: (Curso) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Oferta Formativa", fontWeight = FontWeight.Bold) },
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
                items(listaCursosCompleta) { curso ->
                    ElevatedCard(
                        onClick = { onCursoClick(curso) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.elevatedCardColors(containerColor = AmigoSurface),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp)
                    ) {
                        ListItem(
                            headlineContent = { Text(curso.nome, fontWeight = FontWeight.Bold, color = AmigoPurple) },
                            supportingContent = { Text("${curso.area} • ${curso.duracao}", color = AmigoTextSecondary) },
                            leadingContent = {
                                Surface(
                                    modifier = Modifier.size(48.dp),
                                    shape = RoundedCornerShape(12.dp),
                                    color = AmigoPurpleLight
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.MenuBook, contentDescription = null, tint = AmigoPurple)
                                    }
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

/**
 * Ecrã que apresenta os detalhes e módulos de um curso selecionado.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalheCursoScreen(
    curso: Curso,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Curso", fontWeight = FontWeight.Bold) },
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
                .verticalScroll(rememberScrollState())
                .padding(24.dp)
        ) {
            Text(text = curso.nome, fontSize = 26.sp, color = AmigoPurple, fontWeight = FontWeight.ExtraBold)
            Spacer(modifier = Modifier.height(8.dp))
            Surface(
                color = AmigoBlueLight,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = "${curso.area} | ${curso.duracao}",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    color = AmigoBlue,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(text = "Módulos do Curso", fontWeight = FontWeight.Bold, color = AmigoTextPrimary, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                curso.modulos.forEachIndexed { index, modulo ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AmigoSurface),
                        shape = RoundedCornerShape(12.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Surface(
                                modifier = Modifier.size(28.dp),
                                shape = CircleShape,
                                color = AmigoBackground
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Text(text = "${index + 1}", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AmigoPurple)
                                }
                            }
                            Spacer(modifier = Modifier.width(16.dp))
                            Text(text = modulo, style = MaterialTheme.typography.bodyMedium, color = AmigoTextPrimary)
                        }
                    }
                }
            }
        }
    }
}
