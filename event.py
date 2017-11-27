from event_type import *

class Event:

    def __init__(self, client, eventType):
        self.client = client
        self.eventType = eventType

    def debug(self):
        print("Create event" + str(self.eventType) + " with client: " + str(self.client))