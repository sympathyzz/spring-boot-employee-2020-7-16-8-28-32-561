create table employee(
    id int not null auto_increment primary key,
    name varchar(255),
    gender varchar(255),
    salary varchar(255),
    age int,
    conmpanyId int,
    foreign key(conmpanyId) references company(id)
)