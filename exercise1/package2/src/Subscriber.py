#!/usr/bin/env python
import rospy
from std_msgs.msg import UInt32, Float32

pub = rospy.Publisher('/kthfs/result',Float32,queue_size= 10)  #It would not make sense to repeatedly create a new publisher for each time
                                                             #a message is received from the topic. So I'll init it once globally.


def callback(data):
    q = 0.15
    val = data.data         #Python does not support divison between UInt32 and float so we need to retrieve the data.
    val = val / q
    rospy.loginfo(val)
    pub.publish(val)


def listener():
    rospy.init_node('nodeB', anonymous = True)
    rospy.Subscriber('/Adamsson',UInt32,callback)

    rospy.spin()

if  __name__ == '__main__':
    listener()
