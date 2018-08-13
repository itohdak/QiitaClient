package sample.qiitaclient.http

import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.github.kittinunf.fuel.httpGet
import com.google.gson.Gson

val base_url = "http://ec2-52-197-250-179.ap-northeast-1.compute.amazonaws.com"
val get_with_id = base_url + "/user/id/1"

data class UserListResponse(
        var id: Long,
        var name: String
)

data class UserProfile(
        var id: Long = 0,
        var name: String = ""
        // @get:JsonProperty("created_at") var createdAt: Timestamp,
        // @get:JsonProperty("updated_at") var updatedAt: Timestamp
) {
    class Deserializer : ResponseDeserializable<UserProfile> {
        override fun deserialize(content: String) = Gson().fromJson(content, UserProfile::class.java)
    }
}

fun get(target_url: String) : UserProfile {
    /*
    var id: Long = 0L
    var name: String = ""

    target_url.httpGet().responseObject(UserProfile.Deserializer()) { request, response, result ->
        val (user, err) = result
        println(user)
        id = if (user != null) user.id
             else -1
        name = if (user != null) user.name
               else "unknown"
    }
    return UserProfile(id, name)
    */

    var profile: UserProfile?
    try {
        var result = target_url.httpGet().responseObject(UserProfile.Deserializer())
        // println(result.toString())
        profile = result.third.component1()
    } catch (e: java.lang.IllegalStateException) { // 404: Not Found
        println("404: Not Found")
        return UserProfile(-1, "unknown")
    }
    if (profile != null) return profile
    else return UserProfile(-1, "unknown")
}

/*
fun getAllUsers(): ArrayList<UserProfile> {

}
*/