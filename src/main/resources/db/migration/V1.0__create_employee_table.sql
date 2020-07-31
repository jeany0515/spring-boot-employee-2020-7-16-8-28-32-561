CREATE TABLE if NOT EXISTS employee(
  id     int not null  auto_increment primary key ,
  name   varchar(255) not null,
  age    int,
  salary double
);