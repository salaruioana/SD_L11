from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from kafka import KafkaProducer
import json

# pip install fastapi uvicorn kafka-python
app = FastAPI()

# Configurare Producător Kafka
# value_serializer transformă automat listele noastre în string-uri JSON (bytes)
producer = KafkaProducer(
    bootstrap_servers=['localhost:9092'],
    value_serializer=lambda v: json.dumps(v).encode('utf-8')
)


class InputSequence(BaseModel):
    inputs: list[int]  # Ex: [0, 1, 1, 0]


@app.post("/start")
def start_automation(data: InputSequence):
    if not data.inputs:
        raise HTTPException(status_code=400, detail="Lista de input-uri nu poate fi goală.")

    # Trimitem lista inițială în topicul stării 00
    print(f"Trimit secvența {data.inputs} către topicul stare_00...")
    producer.send('stare_00', data.inputs)
    producer.flush()  # Ne asigurăm că mesajul a fost trimis efectiv

    return {"status": "Accepted", "message": f"Secvența a fost injectată în stare_00."}

#Deschide terminale separate pentru gateway.py și fiecare dintre cele 4 stări (ms00.py, etc.) și pornește-le.

#Rulează comanda de test din terminal (sau folosește Postman):
# curl -X POST "http://127.0.0.1:8000/start" \
#      -H "Content-Type: application/json" \
#      -d '{"inputs": [1, 1, 0, 0]}'
if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="127.0.0.1", port=8000)
