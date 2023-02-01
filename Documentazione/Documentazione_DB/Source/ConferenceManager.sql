create table Conferenza(
	idConferenza serial primary key,
	Nome varchar(100) not null,
	DataInizio date not null,
	DataFine date not null,
	Descrizione text,
	Sede int,
	budget float,
	ComitatoLocale int,
	ComitatoScientifico int
);

alter table Conferenza add constraint fk_comitatoloc foreign key (ComitatoLocale) references Comitato(idComitato);
alter table conferenza add constraint fk_comitatosc foreign key (ComitatoScientifico) references Comitato(idComitato);
alter table Conferenza add constraint fk_sede foreign key (Sede) references Sede(idSede);
alter table conferenza add proprietario int;
alter table conferenza add constraint fk_proprietario foreign key (proprietario) references utente(idUser);

create type ComitatoST as enum ('Scientifico', 'Locale');

create table Comitato(
	idComitato serial primary key,
	Responsabile int, 
	Tipologia ComitatoST
);
alter table Comitato add constraint fk_resp foreign key (Responsabile) references Organizzatore(idOrganizzatore);

create table Ente(
	idEnte serial primary key,
	Nome varchar(100)
);

create table Sponsor(
	idSponsor serial primary key,
	Nome varchar(100)
);

create table Sede(
	idSede serial primary key,
	nomeSede varchar(100) unique,
	indirizzo varchar(100),
	city varchar(100),
	cap varchar(10),
	costoAffitto float,
	avalaibility boolean
);

create table Sala(
	idSala serial primary key,
	prenotata boolean,
	sede int,
	capacity int,
	nomeSala varchar(100)
);

alter table Sala add constraint fk_sede foreign key (sede) references Sede(idSede);

create table Sessione(
	idSessione serial primary key,
	titolo varchar(100),
	chair int,
	dataInizio date,
	dataFine date,
	locazione int --fk di sala
);
alter table Sessione add constraint fk_locazione foreign key (locazione) references Sala(idSala);
alter table Sessione add constraint fk_chair foreign key (chair) references Chair(idChair);

create table Programma(
	idProgramma serial primary key,
	Sessione int --fk della sessione
);
alter table Programma add constraint fk_sessione foreign key (Sessione) references Sessione(idSessione);

create table Intervento(
	idIntervento serial primary key,
	Speaker int, --fk Speaker
	programma int, --fk programma
	orario date
);
alter table Intervento add constraint fk_speaker foreign key (Speaker) references Speaker(idSpeaker);
alter table intervento  add constraint fk_programma foreign key (programma) references Programma(idProgramma);

create table Partecipante(
	idPartecipante serial primary key,
	nome varchar(100),
	cognome varchar(100),
	titolo varchar(100),
	istituzione varchar(100),
	email varchar(50)
);

create table Speaker(
	idSpeaker serial primary key,
	nome varchar(100),
	cognome varchar(100),
	titolo varchar(100),
	istituzione varchar(100),
	email varchar(50),
	isKeynote boolean
);

create table Chair(
	idChair serial primary key,
	nome varchar(100),
	cognome varchar(100),
	titolo varchar(100),
	istituzione varchar(100),
	email varchar(50)
);

create type intervalloST as enum ('Coffee Break', 'Pranzo');

create table Intervallo(
	idIntervello serial primary key,
	oraInizio date,
	oraFine date,
	tipologia intervalloST,
	programma int --fk intervallo
);
alter table intervallo add constraint fk_progr foreign key (programma) references Programma(idProgramma);

create table EventoSociale(
	idEventoSociale serial primary key,
	oraInizio date,
	costo float,
	oraFine date,
	programma int, --fk programma
	tipologia varchar(100)
);
alter table eventosociale add constraint fk_program foreign key (programma) references Programma(idProgramma);

create table Organizzatore(
	idOrganizzatore serial primary key,
	nome varchar(100),
	cognome varchar(100),
	titolo varchar(100),
	istituzione varchar(100),
	email varchar(50)
);
create table membroComitato(
	Organizzatore int,
	Comitato int,
	constraint fk_organizzatore foreign key (Organizzatore) references Organizzatore(idOrganizzatore),
	constraint fk_comitato foreign key (Comitato) references Comitato(idComitato)
);
create table sponsorizzazione(
	Conferenza int,
	Sponsor int,
	Contributo float,
	DataInizio date,
	DataFine date,
	constraint fk_conf foreign key (Conferenza) references Conferenza(idConferenza),
	constraint fk_sponsor foreign key (Sponsor) references Sponsor(idSponsor)
);

create table organizza(
	Conferenza int,
	Ente int,
	constraint fk_conferenza foreign key (Conferenza) references Conferenza(idConferenza),
	constraint fk_ente foreign key (Ente) references Ente(idEnte)
);

create table partecipazione(
	Partecipante int,
	Sessione int,
	constraint fk_partecipante foreign key (Partecipante) references Partecipante(idPartecipante),
	constraint fk_sessione foreign key (Sessione) references Sessione(idSessione)
);

create table utente(
	idUser serial primary key,
	pwd  text not null unique,
	nome varchar(100),
	cognome varchar(100),
	titolo varchar(50),
	email varchar(100),
    username varchar(100) unique
);
