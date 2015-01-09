  #-----------------------------------
  #USER RIGHTS MANAGEMENT
  #-----------------------------------
  CREATE USER 'admincdb'@'localhost' IDENTIFIED BY 'qwerty1234';

  GRANT ALL PRIVILEGES ON `computer-database-db`.* TO 'admincdb'@'localhost' WITH GRANT OPTION;


  FLUSH PRIVILEGES;
