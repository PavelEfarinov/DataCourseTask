create or replace function create_rating_match_trigger()
	returns trigger
	as $$
	DECLARE
	BEGIN
		if (select start_time from rating_round where id = new.rating_round_id) < now() then
            return new;
        else
            raise exception 'Cannot create matches for the rating round that has not started.';
        end if;
	END;
$$ language plpgsql;
create trigger new_rating_match before insert on rating_match for each row execute procedure create_rating_match_trigger();

create or replace function create_sandbox_match_trigger()
	returns trigger
	as $$
	DECLARE
	BEGIN
		if (select start_date from competition where id = new.competition_id) < now() then
            return new;
        else
            raise exception 'Cannot create matches for the competition that has not started.';
        end if;
	END;
$$ language plpgsql;
create trigger new_sandbox_match before insert on sandbox_match for each row execute procedure create_sandbox_match_trigger();

create or replace function create_rating_round_trigger()
	returns trigger
	as $$
	DECLARE
    competition_end_date timestamp;
	BEGIN
        competition_end_date = (select end_date from competition where id = new.competition_id);
		if competition_end_date < now() or competition_end_date is null then
            return new;
        else
            raise exception 'Cannot create new rounds for the competitions that has ended.';
        end if;
        if (select start_date from competition where id = new.competition_id) < new.start_time is null then
            return new;
        else
            raise exception 'Cannot create new rounds scheduled before the competition.';
        end if;
	END;
$$ language plpgsql;
create trigger new_rating_round before insert on rating_round for each row execute procedure create_rating_round_trigger();

create or replace function create_match_solution_trigger()
	returns trigger
	as $$
	BEGIN
        if (select compilation_status from solution where id = new.solution_id) != 'successful' then
            raise exception 'Cannot create matches for the not compiled solutions.';
        end if;
        return new;
	END;
$$ language plpgsql;
create trigger new_match_solution before insert on match_solution for each row execute procedure create_match_solution_trigger();

create or replace function create_storage_record_trigger()
	returns trigger
	as $$
	BEGIN
        if (select storage_id from equipment where id = new.equipment_id) != new.storage_id then
            raise exception 'Cannot create storage record for the items from different storages.';
        end if;
        if exists(select 1 from storage_record where equipment_id = new.equipment_id and closure_date is null) then
            raise exception 'Cannot create storage record for the taken items.';
        end if;
        return new;
	END;
$$ language plpgsql;
create trigger new_storage_record before insert on storage_record for each row execute procedure create_storage_record_trigger();