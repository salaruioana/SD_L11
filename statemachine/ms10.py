from state_worker import run_state_machine_worker

transitions = {
    0: ("Starea 11", "stare_11"),
    1: ("Starea 00", "stare_00")
}

if __name__ == "__main__":
    run_state_machine_worker(current_state="10", topic_in="stare_10", transition_table=transitions)