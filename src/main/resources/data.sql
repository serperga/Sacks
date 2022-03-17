DROP TABLE IF EXISTS buyers;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS order_status;
DROP TABLE IF EXISTS order_status_history;

CREATE TABLE buyers (
  current_amount_in_wallet DECIMAL NOT NULL,
  initial_amount_in_wallet DECIMAL NOT NULL,
  username VARCHAR(250) NOT NULL PRIMARY KEY

);

CREATE TABLE orders (
  order_id INT AUTO_INCREMENT PRIMARY KEY,
  status_id INT NOT NULL,
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

CREATE TABLE order_status (
  status_id INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL
);

CREATE TABLE order_status_history (
  order_id INT AUTO_INCREMENT,
  status_id INT NOT NULL,
  completed_status_in_days INT NOT NULL,
  username VARCHAR(250) NOT NULL,
  PRIMARY KEY (order_id, status_id)
);