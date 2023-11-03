INSERT IGNORE INTO user (username, password, algorithm) VALUES ('ljs', '$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT');

INSERT IGNORE INTO authority (name, user) VALUES ('READ', '1');
INSERT IGNORE INTO authority (name, user) VALUES ('WRITE', '1');

INSERT IGNORE INTO product (name, price, currency) VALUES ('Chocolate', '10', 'USD');