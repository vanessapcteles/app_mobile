package pt.atec.mobile_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.animation.core.tween
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

/**
 * Ponto de entrada principal da aplicação.
 * Gere a navegação entre os diferentes ecrãs com animações suaves e direcionais.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            pt.atec.mobile_app.ui.theme.Mobile_appTheme {
                var ecraAtual by remember { mutableStateOf("login") }
                var nomeLogado by remember { mutableStateOf("") }
                var tokenLogado by remember { mutableStateOf("") }
                var cursoSelecionado by remember { mutableStateOf<Curso?>(null) }

                // AnimatedContent permite animar a troca de ecrãs de forma fluida
                AnimatedContent(
                    targetState = ecraAtual,
                    label = "transicao_ecras",
                    transitionSpec = {
                        // Transição especial para o Dashboard (Zoom In ao entrar, Zoom Out ao sair)
                        if (targetState == "dashboard") {
                            (fadeIn(animationSpec = tween(500)) + scaleIn(initialScale = 0.8f))
                                .togetherWith(fadeOut(animationSpec = tween(400)) + scaleOut(targetScale = 1.1f))
                        } 
                        // Ao voltar para o Login (Logout)
                        else if (targetState == "login") {
                            fadeIn(animationSpec = tween(400)) togetherWith 
                            slideOutHorizontally(targetOffsetX = { it }, animationSpec = tween(400)) + fadeOut()
                        }
                        // Navegação para dentro dos menus (Slide da direita para a esquerda)
                        else if (initialState == "dashboard") {
                            slideInHorizontally(initialOffsetX = { it }, animationSpec = tween(450)) + fadeIn() togetherWith 
                            slideOutHorizontally(targetOffsetX = { -it / 2 }, animationSpec = tween(450)) + fadeOut()
                        }
                        // Voltar para o Dashboard (Slide da esquerda para a direita)
                        else if (targetState == "dashboard") {
                            slideInHorizontally(initialOffsetX = { -it }, animationSpec = tween(450)) + fadeIn() togetherWith 
                            slideOutHorizontally(targetOffsetX = { it / 2 }, animationSpec = tween(450)) + fadeOut()
                        }
                        // Transição padrão suave para qualquer outro caso
                        else {
                            fadeIn(animationSpec = tween(400)) togetherWith fadeOut(animationSpec = tween(400))
                        }
                    }
                ) { targetScreen ->
                    when (targetScreen) {
                        "login" -> {
                            LoginScreen(onLoginClick = { nome, token ->
                                nomeLogado = nome
                                tokenLogado = token
                                ecraAtual = "dashboard"
                            })
                        }
                        "dashboard" -> {
                            DashboardScreen(
                                nome = nomeLogado,
                                onNavigate = { destino -> ecraAtual = destino },
                                onBackClick = { ecraAtual = "login" }
                            )
                        }
                        "cursos" -> {
                            CursosScreen(
                                onBackClick = { ecraAtual = "dashboard" },
                                onCursoClick = { curso ->
                                    cursoSelecionado = curso
                                    ecraAtual = "detalhe_curso"
                                }
                            )
                        }
                        "detalhe_curso" -> {
                            cursoSelecionado?.let {
                                DetalheCursoScreen(
                                    curso = it,
                                    onBackClick = { ecraAtual = "cursos" }
                                )
                            }
                        }
                        "formandos" -> {
                            EcraListaFormandos(
                                token = tokenLogado,
                                onBackClick = { ecraAtual = "dashboard" }
                            )
                        }
                        "salas" -> {
                            SalasScreen(onBackClick = { ecraAtual = "dashboard" })
                        }
                        "formadores" -> {
                            FormadoresScreen(
                                token = tokenLogado,
                                onBackClick = { ecraAtual = "dashboard" }
                            )
                        }
                    }
                }
            }
        }
    }
}
