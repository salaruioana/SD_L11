from state_worker import run_state_machine_worker

transitions = {
    0: ("Starea 01", "stare_01"),
    1: ("Starea 10", "stare_10")
}

if __name__ == "__main__":
    # is_final=True activează mesajul de succes când lista devine goală în această stare
    run_state_machine_worker(current_state="11", topic_in="stare_11", transition_table=transitions, is_final=True)