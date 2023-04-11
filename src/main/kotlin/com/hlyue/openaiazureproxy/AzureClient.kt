package com.hlyue.openaiazureproxy

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.hlyue.openaiazureproxy.models.AzureModel
import com.hlyue.openaiazureproxy.models.AzureResponse
import com.hlyue.openaiazureproxy.models.OAIModel
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.body
import org.springframework.web.reactive.function.client.bodyToFlux
import org.springframework.web.reactive.function.server.ServerRequest
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class AzureClient(request: ServerRequest) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    private data class ChatRequest(val model: String)

    private val apiKey = request.headers().firstHeader("Authorization")?.takeLastWhile { it != ' ' }

    private val endpoint = request.pathVariable("azure-resource")

    private var client =
        WebClient.builder().baseUrl("https://$endpoint.openai.azure.com/").defaultHeader("api-key", apiKey).build()


    fun listModels(): Mono<AzureResponse<AzureModel>> {
        class ResponseType : ParameterizedTypeReference<AzureResponse<AzureModel>>()
        return client.get().uri {
            it.queryParam("api-version", "2022-12-01").path("openai/deployments").build()
        }.retrieve().bodyToMono(ResponseType())
    }

    fun chat(chats: Mono<String>): Flux<String> {
        val cachedChats = chats.cache()
        return cachedChats.flatMap {
            val requestModel = jacksonObjectMapper().readValue<ChatRequest>(it).model
            listModels().flatMapIterable { it.data }
                .filter { OAIModel.getOAIModelFromAzure(it.model) == requestModel }.single()
        }.flatMapMany { model ->
            // Convert OpenAI response to an entity is not necessary, and the last SSE response is not JSON.
            // Passing string directly to client is enough.
            client.post().uri {
                it.queryParam("api-version", "2023-03-15-preview")
                    .pathSegment("openai", "deployments", model.id, "chat", "completions").build()
            }.contentType(MediaType.APPLICATION_JSON).body(cachedChats).retrieve().bodyToFlux<String>()
        }
    }

}