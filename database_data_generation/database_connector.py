import psycopg2


def establish_database_connection():
    print('connecting to the database...')
    connection = psycopg2.connect(host='localhost',
                                  database='postgres',
                                  user='postgres',
                                  password='123456')
    connection.autocommit = True
    return connection
