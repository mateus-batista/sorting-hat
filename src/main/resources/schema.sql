create table casa(
	id SERIAL,
	nome VARCHAR(255),
	descricao VARCHAR(500),
	primary key (id)
);

create table aluno (
	id SERIAL,
	email VARCHAR(255),
	id_casa INT,
	primary key(id),
	foreign key (id_casa) references casa
);