create type request_status as enum ('created','in process','closed');
create type equipment_type as enum ('speaker','microphone','laptop','mouse','keyboard','hosting','extension cord');
create type service_type as enum ('catering','delivery','transfer');
create type competition_type as enum ('online','offline');
create type match_status as enum ('created','in queue','running','finished');
create type compilation_status as enum ('submitted','in queue','compiling','successful','failed');

create table rules (
	id serial primary key,
	description text not null,
	name text not null
);
create index rules_name_idx on rules(name);
create table player_state (
	id serial primary key,
	rules_id int not null references rules(id) on delete cascade,
	name text not null
);
create index player_state_name_idx on player_state using hash (name);
create index player_state_rules_idx on player_state using hash (rules_id);
create table solution_compiler (
	language text primary key,
	application_location text not null
);
create index solution_compiler_language_idx on solution_compiler using hash (language);
create table base_user (
	login text primary key,
	email text,
	password text not null
);
create index base_user_login_idx on base_user using hash (login);
create table organization (
	id serial primary key,
	name text not null,
	description text not null,
	address text
);
create table service (
	id serial primary key,
	responsible_organization_id int not null references organization(id) on delete cascade,
	service_type service_type not null,
	name text not null,
	description text not null
);
create index service_responsible_organization_idx on service using hash (responsible_organization_id);
create index service_service_type_idx on service using hash (service_type);
create table simulation_settings (
	id serial primary key,
	settings_key text not null,
	settings_value text not null,
	unique (settings_key, settings_value)
);
create index simulation_settings_key_idx on simulation_settings using hash (settings_key);
create index simulation_settings_value_idx on simulation_settings using hash (settings_value);
create table organization_representative (
	base_user_login text not null,
	organization_id int not null,
	primary key (base_user_login, organization_id),
	foreign key (base_user_login) references base_user(login) on delete cascade,
	foreign key (organization_id) references organization(id) on delete cascade
);
create index organization_representative_base_user_idx on organization_representative using hash (base_user_login);
create index organization_representative_organization_idx on organization_representative using hash (organization_id);
create table testing_system (
	id serial primary key,
	url text not null,
	rules_id int not null references rules(id) on delete cascade
);
create index testing_system_rules_idx on testing_system using hash (rules_id);
create table competition (
	id serial primary key,
	name text not null,
	description text not null,
	start_date timestamp not null,
	end_date timestamp,
	testing_system_id int not null references testing_system(id) on delete cascade,
	competition_type competition_type not null
);
create index competition_type_idx on competition using hash (competition_type);
create index competition_start_date_idx on competition (start_date);
create table competition_sponsor (
	competition_id int not null,
	organization_id int not null,
	primary key (competition_id, organization_id),
	foreign key (competition_id) references competition(id) on delete cascade,
	foreign key (organization_id) references organization(id) on delete cascade
);
create index competition_sponsor_competition_idx on competition_sponsor using hash (competition_id);
create index competition_sponsor_organization_idx on competition_sponsor using hash (organization_id);
create table competition_service (
	competition_id int not null,
	service_id int not null,
	primary key (competition_id, service_id),
	foreign key (competition_id) references competition(id) on delete cascade,
	foreign key (service_id) references service(id) on delete cascade
);
create index competition_service_competition_idx on competition_service (competition_id);
create index competition_service_service_idx on competition_service using hash (service_id);
create table competition_manager (
	id serial primary key,
	competition_id int not null references competition(id) on delete cascade,
	base_user_login text not null references base_user(login) on delete cascade,
	unique(competition_id, base_user_login)
);
create index competition_manager_competition_idx on competition_manager using hash (competition_id);
create index competition_manager_base_user_idx on competition_manager using hash (base_user_login);
create table competition_participant (
	id serial primary key,
	competition_id int not null references competition(id) on delete cascade,
	base_user_login text not null references base_user(login) on delete cascade,
	unique(competition_id, base_user_login)
);
create index competition_participant_competition_idx on competition_participant using hash (competition_id);
create index competition_participant_base_user_idx on competition_participant using hash (base_user_login);
create table competition_request (
	id serial primary key,
	author_id int not null references competition_participant(id) on delete cascade,
	assignee_id int references competition_manager(id) on delete cascade,
	request_status request_status not null,
	theme text not null,
	description text not null,
	creation_date timestamp not null,
	closure_date timestamp
);
create index competition_request_assignee_idx on competition_request using hash (assignee_id);
create index competition_request_creation_date_idx on competition_request (creation_date);
create index competition_request_status_idx on competition_request using hash (request_status);
create table storage (
	id serial primary key,
	name text,
	competition_id int not null references competition(id) on delete cascade,
	store_manager_id int not null references competition_manager(id) on delete cascade
);
create index storage_competition_idx on storage using hash (competition_id);
create index storage_store_manager_idx on storage using hash (store_manager_id);
create table equipment (
	id serial primary key,
	equipment_type equipment_type not null,
	owner_organization_id int not null references organization(id) on delete cascade,
	storage_id int not null references storage(id) on delete cascade,
	name text not null
);
create index equipment_owner_idx on equipment using hash (owner_organization_id);
create index equipment_type_idx on equipment using hash (equipment_type);
create index equipment_storage_idx on equipment using hash (storage_id);
create table storage_record (
	id serial primary key,
	responsible_manager_id int not null references competition_manager(id) on delete cascade,
	equipment_id int not null references equipment(id) on delete cascade,
	storage_id int not null references storage(id) on delete cascade,
	creation_date timestamp not null,
	closure_date timestamp
);
create index storage_record_manager_idx on storage_record using hash (responsible_manager_id);
create index storage_record_equipment_idx on storage_record using hash (equipment_id);
create index storage_record_storage_idx on storage_record using hash (storage_id);
create index storage_record_creation_date_idx on storage_record (creation_date);
create table rating_elo (
	participant_id int not null,
	rating real not null,
	primary key (participant_id),
	foreign key (participant_id) references competition_participant(id) on delete cascade
);
create index rating_elo_participant_idx on rating_elo using hash (participant_id);
create index rating_elo_rating_idx on rating_elo (rating);
create table player_texture (
	id serial primary key,
	player_state_id int not null references player_state(id) on delete cascade,
	location text not null
);
create index player_texture_player_state_idx on player_texture using hash (player_state_id);
create table map_background_sprite (
	id serial primary key,
	location text not null
);
create table simulation_map (
	id serial primary key,
	map_sprite_id int not null references map_background_sprite(id) on delete cascade,
	location text not null,
	name text not null
);
create index simulation_map_name_idx on simulation_map using hash (name);
create table front_match_player (
	id serial primary key,
	rules_id int not null references rules(id) on delete cascade,
	location text not null
);
create index front_match_player_rules_idx on front_match_player using hash (rules_id);
create table match_log (
	id serial primary key,
	match_map_id int not null references simulation_map(id) on delete cascade
);
create table match_settings (
	match_log_id int not null,
	simulation_settings_id int not null,
	primary key (match_log_id, simulation_settings_id),
	foreign key (match_log_id) references match_log(id) on delete cascade,
	foreign key (simulation_settings_id) references simulation_settings(id) on delete cascade
);
create table match_tick (
	id serial primary key,
	match_log_id int not null references match_log(id) on delete cascade,
	simulation_state text not null,
	simulation_tick int not null
);
create index match_tick_log_idx on match_tick using hash (match_log_id);
create table solution (
	id serial primary key,
	participant_id int not null references competition_participant(id) on delete cascade,
	solution_compiler_language text not null references solution_compiler(language) on delete cascade,
	compilation_status compilation_status not null,
	creation_time timestamp not null,
	file_location text not null,
	executable_location text
);
create index solution_participant_idx on solution using hash (participant_id);
create index solution_creation_time_idx on solution (creation_time);
create index solution_compilation_status_idx on solution using hash (compilation_status);
create table rating_round (
	id serial primary key,
	competition_id int not null references competition(id) on delete cascade,
	start_time timestamp
);
create index rating_round_competition_idx on rating_round using hash (competition_id);
create index rating_start_time_idx on rating_round (start_time);
create table match_result (
	id serial primary key,
	winner_participant_id int references competition_participant(id) on delete cascade,
	match_log_id int not null references match_log(id) on delete cascade
);
create index match_result_winner_participant_idx on match_result using hash (winner_participant_id);
create index match_result_match_log_idx on match_result using hash (match_log_id);
create table match (
	id serial primary key,
	creator_participant_id int references competition_participant(id) on delete cascade,
	testing_system_id int not null references testing_system(id) on delete cascade,
	match_result_id int not null references match_result(id) on delete cascade,
	match_status match_status not null,
	start_time timestamp
);
create index match_creator_idx on match using hash (creator_participant_id);
create index match_result_idx on match using hash (match_result_id);
create index match_status_idx on match using hash (match_status);
create index match_start_time_idx on match using hash (start_time);
create table rating_match (
	match_id int not null,
	rating_round_id int not null,
	primary key (match_id, rating_round_id),
	foreign key (match_id) references match(id) on delete cascade,
	foreign key (rating_round_id) references rating_round(id) on delete cascade
);
create index rating_match_match_idx on rating_match using hash (match_id);
create index rating_match_round_idx on rating_match using hash (rating_round_id);
create table sandbox_match (
	match_id int not null,
	competition_id int not null,
	primary key (match_id),
	foreign key (match_id) references match(id) on delete cascade,
	foreign key (competition_id) references competition(id) on delete cascade
);
create index sandbox_match_match_idx on sandbox_match using hash (match_id);
create index sandbox_match_competition_idx on sandbox_match using hash (competition_id);
create table match_solution (
	match_id int not null,
	solution_id int not null,
	primary key (match_id, solution_id),
	foreign key (match_id) references match(id) on delete cascade,
	foreign key (solution_id) references solution(id) on delete cascade
);
create index match_solution_match_idx on match_solution using hash (match_id);
create index match_solution_solution_idx on match_solution using hash (solution_id);
create table user_results (
	match_result_id int not null,
	participant_id int not null,
	result real,
	primary key (match_result_id, participant_id),
	foreign key (match_result_id) references match_result(id) on delete cascade,
	foreign key (participant_id) references competition_participant(id) on delete cascade
);
create index user_results_participant_idx on user_results using hash (participant_id);
create index user_results_result_idx on user_results (result);
create table player_position (
	id serial primary key,
	solution_id int not null references solution(id) on delete cascade,
	match_tick_id int not null references match_tick(id) on delete cascade,
	player_state_id int not null references player_state(id) on delete cascade,
	x real,
	y real,
	z real,
	rotation_x real,
	rotation_y real,
	rotation_z real,
	score real
);
create index player_position_tick_idx on player_position using hash (match_tick_id);