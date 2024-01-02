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

        val url = "http://ec2-3-36-76-237.ap-northeast-2.compute.amazonaws.com:8080/api/phone/match-all"

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