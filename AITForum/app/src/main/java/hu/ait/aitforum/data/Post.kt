package hu.ait.aitforum.data

data class Post(
    var uid: String = "",
    var author: String = "",
    var postDate: String = "",
    var postTitle: String = "",
    var postBody: String = ""
)

data class PostWithId(
    var postId: String = "",
    var post: Post
)