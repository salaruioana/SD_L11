# state_worker.py
from kafka import KafkaConsumer, KafkaProducer
import json


def run_state_machine_worker(current_state, topic_in, transition_table, is_final=False):
    # Configurare Consumer: auto_offset_reset='latest' înseamnă că prinde doar mesajele noi
    consumer = KafkaConsumer(
        topic_in,
        bootstrap_servers=['localhost:9092'],
        group_id=f'group_{current_state}',
        auto_offset_reset='latest',
        value_deserializer=lambda m: json.loads(m.decode('utf-8'))
    )

    # Configurare Producer
    producer = KafkaProducer(
        bootstrap_servers=['localhost:9092'],
        value_serializer=lambda v: json.dumps(v).encode('utf-8')
    )

    print(f" Microserviciul [{current_state}] ascultă pe topicul '{topic_in}'...")

    try:
        for message in consumer:
            inputs_list = message.value
            print(f"\n[Stare {current_state}] Primit secvența rămasă: {inputs_list}")

            # Cazul 1: Lista e goală și am ajuns în starea finală
            if is_final and not inputs_list:
                print(f" [Stare {current_state}] SUCCES! Automatul s-a oprit în STARE FINALĂ. Output = 1")
                continue

            # Cazul 2: Lista e goală dar nu suntem în starea finală
            if not inputs_list:
                print(f"[Stare {current_state}] Secvența s-a terminat, dar nu suntem în stare finală.")
                continue

            # Consumăm prima valoare din listă
            current_input = inputs_list.pop(0)
            print(f"[Stare {current_state}] Consumat valoarea: {current_input}. Listă rămasă: {inputs_list}")

            # Verificăm tranziția în funcție de input (0 sau 1)
            if current_input in transition_table:
                next_state, next_topic = transition_table[current_input]
                print(f"➔ Tranziție: Din {current_state} cu input {current_input} ➔ Plecăm spre {next_state}")

                # Trimitem restul listei modificate către următorul microserviciu
                producer.send(next_topic, inputs_list)
                producer.flush()
            else:
                print(f" Input invalid ({current_input}) pentru starea {current_state}.")

    except KeyboardInterrupt:
        print(f"\nOprit microserviciul {current_state}.")
    finally:
        consumer.close()