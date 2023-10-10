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
'Blockchain e Sicurezza Informatica', --Titolo conferenza
'2023-10-10 9:00', --Inizio
'2023-10-13 12:30',--Fine
1, --Sede
'La conferenza "Blockchain e Sicurezza Informatica: Sfide e Opportunità" è un evento imperdibile per gli esperti e gli appassionati di sicurezza informatica, sviluppo blockchain e tecnologie emergenti. Questa conferenza mira a esplorare il ruolo cruciale della tecnologia blockchain nella creazione di un ambiente informatico più sicuro e affidabile.', --Descrizione
'CNR,POLIMI', --Ente organizzatore
2 --Utente che inserisce la conferenza 
) into conferenza_id;

--Aggiungo sponsorizzazioni
call add_sponsorizzazione(30,5000.50,'USD',conferenza_id); --Apple
call add_sponsorizzazione(45,3500.50,'USD',conferenza_id); --Amazon
call add_sponsorizzazione(20,3500.50,'USD',conferenza_id); --Cisco
call add_sponsorizzazione(12,3500.50,'USD',conferenza_id); --IBM

select comitato_s from conferenza where id_conferenza=conferenza_id into comitato_sc_id;
select comitato_l from conferenza where id_conferenza=conferenza_id into comitato_lc_id;

--Aggiungo membri comitato
call add_membri_comitato('107,109',comitato_sc_id); -- Comitato scientifico
call add_membri_comitato('309,144',comitato_lc_id); -- Comitato locale

-- Aggiunta sessioni
sessione1_id = add_new_sessione('Introduzione alla Blockchain e Sicurezza Informatica','2023-10-10 10:30','2023-10-10 12:30',49,conferenza_id,107);
sessione2_id = add_new_sessione('Applicazioni della Blockchain nella Sicurezza Informatica','2023-10-11 14:30','2023-10-11 16:30',50,conferenza_id,109);
sessione3_id = add_new_sessione('Sicurezza dei Protocolli Blockchain','2023-10-12 10:30','2023-10-12 12:30',51,conferenza_id,109);
sessione4_id = add_new_sessione('Contratti Intelligenti e Sicurezza','2023-10-12 14:30','2023-10-12 18:30',50,conferenza_id,107);
sessione5_id = add_new_sessione('Panoramica delle Minacce Informatiche Attuali','2023-10-13 10:30','2023-10-13 12:30',34,conferenza_id,109);

-- Specifica programma sessione 1
select id_programma from programma where id_sessione = sessione1_id into programma1_id;
call add_evento('Apertura Sessione',programma1_id,'10 minutes');
call add_intervento('Blockchain: Fondamenti e Applicazioni nella Sicurezza Informatica',
'  Questo intervento fornirà una panoramica completa dei concetti di base della tecnologia blockchain e come questi possono essere applicati per migliorare la sicurezza informatica. Esploreremo come la blockchain crea una struttura di dati immutabile e decentralizzata che può resistere alle minacce informatiche.',
1,
programma1_id,
'30 minutes');

call add_intervento(
    'Decentralizzazione e Sicurezza: Un Binomio Essenziale', --Titolo intervento
    'Questo intervento si concentrerà sull''importanza della decentralizzazione nella creazione di un ambiente informatico più sicuro. Saranno discussi esempi concreti di come la decentralizzazione può ridurre i punti di vulnerabilità e migliorare la resilienza contro gli attacchi informatici.', --Descrizione intervento
    2, --Id speaker
    programma1_id, --Id programma
    '30 minutes');

call add_intervallo('coffee break', programma1_id, '20 minutes');

call add_intervento('Sicurezza Informatica nella Blockchain: Sfide e Soluzioni',
'Questo intervento affronterà le sfide specifiche legate alla sicurezza informatica nei protocolli blockchain. Saranno presentate soluzioni pratiche e strategie di difesa per mitigare le minacce e garantire la sicurezza dei dati nella blockchain.',
3,
programma1_id,
'30 minutes');

-- Specifica programma sessione 2
select id_programma from programma where id_sessione = sessione2_id into programma2_id;
call add_evento('Apertura sessione',programma2_id,'10 minutes');
call add_intervento(
    'Blockchain per la tracciabilità alimentare: Un approccio sostenibile alla sicurezza alimentare',
    'Esamineremo come la tecnologia blockchain possa essere utilizzata per migliorare la tracciabilità degli alimenti, promuovendo la sostenibilità e la sicurezza alimentare.',
    4,
    programma2_id,
    '30 minutes');
call add_intervento(
    'Blockchain per la Gestione delle Identità Digitali',
    ' Questo intervento esplorerà come la blockchain può essere utilizzata per migliorare la gestione delle identità digitali, garantendo la sicurezza e la privacy degli utenti. Saranno presentati casi di studio di progetti che stanno rivoluzionando la gestione delle identità attraverso la blockchain.',
    5,
    programma2_id,
    '30 minutes');
call add_intervallo('coffee break', programma2_id, '20 minutes');

call add_intervento(
    'Blockchain nella Sanità: Garantire l''Integrità dei Dati Medici"',
    'Questo intervento esaminerà come la blockchain viene utilizzata nel settore sanitario per garantire l''integrità e la sicurezza dei dati medici. Saranno discussi casi di successo di sistemi che consentono ai pazienti di controllare i propri dati in modo sicuro e trasparente.',
    6,
    programma2_id,
    '30 minutes');

-- Specifica programma sessione 3
select id_programma from programma where id_sessione = sessione3_id into programma3_id;

call add_evento('Apertura sessione',programma3_id,'10 minutes');

call add_intervento(
    'Sicurezza Crittografica nei Protocolli Blockchain',
    'Questo intervento si concentrerà sulla crittografia e sulla sua importanza nella protezione dei protocolli blockchain. Saranno esaminati gli algoritmi crittografici utilizzati nella blockchain e come questi possono essere resi più sicuri.',
    7,
    programma3_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma3_id,'30 minutes');
call add_intervallo('coffee break', programma3_id, '20 minutes');

call add_intervento(
    'Contratti Intelligenti Sicuri: Prevenire Vulnerabilità e Attacchi',
    'Questo intervento esaminerà le sfide legate alla sicurezza dei contratti intelligenti basati su blockchain. Saranno presentate best practice per evitare vulnerabilità e attacchi, con esempi di contratti intelligenti ben progettati',
    8,
    programma3_id,
    '30 minutes');

call add_intervento(
    'Proteggere la Blockchain: Dalla DDoS alla Consensus',
    ' Questo intervento affronterà diverse minacce alla sicurezza dei protocolli blockchain, dalle attività di tipo DDoS agli attacchi alla struttura di consenso. Saranno discusse strategie per proteggere la rete blockchain da queste minacce.',
    9,
    programma3_id,
    '30 minutes');

-- Specifica programma sessione 4
select id_programma from programma where id_sessione = sessione4_id into programma4_id;
call add_evento('Apertura sessione',programma4_id,'10 minutes');
call add_intervento(
    'Analisi Statica e Dinamica dei Contratti Intelligenti',
    'Questo intervento esplorerà come l''analisi statica e dinamica dei contratti intelligenti può rivelare vulnerabilità potenziali. Saranno presentati strumenti e metodologie per identificare e correggere errori critici nei contratti basati su blockchain.',
    10,
    programma4_id,
    '30 minutes');

call add_intervento(
    'Privacy nei Contratti Intelligenti: Tecnologie e Sfide',
    ' Questo intervento si concentrerà sulla privacy nei contratti intelligenti. Saranno discussi approcci per garantire la privacy dei dati sensibili all''interno dei contratti, tenendo conto delle sfide legate alla trasparenza della blockchain.',
    11,
    programma4_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma4_id,'30 minutes');

call add_evento('Networking e dibattito',programma4_id,'30 minutes');

call add_intervento(
    'Auditing dei Contratti Intelligenti: Approcci e Metodologie',
    'Questo intervento si concentrerà sull''importanza dell''audit dei contratti intelligenti come parte essenziale della sicurezza delle applicazioni blockchain. Saranno presentati approcci e metodologie per condurre audit approfonditi dei contratti intelligenti al fine di identificare e mitigare potenziali vulnerabilità. Saranno discussi strumenti disponibili e best practice per garantire che i contratti intelligenti siano sicuri e affidabili. L''intervento includerà anche esempi concreti di audit di contratti intelligenti e le lezioni apprese da casi reali. Gli ascoltatori acquisiranno una comprensione chiara di come pianificare e condurre audit efficaci dei contratti intelligenti, contribuendo così a migliorare la sicurezza complessiva delle applicazioni blockchain basate su contratti.',
    12,
    programma4_id,
    '30 minutes');

call add_intervallo('coffee break', programma4_id, '20 minutes');

-- Specifica programma sessione 5
select id_programma from programma where id_sessione = sessione5_id into programma5_id;
call add_evento('Apertura sessione',programma5_id,'10 minutes');
call add_intervento(
    'Minacce Informatiche Emergenti: Cosa Sta Cambiando?',
    'Questo intervento esaminerà le minacce informatiche emergenti che stanno cambiando il panorama della sicurezza informatica. Saranno discusse nuove tattiche utilizzate dai criminali informatici e come le aziende possono prepararsi per affrontarle.',
    15,
    programma5_id,
    '30 minutes');

call add_intervento(
    'Blockchain come Difesa: Casi di Studio di Successo',
    'Questo intervento presenterà casi di studio in cui la blockchain è stata utilizzata con successo come strumento di difesa contro le minacce informatiche. Saranno evidenziate le esperienze di organizzazioni che hanno adottato la blockchain per rafforzare la loro sicurezza informatica.',
    16,
    programma5_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma5_id,'30 minutes');

call add_intervallo('pranzo', programma5_id, '30 minutes');

exception
    when others then
        raise notice 'Errore inserimento conferenza';
        rollback;
end $$;