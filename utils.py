from enum import Enum

class ServiceType(Enum):
    ONE = 0
    TWO = 2

class EventType(Enum):
    ARRIVAL = 0
    SERVICE = 1
    INTERRUPTION = 2