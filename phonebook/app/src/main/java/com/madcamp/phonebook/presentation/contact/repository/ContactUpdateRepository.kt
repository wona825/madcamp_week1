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
        val nameUrl = "http://ec2-3-36-76-237.ap-northeast-2.compute.amazonaws.com:8080/api/phone/update-name"
        val favoriteUrl = "http://ec2-3-36-76-237.ap-northeast-2.compute.amazonaws.com:8080/api/phone/update-favorite"
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
