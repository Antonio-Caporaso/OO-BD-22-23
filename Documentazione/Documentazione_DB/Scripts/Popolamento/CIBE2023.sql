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

select * from add_conferenza(
'CIBE 2023', --Titolo conferenza
'2023-7-01 9:00', --Inizio
'2023-7-05 12:30',--Fine
3, --Sede
'La Conferenza Internazionale sulla Biodiversità e l''Ecologia (CIBE 2023) riunisce scienziati, ricercatori e esperti di tutto il mondo per discutere le più recenti scoperte e questioni cruciali legate alla conservazione della biodiversità e alla sostenibilità dell''ecosistema globale. La conferenza fornirà un''opportunità unica per lo scambio di conoscenze e idee, nonché per la promozione di azioni concrete per la salvaguardia del nostro pianeta.',
'CNR,OGS', --Ente organizzatore
2 --Utente che inserisce la conferenza 
) into conferenza_id;

--Aggiungo sponsorizzazioni
call add_sponsorizzazione(35,5000.50,'USD',conferenza_id); --Apple
call add_sponsorizzazione(7,3500.50,'USD',conferenza_id); --Amazon
call add_sponsorizzazione(37,3500.50,'USD',conferenza_id); --Cisco

select comitato_s from conferenza where id_conferenza=conferenza_id into comitato_sc_id;
select comitato_l from conferenza where id_conferenza=conferenza_id into comitato_lc_id;

--Aggiungo membri comitato
call add_membri_comitato('107,109',comitato_sc_id); -- Comitato scientifico
call add_membri_comitato('126,144',comitato_lc_id); -- Comitato locale

-- Aggiunta sessioni
sessione1_id = add_new_sessione('Conservazione della Biodiversità Terrestre','2023-7-01 10:30','2023-7-01 12:30',7,conferenza_id,107);
sessione2_id = add_new_sessione('Biodiversità Marina e Acquatica','2023-7-02 14:30','2023-7-02 16:30',7,conferenza_id,109);
sessione3_id = add_new_sessione('Ecologia Urbana e Biodiversità in Ambienti Antropizzati','2023-7-03 10:30','2023-07-04 12:30',9,conferenza_id,109);
sessione4_id = add_new_sessione('Ecologia Globale e Cambiamenti Globali','2023-7-04 14:30','2023-07-04 18:30',7,conferenza_id,107);
sessione5_id = add_new_sessione('Educazione e Comunicazione per la Conservazione','2023-7-05 10:30','2023-07-05 12:30',8,conferenza_id,109);

-- Specifica programma sessione 1
select id_programma from programma where id_sessione = sessione1_id into programma1_id;
call add_evento('Apertura sessione',programma1_id,'10 minutes');
call add_intervento('Strategie innovative per la conservazione degli habitat terrestri',
' Questo intervento esplorerà le nuove strategie e tecnologie per preservare gli ecosistemi terrestri minacciati, con un focus sulle soluzioni basate sulla tecnologia AI e la modellazione ecologica',
23,
programma1_id,
'30 minutes');

call add_intervento(
    'Il ruolo delle riserve naturali nella conservazione della biodiversità', --Titolo intervento
    'Questo intervento analizzerà l''importanza delle riserve naturali nel preservare la diversità biologica e nel fornire un rifugio per le specie in pericolo',
    254, --Id speaker
    programma1_id, --Id programma
    '30 minutes');

call add_intervallo('coffee break', programma1_id, '20 minutes');

call add_intervento(
    'Restauro ecologico per la conservazione della biodiversità',
    'Questo intervento presenterà casi di successo di restauro ecologico e discuterà delle sfide e delle opportunità nel ripristinare gli ecosistemi danneggiati',
    34,
    programma1_id,
    '30 minutes');

-- Specifica programma sessione 2
select id_programma from programma where id_sessione = sessione2_id into programma2_id;

call add_evento('Apertura sessione',programma2_id,'10 minutes');

call add_intervento(
    'Impatti dei cambiamenti climatici sugli ecosistemi marini',
    'Questo intervento esaminerà gli effetti dei cambiamenti climatici sugli oceani, compresi gli sbalzi di temperatura, l''acidificazione e le risposte delle specie marine',
    157,
    programma2_id,
    '30 minutes');

call add_intervento(
    'Conservazione delle specie marine minacciate',
    ' Questo intervento affronterà le strategie di conservazione per le specie marine in via di estinzione e le misure per proteggere gli habitat costieri',
    35,
    programma2_id,
    '30 minutes');

call add_intervallo('coffee break', programma2_id, '20 minutes');

call add_intervento(
    'Tecnologie innovative per il monitoraggio dell''ecosistema marino"',
    'Questo intervento esporrà le nuove tecnologie utilizzate per monitorare e studiare la biodiversità marina, come i droni subacquei e i sensori oceanografici avanzati',
    63,
    programma2_id,
    '30 minutes');

-- Specifica programma sessione 3
select id_programma from programma where id_sessione = sessione3_id into programma3_id;

call add_evento('Apertura sessione',programma3_id,'10 minutes');

call add_intervento(
    'La biodiversità nelle città del futuro',
    'Questo intervento esaminerà come progettare città sostenibili che promuovano la coesistenza tra l''ambiente urbano e la biodiversità',
    27,
    programma3_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma3_id,'30 minutes');
call add_intervallo('coffee break', programma3_id, '20 minutes');

call add_intervento(
    'Rischi e opportunità per la fauna selvatica urbana',
    'Questo intervento tratterà degli impatti delle città sulla fauna selvatica e delle strategie per mitigare i conflitti)',
    84,
    programma3_id,
    '30 minutes');

call add_intervento(
    'Giardinaggio urbano per la biodiversità',
    'Questo intervento presenterà approcci innovativi per la creazione di giardini urbani che favoriscano la biodiversità e la salute degli ecosistemi urbani',
    91,
    programma3_id,
    '30 minutes');

-- Specifica programma sessione 4
select id_programma from programma where id_sessione = sessione4_id into programma4_id;
call add_evento('Apertura sessione',programma4_id,'10 minutes');
call add_intervento(
    'Impatti dei cambiamenti globali sulla distribuzione delle specie',
    'Questo intervento esaminerà come i cambiamenti climatici influenzano la distribuzione geografica delle specie e le loro risposte adattative',
    10,
    programma4_id,
    '30 minutes');

call add_intervento(
    'Ruolo delle risorse naturali nell''adattamento ai cambiamenti globali',
    'Questo intervento discuterà della gestione sostenibile delle risorse naturali come strategia per affrontare i cambiamenti globali',
    11,
    programma4_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma4_id,'30 minutes');

call add_evento('Networking e dibattito',programma4_id,'30 minutes');

call add_intervento(
    'Prospettive future per la ricerca sulla biodiversità globale',
    'Questo intervento esplorerà le sfide e le opportunità future per la ricerca sulla biodiversità in un mondo in rapido cambiamento',
    12,
    programma4_id,
    '30 minutes');

call add_intervallo('coffee break', programma4_id, '20 minutes');

-- Specifica programma sessione 5
select id_programma from programma where id_sessione = sessione5_id into programma5_id;
call add_evento('Apertura sessione',programma5_id,'10 minutes');
call add_intervento(
    'Comunicare la scienza della biodiversità al pubblico',
    'Questo intervento discuterà delle strategie efficaci per comunicare la scienza della biodiversità al pubblico e promuovere l''interesse e l''azione',
    15,
    programma5_id,
    '30 minutes');

call add_intervento(
    'Educazione ambientale nelle scuole',
    'Questo intervento esaminerà il ruolo cruciale dell''educazione ambientale nella formazione delle nuove generazioni',
    16,
    programma5_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma5_id,'30 minutes');


exception
    when others then
        raise notice 'Errore inserimento conferenza: %', SQLERRM;
        rollback;
end $$;