# openai-azure-proxy
A server to delegate OpenAI chat requests to Azure OpenAI service

Goal:
- Make chatgpt clients ([OpenCat](https://opencat.app)) work.

Non Goal:
- Support all OpenAI/ChatGPT APIs

## How to use

Their is a deployed version on Google Cloud Run: `https://openai-azure-proxy-wykw6ffdpa-de.a.run.app`.

### Setup Azure OpenAI

Refer to [Azure OpenAI doc](https://azure.microsoft.com/en-us/products/cognitive-services/openai-service) for details.
Make sure `gpt-35-turbo` is deployed in your Azure OpenAI studio.

![image](https://user-images.githubusercontent.com/2534277/228407155-1abc7f6d-6def-48f1-9f87-71b692f5cb8a.png)

### Settings in OpenCat

- Model: gpt-3.5-turbo(1)
- API key: Your **Azure** OpenAI key
- Custom API Domain: `openai-azure-proxy-wykw6ffdpa-de.a.run.app/azure/$YOUR ENDPOINT`.
Endpoint is in your Azure Portal, such as: `https://your-deployment-id.openai.azure.com`.
Please use **your-deployment-id** part in the custom API domain. The full Custom API domain should be like:
`openai-azure-proxy-wykw6ffdpa-de.a.run.app/azure/YOUR_DEPLOYMENT_ID`.
- Check **Send API Key**

[#] GPT4: Currently I do not have a GPT4 enabled Azure OpenAI deployment.

## Self deployment

- Docker: https://github.com/richard1122/openai-azure-proxy/pkgs/container/openai-azure-proxy
- Spring Boot: Check source code to build with gradle.
