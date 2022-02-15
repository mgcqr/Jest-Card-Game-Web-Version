# Control protocol

Field "type" will be examined first when a message is received.

# Instruction

> Instruction are  sent  from client to server.

* initial

  > first message since websocket open

  * type : "Initial"

  * **token(String)** token get from login

* make offer

  * type : "MakeOffer"
  * **token(String)** token get from login
  * **card_name(String)** Name of card to show **face up**
  
* take card

  * type : "TakeCard"
  * **token(String)** token get from login
  * **is_face_up(boolean)** Card taken is face up
  * **target_user_id(String)** user whose card has been taken

> Client transfer all instructions via json
>
> Server receive it as an instance of GameInstructionDto in WebsocketRouter and then add corresponding userId to the instance then transmit it to GameRunner

# Response

> Response are  sent from server to client.

* initial

  * type = "Initial"
  * left_user
    * id
    * user_name
  * right_user
    * id
    * user_name
  * trophy  (String[]) names of cards of trophy

  > pass game info to client:
  >
  > * co-players info
  >
  > * trophy

* info

  * type = "Info"
  * operation = "MakeOffer" / "TakeCard"
  * user_id
  * target_user_id
  * card_name

  > inform all client that a user has took a move
  >
  > **When operation ==  "MakeOffer" **
  >
  > target_user_id has no use. 
  >
  > User(user_id) chooses to show his card(card_name) face up
  >
  > **When operation == "TakeCard"**
  >
  > User(user_id)  takes another user's (target_user_id) card (card_name) to his jest.

* operation

  > Demand an operation from a client
  
  * make offer
    * type = "MakeOffer"
    * offer_card_names(String[])
  * take card
    * type = "TakeCard"
    * available_offers (Map<user_id, card_names[]>)
  
* Result

  * type = "Result"
  * results (object [])
    * user_id
    * jest_card_names (String[])
    * score (int)

  