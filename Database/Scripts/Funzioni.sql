--Funzioni ausiliarie per la gestione del database

--Funzione che restituisce tutte le conferenze che si svolgono in un determinato periodo di tempo
create or replace function show_conference_by_date(dataI date, dataF date)
returns setof conferenza as $$
begin
    return query
    select * from conferenza
    where inizio >= start and fine <= dataF;
end;
$$ language plpgsql;

--Funzione che restituisce tutte le conferenze che si svolgono in una determinata sede
create or replace function show_conferences_by_sede(sede int)
returns setof conferenza as $$
begin
    return query
    select * from conferenza
    where id_sede = sede;
end;
$$ language plpgsql;

--Funzione che mostra tutti gli organizzatori appartenenti al comitato scientifico di una conferenza
create or replace function show_comitato_scientifico(conferenza int)
returns setof organizzatore as $$
begin
    return query
    select * from organizzatore
    where id_organizzatore in (
        select id_organizzatore from organizzatore_comitato
        where id_comitato = (
            select id_comitato_scientifico from conferenza
            where id_conferenza = conferenza
        )
    );
end;
$$ language plpgsql;

--Funzione che mostra tutti gli organizzatori appartenenti al comitato locale di una conferenza
create or replace function show_comitato_locale(conferenza int)
returns setof organizzatore as $$
begin
    return query
    select * from organizzatore
    where id_organizzatore in (
        select id_organizzatore from organizzatore_comitato
        where id_comitato = (
            select id_comitato_locale from conferenza
            where id_conferenza = conferenza
        )
    );
end;
$$ language plpgsql;

--Funzione che mostra tutti i partecipanti di una conferenza
create or replace function show_partecipanti(conferenza int)
returns setof partecipante as $$
begin
    return query
    select * from partecipante
    where id_partecipante in (
        select id_partecipante from partecipazione
        where id_sessione in (
            select id_sessione from sessione
            where id_conferenza = conferenza
        )
    );
end;
$$ language plpgsql;

--Funzione che mostra tutte le sessioni di una conferenza
create or replace function show_sessioni(conferenza int)
returns setof sessione as $$
begin
    return query
    select * from sessione
    where id_conferenza = conferenza
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra il programma di una sessione elencando tutti gli interventi in ordine cronologico
create or replace function show_interventi_sessione(sessione int)
returns table
(id_intervento integer,
titolo text,
inizio timestamp,
abstract text,
speaker text)  as $$
declare 
    programma int;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id_intervento,titolo,inizio,abstract, s.nome || ' ' || s.cognome as speaker
    from intervento i join speaker s on i.id_speaker = s.id_speaker
    where i.id_programma = programma
    order by inizio;
end;
$$ language plpgsql;


-- Funzione che mostra il programma di una sessione elencando tutti gli intervalli in ordine cronologico
create or replace function show_intervalli_sessione(sessione int)
returns table
(id_intervallo integer,
tipologia intervallo_st,
inizio timestamp)  
as $$
declare 
    programma int;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id_intervallo,tipologia,inizio
    from intervallo
    where id_programma = programma
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra tutti gli eventi sociali di una sessione in ordine cronologico
create or replace function show_eventi_sociali_sessione(sessione int)
returns table
(id_evento int,
tipologia text,
inizio timestamp) 
 as $$
declare 
    programma int;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id_evento,tipologia,inizio
    from evento_sociale
    where id_programma = programma
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra i dettagli del keynote speaker di una sessione
create or replace function show_keynote_sessione(sessione int)
returns table(
id_speaker int,
nome text,
cognome text,
titolo text,
email text,
ente text) 
 as $$
declare 
    programma int;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select s.id_speaker,s.nome,s.cognome,s.titolo,s.email,e.nome
    from speaker s join ente e on s.id_ente = e.id_ente
    where s.id_speaker = (
        select id_keynote
        from programma
        where id_programma = programma
    );
end;
$$ language plpgsql;

-- Funzione che mostra tutti gli elementi all'interno di un programma 
create or replace function show_programma(sessione int)
returns table(
id int,
appuntamento text,
orario text,
descrizione text,
speaker text) as $$
declare 
    programma int;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    return query
    select i.id_intervento as id,'intervento' as appuntamento, TO_CHAR(i.inizio, 'DD-Mon HH:MI') as orario, i.abstract, s.nome || ' ' || s.cognome as speaker
    from intervento i join speaker s on i.id_speaker = s.id_speaker
    where i.id_programma = programma
    union
    select i2.id_intervallo as id,'intervallo' as appuntamento, TO_CHAR(i2.inizio, 'DD-Mon HH:MI') as orario, unnest(enum_range(NULL::intervallo_st))::text as descrizione, null
    from intervallo i2
    where i2.id_programma = programma
    union
    select e.id_evento as id,'evento' as appuntamento, TO_CHAR(e.inizio, 'DD-Mon HH:MI') as orario, e.tipologia as descrizione, null
    from evento e
    where e.id_programma = programma
    order by orario;
end;
$$ language plpgsql;

--Funzione per aggiungere un nuovo intervento nel programma di una sessione
create or replace procedure add_intervento(titolo text, abstract text, speaker int, sessione int,durata interval)
as $$
declare
    programma int;
    id_entry int;
    query text;
    category text;
    inizio_prev timestamp;
    fine_prev timestamp;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id,appuntamento, max(orario) into id_entry,category,inizio_prev
    from show_programma(sessione);

    query := 'select fine from ' || category || ' where id_'||category||' = ' || id_entry;
    execute query into fine_prev;

    insert into intervento(titolo,abstract,id_speaker,id_programma,inizio,fine)
    values (titolo,abstract,speaker,programa,fine_prev,fine_prev+durata);
end;
$$ language plpgsql;

--Funzione per aggiungere un nuovo intervallo nel programma di una sessione
create or replace procedure add_intervallo(tipologia intervallo_st, sessione int,durata interval)
as $$
declare
    programma int;
    id_entry int;
    query text;
    category text;
    inizio_prev timestamp;
    fine_prev timestamp;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id,appuntamento, max(orario) into id_entry,category,inizio_prev
    from show_programma(sessione);

    query := 'select fine from ' || category || ' where id_'||category||' = ' || id_entry;
    execute query into fine_prev;

    insert into intervallo(tipologia,id_programma,inizio,fine)
    values (tipologia,programa,fine_prev,fine_prev+durata);
end;
$$ language plpgsql;

--Funzione per aggiungere un nuovo evento nel programma di una sessione
create or replace procedure add_evento(tipologia text, sessione int,durata interval)
as $$
declare
    programma int;
    id_entry int;
    query text;
    category text;
    inizio_prev timestamp;
    fine_prev timestamp;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id,appuntamento, max(orario) into id_entry,category,inizio_prev
    from show_programma(sessione);

    query := 'select fine from ' || category || ' where id_'||category||' = ' || id_entry;
    execute query into fine_prev;

    insert into evento_sociale(tipologia,id_programma,inizio,fine)
    values (tipologia,programa,fine_prev,fine_prev+durata);
end;
$$ language plpgsql;

--Funzione per aggiungere una nuova conferenza
create or replace procedure add_conferenza(nome text,inizio timestamp, fine timestamp, sede integer, descrizione text)
as $$
begin
    insert into conferenza(titolo,inizio,fine,id_sede,descrizione)
    values (nome,inizio,fine,sede,descrizione);
end;
$$ language plpgsql;

--Funzione per aggiungere una nuova sessione
create or replace procedure add_sessione(nome text,inizio timestamp, fine timestamp, sala integer, conferenza integer)
as $$
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza)
    values (nome,inizio,fine,sala,conferenza);
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di un ente organizzatore di una conferenza
create or replace procedure add_ente(ente integer, conferenza integer)
as $$
begin
    insert into ente_conferenza(id_ente,id_conferenza)
    values (ente,conferenza);
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di una sponsorizzazione di una conferenza
create or replace procedure add_sponsorizzazione(sponsor integer, contributo numeric(1000,2), valuta char(3), conferenza integer)
as $$
begin
    insert into sponsorizzazione(id_sponsor,contributo,valuta,id_conferenza)
    values (sponsor,contributo,valuta,conferenza);
end;
$$ language plpgsql;

--Funzione per aggiunta di una sessione di una conferenza
create or replace procedure add_sessione(titolo text, inizio timestamp, fine timestamp, sala integer, conferenza integer)
as $$
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza)
    values (titolo,inizio,fine,sala,conferenza);
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di un partecipante di una sessione
create or replace procedure add_partecipante(partecipante integer, sessione integer)
as $$
begin
    insert into partecipante_sessione(id_partecipante,id_sessione)
    values (partecipante,sessione);
end;
$$ language plpgsql;q