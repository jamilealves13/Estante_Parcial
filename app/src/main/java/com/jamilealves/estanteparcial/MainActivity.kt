package com.jamilealves.estanteparcial

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jamilealves.estanteparcial.databinding.ActivityEstanteBinding
import com.jamilealves.estanteparcial.databinding.EstanteListItemLayoutBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEstanteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityEstanteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.listaLivros.adapter = EstanteAdapter(livrosMock)
        binding.listaLivros.layoutManager = LinearLayoutManager(this)

        val paddingHorizontal = resources.getDimensionPixelSize(R.dimen.screen_padding_horizontal)
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(
                systemBars.left + paddingHorizontal,
                systemBars.top,
                systemBars.right + paddingHorizontal,
                systemBars.bottom
            )
            insets
        }
    }
}

class EstanteAdapter(private val livros: List<Livro>) :
    RecyclerView.Adapter<EstanteAdapter.ViewHolder>() {

    class ViewHolder(val binding: EstanteListItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = EstanteListItemLayoutBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val livro = livros[position]
        val context = holder.binding.root.context

        holder.binding.livroTitulo.text = livro.titulo
        holder.binding.livroAutor.text = livro.autor
        holder.binding.livroTagTexto.text = livro.genero
        holder.binding.livroIniciais.text = iniciaisDoTitulo(livro.titulo)

        val nota = livro.nota
        holder.binding.livroNota.text =
            if (nota != null) "★".repeat(nota) + "☆".repeat(5 - nota) else "Ainda não avaliado"

        holder.binding.livroCapa.backgroundTintList =
            ContextCompat.getColorStateList(context, corLombadaPara(position))
        holder.binding.livroTagFundo.setBackgroundResource(tagFundoPara(position))
        holder.binding.livroTagTexto.setTextColor(ContextCompat.getColor(context, tagCorPara(position)))

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, LivroDetailActivity::class.java)
            intent.putExtra(LivroDetailActivity.EXTRA_LIVRO_ID, livro.id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = livros.size
}

enum class StatusLeitura { LIDO, QUERO_LER }

data class Livro(
    val id: String,
    val titulo: String,
    val autor: String,
    val genero: String,
    val decada: Int,
    val paginas: Int?,
    val status: StatusLeitura,
    val nota: Int?, // 1 a 5 — só quando status == LIDO
)

val livrosMock: List<Livro> = listOf(
    Livro("1", "Duna", "Frank Herbert", "Ficção Científica", 1960, 412, StatusLeitura.LIDO, 5),
    Livro("2", "Cem Anos de Solidão", "Gabriel García Márquez", "Realismo Mágico", 1960, 432, StatusLeitura.LIDO, 5),
    Livro("3", "Solaris", "Stanisław Lem", "Ficção Científica", 1960, 264, StatusLeitura.LIDO, 4),
    Livro("4", "O Conto da Aia", "Margaret Atwood", "Distopia", 1980, 336, StatusLeitura.LIDO, 5),
    Livro("5", "Grande Sertão: Veredas", "João Guimarães Rosa", "Clássico", 1950, 624, StatusLeitura.LIDO, 4),
    Livro("6", "A Redoma de Vidro", "Sylvia Plath", "Romance", 1960, 244, StatusLeitura.QUERO_LER, null),
)

val coresLombada = listOf(
    R.color.spine_brown,
    R.color.spine_maroon,
    R.color.spine_navy,
    R.color.spine_brick,
    R.color.spine_olive,
    R.color.spine_purple,
)

val tagFundos = listOf(R.drawable.tag_bg_sage, R.drawable.tag_bg_tan, R.drawable.tag_bg_gray)
val tagCores = listOf(R.color.tag_scifi_text, R.color.tag_tan_text, R.color.tag_gray_text)

fun corLombadaPara(posicao: Int): Int = coresLombada[posicao % coresLombada.size]
fun tagFundoPara(posicao: Int): Int = tagFundos[posicao % tagFundos.size]
fun tagCorPara(posicao: Int): Int = tagCores[posicao % tagCores.size]

fun iniciaisDoTitulo(titulo: String): String {
    return titulo
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }
}