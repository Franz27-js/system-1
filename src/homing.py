import sys
import os
sys.path.insert(0, os.path.abspath('.'))
sys.path.append('/src/lib')

from time import sleep

from lib.interface import Interface


def perform_homing(bot: Interface):

    params = bot.get_homing_paramaters()
    print('Params:', params)

    print('Homing')
    result = bot.set_homing_command(0)
    print(result)
