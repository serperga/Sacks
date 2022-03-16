DROP TABLE IF EXISTS buyers;

CREATE TABLE buyers (
  current_amount_in_wallet INT NOT NULL,
  initial_amount_in_wallet INT NOT NULL,
  username VARCHAR(250) NOT NULL PRIMARY KEY

);