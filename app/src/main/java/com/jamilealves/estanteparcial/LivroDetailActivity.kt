package com.jamilealves.estanteparcial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jamilealves.estanteparcial.databinding.ActivityLivroDetailBinding

class LivroDetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_LIVRO_ID = "livro_id"
    }

    private lateinit var binding: ActivityLivroDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLivroDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val livroId = intent.getStringExtra(EXTRA_LIVRO_ID)
        val posicao = livrosMock.indexOfFirst { it.id == livroId }
        val livro = livrosMock.getOrNull(posicao) ?: livrosMock.first()
        val posicaoSegura = posicao.coerceAtLeast(0)

        binding.detalheTitulo.text = livro.titulo
        binding.detalheAutor.text = livro.autor
        binding.detalheTagTexto.text = livro.genero
        binding.detalheIniciais.text = iniciaisDoTitulo(livro.titulo)
        binding.detalheStatDecadaValor.text = "${livro.decada}s"
        binding.detalheStatPaginasValor.text = livro.paginas?.toString() ?: "—"
        binding.detalheStatStatusValor.text =
            if (livro.status == StatusLeitura.LIDO) "Lido" else "Quero Ler"

        val nota = livro.nota
        binding.detalheEstrelas.text =
            if (nota != null) "★".repeat(nota) + "☆".repeat(5 - nota) else "Ainda não avaliado"

        binding.detalheCapa.backgroundTintList =
            ContextCompat.getColorStateList(this, corLombadaPara(posicaoSegura))
        binding.detalheTagFundo.setBackgroundResource(tagFundoPara(posicaoSegura))
        binding.detalheTagTexto.setTextColor(ContextCompat.getColor(this, tagCorPara(posicaoSegura)))

        binding.btnVoltar.setOnClickListener { finish() }

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(0, systemBars.top, 0, systemBars.bottom)
            insets
        }
    }
}