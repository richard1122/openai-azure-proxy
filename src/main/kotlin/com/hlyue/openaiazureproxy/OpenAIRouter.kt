package com.hlyue.openaiazureproxy

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RequestPredicates
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.RouterFunctions
import org.springframework.web.reactive.function.server.ServerResponse

@Configuration(proxyBeanMethods = false)
class OpenAIRouter {
    @Bean
    fun route(openAIController: OpenAIController): RouterFunction<ServerResponse> {
        val oaiRouters = RouterFunctions.route(RequestPredicates.GET("/v1/models"), openAIController::listModels)
            .andRoute(RequestPredicates.POST("/v1/chat/completions"), openAIController::chat)
        return RouterFunctions.nest(RequestPredicates.path("/azure/{azure-resource}"), oaiRouters)
    }
}