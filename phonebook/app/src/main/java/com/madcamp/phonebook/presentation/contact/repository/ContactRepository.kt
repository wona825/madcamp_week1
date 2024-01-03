package com.madcamp.phonebook.presentation.contact.repository

import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.response.ContactResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.post
import io.ktor.http.ContentType
import io.ktor.http.contentType

object ContactRepository {
    suspend fun fetchUsers(contactList: List<Contact>): List<ContactResponse> {

        val url = "contact_match_all_url"

        val httpClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        return httpClient.use {
            it.post<List<ContactResponse>>(url) {
                contentType(ContentType.Application.Json)
                body = contactList
            }
        }
    }
}
