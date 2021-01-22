import datetime
import random
import time

from DatabaseFunctionsInvoker import DatabaseFunctionsInvoker
from base_operations import reset_database
from competition_entities import TestCompetitionParticipant, TestCompetitionManager
from database_connector import establish_database_connection
from generation_consts import USERS_NUMBER, RATING_ROUNDS_NUMBER, COMPETITION_NUMBER, EQUIPMENT_PIECES_NUMBER, \
    STORAGE_RECORDS_NUMBER, ORGANIZATIONS_NUMBER, STORAGES_NUMBER
from random_entities_generator import generate_login, generate_organization_names, generate_organization_address, \
    generate_organization_description, generate_equipment_type, generate_equipment_name

connection = establish_database_connection()
cur = connection.cursor()

try:
    reset_database(connection)

    print('generating more data.')
    dfi = DatabaseFunctionsInvoker(connection)

    base_users = [generate_login() for i in range(USERS_NUMBER)]
    for user in base_users:
        dfi.create_base_user(user)

    print('generating organizations.')
    organization_names = generate_organization_names(ORGANIZATIONS_NUMBER)
    organization_ids = []
    for organization in organization_names:
        organization_ids.append(
            dfi.create_organization(organization, generate_organization_description(), generate_organization_address()))

    for i in range(COMPETITION_NUMBER):
        print('creating competition_{0}'.format(i))
        print('generating match part of the competition')

        competition_id = dfi.create_test_competition(i)
        competition_participants = []
        print('generating participants.')
        for user in base_users:
            competition_participants.append(TestCompetitionParticipant(user))
            competition_participants[-1].competition_id = competition_id
            competition_participants[-1].participant_id = dfi.partake_in_competition(user, competition_id)
            for _ in range(competition_participants[-1].number_of_solutions):
                competition_participants[-1].solutions.append(dfi.create_competition_solution(competition_participants[-1].participant_id, competition_id))
                dfi.compile_solution(competition_participants[-1].solutions[-1])

        rating_rounds = []
        for i in range(RATING_ROUNDS_NUMBER):
            print('generating rating round: {0}'.format(i))
            ts = time.time()
            ts -= 60
            timestamp = datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')
            rating_rounds.append(dfi.create_rating_round(competition_id, timestamp))

        for i in range(RATING_ROUNDS_NUMBER):
            print('starting round: {0}'.format(i))
            matches = dfi.start_rating_round(rating_rounds[i])[0][0]
            for match in matches:
                solutions = dfi.get_match_solutions(match)
                dfi.fill_match_with_turns(match, solutions[0], solutions[1])
                author_1 = dfi.get_solution_author(solutions[0])
                author_2 = dfi.get_solution_author(solutions[1])
                winner_tuple = (author_1, author_2) if random.randrange(100) > 50 else (
                    author_2, author_1)
                dfi.add_user_results(match, winner_tuple[0], 1)
                dfi.add_user_results(match, winner_tuple[1], 0)
                dfi.end_rating_match(match)

        print('generating sandbox matches.')
        for user_1 in competition_participants:
            for user_2 in competition_participants:
                if user_1 != user_2 and random.randrange(100) > 95:
                    # print('generating matches for: {0} vs {1}'.format(user_1.login, user_2.login))
                    for solution_1 in user_1.solutions:
                        for solution_2 in user_2.solutions:
                            white_black_tuple = (solution_1, solution_2) if random.randrange(100) > 50 else (
                                solution_2, solution_1)
                            test_match = dfi.create_test_sandbox_match(
                                user_1.participant_id if random.randrange(100) > 50 else user_2.participant_id,
                                white_black_tuple[0], white_black_tuple[1])
                            dfi.fill_match_with_turns(test_match, white_black_tuple[0], white_black_tuple[1])

        print('generating organization part of the competition')

        for organization in organization_ids:
            dfi.sponsor_competition(organization, competition_id)

        competition_request_ids = []
        for participant in competition_participants:
            competition_request_ids.append(
                dfi.create_competition_request(participant.participant_id, "test theme", "test description"))

        print('generating managers.')
        competition_managers = []
        for user in base_users:
            competition_managers.append(TestCompetitionManager(user))
            competition_managers[-1].competition_id = competition_id
            competition_managers[-1].manager_id = dfi.manage_competition(user, competition_id)

        for manager in competition_managers:
            dfi.process_competition_request(competition_request_ids[-1], manager.manager_id)
            dfi.close_competition_request(competition_request_ids.pop())

        print('generating storages.')

        for i in range(STORAGES_NUMBER):
            storage_id = dfi.create_storage(competition_id, competition_managers[0].manager_id,
                                            'Storage {0}'.format(i))
            equipment_ids = []
            for _ in range(EQUIPMENT_PIECES_NUMBER):
                type = generate_equipment_type()
                equipment_ids.append(
                    dfi.create_equipment(type, random.choice(organization_ids), storage_id,
                                         generate_equipment_name() + ' ' + type.capitalize()))

            storage_record_ids = []
            taken_equipment_ids = random.sample(population=equipment_ids, k=STORAGE_RECORDS_NUMBER)
            for taken_equipment in taken_equipment_ids:
                storage_record_ids.append(
                    dfi.create_storage_record(storage_id, random.choice(competition_managers).manager_id,
                                              taken_equipment))

            for storage_record in storage_record_ids:
                dfi.close_storage_record(storage_record)

        # dfi.end_competition(competition_id)
        print('----------------------------------------------------------------------------------------------')

finally:
    cur.close()
    connection.close()
