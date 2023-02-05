package com.apiguave.tinderclonecompose.ui.main

object Routes{
    const val Login = "login"
    const val SignUp = "signup"
    private const val AddPictureBaseRoute = "add_picture"
    const val AddPicture = "$AddPictureBaseRoute/{${Arguments.Caller}}"
    const val Home = "home"
    const val EditProfile = "edit_profile"
    const val MatchList = "match_list"
    const val Chat = "chat"
    const val NewMatch = "new_match"

    fun getAddPictureRoute(caller: String) = "$AddPictureBaseRoute/$caller"
}

object Arguments{
    const val Caller = "caller"
}