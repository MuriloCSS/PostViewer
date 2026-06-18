package br.edu.ifsp.scl.sc3038467.postviewer.data

import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query



@Entity(tableName = "tabela_comentarios_locais")
data class ComentarioLocal(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val postId: Int,
    val texto: String
)

@Dao
interface ComentarioDao {
    @Insert
    suspend fun salvarComentario(comentario: ComentarioLocal)

    @Query("SELECT * FROM tabela_comentarios_locais WHERE postId = :postId")
    suspend fun buscarComentariosDoPost(postId: Int): List<ComentarioLocal>
}
