package pt.atec.mobile_app

import com.google.gson.annotations.SerializedName
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Modelo de dados para Formadores recebidos da API.
 */
data class FormadorData(
    @SerializedName("nome") val nomePt: String?,
    @SerializedName("name") val nomeEn: String?,
    @SerializedName("username") val username: String?,
    @SerializedName("full_name") val fullName: String?,
    @SerializedName("email") val email: String?
) {
    val nomeExibicao: String
        get() {
            val nomeReal = nomePt ?: nomeEn ?: fullName
            if (nomeReal != null) return nomeReal
            val handle = username ?: email?.substringBefore("@")
            return formatarHandle(handle) ?: "Sem Nome"
        }
}

/**
 * Modelo de dados para Formandos recebidos da API.
 */
data class FormandoData(
    @SerializedName("nome") val nome: String?,
    @SerializedName("email") val email: String?,
    @SerializedName("telemovel") val telemovel: String?,
    @SerializedName("username") val username: String?
) {
    val nomeExibicao: String
        get() = nome ?: username ?: email?.substringBefore("@") ?: "Sem Nome"
}

// Modelos para autenticação
data class LoginRequest(val email: String, val password: String)
data class LoginResponse(val token: String, val message: String, val user: UserData?)

/**
 * Modelo de dados do utilizador autenticado.
 */
data class UserData(
    @SerializedName("nome") val nomePt: String?,
    @SerializedName("name") val nomeEn: String?,
    @SerializedName("username") val username: String?,
    val email: String?
) {
    val nomeExibicao: String
        get() {
            val nomeReal = nomePt ?: nomeEn
            if (nomeReal != null) return nomeReal
            val handle = username ?: email?.substringBefore("@")
            return formatarHandle(handle) ?: "Utilizador"
        }
}

/**
 * Função auxiliar para formatar nomes baseados em handles (ex: nome.apelido -> Nome Apelido).
 */
private fun formatarHandle(handle: String?): String? {
    return handle?.split(".")
        ?.joinToString(" ") { it.replaceFirstChar { char -> if (char.isLowerCase()) char.titlecase() else char.toString() } }
}

/**
 * Interface Retrofit que define os endpoints da API.
 * Renomeada para APIService por ser agora uma interface genérica para toda a aplicação.
 */
interface APIService {
    @POST("auth/login")
    suspend fun realizarLogin(@Body request: LoginRequest): LoginResponse
    
    @GET("formadores")
    suspend fun getFormadores(@Header("Authorization") token: String): List<FormadorData>
    
    @GET("formandos")
    suspend fun getFormandos(@Header("Authorization") token: String): List<FormandoData>

    companion object {
        // Endereço base da API (10.0.2.2 aponta para o localhost da máquina host no emulador Android)
        private const val BASE_URL = "http://10.0.2.2:3001/api/"
        
        fun create(): APIService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}
