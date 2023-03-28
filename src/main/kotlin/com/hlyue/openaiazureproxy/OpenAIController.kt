package com.hlyue.openaiazureproxy

import com.hlyue.openaiazureproxy.models.AzureResponse
import com.hlyue.openaiazureproxy.models.OAIModel
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import reactor.core.publisher.Mono

@Component
class OpenAIController {

    fun listModels(request: ServerRequest): Mono<ServerResponse> {
        val client = AzureClient(request)
        return ServerResponse.ok().body(client.listModels().map {
            AzureResponse(
                data = it.data.map { OAIModel.fromAzureModel(it) }, `object` = it.`object`
            )
        })
    }

    fun chat(request: ServerRequest): Mono<ServerResponse> {
        val client = AzureClient(request)
        val response = client.chat(request.bodyToMono())
        return ServerResponse.ok().sse().body(response)
    }

}