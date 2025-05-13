import sys
import os
sys.path.insert(0, os.path.abspath('.'))
sys.path.append('/src/lib')

from time import sleep

from lib.interface import Interface

class Robot:
    def __init__(self, bot):
        self.bot: Interface = bot

    def getCurrentPos(self):
        self.bot.get_pose()

    def homing(self):
        self.bot.get_homing_paramaters()
        print('Homing')
        result = self.bot.set_homing_command(0)
        print(f"Result: {result}")

    def ptp(self, x, y, z, r):
        self.bot.set_point_to_point_command(1, x, y, z, r)
    
    def setSuction(self, enable, state):
        sleep(1)
        self.bot.set_end_effector_suction_cup(enable, state)
    
    def getAlarmState(self):
        self.bot.get_alarms_state()
        