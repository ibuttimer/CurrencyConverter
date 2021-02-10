CREATE SCHEMA `currency_exchange`;

CREATE USER 'cc_user'@'localhost' IDENTIFIED BY 'cc1234'; -- Create the user if you haven’t yet
GRANT ALL ON currency_exchange.* TO 'cc_user'@'localhost'; -- Gives all privileges to the new user on currency_exchange
