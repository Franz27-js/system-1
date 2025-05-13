from homing import *
from pointToPoint import *
from Robot import *

from lib.interface import Interface

bot = Interface('COM3')


#perform_homing(bot)
#perform_ptp(bot)
robot = Robot(bot)
robot.homing()
robot.ptp(200, 0, -40, 1)

