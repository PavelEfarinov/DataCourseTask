import re

import psycopg2


def generate_drop_tables_script():
    with open('database_instructions/create_tables.sql') as inf:
        content = inf.read()
        result = ''
        tables = re.findall(r'create table ([\w]*)', content)
        for table in tables:
            result += 'drop table {0} cascade;\n'.format(table)
        return result


def generate_drop_types_script():
    with open('database_instructions/create_tables.sql') as inf:
        content = inf.read()
        result = ''
        functions = re.findall(r'create type ([\w]*)', content)
        for function in functions:
            result += 'drop type {0} cascade;\n'.format(function)
        return result


def generate_drop_functions_script():
    with open('database_instructions/create_functions.sql') as inf:
        content = inf.read()
        result = ''
        functions = re.findall(r'create or replace function ([\w]*)\(', content)
        for function in functions:
            result += 'drop function {0};\n'.format(function)
        return result


def generate_drop_triggers_script():
    with open('database_instructions/create_triggers.sql') as inf:
        content = inf.read()
        result = ''
        triggers = re.findall(r'create trigger ([\w]*) [\w]* [\w]* on ([\w]*)', content)
        for trigger in triggers:
            result += 'drop trigger {0} on {1};\n'.format(trigger[0], trigger[1])

        functions = re.findall(r'create or replace function ([\w]*)\(', content)
        for function in functions:
            result += 'drop function {0};\n'.format(function)
        return result


def generate_drop_script():
    with open('database_instructions/drop_generated.sql', "w") as ouf:
        result = ''
        result += generate_drop_types_script()
        result += generate_drop_triggers_script()
        result += generate_drop_functions_script()
        result += generate_drop_tables_script()
        ouf.write(result)


def drop_all_objects(db_connection):
    cur = db_connection.cursor()
    print('dropping old tables...')
    with open('database_instructions/drop_generated.sql') as inf:
        content = inf.read()
        content = content.replace('\n', ' ')
        commands = content.split(';')
        for command in commands[:-1]:
            command = command.strip()
            try:
                if command != '':
                    cur.execute(command)
            except psycopg2.errors.UndefinedFunction:
                pass
            except psycopg2.errors.UndefinedTable:
                pass
            except psycopg2.errors.UndefinedObject:
                pass
            db_connection.commit()


def create_tables(db_connection):
    cur = db_connection.cursor()
    print('creating new tables.')
    with open('database_instructions/create_tables.sql') as inf:
        content = inf.read()
        content = content.replace('\n', ' ').replace('\t', ' ').replace('  ', ' ')
        commands = content.split(';')
        for command in commands[:-1]:
            command = command.strip()
            cur.execute(command)


def create_functions(db_connection):
    cur = db_connection.cursor()
    print('creating new functions.')
    with open('database_instructions/create_functions.sql') as inf:
        content = inf.read()
        cur.execute(content)


def fill_with_test_data(db_connection):
    cur = db_connection.cursor()
    print('generating test data.')
    with open('database_instructions/create_data.sql') as inf:
        content = inf.read()
        cur.execute(content)


def create_triggers(db_connection):
    cur = db_connection.cursor()
    print('applying new triggers.')
    with open('database_instructions/create_triggers.sql') as inf:
        content = inf.read()
        cur.execute(content)


def reset_database(connection):
    cur = connection.cursor()
    drop_all_objects(connection)
    create_tables(connection)
    create_triggers(connection)
    create_functions(connection)
    fill_with_test_data(connection)
    generate_drop_script()
    cur.close()
