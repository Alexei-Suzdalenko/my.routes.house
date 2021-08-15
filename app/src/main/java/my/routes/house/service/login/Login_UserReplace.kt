package my.routes.house.service.login

object Login_UserReplace {
    fun executeMyReplaces(email: String): String {
        var result = email.replace(".", "")
            result = result.replace("#", "")
            result = result.replace("$", "")
            result = result.replace("[", "")
            result = result.replace("]", "")
        return result
    }
}