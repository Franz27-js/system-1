import sys
import os
sys.path.insert(0, os.path.abspath('.'))
sys.path.append('/src/lib')

from time import sleep

from lib.interface import Interface


def perform_ptp(bot: Interface):
    bot.set_point_to_point_command(1, 200, 0, -40, 0)
    sleep(1)
    bot.set_end_effector_suction_cup(True, True)
    bot.set_point_to_point_command(1, 200, 0, 0, 0)
    bot.set_end_effector_suction_cup(False, False)
    sleep(1)