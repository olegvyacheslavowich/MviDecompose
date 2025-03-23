package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store

interface EditContactStore :
    Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> {

    sealed class Label {
        object ContactSaved : Label()
    }

    sealed interface Intent {
        data class ChangeUserName(val userName: String) : Intent
        data class ChangePhoneNumber(val phoneNumber: String) : Intent
        object SaveContact : Intent
    }

    data class State(
        val id: Int,
        val userName: String,
        val phoneNumber: String
    )

}