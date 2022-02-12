# Control protocol

Field "type" will be examined first when a message is received.

# Instruction

> Instruction are  sent  from client to server.

* initial

  > first message since websocket open

  * type : "initial"

  * **token(String)** token get from login

* make offer

  * type : "makeOffer"

  * **token(String)** token get from login
  * **cardName(String)** Name of card to show

* take card

  * type : "takeCard"

  * **token(String)** token get from login
  * **cardName(String)** Name of card taken
  * **userName(String)** user whose card has been taken

  

# Response

> Instruction are  sent from server to client.

* initial

  > pass game info to client:
  >
  > * co-players info
  >
  > * trophy

* info

  > inform all client that a user has took a move

* operation

  > Demand an operation from a client