CREATE SCHEMA conference;
set search_path to conference;

--Definizione tipi
create type comitato_st as enum ('locale','scientifico');
create type intervallo_st as enum ('pranzo','coffee break');

--Creazione tabelle
create table ente(
id_ente serial primary key,
nome text not null unique,
sigla varchar(7) not null,
unique (nome,sigla)
);

create table sede(
    id_serial serial primary key,
    nome text not null,
    via text not null,
    civico varchar(5) not null,
    cap varchar(5) not null,
    city text not null
);

create table sponsor(
    id_sponsor serial primary key,
    nome text not null
);

create table comitato(
    id_comitato serial primary key,
    tipologia comitato_st not null
);

create table organizzatore(
    id_organizzatore serial primary key,
    nome text not null,
    cognome text not null,
    titolo varchar(10) not null,
    email text not null unique,
    id_ente integer references ente(id_ente) on delete cascade
);

create table sala(
    id_sala serial primary key,
    nome text not null,
    capienza integer not null,
    id_sede integer references sede(id_serial) on delete cascade
);

create table conferenza(
    id_conferenza serial primary key,
    titolo text not null,
    descrizione text not null,
    inizio timestamp not null,
    fine timestamp not null,
    id_sede integer references sede(id_serial) on delete set null,
    id_comitato_scientifico integer references comitato(id_comitato) on delete set null,
    id_comitato_locale integer references comitato(id_comitato) on delete set null,
    check (inizio <= fine),
    check (inizio >= now())
);

create table partecipante(
    id_partecipante serial primary key,
    nome text not null,
    cognome text not null,
    titolo varchar(10) not null,
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
    check (inizio <= fine)
);

create table partecipazione(
    id_partecipazione serial primary key,
    id_partecipante integer references partecipante(id_partecipante) on delete cascade,
    id_sessione integer references sessione(id_sessione) on delete cascade,
    unique (id_partecipante,id_sessione)
);

create table ente_conferenza(
    id_ente_conferenza serial primary key,
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
    id_sponsor_conferenza serial primary key,
    id_sponsor integer references sponsor(id_sponsor) on delete cascade,
    contributo numeric(1000,2) not null,
    valuta char(3) references valuta(iso) not null,
    id_conferenza integer references conferenza(id_conferenza) on delete cascade,
    unique (id_sponsor,id_conferenza)
);

create table speaker(
    id_speaker serial primary key,
    nome text not null,
    cognome text not null,
    titolo varchar(10) not null,
    email text not null unique,
    id_ente integer references ente(id_ente) on delete set null
);

create table programma(
    id_programma serial primary key,
    id_sessione integer references sessione(id_sessione) on delete cascade,
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
    id_programma integer references programma(id_programma) on delete cascade,
    unique (id_speaker,id_programma),
    check (inizio <= fine)
);

create table intervallo(
    id_intervallo serial primary key,
    tipologia intervallo_st not null,
    inizio timestamp not null,
    fine timestamp not null,
    check (inizio <= fine),
    id_programma integer references programma(id_programma) on delete cascade
);

create table evento(
    id_evento serial primary key,
    tipologia text not null,
    inizio timestamp not null,
    fine timestamp not null,
    check (inizio <= fine),
    id_programma integer references programma(id_programma) on delete cascade
);

create table organizzatore_comitato(
    id_organizzatore_comitato serial primary key,
    id_organizzatore integer references organizzatore(id_organizzatore) on delete cascade,
    id_comitato integer references comitato(id_comitato) on delete cascade,
    unique (id_organizzatore,id_comitato)
);
