set search_path to conference;
/* Definizione dei vincoli e dei trigger */

/*-------------------------------------------------------------------------------------------*
 |                          Vincoli sul PROGRAMMA della sessione                             |
 *-------------------------------------------------------------------------------------------*/

 -- In un programma non devono esserci eventi, intervalli o interventi che si sovrappongono
create or replace function check_programma() returns trigger as $$
declare
    inizio_evento timestamp;
    fine_evento timestamp;
    inizio_intervallo timestamp;
    fine_intervallo timestamp;
    inizio_intervento timestamp;
    fine_intervento timestamp;
    intervento_id integer;
    intervallo_id integer;
    evento_id integer;
    interventi_cur cursor for select id_intervento from intervento where id_programma = new.id_programma;
    intervalli_cur cursor for select id_intervallo from intervallo where id_programma = new.id_programma;
    eventi_cur cursor for select id_evento from evento where id_programma = new.id_programma;
begin
open interventi_cur;
loop 
    fetch interventi_cur into intervento_id;
    exit when not found;
    select inizio,fine into inizio_intervento,fine_intervento
    from intervento
    where id_intervento = intervento_id;
    if (new.inizio>=inizio_intervento AND new.fine<=fine_intervento) then
        raise exception 'Impossibile inserire questo intervento in questo orario';
    end if;
end loop;
close interventi_cur;
open intervalli_cur;
loop 
    fetch intervalli_cur into intervallo_id;
    exit when not found;
    select inizio,fine into inizio_intervallo,fine_intervallo
    from intervallo
    where id_intervallo = intervallo_id;
    if (new.inizio>=inizio_intervallo AND new.fine<=fine_intervallo) then
        raise exception 'Impossibile inserire questo intervallo in questo orario';
    end if;
end loop;
close intervalli_cur;
open eventi_cur;
loop 
    fetch eventi_cur into evento_id;
    exit when not found;
    select inizio,fine into inizio_evento,fine_evento
    from evento
    where id_evento = evento_id;
    if (new.inizio>=inizio_evento AND new.fine<=fine_evento) then
        raise exception 'Impossibile inserire questo evento in questo orario';
    end if;
end loop;
close eventi_cur;
return new;
end;
$$ language plpgsql;

create trigger check_programma
before insert or update on intervento
for each row
execute function check_programma();
create trigger check_programma
before insert or update on intervallo
for each row
execute function check_programma();
create trigger check_programma
before insert or update on evento
for each row
execute function check_programma();

--  la data e l'orario devono essere compresi in quelli della sessione 
create or replace function check_data() returns trigger as $$
declare
    inizio_sessione timestamp;
    fine_sessione timestamp;
begin
    select inizio,fine into inizio_sessione,fine_sessione
    from sessione
    where id_sessione = (select id_sessione from programma where id_programma = new.id_programma);

    if (new.inizio < inizio_sessione OR new.fine > fine_sessione) then
        raise exception 'L''intervento non è compreso nella sessione';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger check_data_evento
before insert or update on evento
for each row
execute function check_data();

create trigger check_data_intervento
before insert or update on intervento
for each row
execute function check_data();

create trigger check_data_intervallo
before insert or update on intervallo
for each row
execute function check_data();
/*------------------------------------------------------------------------------------------*
 |                          Vincoli per la tabella SESSIONE                                 |
 *------------------------------------------------------------------------------------------*/

/* Ogni volta che si aggiunge una sessione ad una conferenza
  si crea un programma vuoto per quella sessione */
  
create or replace function create_programma_sessione() returns trigger as $$
begin
    insert into programma(id_sessione) values (new.id_sessione);
    return new;
end;
$$ language plpgsql;

create trigger create_programma_sessione
after insert on sessione
for each row
execute function create_programma_sessione();

 -- La sala deve appartenere alla sede della conferenza
create or replace function check_sala_sessione() returns trigger as $$
declare
    sede integer;
    sala integer;
begin
    select id_sede into sede
    from conferenza
    where id_conferenza = new.id_conferenza;
    
    select id_sala into sala
    from sala
    where id_sala = new.id_sala;
    
    IF sala IS NULL THEN
        Return new;
    END IF;
    
    IF sala NOT IN (
        SELECT id_sala
        FROM sala
        WHERE id_sede = sede
    ) THEN
        RAISE exception 'La sala selezionata non appartiene alla sede della conferenza';
    END IF;
    
    return new;
end;
$$ language plpgsql;


create trigger check_sala_sessione
before insert or update on sessione
for each row
execute function check_sala_sessione();

/*  La data e l'orario della sessione devono essere compresi 
 *  tra la data e l'orario di inizio e fine della conferenza */

create or replace function check_data_sessione() returns trigger as $$
declare
    inizio_conferenza timestamp;
    fine_conferenza timestamp;
begin
    select inizio, fine into inizio_conferenza,fine_conferenza
    from conferenza
    where id_conferenza = new.id_conferenza;

    if (new.inizio < inizio_conferenza OR new.fine > fine_conferenza) then
        raise exception 'La sessione non è compresa nella conferenza';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger check_data_sessione
before insert or update on sessione
for each row
execute function check_data_sessione();

-- Il coordinatore di una sessione specificato nel programma deve appartenere al comitato scientifico della conferenza della sessione
create or replace function check_coordinatore_sessione() returns trigger as $$
declare 
    id_comitato_scientifico_conferenza integer;
begin

select comitato_s into id_comitato_scientifico_conferenza
from conferenza c
where c.id_conferenza = new.id_conferenza;

if (new.id_coordinatore is not null) then
    if (id_comitato_scientifico_conferenza not in (select id_comitato from organizzatore_comitato where id_organizzatore = new.id_coordinatore))  then
        raise exception 'Il coordinatore della sessione deve appartenere al comitato scientifico della conferenza';
    end if;
end if;
return new;
end;
$$ language plpgsql;

create trigger check_coordinatore_sessione
before insert or update on sessione
for each row
execute function check_coordinatore_sessione();

/*------------------------------------------------------------------------------------------*
 |                          Vincoli per la tabella CONFERENZA                               |
 *------------------------------------------------------------------------------------------*/

/* 1. Quando si crea una nuova conferenza si deve creare anche 
un comitato scientifico e un comitato locale e associarli alla conferenza*/

create or replace function create_comitati_conferenza() returns trigger as $$
declare 
    id_comitatoscientifico integer;
    id_comitatolocale integer;
begin
    insert into comitato(tipologia) values ('scientifico') returning id_comitato into id_comitatoscientifico;
    insert into comitato(tipologia) values ('locale') returning id_comitato into id_comitatolocale;
    update conferenza set comitato_s = id_comitatoscientifico, comitato_l = id_comitatolocale where id_conferenza = new.id_conferenza;
    return new;
end;
$$ language plpgsql;

create trigger create_comitati_conferenza
after insert on conferenza
for each row
execute function create_comitati_conferenza();

/* 2. In fase di aggiornamento l'id del comitato scientifico deve riferirsi
   ad un comitato scientifico e l'id del comitato locale deve riferirsi ad un comitato locale*/
   
create or replace function check_comitati_conferenza() returns trigger as $$
declare 
    id_comitato_scientifico integer;
    id_comitato_locale integer;
begin
    select id_comitato into id_comitato_scientifico
    from comitato
    where id_comitato = new.comitato_s;
    
    select id_comitato into id_comitato_locale
    from comitato
    where id_comitato = new.comitato_l;
    
    IF id_comitato_scientifico IS NULL THEN
        return new;
    END IF;
    
    IF id_comitato_locale IS NULL THEN
        return new;
    END IF;
    
    IF (select tipologia from comitato where id_comitato = id_comitato_scientifico) <> 'scientifico' THEN
        RAISE EXCEPTION 'Il comitato scientifico deve essere scientifico';
    END IF;
    
    IF (select tipologia from comitato where id_comitato = id_comitato_locale) <> 'locale' THEN
        RAISE EXCEPTION 'Il comitato locale deve essere locale';
    END IF;
    
    return new;
end;
$$ language plpgsql;

create trigger check_comitati_conferenza
before update on conferenza
for each row
execute function check_comitati_conferenza();

/* --------------------------------------------------------------------------------------------*
 |                          Vincoli per la tabella SALA                                        |
 *---------------------------------------------------------------------------------------------*/

-- 1. Una sala non può ospitare più di una sessione alla volta
create or replace function check_sala_sessione_unica() returns trigger as $$
declare
    inizio_sessione timestamp;
    fine_sessione timestamp;
    sessioni cursor for select id_sessione from sessione where id_sala = new.id_sala;
    sessione_id integer;
begin
    open sessioni;
    loop
        fetch sessioni into sessione_id;
        exit when not found;
        select inizio,fine into inizio_sessione,fine_sessione
        from sessione
        where id_sessione = sessione_id;
        if (new.inizio >= inizio_sessione AND new.inizio <= fine_sessione) OR (new.fine >= inizio_sessione AND new.fine <= fine_sessione) then
            raise exception 'La sala non può ospitare più di una sessione alla volta';
        end if;
    end loop;
    close sessioni;
    return new;
end;
$$ language plpgsql;

create trigger check_sala_sessione_unica
before insert or update on sessione
for each row
execute function check_sala_sessione_unica();

/* --------------------------------------------------------------------------------------------*
 |                          Vincoli per la tabella ORGANIZZATORE                               |
 *---------------------------------------------------------------------------------------------*/

-- 1. Gli organizzatori devono appartenere agli enti che hanno organizzato la conferenza
create or replace function check_organizzatore_comitato() returns trigger as $$
declare
    ente_id integer;
begin

    select id_ente into ente_id
    from organizzatore o
    where o.id_organizzatore = new.id_organizzatore;
    
    IF ente_id IS NULL THEN
        RAISE EXCEPTION 'L''organizzatore non esiste';
    END IF;
    
    IF ente_id NOT IN (
        SELECT id_ente
        FROM ente_conferenza
        WHERE id_conferenza IN (
            SELECT id_conferenza
            FROM conferenza
            WHERE NEW.id_comitato IN (id_comitato_scientifico, id_comitato_locale)
        )
    ) THEN
        RAISE EXCEPTION 'L''organizzatore deve appartenere ad un ente che ha organizzato la conferenza';
    END IF;
    return new;
end;
$$ language plpgsql;

create trigger check_organizzatore_comitato
before insert or update on organizzatore_comitato
for each row
execute function check_organizzatore_comitato();

-- Ogni volta che aggiorno de date di inizio e fine di una conferenza bisogna eliminare tutte le sessioni che non si trovano più all'interno della conferenza
create or replace function delete_sessioni_conferenza() returns trigger as $$
declare
    sessioni_cur cursor for 
    select id_sessione 
    from sessione 
    where id_conferenza = old.id_conferenza;
    sessione_id integer;
begin
    open sessioni_cur;
    loop
        fetch sessioni_cur into sessione_id;
        exit when not found;
        if (select inizio from sessione where id_sessione = sessione_id) < new.inizio 
        OR (select fine from sessione where id_sessione = sessione_id) > new.fine then
            delete from sessione where id_sessione = sessione_id;
        end if;
    end loop;
    close sessioni_cur;
    return new;
end;
$$ language plpgsql;

create trigger delete_sessioni_conferenza
before update on conferenza
for each row
execute function delete_sessioni_conferenza();

-- Ogni volta che aggiungo un partecipante alla sessione devo controllare che la capienza della sala non sia stata raggiunta
create or replace function check_capienza_sala() returns trigger as $$
declare
    capienza_s integer;
    partecipanti integer;
begin
    select capienza into capienza_s
    from sala
    where id_sala = new.id_sala;
    
    select count(*) into partecipanti
    from partecipazione
    where id_sessione = new.id_sessione;
    
    if (partecipanti >= capienza_s) then
        raise exception 'La capienza della sala è stata raggiunta';
    end if;
    return new;
end;
$$ language plpgsql;

create trigger check_capienza_sala
before insert on partecipazione
for each row
execute function check_capienza_sala();
