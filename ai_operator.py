from py4j.java_gateway import JavaGateway # type: ignore
from transformers import pipeline # type: ignore
import os

# Configuración del modelo
MODEL_NAME = "bert-base-uncased"
MAX_RESPONSE_LENGTH = 100
TEMPERATURE = 0.7

# Inicializar el modelo de generación de texto
generator = pipeline("text-generation", model=MODEL_NAME)

def generate_response(prompt):
    try:
        response = generator(
            prompt,
            max_length=MAX_RESPONSE_LENGTH,
            temperature=TEMPERATURE,
            num_return_sequences=1,
        )
        return response[0]["generated_text"]
    except Exception as e:
        print(f"Error al generar respuesta: {e}")
        return "Disculpa, no puedo entender tu mensaje."

if __name__ == "__main__":
    # Configurar el servidor Py4J
    gateway = JavaGateway()
    gateway.setFieldName("aiOperator")
    gateway.setField(generate_response)
    gateway.start()