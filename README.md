# TicketService
Ticketing Service Interface

# Maven Commands:
1.Go to the repo directory under which the pom.xml is present
2. mvn clean package assembly:assembly OR mvn clean package assembly:assembly -DskipTests (To skip the test cases) // This will generate jar(*-jar-with-dependencies.jar) file under target folder.
3. java -jar target\TicketService-0.0.1-jar-with-dependencies.jar com.walmart.ticket.Start // Program will start. Then follow the command prompt.
4. As an enhancement, the start program can be replaced by some user interface  we can create REST API around the current implementation, so ensured that the other classes does not depend on the Start method.


# Implementation:
Following data model has been implemented
* Theatre: Theatre holds the matrix of seats. Each element of matrix represents Seat object.
* Location: Represents the x,y of the Seat. 
* Seat: contains Location(in x,y co-ordinates), STATUS (Reserved, Hold Or Available), reservedBy(Customer) 
* Customer: to store customer related information i.e. email.
* Hold: works as a separate timed based object where it has user requested information (# of seats, Customer email, id), and when event happened (timestamp).The id of this object is created in a synchronized block.

# Service Implementation:
* Created a simple Non Spring Java application without log4j to keep it simple without much libraries   
* TicketServiceImpl: implements TicketService Interface. Basically, it has ConcurrentSkipListMap to save created SeatHolds. The description of the methods are provided in the TicketService Interface 

# Note: 
* Test cases are covered mostly for Service and Helper Classes. Have not created tests for  model objects and Starter classes for this exercise

