package com.madcamp.phonebook.presentation.contact.repository

import com.madcamp.phonebook.domain.model.Contact
import com.madcamp.phonebook.domain.response.ContactResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.put
import io.ktor.http.ContentType
import io.ktor.http.contentType

object ContactUpdateRepository {
    suspend fun fetchUsers(contact: Contact, type: Int): List<ContactResponse> {
        val nameUrl = "update_contact_name_url"
        val favoriteUrl = "update_contact_favorite_status_url"
        val url = if (type == 1) nameUrl else favoriteUrl

        val httpClient = HttpClient {
            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }
        }

        return httpClient.use {
            it.put<List<ContactResponse>>(url) {
                contentType(ContentType.Application.Json)
                body = contact
            }
        }
    }
}