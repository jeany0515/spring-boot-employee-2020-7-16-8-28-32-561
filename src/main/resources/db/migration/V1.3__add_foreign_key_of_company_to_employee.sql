alter table employee ADD column company_id int;
alter table employee add foreign key(company_id) REFERENCES company(id);