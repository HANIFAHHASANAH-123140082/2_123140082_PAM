package org.example.project

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

data class News(
    val id: Int,
    val title: String,
    val category: String,
    val content: String
)

val newsDatabase = listOf(
    News(1, "Teknologi AI Semakin Canggih", "Teknologi", "AI kini bisa menulis kode sendiri"),
    News(2, "Timnas Indonesia Menang", "Olahraga", "Timnas menang 3-0 lawan Malaysia"),
    News(3, "Harga BBM Turun", "Ekonomi", "Pemerintah umumkan penurunan harga BBM"),
    News(4, "Kotlin Multiplatform Stabil", "Teknologi", "JetBrains rilis KMP versi stabil"),
    News(5, "Liga Champions Malam Ini", "Olahraga", "Real Madrid vs Barcelona pukul 02.00"),
    News(6, "Inflasi Terkendali", "Ekonomi", "Bank Indonesia catat inflasi 2.5%"),
    News(7, "Robot Humanoid Baru", "Teknologi", "Tesla perkenalkan Optimus Gen 3"),
    News(8, "Olimpiade 2028 Persiapan", "Olahraga", "Los Angeles siapkan venue olimpiade"),
    News(9, "Startup Indonesia Unicorn", "Ekonomi", "Startup baru Indonesia raih status unicorn"),
    News(10, "Android 16 Diluncurkan", "Teknologi", "Google rilis Android 16 dengan fitur AI")
)

fun newsFlow(): Flow<News> = flow {
    var index = 0
    while (true) {
        emit(newsDatabase[index % newsDatabase.size])
        index++
        delay(2000)
    }
}

suspend fun fetchNewsDetail(news: News): String {
    delay(500)
    return """
        === DETAIL BERITA ===
        Judul   : ${news.title}
        Kategori: ${news.category}
        Isi     : ${news.content}
        ====================
    """.trimIndent()
}

class NewsViewModel {
    private val _readCount = MutableStateFlow(0)
    val readCount: StateFlow<Int> = _readCount.asStateFlow()

    fun incrementReadCount() {
        _readCount.value++
    }

    fun getReadCount(): Int = _readCount.value
}

suspend fun runNewsFeedSimulator() {
    val viewModel = NewsViewModel()
    val filterCategory = "Teknologi"

    println("╔══════════════════════════════════════╗")
    println("║      NEWS FEED SIMULATOR             ║")
    println("║  Hanifah Hasanah - 123140082         ║")
    println("║  Filter Kategori: $filterCategory      ║")
    println("╚══════════════════════════════════════╝")
    println()

    val countJob = CoroutineScope(Dispatchers.Default).launch {
        viewModel.readCount.collect { count ->
            if (count > 0) {
                println(">> Total berita dibaca: $count berita")
            }
        }
    }

    newsFlow()
        .filter { news -> news.category == filterCategory }
        .map { news -> "[${news.category.uppercase()}] ${news.title}" }
        .onEach { viewModel.incrementReadCount() }
        .take(5)
        .collect { formattedNews ->
            println("Berita baru: $formattedNews")
            val detail = withContext(Dispatchers.Default) {
                fetchNewsDetail(
                    newsDatabase.first {
                        "[${it.category.uppercase()}] ${it.title}" == formattedNews
                    }
                )
            }
            println(detail)
        }

    countJob.cancel()
    println("\nSimulasi selesai! Total dibaca: ${viewModel.getReadCount()} berita")
}