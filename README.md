PostGreSQL database running on port 5572.
To initialize databaze run code:
To setup Person table crete table: 
CREATE TABLE person(
   birthnumber VARCHAR(11) NOT NULL,
   firstname VARCHAR (20) NOT NULL,
   lastname VARCHAR (30) NOT NULL,
   address CHAR (50),
   city VARCHAR(20),
   PRIMARY KEY(birthnumber)
);

And initialize it with values:
