package pt.atec.mobile_app

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pt.atec.mobile_app.ui.theme.*

/**
 * Ecrã que apresenta a listagem de formadores com design moderno, clean e animações de transição.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormadoresScreen(token: String, onBackClick: () -> Unit) {
    // Estado para controlar a navegação interna entre lista e detalhe
    var exibindoDetalhe by remember { mutableStateOf(false) }
    var formadorSelecionado by remember { mutableStateOf<FormadorData?>(null) }
    
    var listaFormadores by remember { mutableStateOf<List<FormadorData>>(emptyList()) }
    var aCarregar by remember { mutableStateOf(true) }
    var erro by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        try {
            val service = APIService.create()
            val tokenHeader = if (token.startsWith("Bearer ")) token else "Bearer $token"
            val response = service.getFormadores(tokenHeader)
            listaFormadores = response.filterNotNull()
            aCarregar = false
        } catch (e: Exception) {
            erro = "Erro ao carregar dados: ${e.message}"
            aCarregar = false
        }
    }

    val listaFiltrada = listaFormadores.filter {
        it.nomeExibicao.contains(searchQuery, ignoreCase = true) || 
        (it.email?.contains(searchQuery, ignoreCase = true) ?: false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (exibindoDetalhe) "Ficha do Formador" else "Equipa Pedagógica", 
                        fontWeight = FontWeight.Bold 
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = {
                        if (exibindoDetalhe) {
                            exibindoDetalhe = false
                            formadorSelecionado = null
                        } else {
                            onBackClick()
                        }
                    }) {
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
        Box(modifier = Modifier.padding(padding).fillMaxSize().background(AmigoBackground)) {
            AnimatedContent(
                targetState = exibindoDetalhe,
                transitionSpec = {
                    if (!targetState) { // Voltando para a lista
                        slideInHorizontally(animationSpec = tween(400), initialOffsetX = { -it }) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(400), targetOffsetX = { it }) + fadeOut()
                    } else { // Indo para o detalhe
                        slideInHorizontally(animationSpec = tween(400), initialOffsetX = { it }) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(400), targetOffsetX = { -it }) + fadeOut()
                    }
                },
                label = "transicao_formadores"
            ) { mostrandoDetalhe ->
                if (mostrandoDetalhe) {
                    // --- VISTA DETALHADA ---
                    formadorSelecionado?.let { formador ->
                        Column(
                            modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp).padding(top = 24.dp).verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(120.dp),
                                shape = CircleShape,
                                color = AmigoSurface,
                                shadowElevation = 6.dp
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(Icons.Default.Person, null, modifier = Modifier.size(64.dp), tint = AmigoBlue)
                                }
                            }

                            Spacer(modifier = Modifier.height(20.dp))
                            Text(formador.nomeExibicao, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = AmigoPurple)
                            
                            if (formador.username != null) {
                                Text(text = "@${formador.username}", fontSize = 16.sp, color = AmigoTextSecondary)
                            }

                            Spacer(modifier = Modifier.height(32.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(containerColor = AmigoSurface),
                                shape = RoundedCornerShape(20.dp),
                                elevation = CardDefaults.cardElevation(2.dp)
                            ) {
                                Column(modifier = Modifier.padding(20.dp)) {
                                    InfoRowFormador(
                                        icon = Icons.Default.Email,
                                        label = "Email Institucional",
                                        value = formador.email ?: "Não disponível"
                                    )
                                }
                            }
                        }
                    }
                } else {
                    // --- VISTA DA LISTAGEM ---
                    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            modifier = Modifier.fillMaxWidth().padding(vertical = 12.dp),
                            placeholder = { Text("Pesquisar formador...") },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null, tint = AmigoBlue) },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(focusedBorderColor = AmigoBlue, unfocusedBorderColor = Color.LightGray),
                            singleLine = true
                        )

                        if (aCarregar) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(color = AmigoBlue)
                            }
                        } else if (listaFiltrada.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("Nenhum formador encontrado.", color = AmigoTextSecondary)
                            }
                        } else {
                            LazyColumn(contentPadding = PaddingValues(bottom = 16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(listaFiltrada) { formador ->
                                    ElevatedCard(
                                        onClick = { 
                                            formadorSelecionado = formador
                                            exibindoDetalhe = true
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = AmigoSurface),
                                        elevation = CardDefaults.elevatedCardElevation(3.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(16.dp).fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                                            Surface(modifier = Modifier.size(48.dp), shape = CircleShape, color = AmigoBlueLight) {
                                                Box(contentAlignment = Alignment.Center) {
                                                    Icon(Icons.Default.Person, null, tint = AmigoBlue)
                                                }
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column {
                                                Text(text = formador.nomeExibicao, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = AmigoPurple)
                                                Text(text = formador.email ?: "", fontSize = 14.sp, color = AmigoTextSecondary)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoRowFormador(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = AmigoBlueLight) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, null, tint = AmigoBlue, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 12.sp, color = AmigoTextSecondary)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AmigoTextPrimary)
        }
    }
}
