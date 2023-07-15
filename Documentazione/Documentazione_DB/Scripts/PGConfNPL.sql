do $$
declare
conferenza_id integer; --Id conferenza inserita
comitato_sc_id integer; --Id comitato scientifico inserito
comitato_lc_id integer; --Id comitato locale inserito
sessione1_id integer;
sessione2_id integer;
sessione3_id integer;
sessione4_id integer;
sessione5_id integer;
programma1_id integer;
programma2_id integer;
programma3_id integer;
programma4_id integer;
programma5_id integer;
begin

call add_conferenza(
'PGConf NPL 2023', --Titolo conferenza
'2023-08-03 9:00', --Inizio
'2023-08-05 12:30',--Fine
5,
'PGConf e'' una conferenza di tre giorni a Napoli ricca di storie di utenti e best practice su come utilizzare PostgreSQL, il database open source pie'' avanzato al mondo', --Descrizione
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
sessione1_id = add_new_sessione('Best practice su PostgreSQL','2023-08-03 10:30','2023-08-03 12:30',13,conferenza_id,1);
sessione2_id = add_new_sessione('PostgreSQL è il database del futuro','2023-08-03 14:30','2023-08-03 16:30',14,conferenza_id,2);
sessione3_id = add_new_sessione('Performance di PostgreSQL','2023-08-04 10:30','2023-08-04 12:30',15,conferenza_id,1);
sessione4_id = add_new_sessione('PostgreSQL e il mondo del lavoro','2023-08-04 14:30','2023-08-04 18:30',13,conferenza_id,2);
sessione5_id = add_new_sessione('PostgreSQL e Kubernetes','2023-08-05 10:30','2023-08-05 12:30',14,conferenza_id,1);

-- Specifica programma sessione 1
select id_programma from programma where id_sessione = sessione1_id into programma1_id;
call add_evento('Apertura sessione',programma1_id,'10 minutes');
call add_intervento('Ottimizzazione delle query in PostgreSQL',
' Questo intervento illustrerà le migliori pratiche per ottimizzare le query in PostgreSQL, inclusi suggerimenti per la scrittura di query efficienti e l''utilizzo degli indici corretti.',
1,
programma1_id,
'30 minutes');
call add_intervento(
    'Gestione delle transazioni in PostgreSQL', --Titolo intervento
    'Questo intervento fornirà consigli e best practice per la gestione delle transazioni in PostgreSQL, comprese le strategie di commit e rollback, il controllo della concorrenza e l''utilizzo di blocchi di transazioni.', --Descrizione intervento
    2, --Id speaker
    programma1_id, --Id programma
    '30 minutes'); --Durata intervento
call add_intervallo('coffee break', programma1_id, '20 minutes');
call add_intervento('Monitoraggio e tuning delle prestazioni in PostgreSQL',
'Questo intervento si concentrerà sul monitoraggio e tuning delle prestazioni in PostgreSQL, presentando strumenti e tecniche per l''identificazione e risoluzione dei problemi di performance.',
3,
programma1_id,
'30 minutes');

-- Specifica programma sessione 2
select id_programma from programma where id_sessione = sessione2_id into programma2_id;
call add_evento('Apertura sessione',programma2_id,'10 minutes');
call add_intervento(
    'Le nuove funzionalità di PostgreSQL15',
    'Questo intervento presenterà le nuove funzionalità introdotte nella versione 14 di PostgreSQL e come queste possono influenzare il futuro dello sviluppo e dell''utilizzo del database.',
    4,
    programma2_id,
    '30 minutes');
call add_intervento(
    'PostgreSQL e il mondo del cloud',
    'Questo intervento esplorerà l''integrazione di PostgreSQL con le piattaforme di cloud computing e le implicazioni di questa evoluzione per le applicazioni e l''adozione del database nel futuro.',
    5,
    programma2_id,
    '30 minutes');
call add_intervallo('coffee break', programma2_id, '20 minutes');
call add_intervento(
    'PostgreSQL e l''intelligenza artificiale',
    'Questo intervento illustrerà come PostgreSQL può essere utilizzato nell''ambito dell''intelligenza artificiale, compresi casi d''uso, funzionalità e best practice per l''integrazione di PostgreSQL con strumenti e librerie di AI.',
    6,
    programma2_id,
    '30 minutes');
-- Specifica programma sessione 3
select id_programma from programma where id_sessione = sessione3_id into programma3_id;
call add_evento('Apertura sessione',programma3_id,'10 minutes');
call add_intervento(
    'Strategie di indicizzazione per migliorare le prestazioni in PostgreSQL',
    'Questo intervento esplorerà diverse strategie di indicizzazione per ottimizzare le prestazioni delle query in PostgreSQL, compreso l''utilizzo di indici multi-colonna, indici parziali e indici funzionali',
    7,
    programma3_id,
    '30 minutes');
call add_evento('Sessione di domande e risposte',programma3_id,'30 minutes');
call add_intervallo('coffee break', programma3_id, '20 minutes');
call add_intervento(
    'Tuning del server PostgreSQL per prestazioni ottimali',
    'Questo intervento si concentrerà sulla configurazione e ottimizzazione del server PostgreSQL per garantire prestazioni ottimali, compreso il tuning dei parametri di configurazione e la gestione della memoria.',
    8,
    programma3_id,
    '30 minutes');
call add_intervento(
    'Monitoraggio delle prestazioni in PostgreSQL',
    'Questo intervento illustrerà gli strumenti e le tecniche per il monitoraggio delle prestazioni in PostgreSQL, compresi i metodi per identificare e risolvere i colli di bottiglia e i problemi di prestazioni.',
    9,
    programma3_id,
    '30 minutes');
-- Specifica programma sessione 4
select id_programma from programma where id_sessione = sessione4_id into programma4_id;
call add_evento('Apertura sessione',programma4_id,'10 minutes');
call add_intervento(
    'Vantaggi di PostgreSQL nel contesto aziendale',
    'Questo intervento fornirà un''analisi dei vantaggi di utilizzare PostgreSQL nel contesto aziendale, compresa l''affidabilità, le prestazioni e il supporto della comunità.',
    10,
    programma4_id,
    '30 minutes');
call add_intervento(
    'PostgreSQL e il mondo del cloud computing',
    'Questo intervento esplorerà come PostgreSQL si integra nel mondo del cloud computing e come le aziende possono trarre vantaggio dall''utilizzo di PostgreSQL come database cloud-native.',
    11,
    programma4_id,
    '30 minutes');
call add_evento('Sessione di domande e risposte',programma4_id,'30 minutes');
call add_evento('Networking e dibattito',programma4_id,'30 minutes');
call add_intervento(
    'PostgreSQL e le opportunità di lavoro',
    'Questo intervento fornirà informazioni sulle opportunità di lavoro e carriera legate all''esperienza con PostgreSQL, inclusi i ruoli professionali, le competenze richieste e le tendenze del mercato.',
    12,
    programma4_id,
    '30 minutes');
call add_intervento(
    'Successo delle aziende con PostgreSQL',
    'Questo intervento presenterà casi di successo di aziende che hanno adottato PostgreSQL come database principale e i benefici che hanno ottenuto.',
    13,
    programma4_id,
    '30 minutes');
call add_intervallo('coffee break', programma4_id, '20 minutes');
call add_intervento(
    'PostgreSQL e le tecnologie emergenti',
    'Questo intervento esplorerà come PostgreSQL si integra con le tecnologie emergenti, come l''intelligenza artificiale, l''Internet of Things (IoT) e l''analisi dei big data.',
    14,
    programma4_id,
    '30 minutes');
-- Specifica programma sessione 5
select id_programma from programma where id_sessione = sessione5_id into programma5_id;
call add_evento('Apertura sessione',programma5_id,'10 minutes');
call add_intervento(
    'Introduzione a Kubernetes per gli amministratori di database',
    'Questo intervento fornirà un''introduzione a Kubernetes e spiegherà come gli amministratori di database possono utilizzare questa piattaforma per orchestrare e gestire il deployment di istanze di PostgreSQL.',
    15,
    programma5_id,
    '30 minutes');
call add_intervento(
    'Implementazione di PostgreSQL su Kubernetes',
    'Questo intervento illustrerà come implementare PostgreSQL su un cluster Kubernetes, fornendo linee guida e best practice per il deployment, la scalabilità e la gestione delle istanze di PostgreSQL.',
    16,
    programma5_id,
    '30 minutes');
call add_evento('Sessione di domande e risposte',programma5_id,'20 minutes');
call add_intervallo('pranzo', programma5_id, '30 minutes');
exception
    when others then
        raise notice 'Errore inserimento conferenza';
        rollback;
end $$;