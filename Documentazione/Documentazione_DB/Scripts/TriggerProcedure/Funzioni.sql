set search_path to conference;
--Funzione che restituisce tutte le conferenze che si svolgono in un determinato periodo di tempo
create or replace function show_conference_by_date(dataI date, dataF date)
returns setof conferenza as $$
begin
    return query
    select * from conferenza
    where inizio >= dataI and fine <= dataF;
end;
$$ language plpgsql;

--Funzione che restituisce tutte le conferenze che si svolgono in una determinata sede
create or replace function show_conferences_by_sede(sede_id int)
returns setof conferenza as $$
begin
    return query
    select * from conferenza
    where id_sede = sede_id;
end;
$$ language plpgsql;

--Funzione che restituisce tutte le conferenze che si svolgono in una sede e in un determinato periodo di tempo
create or replace function show_conferences_by_sede_and_date(sede_id int, dataI date, dataF date)
returns setof conferenza as $$
begin
    return query
    select * from conferenza
    where id_sede = sede_id and inizio >= dataI and fine <= dataF;
end;
$$ language plpgsql;

--Funzione che mostra tutti gli organizzatori appartenenti al comitato scientifico di una conferenza
CREATE OR REPLACE FUNCTION show_comitato_scientifico(conferenza_id integer)
RETURNS SETOF organizzatore
AS $$
BEGIN
    RETURN QUERY
    -- Select dei dettagli dell'organizzatore
    SELECT * FROM organizzatore
    WHERE id_organizzatore IN (
        -- Select degli id degli organizzatori appartenenti al comitato scientifico
        SELECT id_organizzatore FROM organizzatore_comitato
        WHERE id_comitato = (
            -- Select dell'id del comitato scientifico della conferenza
            SELECT comitato_s FROM conferenza
            WHERE id_conferenza = conferenza_id
        )
    );
END;
$$ LANGUAGE plpgsql;

--Funzione che mostra tutti gli organizzatori appartenenti al comitato locale di una conferenza
create or replace function show_comitato_locale(conferenza_id int)
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
            select comitato_l from conferenza
            where id_conferenza = conferenza_id
        )
    );
end;
$$ language plpgsql;

--Funzione che mostra tutti i partecipanti di una conferenza
create or replace function show_partecipanti(conferenza_id int)
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
            where id_conferenza = conferenza_id
        )
    );
end;
$$ language plpgsql;

--Funzione che mostra tutte le sessioni di una conferenza
create or replace function show_sessioni(conferenza_id int)
returns setof sessione as $$
begin
    return query
    select * from sessione
    where id_conferenza = conferenza_id
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra il programma di una sessione elencando tutti gli interventi in ordine cronologico
create or replace function show_interventi_sessione(sessione_id int)
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
    where id_sessione = sessione_id;

    return query
    select i.titolo,i.inizio,i.fine,i.abstract, s.nome || ' ' || s.cognome as speaker
    from intervento i join speaker s on i.id_speaker = s.id_speaker
    where i.id_programma = programma
    order by inizio;
end;
$$ language plpgsql;


-- Funzione che mostra il programma di una sessione elencando tutti gli intervalli in ordine cronologico
create or replace function show_intervalli_sessione(sessione_id int)
returns table
(
id_intervallo integer,
tipologia intervallo_st,
inizio timestamp,
fine timestamp
)  
as $$
declare 
    programma_id integer;
begin
    select id_programma into programma_id
    from programma
    where id_sessione = sessione_id;

    return query
    select i.id_intervallo,i.tipologia,i.inizio,i.fine
    from intervallo i
    where id_programma = programma_id
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra gli speaker di una sessione
create or replace function show_speaker_sessione(sessione_id int)
returns table
(
id_speaker integer,
nome text,
cognome text,
titolo text,
email text,
ente text
)
as $$
declare
    programma_id integer;
begin
    select id_programma into programma_id
    from programma
    where id_sessione = sessione_id;

    return query
    select s.id_speaker,s.nome,s.cognome,s.titolo::text,s.email,e.nome
    from speaker s join ente e on s.id_ente = e.id_ente
    where s.id_speaker in (
        select i.id_speaker
        from intervento i
        where i.id_programma = programma_id
    );
end;
$$ language plpgsql;

-- Funzione che aggiunge uno speaker al programma di una sessione
create or replace procedure add_keynote_speaker(speaker_id integer, sessione_id integer)
as $$
declare
    progamma_id integer;
begin
    select id_programma into progamma_id
    from programma
    where id_sessione = sessione_id;

    update programma set id_keynote = speaker_id
    where id_programma = progamma_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione che mostra tutti gli eventi sociali di una sessione in ordine cronologico
create or replace function show_eventi_sociali_sessione(sessione_id int)
returns table
(
id_evento integer,
tipologia text,
inizio timestamp,
fine timestamp) 
 as $$
declare 
    programma_id integer;
begin
    select id_programma into programma_id
    from programma
    where id_sessione = sessione_id;

    return query
    select e.id_evento,e.tipologia,e.inizio,e.fine
    from evento e
    where e.id_programma = programma_id
    order by inizio;
end;
$$ language plpgsql;

-- Funzione che mostra i dettagli del keynote speaker di una sessione
create or replace function show_keynote_sessione(sessione_id int)
returns table(
id_speaker integer,
nome text,
cognome text,
titolo text,
email text,
ente text) 
 as $$
declare
    programma_id integer;
begin
    select p.id_programma into programma_id
    from programma p
    where p.id_sessione = sessione_id;

    return query
    select s.id_speaker,s.nome,s.cognome,s.titolo::text,s.email,e.nome
    from speaker s join ente e on s.id_ente = e.id_ente
    where s.id_speaker = (
        select p.id_keynote
        from programma p
        where p.id_programma = programma_id
    );
end;
$$ language plpgsql;

-- Funzione che mostra tutti gli elementi all'interno di un programma 
CREATE OR REPLACE FUNCTION show_programma(sessione_id int)
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
    programma_id integer;
BEGIN
    SELECT id_programma INTO programma_id
    FROM programma
    WHERE id_sessione = sessione_id;

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
        WHERE i.id_programma = programma_id

        UNION ALL

        SELECT i2.id_intervallo AS id_entry,
               'intervallo' AS appuntamento,
               i2.inizio,
               i2.fine,
               tipologia::text as descrizione,
               NULL
        FROM intervallo i2
        WHERE i2.id_programma = programma_id

        UNION ALL

        SELECT e.id_evento AS id_entry,
               'evento' AS appuntamento,
               e.inizio,
               e.fine,
               e.tipologia AS descrizione,
               NULL
        FROM evento e
        WHERE e.id_programma = programma_id
    ) AS subquery
    ORDER BY inizio;
END;
$$ LANGUAGE plpgsql;

--Funzione per aggiungere un nuovo intervento nel programma di una sessione
create or replace procedure add_intervento
(titolo text, 
abstract text, 
speaker_id int, 
programma_id int,
durata interval)
as $$
declare
    sessione_id integer;
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
    values (titolo,abstract,speaker_id,programma_id,fine_prev,fine_prev+durata);
    raise notice 'Intervento aggiunto con successo';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

create or replace function add_new_intervento 
(titolo text, 
abstract text, 
speaker_id int, 
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
    values (titolo,abstract,speaker_id,programma_id,fine_prev,fine_prev+durata) returning id_intervento into intervento_id;
    raise notice 'Intervento aggiunto con successo';
    return intervento_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

--Funzione per aggiungere un nuovo intervallo nel programma di una sessione
create or replace procedure 
add_intervallo(tipologia text , programma_id int, durata interval)
as $$
declare
    sessione_id integer;
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

    insert into intervallo(tipologia,id_programma,inizio,fine)
    values (tipologia::intervallo_st, programma_id, fine_prev, fine_prev+durata);
    raise notice 'Intervallo aggiunto con successo';
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
    values (tipologia::intervallo_st, programma_id, fine_prev, fine_prev+durata) returning id_intervallo into intervallo_id;
    raise notice 'Intervallo aggiunto con successo';
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
programma_id int, 
durata interval)
as $$
declare
    sessione_id integer;
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

    insert into evento(tipologia, id_programma, inizio, fine)
    values (tipologia, programma_id, fine_prev, fine_prev+durata);
    raise notice 'Evento aggiunto con successo';
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
    raise notice 'Evento aggiunto con successo';
    return evento_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$
language plpgsql;

--Funzione per aggiungere una nuova conferenza
CREATE OR REPLACE FUNCTION add_conferenza_details
(nome text, inizio timestamp, fine timestamp, sede_id integer, abstract text, utente_id integer)
RETURNS integer AS $$
DECLARE
    id integer;
BEGIN
        INSERT INTO conferenza(titolo, inizio, fine, id_sede, descrizione, id_utente) 
        VALUES (nome, inizio, fine, sede_id, abstract,utente_id)
        RETURNING id_conferenza INTO id;
        RETURN id;
    EXCEPTION
        WHEN OTHERS THEN
            RAISE NOTICE 'Errore nell''inserimento di una conferenza: %', SQLERRM;
            RETURN 0; 
END;
$$ LANGUAGE plpgsql;

-- Funzione per l'aggiunta di un ente organizzatore di una conferenza
create or replace procedure add_ente(ente_id integer, conferenza_id integer)
as $$
begin
    insert into ente_conferenza(id_ente,id_conferenza)
    values (ente_id,conferenza_id);
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

create or replace function add_new_ente(ente_id integer, conferenza_id integer)
returns integer
as $$
declare
    ente_id integer;
begin
    insert into ente_conferenza(id_ente,id_conferenza)
    values (ente_id,conferenza_id) returning id into ente_id;
    return ente_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di una sponsorizzazione di una conferenza
create or replace procedure add_sponsorizzazione(sponsor_id integer, contributo numeric(1000,2), valuta char(3), conferenza_id integer)
as $$
begin
    insert into sponsor_conferenza(id_sponsor,contributo,valuta,id_conferenza)
    values (sponsor_id,contributo,valuta,conferenza_id);
    raise notice 'Sponsorizzazione aggiunta con successo';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

--Funzione per aggiunta di una sessione di una conferenza
create or replace procedure add_sessione(titolo text, inizio timestamp, fine timestamp, sala_id integer, conferenza_id integer,coordinatore_id integer)
as $$
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza,id_coordinatore)
    values (titolo,inizio,fine,sala_id,conferenza_id,coordinatore_id);
    raise notice 'Sessione inserita correttamente';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

create or replace function add_new_sessione
(titolo text, inizio timestamp, fine timestamp, sala_id integer, conferenza_id integer,coordinatore_id integer) 
returns integer
as $$
declare
    sessione_id integer;
begin
    insert into sessione(titolo,inizio,fine,id_sala,id_conferenza,id_coordinatore)
    values (titolo,inizio,fine,sala_id,conferenza_id,coordinatore_id) returning id_sessione into sessione_id;
    raise notice 'Sessione inserita correttamente';
    return sessione_id;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione per l'aggiunta di un partecipante di una sessione
create or replace procedure add_partecipante(partecipante_id integer, sessione_id integer)
as $$
begin
    insert into partecipante_sessione(id_partecipante,id_sessione)
    values (partecipante_id,sessione_id);
    raise notice 'Partecipante inserito correttamente';
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

-- Funzione in sql dinamico che presa una conferenza e una stringa di Sigle separate da virgola aggiunge gli enti come organizzatori di quella conferenza
CREATE OR REPLACE PROCEDURE add_enti(conferenza_id integer, sigle text)
AS $$
DECLARE
    sigla_ente text;
    ente_id integer;
BEGIN
        FOR sigla_ente IN SELECT unnest(string_to_array(sigle, ',')) LOOP
            -- Cerca l'id dell'ente corrispondente alla sigla
            SELECT id_ente INTO ente_id FROM ente WHERE sigla = sigla_ente;
            
            -- Inserisci la tupla (id_ente, conferenza) nella tabella ente_conferenza
            INSERT INTO ente_conferenza(id_ente, id_conferenza) VALUES (ente_id, conferenza_id);
        END LOOP;
        RAISE NOTICE 'Enti inseriti correttamente';
    EXCEPTION
        WHEN OTHERS THEN
            RAISE EXCEPTION 'Errore durante l''inserimento delle tuple nella tabella ente_conferenza: %', SQLERRM;
END;
$$ LANGUAGE plpgsql;


--Procedura per l'aggiunta di una conferenza con enti organizzatori
create or replace function add_conferenza(nome text, inizio timestamp, fine timestamp, sede integer, descrizione text, sigle text, utente integer)
returns integer as $$
declare
    id_conferenza int;
begin
    id_conferenza := add_conferenza_details(nome,inizio,fine,sede,descrizione,utente);
    call add_enti(id_conferenza,sigle);
    raise notice 'Conferenza inserita correttamente';
    return id_conferenza;
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
        alter table conferenza disable trigger delete_sessioni_conferenza;
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
            rollback;
	end;
	$$ language plpgsql;

-- Funzione che mostra tutti i membri degli enti organizzatori di una conferenza
create or replace function show_members(conferenza_id integer)
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
    from organizzatore o natural join ente_conferenza ec natural join ente e  
    where ec.id_conferenza = conferenza_id
    group by e.sigla;
end;
$$ language plpgsql;

--Funzione che calcola la percentuale di enti sulla base degli interventi in un preciso mese
CREATE OR REPLACE FUNCTION show_percentage_interventi(mese int, anno int)
RETURNS TABLE (
    ente text,
    percentuale numeric
) AS $$
DECLARE
    totale int;
BEGIN
    SELECT COUNT(*) INTO totale
    FROM intervento
    WHERE date_part('month', inizio) = mese AND date_part('year', inizio) = anno;

    RETURN QUERY
    SELECT e.nome, (COUNT(*) * 100.0 / NULLIF(totale, 0))
    FROM intervento i
    JOIN speaker s ON i.id_speaker = s.id_speaker
    JOIN ente e ON s.id_ente = e.id_ente
    WHERE date_part('month', inizio) = mese AND date_part('year', inizio) = anno
    GROUP BY e.nome;
END;
$$ LANGUAGE plpgsql;

--Funzione che calcola la percentuale di enti sulla base degli interventi in un preciso anno
CREATE OR REPLACE FUNCTION show_percentage_interventi(anno int)
RETURNS TABLE (
    nome text,
    percentuale numeric
) AS $$
DECLARE
    totale int;
BEGIN
    SELECT COUNT(*) INTO totale
    FROM intervento
    WHERE date_part('year', inizio) = anno;

    RETURN QUERY
    SELECT e.nome, (COUNT(*) * 100.0 / totale)
    FROM intervento i
    JOIN speaker s ON i.id_speaker = s.id_speaker
    JOIN ente e ON s.id_ente = e.id_ente
    WHERE date_part('year', inizio) = anno
    GROUP BY e.nome;
END;
$$ LANGUAGE plpgsql;

--Aggiungere un organizzatore ad un comitato
create or replace procedure 
add_membro_comitato(organizzatore_id integer, comitato_id integer)
as $$
begin
    insert into organizzatore_comitato values (organizzatore_id,comitato_id);
end;
$$ language plpgsql;

-- Funzione che riceve in input un id di un comitato e restituisce tutti i membri di quel comitato
create or replace function show_membri_comitato(comitato_id integer)
returns table
(
    id integer,
    nome text,
    cognome text,
    email text,
    titolo titolo_st
) as $$
begin
    return query
    select o.id_organizzatore, o.nome, o.cognome, o.email, o.titolo
    from organizzatore o natural join membro_comitato mc
    where mc.id_comitato = comitato_id;
end;
$$ language plpgsql;

-- Funzione che riceve in input una stringa contenente varie chiavi primarie di organizzatori e l'id di un comitato e aggiunge i membri al comitato
create or replace procedure add_membri_comitato(organizzatori text, comitato integer)
as $$
declare
    id_organizzatore integer;
begin
    for id_organizzatore in select unnest(string_to_array(organizzatori,','))::integer
    loop
        call add_membro_comitato(id_organizzatore,comitato);
    end loop;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$
language plpgsql;

--Funzione che mostra le sedi con sale libere nel periodo indicato
create or replace function show_sedi_libere(inizio_c timestamp, fine_c timestamp)
returns table(
id_sede integer,
nome text,
id_indirizzo integer
)
 as $$
begin
    return query
    select s.id_sede, s.nome, s.id_indirizzo
    from sede s
    where not exists
    (
        select *
        from sala s1
        where s1.id_sede = s.id_sede and
        exists
        (
            select *
            from sessione s2
            where s2.id_sala = s1.id_sala and
            (s2.inizio>=inizio_c and s2.inizio<=fine_c)
        )
    );
end;
$$ language plpgsql;

-- Funzione che mostra le sale libere in una sede nel periodo indicato
create or replace function show_sale_libere(sede_id integer, inizio_c timestamp, fine_c timestamp)
returns setof sala
as $$
begin
    return query
    select *
    from sala s
    where s.id_sede = sede_id and
    not exists
    (
        select *
        from sessione s1
        where s1.id_sala = s.id_sala and
        (s1.inizio>=inizio_c and s1.inizio<=fine_c)
    );
end;
$$ language plpgsql;

-- Funzione che inserisce una nuova sponsorizzazione e restituisce l'id della sponsorizzazione
create or replace function add_new_sponsorizzazione(sponsor_id integer, contributo numeric, valuta text, conferenza_id integer)
returns integer
as $$
begin
    insert into sponsor_conferenza(id_sponsor,contributo,valuta,id_conferenza)
    values (sponsor_id,contributo,valuta,conferenza_id) returning id_sponsor;
    exception
        when others then
            raise notice '%', sqlerrm;
end;
$$ language plpgsql;

