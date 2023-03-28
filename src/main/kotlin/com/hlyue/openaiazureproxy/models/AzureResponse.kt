package com.hlyue.openaiazureproxy.models

data class AzureResponse<T>(
    val data: List<T>,
    val `object`: String,
)

data class AzureModel(
    val model: String,
    val id: String,
)

data class OAIModel(
    val id: String,
    val `object`: String,
    val root: String
) {
    companion object {
        val AZURE_OPENAI_MODEL_MAP = mapOf("gpt-35-turbo" to "gpt-3.5-turbo")

        fun fromAzureModel(model: AzureModel): OAIModel {
            val oaiModelName = AZURE_OPENAI_MODEL_MAP.getOrDefault(model.model, model.model)
            return OAIModel(
                id = oaiModelName,
                `object` = "model",
                root = oaiModelName,
            )
        }
    }
}