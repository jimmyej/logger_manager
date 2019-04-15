create table if not exists log_values ( id int AUTO_INCREMENT PRIMARY KEY, log_date timestamp not null, log_level integer not null, log_name varchar(255) not null, class_name varchar(255), method_name varchar(255), log_message varchar(1000) not null, log_exception varchar(10000) );