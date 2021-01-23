create or replace function create_base_user(new_login text, new_password text, new_email text default null)
	returns void
	as $$
	BEGIN
		insert into base_user(login, password, email) values (new_login, new_password, new_email);
	END;
$$ language plpgsql;

create or replace function create_match_player(param_rules_id int, player_location text)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into front_match_player(rules_id, location)
			values (param_rules_id, player_location) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_rule_set(param_name text, param_description text)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into rules(name, description) values (param_name, param_description) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_player_state(param_name text, param_rules_id int)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into player_state(name, rules_id) values (param_name, param_rules_id) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_testing_system_with_rules(param_url text, param_rules_name text, param_rules_description text)
	returns int
	as $$
	DECLARE
		return_value int;
		new_roles_id int;
	BEGIN
		new_roles_id = create_rule_set(param_rules_name, param_rules_description);
		insert into testing_system(url, rules_id) values (param_url, new_roles_id) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_solution_compiler(param_language text, param_location text)
	returns void
	as $$
	BEGIN
		insert into solution_compiler(language, application_location) values (param_language, param_location);
	END;
$$ language plpgsql;

create or replace function partake_in_competition(user_login text, param_competition_id int)
	returns int
	as $$
	DECLARE
		new_participant_id int;
	BEGIN
		insert into competition_participant(competition_id, base_user_login) values (param_competition_id, user_login) returning id into new_participant_id;
		insert into rating_elo (participant_id, rating) values (new_participant_id, 1600);
		return new_participant_id;
	END;
$$ language plpgsql;

create or replace function manage_competition(user_login text, param_competition_id int)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into competition_manager(competition_id, base_user_login) values (param_competition_id, user_login) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_competition(
	param_competition_name text,
	param_competition_description text, 
	param_competition_start timestamp, 
	param_competition_type competition_type,
	param_testing_system_id int, 
	param_competition_end timestamp default null)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into competition(name, description, start_date, end_date, testing_system_id,	competition_type)
			values (
				param_competition_name,
				param_competition_description,
				param_competition_start,
				param_competition_end,
				param_testing_system_id,
				param_competition_type
			) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function create_rating_round(param_competition_id int, param_round_start timestamp default null)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into rating_round(competition_id, start_time) values (param_competition_id, param_round_start) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function add_user_solution(param_participant_id int, solution_location text, solution_language text)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into solution(participant_id, solution_compiler_language, creation_time, file_location, compilation_status)
			values (
				param_participant_id,
				solution_language,
				current_timestamp,
				solution_location,
				'submitted'
				) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function add_simulation_settings(new_settings_key text, new_settings_value text)
returns int
	as $$
	DECLARE
		new_id int := null;
	BEGIN
		insert into simulation_settings(settings_key, settings_value) values (new_settings_key, new_settings_value) returning id into new_id;
		return new_id;
		exception when unique_violation then
			return (select id from simulation_settings where settings_key like new_settings_key limit 1);
	END;
$$ language plpgsql;

create or replace function add_match_settings(new_settings_key text, new_settings_value text, match_id int)
	returns void
	as $$
	DECLARE
		settings_id int;
	BEGIN
		settings_id = add_simulation_settings(new_settings_key, new_settings_value);
		insert into match_settings(match_log_id, simulation_settings_id)
			values (
				(select match_log_id from match join match_result on match.match_result_id = match_result.id where match.id = match_id limit 1),
				settings_id
			);
	END;
$$ language plpgsql;

create or replace function add_match_map(sprite_location text, param_map_name text, map_location text)
	returns int
	as $$
	DECLARE
		return_value int;
		new_match_map_id int;
	BEGIN
		insert into map_background_sprite(location)
			values (
				sprite_location
			) returning id into new_match_map_id;
		insert into simulation_map(map_sprite_id, name, location)
			values (
				new_match_map_id,
				param_map_name,
				map_location
			) returning id into return_value;
		return return_value;

	END;
$$ language plpgsql;


create or replace function create_match(match_creator_id int, match_map text, is_rating boolean, param_rating_round_id int, param_solutions_id int[])
	returns int
	as $$
	DECLARE
		match_competition_id int;
		match_testing_system_id int;
		new_match_log_id int;
		new_match_result_id int;
		new_match_id int;
		loop_solution int;
	BEGIN
		match_competition_id = (select competition_id from solution join competition_participant on participant_id = competition_participant.id where solution.id = param_solutions_id[1] limit 1);
		match_testing_system_id = (select testing_system_id from competition where id = match_competition_id limit 1);

		insert into match_log(match_map_id)
			values (
				(select id from simulation_map where name = match_map limit 1)
			) returning id into new_match_log_id;

		insert into match_result(match_log_id)
			values (
				new_match_log_id
			) returning id into new_match_result_id;

		insert into match(creator_participant_id, testing_system_id, match_result_id, match_status)
			values (
				match_creator_id,
				match_testing_system_id,
				new_match_result_id,
				'created'
			) returning id into new_match_id;

		foreach loop_solution in array param_solutions_id
		loop
			insert into match_solution (match_id, solution_id)
				values (
					new_match_id,
					loop_solution
				);
		end loop;
		if is_rating then
			insert into rating_match(rating_round_id, match_id)
				values (
					param_rating_round_id,
					new_match_id
				);
		else
			insert into sandbox_match(competition_id, match_id)
				values (
					match_competition_id,
					new_match_id
				);
		end if;
		return new_match_id;
	END;
$$ language plpgsql;

create or replace function add_match_tick(param_match_log_id int, param_simulation_state text, param_simulation_tick int)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into match_tick(match_log_id, simulation_state, simulation_tick)
			values (
				param_match_log_id,
				param_simulation_state,
				param_simulation_tick
			) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function add_player_position(
	param_solution_id int,
	param_match_tick_id int,
	param_player_state_id int,
	param_score real,
	param_x real,
	param_y real default null,
	param_z real default null,
	param_rotation_x real default null,
	param_rotation_y real default null,
	param_rotation_z real default null
	)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into player_position(
			solution_id,
			match_tick_id,
			player_state_id,
			x,
			y,
			z,
			rotation_x,
			rotation_y,
			rotation_z,
			score
			)
			values (param_solution_id,
				param_match_tick_id,
				param_player_state_id,
				param_x,
				param_y,
				param_z,
				param_rotation_x,
				param_rotation_y,
				param_rotation_z,
				param_score
			) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function fill_board_on_startup(
    param_white_solution_id int,
    param_black_solution_id int,
    param_match_tick_id int
    )
	returns void
	as $$
	DECLARE
	param_chess_king_id int;
    param_chess_rook_id int;
    param_chess_queen_id int;
    param_chess_pawn_id int;
    param_chess_knight_id int;
    param_chess_bishop_id int;
    chess_rules_id int;
	BEGIN
        chess_rules_id = (select id from rules where name = 'test chess rules');
	    param_chess_king_id = (select id from player_state where name = 'king' and rules_id = chess_rules_id);
        param_chess_rook_id = (select id from player_state where name = 'rook' and rules_id = chess_rules_id);
        param_chess_queen_id = (select id from player_state where name = 'queen' and rules_id = chess_rules_id);
        param_chess_pawn_id = (select id from player_state where name = 'pawn' and rules_id = chess_rules_id);
        param_chess_knight_id = (select id from player_state where name = 'knight' and rules_id = chess_rules_id);
        param_chess_bishop_id = (select id from player_state where name = 'bishop' and rules_id = chess_rules_id);

        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 1, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 2, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 3, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 4, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 5, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 6, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 7, 2);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_pawn_id, null, 8, 2);
        
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 1, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 2, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 3, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 4, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 5, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 6, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 7, 7);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_pawn_id, null, 8, 7);
        
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_rook_id, null, 1, 1);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_rook_id, null, 8, 1);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_rook_id, null, 1, 8);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_rook_id, null, 8, 8);
        
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_knight_id, null, 2, 1);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_knight_id, null, 7, 1);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_knight_id, null, 2, 8);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_knight_id, null, 7, 8);
        
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_bishop_id, null, 3, 1);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_bishop_id, null, 6, 1);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_bishop_id, null, 3, 8);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_bishop_id, null, 6, 8);
        
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_queen_id, null, 4, 1);
        perform add_player_position(param_white_solution_id, param_match_tick_id, param_chess_king_id, null, 5, 1);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_queen_id, null, 4, 8);
        perform add_player_position(param_black_solution_id, param_match_tick_id, param_chess_king_id, null, 5, 8);
	END;
$$ language plpgsql;

create or replace function make_solution_compiled(param_solution_id int, param_executable_location text default null)
	returns void
	as $$
	BEGIN
		update solution set (compilation_status, executable_location) = ('successful', param_executable_location) where id = param_solution_id;
	END;
$$ language plpgsql;

create or replace function start_match(param_match_id int)
	returns void
	as $$
	BEGIN
		update match set (start_time, match_status) = (localtimestamp, 'running') where id = param_match_id;
	END;
$$ language plpgsql;

create or replace function add_rating_round(param_competition_id int, param_start_time timestamp)
	returns int
	as $$
	DECLARE
		return_value int;
	BEGIN
		insert into rating_round(competition_id, start_time)
			values (
				param_competition_id,
				param_start_time
			) returning id into return_value;
		return return_value;
	END;
$$ language plpgsql;

create or replace function find_recent_solution(param_participant_id int)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    return_value = (select solution.id from competition_participant
        join solution on competition_participant.id = solution.participant_id
    where competition_participant.id = param_participant_id
    order by creation_time desc limit 1);
    return return_value;
END;
$$ language plpgsql;

create or replace function start_chess_rating_round(param_rating_round_id int)
	returns int[]
	as $$
	DECLARE
    recent_solution_ids int[];
    loop_solution_id int;
    nested_loop_solution_id int;
    created_rating_matches int[];
    new_created_match int;
    linked_competition_id int;
	BEGIN
        linked_competition_id = (select competition_id from rating_round where id = param_rating_round_id);
	    recent_solution_ids = ARRAY(select find_recent_solution(id) from competition_participant where competition_id = linked_competition_id);
        foreach loop_solution_id in array recent_solution_ids
		loop
            foreach nested_loop_solution_id in array recent_solution_ids
		    loop
                if loop_solution_id != nested_loop_solution_id then
                    new_created_match = create_match(null, 'standard chess map', true, param_rating_round_id, ARRAY[loop_solution_id, nested_loop_solution_id]);
                    created_rating_matches = created_rating_matches || new_created_match;
                    if random() > 0.5 then
                        perform add_match_settings('white_player', loop_solution_id::text, new_created_match);
                        perform add_match_settings('black_player', nested_loop_solution_id::text, new_created_match);
                    else
                        perform add_match_settings('white_player', nested_loop_solution_id::text, new_created_match);
                        perform add_match_settings('black_player', loop_solution_id::text, new_created_match);
                    end if;
                end if;
            end loop;
		end loop;
        return created_rating_matches;
	END;
$$ language plpgsql;

create or replace function add_user_results(param_match_id int, param_participant_id int, param_result real)
	returns void
	as $$
DECLARE
BEGIN
    insert into user_results(match_result_id, participant_id, result) values (param_match_id, param_participant_id, param_result);
END;
$$ language plpgsql;

create or replace function end_match(param_match_id int)
	returns void
	as $$
DECLARE
winner_id int;
corresponding_match_result_id int;
BEGIN
    corresponding_match_result_id = (select match_result_id from match where id = param_match_id);
    winner_id = (select participant_id from user_results where match_result_id = corresponding_match_result_id order by result desc limit 1);
    update match_result set winner_participant_id = winner_id where id = corresponding_match_result_id;
    update match set match_status = 'finished' where id = param_match_id;
END;
$$ language plpgsql;

create or replace function calculate_participants_elo(param_match_id int)
	returns void
	as $$
DECLARE
winner_id int;
loser_id int;
expected_score_a real;
expected_score_b real;
real_score_a real;
real_score_b real;
rating_a real;
rating_b real;
new_rating_a real;
new_rating_b real;
BEGIN
    winner_id = (select winner_participant_id from match join match_result on match.match_result_id = match_result.id where match.id = param_match_id);
    loser_id = (select participant_id from match
        join match_result on match.match_result_id = match_result.id
        join user_results on user_results.match_result_id = match_result.id
        where match.id = param_match_id and participant_id != winner_id limit 1);
    rating_a = (select rating from competition_participant join rating_elo on rating_elo.participant_id = competition_participant.id where competition_participant.id = winner_id);
    rating_b = (select rating from competition_participant join rating_elo on rating_elo.participant_id = competition_participant.id where competition_participant.id = loser_id);

    real_score_a = (select result from match
        join match_result on match.match_result_id = match_result.id
        join user_results on user_results.match_result_id = match_result.id
        where match.id = param_match_id and participant_id = winner_id limit 1);
    real_score_b = (select result from match
        join match_result on match.match_result_id = match_result.id
        join user_results on user_results.match_result_id = match_result.id
        where match.id = param_match_id and participant_id = loser_id limit 1);

    expected_score_a = 1 / (1 + power(10, (rating_b - rating_a) / 400));
    expected_score_b = 1 / (1 + power(10, (rating_a - rating_b) / 400));

    new_rating_a = rating_a + 20 * (real_score_a - expected_score_a);
    new_rating_b = rating_b + 20 * (real_score_b - expected_score_b);
    update rating_elo set rating = new_rating_a where participant_id = winner_id;
    update rating_elo set rating = new_rating_b where participant_id = loser_id;
END;
$$ language plpgsql;

create or replace function end_rating_match(param_match_id int)
	returns void
	as $$
BEGIN
    perform end_match(param_match_id);
    perform calculate_participants_elo(param_match_id);
END;
$$ language plpgsql;

create or replace function create_competition_request(param_author_id int, param_theme text, param_description text)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    insert into competition_request(author_id, assignee_id, request_status, theme, description, creation_date, closure_date)
    values(param_author_id, null, 'created', param_theme, param_description, localtimestamp, null) returning id into return_value;
    return return_value;
END;
$$ language plpgsql;

create or replace function process_competition_request(param_competition_request_id int, param_assignee_id int)
	returns void
	as $$
BEGIN
    update competition_request set (request_status, assignee_id) = ('in process', param_assignee_id) where id = param_competition_request_id;
END;
$$ language plpgsql;

create or replace function close_competition_request(param_competition_request_id int)
	returns void
	as $$
BEGIN
    update competition_request set (request_status, closure_date) = ('closed', localtimestamp) where id = param_competition_request_id;
END;
$$ language plpgsql;

create or replace function create_organization(param_name text, param_description text, param_address text)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    insert into organization(name, description, address)
    values(param_name, param_description, param_address) returning id into return_value;
    return return_value;
END;
$$ language plpgsql;

create or replace function sponsor_competition(param_organization_id int, param_competition_id int)
	returns void
	as $$
DECLARE
BEGIN
    insert into competition_sponsor (organization_id, competition_id) values (param_organization_id, param_competition_id);
END;
$$ language plpgsql;

create or replace function create_equipment(param_type_name equipment_type, param_owner_organization_id int, param_storage_id int, param_name text)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    insert into equipment(equipment_type, owner_organization_id, storage_id, name)
    values(param_type_name, param_owner_organization_id, param_storage_id, param_name) returning id into return_value;
    return return_value;
END;
$$ language plpgsql;

create or replace function create_storage(param_competition_id int, param_store_manager_id int, param_name text)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    insert into storage(competition_id, store_manager_id, name)
    values(param_competition_id, param_store_manager_id, param_name) returning id into return_value;
    return return_value;
END;
$$ language plpgsql;

create or replace function create_storage_record(param_storage_id int, param_responsible_manager_id int, param_equipment_id int)
	returns int
	as $$
DECLARE
return_value int;
BEGIN
    insert into storage_record(storage_id, responsible_manager_id, equipment_id, creation_date)
    values(param_storage_id, param_responsible_manager_id, param_equipment_id, localtimestamp) returning id into return_value;
    return return_value;
END;
$$ language plpgsql;

create or replace function close_storage_record(param_storage_record_id int)
	returns void
	as $$
BEGIN
    update storage_record set closure_date = localtimestamp where id = param_storage_record_id;
END;
$$ language plpgsql;

create or replace function end_competition(param_competition_id int)
	returns void
	as $$
BEGIN
    update competition set end_date = localtimestamp where id = param_competition_id;
END;
$$ language plpgsql;
