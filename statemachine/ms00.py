from state_worker import run_state_machine_worker

# Definim unde pleacă automatul când primește 0 sau 1
transitions = {
    0: ("Starea 10", "stare_10"),
    1: ("Starea 01", "stare_01")
}

if __name__ == "__main__":
    run_state_machine_worker(current_state="00", topic_in="stare_00", transition_table=transitions)