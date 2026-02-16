package pt.atec.mobile_app

import android.net.Uri
import android.util.Patterns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import pt.atec.mobile_app.ui.theme.*

/**
 * Modelo de dados que representa um formando na aplicação.
 */
data class FormandoAtivo(
    val nome: String,
    val telemovel: String,
    val email: String,
    val fotoUri: Uri? = null
)

/**
 * Ecrã principal de gestão de formandos com design limpo, moderno e animações fluidas.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EcraListaFormandos(token: String, onBackClick: () -> Unit) {
    // Estados de navegação interna
    var ecrãInterno by remember { mutableStateOf("lista") } // "lista", "detalhe", "formulario"
    var formandoSelecionado by remember { mutableStateOf<FormandoAtivo?>(null) }
    
    val listaLocal = remember { mutableStateListOf<FormandoAtivo>() }
    val listaApi = remember { mutableStateListOf<FormandoAtivo>() }
    var carregando by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        try {
            val service = APIService.create()
            val dados = service.getFormandos("Bearer $token")
            listaApi.clear()
            listaApi.addAll(dados.map { 
                FormandoAtivo(
                    nome = it.nomeExibicao,
                    telemovel = it.telemovel ?: "N/A",
                    email = it.email ?: "N/A"
                )
            })
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            carregando = false
        }
    }

    val listaTotal = listaApi + listaLocal
    var fotoInput by remember { mutableStateOf<Uri?>(null) }
    var nomeInput by remember { mutableStateOf("") }
    var telInput by remember { mutableStateOf("") }
    var mailInput by remember { mutableStateOf("") }

    // Validações
    val isEmailValido = Patterns.EMAIL_ADDRESS.matcher(mailInput).matches()
    val isFormularioValido = nomeInput.isNotBlank() && telInput.length == 9 && isEmailValido

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { fotoInput = it }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        when (ecrãInterno) {
                            "detalhe" -> "Ficha do Formando"
                            "formulario" -> "Novo Registo"
                            else -> "Formandos"
                        }, 
                        fontWeight = FontWeight.Bold 
                    ) 
                },
                navigationIcon = {
                    IconButton(onClick = { 
                        if (ecrãInterno != "lista") {
                            ecrãInterno = "lista"
                            formandoSelecionado = null
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
        },
        floatingActionButton = {
            if (ecrãInterno == "lista") {
                FloatingActionButton(
                    onClick = { ecrãInterno = "formulario" }, 
                    containerColor = AmigoBlue,
                    contentColor = Color.White,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Adicionar")
                }
            }
        }
    ) { p ->
        Box(modifier = Modifier.padding(p).fillMaxSize().background(AmigoBackground)) {
            AnimatedContent(
                targetState = ecrãInterno,
                transitionSpec = {
                    if (targetState == "lista") {
                        slideInHorizontally(animationSpec = tween(400), initialOffsetX = { -it }) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(400), targetOffsetX = { it }) + fadeOut()
                    } else {
                        slideInHorizontally(animationSpec = tween(400), initialOffsetX = { it }) + fadeIn() togetherWith
                        slideOutHorizontally(animationSpec = tween(400), targetOffsetX = { -it }) + fadeOut()
                    }
                },
                label = "transicao_formandos"
            ) { ecrã ->
                when (ecrã) {
                    "detalhe" -> {
                        formandoSelecionado?.let { f ->
                            Column(
                                modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Surface(
                                    modifier = Modifier.size(130.dp),
                                    shape = CircleShape,
                                    color = AmigoSurface,
                                    shadowElevation = 8.dp
                                ) {
                                    if (f.fotoUri != null) {
                                        AsyncImage(
                                            model = f.fotoUri,
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxSize().clip(CircleShape),
                                            contentScale = ContentScale.Crop
                                        )
                                    } else {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.Person, null, modifier = Modifier.size(70.dp), tint = AmigoBlue)
                                        }
                                    }
                                }
                                Spacer(modifier = Modifier.height(20.dp))
                                Text(f.nome, fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = AmigoPurple)
                                Text("Formando Registado", color = AmigoTextSecondary, fontSize = 14.sp)
                                Spacer(modifier = Modifier.height(32.dp))
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = AmigoSurface),
                                    shape = RoundedCornerShape(20.dp),
                                    elevation = CardDefaults.cardElevation(2.dp)
                                ) {
                                    Column(modifier = Modifier.padding(20.dp)) {
                                        InfoRow(icon = Icons.Default.Phone, label = "Contacto Telefónico", value = f.telemovel)
                                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp), color = AmigoBackground)
                                        InfoRow(icon = Icons.Default.Email, label = "Endereço de E-mail", value = f.email)
                                    }
                                }
                            }
                        }
                    }
                    "formulario" -> {
                        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
                            Column(
                                modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                OutlinedTextField(
                                    value = nomeInput, onValueChange = { nomeInput = it },
                                    label = { Text("Nome Completo") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                OutlinedTextField(
                                    value = telInput, 
                                    onValueChange = { input -> 
                                        if (input.all { it.isDigit() } && input.length <= 9) telInput = input 
                                    },
                                    label = { Text("Telemóvel (9 dígitos)") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                                )
                                OutlinedTextField(
                                    value = mailInput, onValueChange = { mailInput = it },
                                    label = { Text("Email") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                                    isError = mailInput.isNotEmpty() && !isEmailValido
                                )
                                if (mailInput.isNotEmpty() && !isEmailValido) {
                                    Text("Introduza um e-mail válido", color = AmigoError, fontSize = 12.sp, modifier = Modifier.padding(start = 8.dp))
                                }
                                
                                if (fotoInput != null) {
                                    AsyncImage(model = fotoInput, contentDescription = null, modifier = Modifier.size(120.dp).clip(RoundedCornerShape(16.dp)).align(Alignment.CenterHorizontally), contentScale = ContentScale.Crop)
                                }
                                
                                Button(
                                    onClick = { launcher.launch("image/*") },
                                    modifier = Modifier.fillMaxWidth(),
                                    shape = RoundedCornerShape(12.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = AmigoBlue.copy(alpha = 0.1f), contentColor = AmigoBlue)
                                ) {
                                    Icon(Icons.Default.PhotoLibrary, null)
                                    Spacer(Modifier.width(8.dp))
                                    Text("Escolher Foto", fontWeight = FontWeight.Bold)
                                }
                            }
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    if (isFormularioValido) {
                                        listaLocal.add(FormandoAtivo(nomeInput, telInput, mailInput, fotoInput))
                                        ecrãInterno = "lista"
                                        nomeInput = ""; telInput = ""; mailInput = ""; fotoInput = null
                                    }
                                },
                                modifier = Modifier.fillMaxWidth().height(56.dp),
                                shape = RoundedCornerShape(16.dp),
                                enabled = isFormularioValido,
                                colors = ButtonDefaults.buttonColors(containerColor = AmigoPurple)
                            ) {
                                Text("Confirmar Registo", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                            }
                        }
                    }
                    else -> {
                        if (carregando) {
                            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator(color = AmigoBlue) }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize(), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                items(listaTotal) { f ->
                                    ElevatedCard(
                                        onClick = { 
                                            formandoSelecionado = f
                                            ecrãInterno = "detalhe"
                                        },
                                        modifier = Modifier.fillMaxWidth(),
                                        shape = RoundedCornerShape(16.dp),
                                        colors = CardDefaults.elevatedCardColors(containerColor = AmigoSurface),
                                        elevation = CardDefaults.elevatedCardElevation(3.dp)
                                    ) {
                                        ListItem(
                                            headlineContent = { Text(f.nome, fontWeight = FontWeight.Bold, color = AmigoPurple) },
                                            supportingContent = { Text(f.email, color = AmigoTextSecondary) },
                                            leadingContent = {
                                                Surface(modifier = Modifier.size(48.dp), shape = CircleShape, color = AmigoBlueLight) {
                                                    if (f.fotoUri != null) {
                                                        AsyncImage(model = f.fotoUri, contentDescription = null, modifier = Modifier.fillMaxSize().clip(CircleShape), contentScale = ContentScale.Crop)
                                                    } else {
                                                        Box(contentAlignment = Alignment.Center) {
                                                            Icon(Icons.Default.Person, null, tint = AmigoBlue)
                                                        }
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
            }
        }
    }
}

@Composable
fun InfoRow(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String, value: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Surface(modifier = Modifier.size(40.dp), shape = CircleShape, color = AmigoBlueLight) {
            Box(contentAlignment = Alignment.Center) {
                Icon(icon, contentDescription = null, tint = AmigoBlue, modifier = Modifier.size(20.dp))
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(label, fontSize = 12.sp, color = AmigoTextSecondary)
            Text(value, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = AmigoTextPrimary)
        }
    }
}
