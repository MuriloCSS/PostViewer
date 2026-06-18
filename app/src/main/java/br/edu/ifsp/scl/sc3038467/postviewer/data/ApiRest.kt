package br.edu.ifsp.scl.sc3038467.postviewer.data

import br.edu.ifsp.scl.sc3038467.postviewer.model.Comment
import br.edu.ifsp.scl.sc3038467.postviewer.model.Post
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("posts")
    suspend fun buscarPosts(): List<Post>

    @GET("posts/{id}/comments")
    suspend fun buscarComentarios(@Path("id") postId: Int): List<Comment>
}
