import random
import string

from generation_consts import MAX_SOLUTIONS_NUMBER


class TestCompetitionParticipant:
    def __init__(self, login: string):
        self.number_of_solutions = random.randrange(1, MAX_SOLUTIONS_NUMBER + 1)
        self.login = login
        self.participant_id = 0
        self.competition_id = 0
        self.solutions = []


class TestCompetitionManager:
    def __init__(self, login: string):
        self.login = login
        self.manager_id = 0
        self.competition_id = 0