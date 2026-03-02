from openai import OpenAI

client = OpenAI(api_key="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")

texto_ocr = """
OFERTA ESPECIAL
Viaje a París 5 días

Precio: 1200 €
Otros precios: 950 € y 1100 €

Salidas:
Todos los lunes

El circuito incluye:
Vuelo + Hotel 4 estrellas
"""

prompt = f"""
Extrae del siguiente texto los siguientes campos:
- titulo
- precio_principal
- otros_precios (array vacío si no hay más)
- descripcion_general
- apartados (detecta secciones como Salidas, Precios, El circuito incluye, etc.)

Devuelve exclusivamente JSON válido.

Texto:
{texto_ocr}
"""

response = client.chat.completions.create(
    model="gpt-4o-mini",
    messages=[
        {"role": "user", "content": prompt}
    ],
    temperature=0
)

print(response.choices[0].message.content)