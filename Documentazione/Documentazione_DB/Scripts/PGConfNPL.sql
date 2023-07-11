do $$
declare
conferenza_id integer; --Id conferenza inserita
comitato_sc_id integer; --Id comitato scientifico inserito
comitato_lc_id integer; --Id comitato locale inserito

begin

call add_conferenza(
'PGConf NPL 2023', --Titolo conferenza
'2023-08-03 9:00', --Inizio
'2023-08-05 12:30',--Fine
5,
'PGConf e'' una conferenza di tre giorni a Napoli 
ricca di storie di utenti e best practice su come utilizzare PostgreSQL, 
il database open source pie'' avanzato al mondo', --Descrizione
'UNINA', --Ente organizzatore
1 --Utente che inserisce la conferenza 
);

select c.id_conferenza FROM conferenza c where titolo ='PGConf NPL 2023' and inizio='2023-08-03 9:00' and fine='2023-08-05 12:30' and id_sede=5 into conferenza_id;
if (not found) then
    rollback;
end if;
--Aggiungo sponsorizzazioni
call add_sponsorizzazione(1,5000.50,'USD',conferenza_id); --Apple
call add_sponsorizzazione(2,3500.50,'USD',conferenza_id); --Amazon
call add_sponsorizzazione(17,3500.50,'USD',conferenza_id); --Cisco
call add_sponsorizzazione(30,3500.50,'USD',conferenza_id); --IBM

select comitato_s from conferenza where id_conferenza=conferenza_id into comitato_sc_id;
select comitato_l from conferenza where id_conferenza=conferenza_id into comitato_lc_id;

--Aggiungo membri comitato
call add_membri_comitato('1,2',comitato_sc_id); -- Comitato scientifico
call add_membri_comitato('3,4',comitato_lc_id); -- Comitato locale

-- Aggiunta sessioni

exception
    when others then
        raise notice 'Errore inserimento conferenza';
        rollback;
end $$;