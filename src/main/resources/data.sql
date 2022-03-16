DROP TABLE IF EXISTS buyers;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;

CREATE TABLE buyers (
  current_amount_in_wallet DECIMAL NOT NULL,
  initial_amount_in_wallet DECIMAL NOT NULL,
  username VARCHAR(250) NOT NULL PRIMARY KEY

);

CREATE TABLE orders (
  id INT AUTO_INCREMENT PRIMARY KEY,
  status VARCHAR(250) NOT NULL,
  estimated_days INT NOT NULL,
  amount DECIMAL NOT NULL,
  username VARCHAR(250) NOT NULL
);

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL NOT NULL
);