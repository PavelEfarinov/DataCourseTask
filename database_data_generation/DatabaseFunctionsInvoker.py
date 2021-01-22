import string

from generation_consts import TURN_NUMBER


class DatabaseFunctionsInvoker:
    def __init__(self, connection):
        self.connection = connection
        self.cursor = connection.cursor()

    def create_base_user(self, login: string):
        self.cursor.execute('select create_base_user(%s,%s)', (login, 'default_password'))
        return self.cursor.fetchone()[0]

    def partake_in_competition(self, login: string, competition_id: int):
        self.cursor.execute('select partake_in_competition(%s,%s)', (login, competition_id))
        return self.cursor.fetchone()[0]

    def create_test_competition(self, competition_number: int):
        self.cursor.execute('select id from testing_system where url = %s', ('https://www.chess.com',))
        ts_id = self.cursor.fetchone()[0]
        self.cursor.execute('select create_competition(%s,%s,localtimestamp,%s,%s)',
                            ('test_name_{0}'.format(competition_number),
                             'test_description_{0}'.format(competition_number),
                             'online',
                             ts_id,))
        new_competition_id = self.cursor.fetchone()[0]

        return new_competition_id

    def create_competition_solution(self, login: string, competition_id: int):
        self.cursor.execute('select add_user_solution(%s,%s,%s,%s)',
                            (login, 'test_solution_location', 'java', competition_id))
        return self.cursor.fetchone()[0]

    def create_test_sandbox_match(self, match_creator_id: int, solution_white_id: int, solution_black_id: int):
        self.cursor.execute('select create_match(%s, %s, %s, %s, ARRAY[%s, %s])',
                            (match_creator_id, 'standard chess map', False, None,
                             solution_white_id, solution_black_id))
        new_match_id = self.cursor.fetchone()[0]
        self.cursor.execute('select add_match_settings(%s,%s,%s)',
                            ('white_player', str(solution_white_id), new_match_id))
        self.cursor.execute('select add_match_settings(%s,%s,%s)',
                            ('black_player', str(solution_black_id), new_match_id))
        return new_match_id

    def fill_match_with_turns(self, test_chess_match_id: int, whites_solution: int, blacks_solution: int):
        for i in range(TURN_NUMBER):
            self.cursor.execute('select add_match_tick(%s, \'\', %s)', (test_chess_match_id, i,))
            match_tick_id = self.cursor.fetchone()[0]
            self.cursor.execute(
                'select fill_board_on_startup(%s, %s, %s)', (
                    whites_solution,
                    blacks_solution,
                    match_tick_id))

    def create_rating_round(self, competition_id: int, start_time):
        self.cursor.execute('select add_rating_round(%s, %s)',
                            (competition_id, start_time))
        return self.cursor.fetchone()[0]

    def start_rating_round(self, round_id: int):
        self.cursor.execute('select start_chess_rating_round(%s)',
                            (round_id,))
        return self.cursor.fetchall()

    def get_match_solutions(self, match_id: int):
        self.cursor.execute(
            'select match_solution.solution_id from match join match_solution on match.id = match_solution.match_id where match.id = %s',
            (match_id,))
        return self.cursor.fetchall()

    def get_solution_author(self, solution_id: int):
        self.cursor.execute(
            'select competition_participant.id from solution join competition_participant on solution.participant_id = competition_participant.id where solution.id = %s',
            (solution_id,))
        return self.cursor.fetchone()

    def add_user_results(self, match_id: int, participant_id: int, result: float):
        self.cursor.execute(
            'select add_user_results(%s, %s, %s)',
            (match_id, participant_id, result))

    def end_match(self, match_id: int):
        self.cursor.execute(
            'select end_match(%s)',
            (match_id,))

    def end_rating_match(self, match_id: int):
        self.cursor.execute(
            'select end_rating_match(%s)',
            (match_id,))

    def compile_solution(self, solution_id: int):
        self.cursor.execute(
            'select make_solution_compiled(%s)',
            (solution_id,))

    def manage_competition(self, login: str, competition_id: int):
        self.cursor.execute(
            'select manage_competition(%s,%s)',
            (login, competition_id))
        return self.cursor.fetchone()[0]

    def create_competition_request(self, author_id: int, theme: str, description: str):
        self.cursor.execute(
            'select create_competition_request(%s,%s,%s)',
            (author_id, theme, description))
        return self.cursor.fetchone()[0]

    def process_competition_request(self, competition_request_id: int, assignee_id: int):
        self.cursor.execute(
            'select process_competition_request(%s, %s)',
            (competition_request_id, assignee_id))

    def close_competition_request(self, competition_request_id: int):
        self.cursor.execute(
            'select close_competition_request(%s)',
            (competition_request_id,))

    def create_organization(self, name: str, description: str, address: str):
        self.cursor.execute(
            'select create_organization(%s,%s,%s)',
            (name, description, address,))
        return self.cursor.fetchone()[0]

    def sponsor_competition(self, organization_id: int, competition_id: int):
        self.cursor.execute(
            'select sponsor_competition(%s,%s)',
            (organization_id, competition_id,))

    def create_equipment(self, type_name: str, owner_organization_id: int, storage_id: int, name: str):
        self.cursor.execute(
            'select create_equipment(%s,%s,%s,%s)',
            (type_name, owner_organization_id, storage_id, name,))
        return self.cursor.fetchone()[0]

    def create_storage(self, competition_id: int, store_manager_id: int, name: str):
        self.cursor.execute(
            'select create_storage(%s,%s,%s)',
            (competition_id, store_manager_id, name,))
        return self.cursor.fetchone()[0]

    def create_storage_record(self, storage_id: int, responsible_manager_id: int, equipment_id: int):
        self.cursor.execute(
            'select create_storage_record(%s,%s,%s)',
            (storage_id, responsible_manager_id, equipment_id,))
        return self.cursor.fetchone()[0]

    def close_storage_record(self, storage_record_id: int):
        self.cursor.execute(
            'select close_storage_record(%s)',
            (storage_record_id,))

    def end_competition(self, competition_id: int):
        self.cursor.execute(
            'select end_competition(%s)',
            (competition_id,))
