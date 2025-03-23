package com.example.mvidecomposetest.presentation

import com.arkivanov.mvikotlin.core.store.Store
import com.example.mvidecomposetest.domain.Contact

interface ContactListStore :
    Store<ContactListStore.Intent, ContactListStore.State, ContactListStore.Label> {

    sealed interface Label {
        object AddContact : Label
        data class EditContact(val contact: Contact) : Label
    }

    sealed interface Intent {
        class ChangeContact(val contact: Contact) : Intent
        object AddContact : Intent
    }

    data class State(
        val list: List<Contact>
    )

}