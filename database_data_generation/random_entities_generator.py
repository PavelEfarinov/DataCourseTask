import random
import string

from faker import Faker

from organization_names import top_companies_2000
from popular_names import top_200_names, top_1000_surnames


def generate_login():
    return '{0}_{1}_{2}'.format(random.choice(top_200_names).lower(), random.choice(top_1000_surnames).lower(),
                                ''.join(random.choices(string.digits, k=4)))


def generate_organization_names(number: int):
    return random.sample(top_companies_2000, k=number)


def generate_organization_description():
    return Faker().paragraph(nb_sentences=5)


def generate_organization_address():
    return Faker().address()


def generate_equipment_type():
    return random.choice(['speaker',
                          'microphone',
                          'laptop',
                          'mouse',
                          'keyboard',
                          'hosting',
                          'extension cord'])


def generate_equipment_name():
    return Faker().cryptocurrency_name().lower()\
        .replace('coin', 'Gaming')\
        .replace('cash', 'Stream')\
        .replace(' ', '').capitalize()
