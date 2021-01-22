do $$
declare
test_competition_id int;
test_tesing_system_id int;
test_vanya_solution_id int;
test_petya_solution_id int;
test_efarinov_solution_id int;
test_untitled_solution_id int;
test_vanya_participant_id int;
test_petya_participant_id int;
test_chess_match_id int;
test_first_match_tick int;
test_second_match_tick int;
test_chess_king_id int;
test_chess_rook_id int;
test_chess_queen_id int;
test_chess_pawn_id int;
test_chess_knight_id int;
test_chess_bishop_id int;
test_chess_rules_id int;
begin

test_tesing_system_id = create_testing_system_with_rules('https://www.chess.com', 'test chess rules', 'https://en.wikipedia.org/wiki/Rules_of_chess');

test_chess_rules_id = (select id from rules where name = 'test chess rules');

test_chess_king_id = create_player_state('king', test_chess_rules_id);
test_chess_rook_id = create_player_state('rook', test_chess_rules_id);
test_chess_queen_id = create_player_state('queen', test_chess_rules_id);
test_chess_pawn_id = create_player_state('pawn', test_chess_rules_id);
test_chess_knight_id = create_player_state('knight', test_chess_rules_id);
test_chess_bishop_id = create_player_state('bishop', test_chess_rules_id);

perform create_solution_compiler('go', 'test_location');
perform create_solution_compiler('java', 'test_location');
perform create_solution_compiler('c++', 'test_location');
perform create_solution_compiler('c#', 'test_location');

test_competition_id = create_competition('Test Competition', 'This is test competition to test data insertion', localtimestamp - interval '1 minute', 'online', test_tesing_system_id);

perform create_match_player(test_chess_rules_id, 'some url to get the player from');

perform add_match_map('sprite_location', 'standard chess map', 'map_location');

perform create_base_user('admin', 'admin');
perform create_base_user('efarinovPublic', 'myDumbPassword');
perform create_base_user('untitledUser', 'password');
perform create_base_user('manager', 'manager');
perform create_base_user('vanya', '*');
perform create_base_user('petya', '*');

test_vanya_participant_id = partake_in_competition('vanya', test_competition_id);
test_petya_participant_id = partake_in_competition('petya', test_competition_id);
perform partake_in_competition('efarinovPublic', test_competition_id);
perform partake_in_competition('untitledUser', test_competition_id);

test_vanya_solution_id = add_user_solution(test_vanya_participant_id, 'some_folder', 'c++');
test_petya_solution_id = add_user_solution(test_petya_participant_id, 'some_folder', 'java');

perform make_solution_compiled(test_vanya_solution_id);
perform make_solution_compiled(test_petya_solution_id);

test_chess_match_id = create_match(null, 'standard chess map', false, null, ARRAY[test_vanya_solution_id, test_petya_solution_id]);

perform add_match_settings('white_player', test_vanya_solution_id::text, test_chess_match_id);
perform add_match_settings('black_player', test_petya_solution_id::text, test_chess_match_id);

test_first_match_tick = add_match_tick(test_chess_match_id, '', 0);

perform fill_board_on_startup(
    test_vanya_solution_id,
    test_petya_solution_id,
    test_first_match_tick);

perform add_user_results(test_chess_match_id, test_vanya_participant_id, 1.0);
perform add_user_results(test_chess_match_id, test_petya_participant_id, 0.0);

perform end_match(test_chess_match_id);

end $$;