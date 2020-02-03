# FindSatoshi backend code

This is backend code for game FindSatoshi

## TODO 
- [] When service starts if check whether the game is running.
  - [] If the game is running then it listens for new new ticket requests
  - [] If the game is not running, it starts the game, writes new row in database
- [] REST API
  - [] POST ticket -> create new ticket request, this service contacts openNode for new lightning payment. and returns it to the user. if payment is sucessful then tickets gets marked as paid and assigned to the curently running game.
  - [] POST claimWinnings -> users sends request to API to retrieve winnings. if users supplies payment request with correct winning number && my service determines user is a winner then this service contacts OpenNode for a new withdrawal request
  - [] GET tickets -> List of all tickets currently in the draft.

## REST API
 - POST /ticket
 - GET /tickets
 - GET /winnings

## DATABASE 