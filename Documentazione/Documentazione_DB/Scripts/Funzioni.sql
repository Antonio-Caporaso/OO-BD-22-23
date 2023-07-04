set search_path to conference;
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
    -- Select dei dettagli dell'organizzatore
    select * from organizzatore
    where id_organizzatore in (
        -- Select degli id degli organizzatori appartenenti al comitato scientifico
        select id_organizzatore from organizzatore_comitato
        where id_comitato = (
            -- Select dell'id del comitato scientifico della conferenza
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
    -- Select dei dettagli dell'organizzatore
    select * from organizzatore
    where id_organizzatore in (
        -- Select degli id degli organizzatori appartenenti al comitato locale
        select id_organizzatore from organizzatore_comitato
        where id_comitato = (
            -- Select dell'id del comitato locale della conferenza
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
    -- Select dei dettagli del partecipante
    select * from partecipante
    where id_partecipante in (
        -- Select degli id dei partecipanti
        select id_partecipante from partecipazione
        where id_sessione in (
            -- Select degli id delle sessioni della conferenza
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
(
titolo text,
inizio timestamp,
fine timestamp,
abstract text,
speaker text
)  as $$
declare 
    programma integer;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select titolo,inizio,fine,abstract, s.nome || ' ' || s.cognome as speaker
    from intervento i join speaker s on i.id_speaker = s.id_speaker
    where i.id_programma = programma
    order by inizio;
end;
$$ language plpgsql;


-- Funzione che mostra il programma di una sessione elencando tutti gli intervalli in ordine cronologico
create or replace function show_intervalli_sessione(sessione int)
returns table
(
id_intervallo integer,
tipologia intervallo_st,
inizio timestamp,
fine timestamp
)  
as $$
declare 
    programma integer;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id_intervallo,tipologia,inizio,fine
    from intervallo i
    where id_programma = programma
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra tutti gli eventi sociali di una sessione in ordine cronologico
create or replace function show_eventi_sociali_sessione(sessione int)
returns table
(
id_evento integer,
tipologia text,
inizio timestamp,
fine timestamp) 
 as $$
declare 
    programma integer;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione;

    select id_evento,tipologia,inizio,fine
    from evento
    where id_programma = programma
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra i dettagli del keynote speaker di una sessione
create or replace function show_keynote_sessione(sessione int)
returns table(
id_speaker integer,
nome text,
cognome text,
titolo text,
email text,
ente text) 
 as $$
declare 
    programma integer;
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
CREATE OR REPLACE FUNCTION show_programma(sessione int)
RETURNS TABLE (
    id_entry integer,
    appuntamento text,
    inizio timestamp,
    fine timestamp,
    descrizione text,
    speaker text
)
AS $$
DECLARE
    programma integer;
BEGIN
    SELECT id_programma INTO programma
    FROM programma
    WHERE id_sessione = sessione;

    RETURN QUERY
    SELECT *
    FROM (
        SELECT distinct i.id_intervento AS id_entry,
               'intervento' AS appuntamento,
               i.inizio,
               i.fine,
               i.abstract,
               s.nome || ' ' || s.cognome AS speaker
        FROM intervento i
        JOIN speaker s ON i.id_speaker = s.id_speaker
        WHERE i.id_programma = programma

        UNION ALL

        SELECT i2.id_intervallo AS id_entry,
               'intervallo' AS appuntamento,
               i2.inizio,
               i2.fine,
               tipologia::text as descrizione,
               NULL
        FROM intervallo i2
        WHERE i2.id_programma = programma

        UNION ALL

        SELECT e.id_evento AS id_entry,
               'evento' AS appuntamento,
               e.inizio,
               e.fine,
               e.tipologia::text AS descrizione,
               NULL
        FROM evento e
        WHERE e.id_programma = programma
    ) AS subquery
    ORDER BY inizio;
END;
$$ LANGUAGE plpgsql;

--Funzione per aggiungere un nuovo intervento nel programma di una sessione
create or replace procedure add_intervento
(titolo text, 
abstract text, 
speaker text, 
sessione_id int,
durata interval)
as $$
declare
    programma integer;
    fine_prev timestamp;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione_id;

    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into intervento(titolo,abstract,id_speaker,id_programma,inizio,fine)
    values (titolo,abstract,speaker,programma,fine_prev,fine_prev+durata);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

create or replace function add_new_intervento 
(titolo text, 
abstract text, 
speaker text, 
programma_id int,
durata interval)
returns integer
as $$
declare
    sessione_id integer;
    intervento_id integer;
    fine_prev timestamp;
begin
    select id_sessione into sessione_id
    from programma
    where id_programma = programma_id;

    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into intervento(titolo,abstract,id_speaker,id_programma,inizio,fine)
    values (titolo,abstract,speaker,programma,fine_prev,fine_prev+durata) returning id_intervento into intervento_id;
    raise notice 'Inserimento completato';
    return intervento_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

--Funzione per aggiungere un nuovo intervallo nel programma di una sessione
create or replace procedure 
add_intervallo(tipologia text , sessione_id int, durata interval)
as $$
declare
    programma integer;
    fine_prev timestamp;
begin
    select id_programma into programma
    from programma
    where id_sessione = sessione_id;

    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into intervallo(tipologia,id_programma,inizio,fine)
    values (tipologia::intervallo_st, programma, fine_prev, fine_prev+durata);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ 
language plpgsql;

create or replace function 
add_new_intervallo(tipologia text , programa_id int, durata interval)
returns integer
as $$
declare
    sessione_id integer;
    fine_prev timestamp;
    intervallo_id integer;
begin
    select id_sessione into sessione_id
    from programma
    where id_programma = programma_id;

    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into intervallo(tipologia,id_programma,inizio,fine)
    values (tipologia::intervallo_st, programma, fine_prev, fine_prev+durata) returning id_intervallo into intervallo_id;
    raise notice 'Inserimento completato';
    return intervallo_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ 
language plpgsql;

--Funzione per aggiungere un nuovo evento nel programma di una sessione
create or replace procedure 
add_evento
(tipologia text, 
sessione_id int, 
durata interval)
as $$
declare
    programma_id integer;
    fine_prev timestamp;
begin
    -- Recupera l'id del programma della sessione
    select id_programma into programma_id
    from programma
    where id_sessione = sessione_id;

    -- Recupera l'id dell'ultimo punto in programma, la tipologia e la fine
    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into evento(tipologia, id_programma, inizio, fine)
    values (tipologia, programma_id, fine_prev, fine_prev+durata);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$
language plpgsql;

create or replace function
add_new_evento
(tipologia text, 
programma_id int, 
durata interval)
returns integer
as $$
declare
    evento_id integer;
    sessione_id integer;
    fine_prev timestamp;
begin
    -- Recupera l'id della sessione
    select id_sessione into sessione_id
    from programma
    where id_programma = programma_id;

    -- Recupera l'id dell'ultimo punto in programma, la tipologia e la fine
    select max(fine) into fine_prev
    from show_programma(sessione_id);

    if (fine_prev is null) then
        select inizio into fine_prev
        from sessione
        where id_sessione = sessione_id;
    end if;

    insert into evento(tipologia, id_programma, inizio, fine)
    values (tipologia, programma_id, fine_prev, fine_prev+durata) returning id_evento into evento_id;
    raise notice 'Inserimento completato';
    return evento_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$
language plpgsql;

--Funzione per aggiungere una nuova conferenza
CREATE OR REPLACE FUNCTION add_conferenza_details
(nome text, inizio timestamp, fine timestamp, sede integer, abstract text, utente integer)
RETURNS integer AS $$
DECLARE
    id integer;
BEGIN
        INSERT INTO conferenza(titolo, inizio, fine, id_sede, descrizione, id_utente) 
        VALUES (nome, inizio, fine, sede, abstract,utente)
        RETURNING id_conferenza INTO id;
        raise notice 'Inserimento completato';
        RETURN id;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE NOTICE 'Errore nell''inserimento di una conferenza: %', SQLERRM;
            RETURN 0; 
END;
$$ LANGUAGE plpgsql;

-- Funzione per l'aggiunta di un ente organizzatore di una conferenza
create or replace procedure add_ente(ente integer, conferenza integer)
as $$
begin
    insert into ente_conferenza(id_ente,id_conferenza)
    values (ente,conferenza);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di una sponsorizzazione di una conferenza
create or replace procedure add_sponsorizzazione(sponsor integer, contributo numeric(1000,2), valuta char(3), conferenza integer)
as $$
begin
    insert into sponsorizzazione(id_sponsor,contributo,valuta,id_conferenza)
    values (sponsor,contributo,valuta,conferenza);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

--Funzione per aggiunta di una sessione di una conferenza
create or replace procedure add_sessione(titolo text, inizio timestamp, fine timestamp, sala integer, conferenza integer)
as $$
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza)
    values (titolo,inizio,fine,sala,conferenza);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

create or replace function add_new_sessione
(titolo text, inizio timestamp, fine timestamp, sala integer, conferenza integer) 
returns integer
as $$
declare
    sessione_id integer;
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza)
    values (titolo,inizio,fine,sala,conferenza) returning id_sessione into sessione_id;
    raise notice 'Inserimento completato';
    return sessione_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di un partecipante di una sessione
create or replace procedure add_partecipante(partecipante integer, sessione integer)
as $$
begin
    insert into partecipante_sessione(id_partecipante,id_sessione)
    values (partecipante,sessione);
    raise notice 'Inserimento completato';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione in sql dinamico che presa una conferenza e una stringa di Sigle separate da virgola aggiunge gli enti come organizzatori di quella conferenza
CREATE OR REPLACE PROCEDURE add_enti(conferenza integer, sigle text)
AS $$
DECLARE
    sigla_ente text;
    ente_id integer;
BEGIN
        FOR sigla_ente IN SELECT unnest(string_to_array(sigle, ',')) LOOP
            -- Cerca l'id dell'ente corrispondente alla sigla
            SELECT id_ente INTO ente_id FROM ente WHERE sigla = sigla_ente;
            
            -- Inserisci la tupla (id_ente, conferenza) nella tabella ente_conferenza
            INSERT INTO ente_conferenza(id_ente, id_conferenza) VALUES (ente_id, conferenza);
        END LOOP;
        RAISE NOTICE 'Inserimento completato';
    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION 'Errore durante l''inserimento delle tuple nella tabella ente_conferenza: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;


--Procedura per l'aggiunta di una conferenza con enti organizzatori
create or replace procedure add_conferenza(nome text, inizio timestamp, fine timestamp, sede integer, descrizione text, sigle text)
as $$
declare
    id_conferenza int;
begin
    id_conferenza := add_conferenza_details(nome,inizio,fine,sede,descrizione);
    call add_enti(id_conferenza,sigle);
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Procedura per slittare una conferenza in avanti nel tempo
	create or replace procedure 
	slitta_conferenza(conferenza_id integer, durata interval)
	as $$
	declare
		sessione_id integer;
		intervento_id integer;
		evento_id integer;
		intervallo_id integer;
		sessioni cursor for 
			select id_sessione 
			from sessione 
			where id_conferenza = conferenza_id;
			
		interventi cursor for
			select id_intervento 
			from intervento i join programma p 
			on i.id_programma = p.id_programma 
			where p.id_sessione in 
			(select id_sessione 
			from sessione 
			where id_conferenza = conferenza_id);
			
		intervalli cursor for
			select id_intervallo 
			from intervallo i join programma p 
			on i.id_programma = p.id_programma 
			where p.id_sessione in 
				(select id_sessione 
				from sessione 
				where id_conferenza = conferenza_id);
				
		eventi cursor for
			select id_evento 
			from evento e join programma p 
			on e.id_programma = p.id_programma 
			where p.id_sessione in 
				(select id_sessione 
				from sessione 
				where id_conferenza = conferenza_id);
	begin
        alter table conferenza disable trigger all;
        alter table sessione disable trigger all;
        alter table intervento disable trigger all;
        alter table intervallo disable trigger all;
        alter table evento disable trigger all;
        alter table programma disable trigger all;
		update conferenza
		set inizio = inizio + durata, fine = fine + durata
		where id_conferenza = conferenza_id;
	
		open sessioni;
		loop
			fetch sessioni into sessione_id;
			exit when not found;
			
			update sessione
			set inizio = inizio + durata, fine = fine + durata
			where id_sessione = sessione_id;
		
			open interventi;
			loop
				fetch interventi into intervento_id;
				exit when not found;
	
				update intervento
				set inizio = inizio + durata, fine = fine + durata
				where id_intervento = intervento_id ;
			end loop;
			close interventi;
			
			open intervalli;
			loop
				fetch intervalli into intervallo_id;
				exit when not found;
	
				update intervallo
				set inizio = inizio + durata, fine = fine + durata
				where id_intervallo = intervallo_id;
			end loop;
			close intervalli;
			
			open eventi;
			loop
				fetch eventi into evento_id;
				exit when not found;
	
				update evento
				set inizio = inizio + durata, fine = fine + durata
				where id_evento = evento_id;
			end loop;
			close eventi;
		end loop;
		close sessioni;
        alter table conferenza enable trigger all;
        alter table sessione enable trigger all;
        alter table intervento enable trigger all;
        alter table intervallo enable trigger all;
        alter table evento enable trigger all;
        alter table programma enable trigger all;
        raise notice 'Slittamento completato';
	exception
		when others then
			raise notice '%', sqlerrm;
	end;
	$$ language plpgsql;

-- Funzione che mostra tutti i membri degli enti organizzatori di una conferenza
create or replace function show_members(conferenza integer)
returns table 
(
    id integer, 
    nome text, 
    cognome text, 
    email text,
    titolo titolo_st, 
    sigla varchar(7)
) 
as $$
begin
    return query
    select o.id_organizzatore, o.nome, o.cognome, o.email,o.titolo, e.sigla
    from organizzatore o join ente_conferenza ec natural join ente e  
    on o.id_ente = ec.id_ente
    where ec.id_conferenza = conferenza
    GROUP by e.sigla;
end;
$$ language plpgsql;

--Funzione che calcola la percentuale di enti sulla base degli interventi in un preciso mese
create or replace function show_percentage_interventi(mese int, anno int)
returns table
(
    ente text,
    percentuale text
) as $$
declare
    totale int;
begin
    select count(*) into totale
    from intervento
    where date_part('month',inizio) = mese and date_part('year',inizio) = anno;

    return query
    select e.nome, (count(*)*100/totale)::text || '%'
    from intervento i join speaker s 
    on i.id_speaker = s.id_speaker join ente e 
    on s.id_ente = e.id_ente
    where date_part('month',inizio) = mese and date_part('year',inizio) = anno
    group by e.nome;
end;
$$ language plpgsql;

--Funzione che calcola la percentuale di enti sulla base degli interventi in un preciso anno
create or replace function show_percentage_interventi(anno int)
returns table
(
    nome varchar(7),
    percentuale text
) as $$
declare
    totale int;
begin
    select count(*) into totale
    from intervento
    where date_part('year',inizio) = anno;

    return query
    select e.nome, (count(*)*100/totale)::text || '%'
    from intervento i join speaker s 
    on i.id_speaker = s.id_speaker join ente e 
    on s.id_ente = e.id_ente
    where date_part('year',inizio) = anno
    group by e.nome;
end;
$$ language plpgsql;

-- Mostra tutte le sessioni presenti
create or replace function show_all_sessioni()
returns table
(
    titolo text,
    inizio timestamp,
    fine timestamp,
    conferenza text,
    sala text
) as $$
begin
    return query
    select s.titolo,s.inizio,s.fine,c.titolo,s1.nome from sessione s, conferenza c,sala s1
    where s.id_conferenza=c.id_conferenza and s.id_sala=s1.id_sala
    order by s.id_conferenza, s.inizio;
end;
$$ language plpgsql;