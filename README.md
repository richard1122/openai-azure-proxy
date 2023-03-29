# openai-azure-proxy
A server to delegate OpenAI chat requests to Azure OpenAI service

Goal:
- Make chatgpt clients ([OpenCat](https://opencat.app)) work.

Non Goal:
- Support all OpenAI/ChatGPT APIs

## How to use

Their is a already deployed version on Google Cloud Run: `https://openai-azure-proxy-wykw6ffdpa-de.a.run.app`.

### Setup Azure OpenAI

Refer to [Azure OpenAI doc](https://azure.microsoft.com/en-us/products/cognitive-services/openai-service) for details.
Make sure `gpt-35-turbo` is deployed in your Azure OpenAI studio.

![image](https://user-images.githubusercontent.com/2534277/228407155-1abc7f6d-6def-48f1-9f87-71b692f5cb8a.png)

