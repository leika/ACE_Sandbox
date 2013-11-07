# ACE Sandbox

The ACE Sandbox is a simple test Application to show some specific configurations, errors and best practices in monitoring a Java Application with Appdynamics.

It's not meant to be a real life Application like the Demo app or CartApp Applications are. Instead it's very simplified in Mockups and Code. This should help in understanding the different relationsships and setup steps.

It would for example not show valid BT after initial setup. It will show disconnected tiers, and Trafic on the Backend WebTier. The real picture need some configuration to happen.

## Build


- Edit gradle.properties 
	- Application Name
 	- Agent Location

- Build the System
  	- ./gradlew build


