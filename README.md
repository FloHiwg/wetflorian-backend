# wetflorian-backend

This is the backend of an office project to higher the survival chances of the plants coming to our office. This service 
should wait for a datagram message including a value from a node which is attached to a plant and save this value into a data
storage. This data should be assessable for a frontend to display this values on a dashboard. 

## Core components 

### Service

The service opens a datagramsocket and waits for a message on a specific port. After receiving the message it save the message 
and creates a corresponding plant.


### Controller

The controller publishes some endpoints to access the data for the frontend.
Further documentation will follow...
