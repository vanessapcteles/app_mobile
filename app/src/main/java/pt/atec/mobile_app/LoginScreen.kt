package pt.atec.mobile_app

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

/**
 * Ecrã de Login da aplicação.
 * Permite ao utilizador autenticar-se utilizando email e palavra-passe.
 */
@Composable
fun LoginScreen(onLoginClick: (String, String) -> Unit) {
    // Escopo para chamadas assíncronas (corrotinas)
    val scope = rememberCoroutineScope()
    // Instância do serviço da API (Renomeado para APIService)
    val service = remember { APIService.create() }
    
    // Estados para os campos de entrada e mensagens de erro
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var erroMensagem by remember { mutableStateOf<String?>(null) }

    // Definição das cores padrão do tema
    val azulPrimario = Color(0xFF0077B6)
    val roxoPrimario = Color(0xFF3F3D89)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logótipo da aplicação
        Image(
            painter = painterResource(id = R.drawable.logo_amigo_app_mobile_sem_fundo),
            contentDescription = "Logo AMIGO",
            modifier = Modifier.size(250.dp)
        )

        Text(
            text = "Bem-vindo ao A.M.I.G.O",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = azulPrimario
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de introdução do Email
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            leadingIcon = { Icon(Icons.Default.Person, null, tint = azulPrimario) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = azulPrimario,
                unfocusedBorderColor = Color.LightGray
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de introdução da Palavra-passe
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Palavra-passe") },
            leadingIcon = { Icon(Icons.Default.Lock, null, tint = azulPrimario) },
            visualTransformation = PasswordVisualTransformation(), // Oculta os caracteres
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = azulPrimario,
                unfocusedBorderColor = Color.LightGray
            )
        )

        // Exibição de mensagem de erro, caso exista
        if (erroMensagem != null) {
            Text(text = erroMensagem!!, color = Color.Red, fontSize = 12.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botão para realizar a tentativa de login
        Button(
            onClick = {
                scope.launch {
                    try {
                        val response = service.realizarLogin(LoginRequest(email, password))
                        if (response.token.isNotEmpty()) {
                            // Se o login for bem-sucedido, invoca a função de callback passada por parâmetro
                            val nomeParaMostrar = response.user?.nomeExibicao ?: email
                            onLoginClick(nomeParaMostrar, response.token)
                        }
                    } catch (e: Exception) {
                        // Feedback visual em caso de falha na autenticação ou conexão
                        erroMensagem = "Erro: Dados inválidos ou Servidor offline"
                        Log.e("LOGIN_ERROR", e.message ?: "Erro desconhecido")
                    }
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = roxoPrimario),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Entrar", fontWeight = FontWeight.Bold, fontSize = 18.sp)
        }
    }
}
