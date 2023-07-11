create schema conference;
set search_path to conference;

create table ente(
id_ente serial primary key,
nome text not null unique,
sigla varchar(8) not null,
unique (nome,sigla)
);

create table indirizzo(
    id_indirizzo serial primary key,
    via text not null,
    civico varchar(5) not null,
    cap varchar(5) ,
    city text not null,
    provincia varchar(2) not null,
    nazione text
);

create table sede(
    id_sede serial primary key,
    nome text ,
    id_indirizzo integer references indirizzo(id_indirizzo) on delete set null
);

create table sponsor(
    id_sponsor serial primary key,
    nome text not null
);

create type comitato_st as enum ('locale','scientifico');
create table comitato(
    id_comitato serial primary key,
    tipologia comitato_st not null
);

create type titolo_st as enum ('Dottore','Dottoressa','Professore','Professoressa','Assistente','Ricercatore','Ricercatrice','Ingegnere');
create table organizzatore(
    id_organizzatore serial primary key,
    nome text not null,
    cognome text not null,
    titolo titolo_st,
    email text not null unique,
    id_ente integer references ente(id_ente) on delete cascade
);

create table utente(
    id_utente serial primary key,
    username text not null unique,
    nome text not null,
    cognome text not null,
    titolo titolo_st,
    email text not null unique,
    password text not null,
    id_ente integer references ente(id_ente) on delete cascade
);

create table sala(
    id_sala serial primary key,
    nome text not null,
    capienza integer not null,
    id_sede integer references sede(id_sede) on delete cascade
);

create table conferenza(
    id_conferenza serial primary key,
    titolo text not null,
    descrizione text not null,
    inizio timestamp not null,
    fine timestamp not null,
    id_sede integer references sede(id_sede) on delete set null,
    comitato_s integer references comitato(id_comitato) on delete set null,
    comitato_l integer references comitato(id_comitato) on delete set null,
    id_utente integer references utente(id_utente) on delete cascade,
    check (inizio <= fine),
    unique (titolo,inizio,fine,id_sede)
);

create table partecipante(
    id_partecipante serial primary key,
    nome text not null,
    cognome text not null,
    titolo titolo_st,
    email text not null unique, 
    id_ente integer references ente(id_ente) on delete set null
);

create table sessione(
    id_sessione serial primary key,
    titolo text not null,
    inizio timestamp not null,
    fine timestamp not null,
    id_coordinatore integer references organizzatore(id_organizzatore) on delete set null,
    id_conferenza integer references conferenza(id_conferenza) on delete cascade,
    id_sala integer references sala(id_sala) on delete set null,
    check (inizio <= fine),
    unique (titolo,inizio,fine,id_conferenza,id_sala)
);

create table partecipazione(
    id_partecipante integer references partecipante(id_partecipante) on delete cascade,
    id_sessione integer references sessione(id_sessione) on delete cascade,
    unique (id_partecipante,id_sessione) 
);

create table ente_conferenza(
    id_ente integer references ente(id_ente) on delete cascade,
    id_conferenza integer references conferenza(id_conferenza) on delete cascade,
    unique (id_ente,id_conferenza)
);

create table valuta(
    iso char(3) primary key,
    nome text not null,
    simbolo text not null
);

create table sponsor_conferenza(
    id_sponsor integer references sponsor(id_sponsor) on delete cascade not null,
    contributo numeric(1000,2) not null,
    valuta char(3) references valuta(iso) not null,
    id_conferenza integer references conferenza(id_conferenza) on delete cascade not null,
    unique (id_sponsor,id_conferenza) 
);


create table speaker(
    id_speaker serial primary key,
    nome text not null,
    cognome text not null,
    titolo titolo_st,
    email text not null unique,
    id_ente integer references ente(id_ente) on delete cascade NOT NULL
);


create table programma(
    id_programma serial primary key,
    id_sessione integer references sessione(id_sessione) on delete cascade not null,
    id_keynote integer references speaker(id_speaker) on delete set null,
    unique (id_programma, id_sessione)
);



create table intervento(
    id_intervento serial primary key,
    titolo text not null,
    abstract text not null,
    inizio timestamp not null,
    fine timestamp not null,
    id_speaker integer references speaker(id_speaker) on delete cascade,
    id_programma integer references programma(id_programma) on delete cascade not null,
    unique (id_speaker,titolo,id_programma), 
    check (inizio <= fine) 
);

create type intervallo_st as enum ('pranzo','coffee break');
create table intervallo(
    id_intervallo serial primary key,
    tipologia intervallo_st not null,
    inizio timestamp not null,
    fine timestamp not null,
    check (inizio <= fine), 
    id_programma integer references programma(id_programma) on delete cascade not null
);


create table evento(
    id_evento serial primary key,
    tipologia text not null,
    inizio timestamp not null,
    fine timestamp not null,
    check (inizio <= fine), 
    id_programma integer references programma(id_programma) on delete cascade not null
);

create table organizzatore_comitato(
    id_organizzatore integer references organizzatore(id_organizzatore) on delete cascade,
    id_comitato integer references comitato(id_comitato) on delete cascade,
    unique (id_organizzatore,id_comitato) 
);