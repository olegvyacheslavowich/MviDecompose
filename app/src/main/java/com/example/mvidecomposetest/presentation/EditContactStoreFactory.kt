package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.example.mvidecomposetest.presentation.EditContactStore.Intent

class EditContactStoreFactory(
    private val storeFactory: StoreFactory
) {

    private val store: Store<EditContactStore.Intent, EditContactStore.State, EditContactStore.Label> =
        storeFactory.create(
            name = "EditContactStoreFactory",
            initialState = EditContactStore.State("", ""),

            )

    private sealed interface Action

    private sealed interface Message {
        data class ChangeUserName(val userName: String) : Message
        data class ChangePhoneNumber(val phoneNumber: String) : Message
    }

    private object ReducerImpl : Reducer<EditContactStore.State, Message> {
        override fun EditContactStore.State.reduce(msg: Message): EditContactStore.State =
            when (msg) {
                is Message.ChangePhoneNumber -> {
                    copy(phoneNumber = msg.phoneNumber)
                }

                is Message.ChangeUserName -> {
                    copy(userName = msg.userName)
                }
            }

    }

}