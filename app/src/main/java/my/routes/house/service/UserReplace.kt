package my.routes.house.service

object UserReplace {
    fun executeMyReplaces(email: String): String {
        var result = email.replace(".", "")
            result = result.replace("#", "")
            result = result.replace("$", "")
            result = result.replace("[", "")
            result = result.replace("]", "")
        return result
    }
}