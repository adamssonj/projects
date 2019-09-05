#!/usr/bin/env python
import rospy
from std_msgs.msg import UInt32

def talker():
    pub = rospy.Publisher('/Adamsson', UInt32, queue_size = 10)     #We use unsigned integers since k > 0 to save space and the ability to represent larger numbers.
    rospy.init_node('talker',anonymous=True)
    rate = rospy.Rate(20)
    n = 4
    k = n

    while not rospy.is_shutdown():
        rospy.loginfo(k)
        pub.publish(k)
        rate.sleep()
        k = k + n

if  __name__ == '__main__':
    try:
        talker()
    except rospy.ROSInterruptException:
        pass
