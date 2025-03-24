package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.example.mvidecomposetest.data.RepositoryImpl
import com.example.mvidecomposetest.domain.Contact
import com.example.mvidecomposetest.domain.EditContactUseCase
import com.example.mvidecomposetest.presentation.EditContactStore.Intent

class EditContactStoreFactory() {


    private val storeFactory: StoreFactory = DefaultStoreFactory()
    private val editContactUseCase: EditContactUseCase = EditContactUseCase(RepositoryImpl)

    fun create(contact: Contact): EditContactStore = object : EditContactStore,
        Store<Intent, EditContactStore.State, EditContactStore.Label> by storeFactory.create(
            name = "EditContactStoreFactory",
            initialState = EditContactStore.State(contact.id, contact.username, contact.phone),
            reducer = ReducerImpl,
            executorFactory = { ExecutorImpl() }
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

    private inner class ExecutorImpl :
        CoroutineExecutor<Intent, Action, EditContactStore.State, Message, EditContactStore.Label>() {

        override fun executeIntent(intent: Intent, getState: () -> EditContactStore.State) {
            when (intent) {
                is Intent.ChangePhoneNumber -> {
                    //отправляем сообщение
                    dispatch(Message.ChangePhoneNumber(phoneNumber = intent.phoneNumber))
                }

                is Intent.ChangeUserName -> {
                    //отправляем сообщение
                    dispatch(Message.ChangeUserName(intent.userName))
                }

                Intent.SaveContact -> {
                    val state = getState()
                    editContactUseCase(
                        Contact(
                            id = state.id,
                            username = state.userName,
                            phone = state.phoneNumber
                        )
                    )
                    //отпавляем лэйбл
                    publish(EditContactStore.Label.ContactSaved)
                }

            }
        }
    }

}