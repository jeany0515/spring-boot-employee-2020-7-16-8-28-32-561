CREATE TABLE if NOT EXISTS company(
  id              int not null  auto_increment primary key,
  company_name     varchar(255) not null,
  employees_number int
);