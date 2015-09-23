CREATE database twiterdb;
use twitterdb;
CREATE TABLE twitter
(
ID int NOT NULL,
twitname varchar(25) NOT NULL,
twittext varchar(140),
status varchar(15),
PRIMARY KEY (ID)
)
