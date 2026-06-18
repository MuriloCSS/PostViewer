package br.edu.ifsp.scl.sc3038467.postviewer.model

data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

data class Comment(
    val postId: Int,
    val id: Int,
    val name: String,
    val email: String,
    val body: String
)

data class ComentarioTela(
    val autor: String,
    val texto: String
)