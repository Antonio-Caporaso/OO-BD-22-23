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
'Innovazione Tecnologica del 21° secolo', --Titolo conferenza
'2023-10-01 9:00', --Inizio
'2023-10-05 12:30',--Fine
17, --Sede
'La Conferenza sull''Innovazione Tecnologica nel 21° Secolo è un evento globale che riunisce i principali esperti per esplorare il futuro delle tecnologie e il loro impatto sulla società contemporanea.', --Descrizione
'CNR,POLIMI', --Ente organizzatore
1 --Utente che inserisce la conferenza 
) into conferenza_id;

--Aggiungo sponsorizzazioni
call add_sponsorizzazione(24,5000.50,'USD',conferenza_id); --Apple
call add_sponsorizzazione(29,3500.50,'USD',conferenza_id); --Amazon
call add_sponsorizzazione(53,3500.50,'USD',conferenza_id); --Cisco
call add_sponsorizzazione(2,3500.50,'USD',conferenza_id); --IBM

select comitato_s from conferenza where id_conferenza=conferenza_id into comitato_sc_id;
select comitato_l from conferenza where id_conferenza=conferenza_id into comitato_lc_id;

--Aggiungo membri comitato
call add_membri_comitato('107,109',comitato_sc_id); -- Comitato scientifico
call add_membri_comitato('309,144',comitato_lc_id); -- Comitato locale

-- Aggiunta sessioni
sessione1_id = add_new_sessione('Le sfide dell''intelligenza artificiale','2023-10-01 10:30','2023-10-01 12:30',49,conferenza_id,107);
sessione2_id = add_new_sessione('Sostenibilità e Tecnologia','2023-10-02 14:30','2023-10-02 16:30',50,conferenza_id,109);
sessione3_id = add_new_sessione('Cybersecurity nel mondo digitale','2023-10-03 10:30','2023-10-04 12:30',51,conferenza_id,109);
sessione4_id = add_new_sessione('Futuro dell''istruzione digitale','2023-10-04 14:30','2023-10-04 18:30',50,conferenza_id,107);
sessione5_id = add_new_sessione('Innovazione nel settore della salute','2023-10-05 10:30','2023-10-05 12:30',34,conferenza_id,109);

-- Specifica programma sessione 1
select id_programma from programma where id_sessione = sessione1_id into programma1_id;
call add_evento('Apertura sessione',programma1_id,'10 minutes');
call add_intervento('Etica nell''IA: Affrontare le sfide morali nell''apprendimento automatico',
'  Questo intervento esplorerà le questioni etiche che sorgono con l''uso diffuso dell''intelligenza artificiale e come affrontarle in modo responsabile.',
1,
programma1_id,
'30 minutes');

call add_intervento(
    '"L''IA nel settore sanitario: Trasformare la diagnosi medica', --Titolo intervento
    'Un''analisi delle applicazioni dell''IA nella medicina diagnostica, con esempi concreti di come la tecnologia sta migliorando la precisione delle diagnosi.', --Descrizione intervento
    2, --Id speaker
    programma1_id, --Id programma
    '30 minutes');

call add_intervallo('coffee break', programma1_id, '20 minutes');

call add_intervento('L''evoluzione dell''apprendimento automatico: Dalle reti neurali alle reti neurali artificiali',
'Questo intervento fornirà una panoramica delle ultime tendenze nell''apprendimento automatico, concentrandosi sulla transizione dalle reti neurali tradizionali alle nuove reti neurali artificiali.',
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
    'Energia solare e IoT: La congiunzione per una gestione energetica intelligente',
    ' Questo intervento esplorerà come l''Internet delle cose (IoT) e l''energia solare possano lavorare insieme per ottimizzare l''uso delle risorse energetiche in modo sostenibile.',
    5,
    programma2_id,
    '30 minutes');
call add_intervallo('coffee break', programma2_id, '20 minutes');

call add_intervento(
    'Mobilità urbana sostenibile: Veicoli elettrici e condivisione dei trasporti',
    'Un''analisi delle tecnologie emergenti nella mobilità urbana, inclusi veicoli elettrici e sistemi di condivisione dei trasporti, per ridurre l''impatto ambientale delle città.',
    6,
    programma2_id,
    '30 minutes');

-- Specifica programma sessione 3
select id_programma from programma where id_sessione = sessione3_id into programma3_id;

call add_evento('Apertura sessione',programma3_id,'10 minutes');

call add_intervento(
    'La minaccia delle vulnerabilità zero-day: Difendersi dagli attacchi informatici',
    'Questo intervento esplorerà le sfide poste dalle vulnerabilità zero-day e come le aziende possono proteggersi da attacchi informatici avanzati.',
    7,
    programma3_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma3_id,'30 minutes');
call add_intervallo('coffee break', programma3_id, '20 minutes');

call add_intervento(
    'Intelligenza artificiale e sicurezza cibernetica: Un''arma a doppio taglio?',
    'Una discussione sui vantaggi e sulle sfide dell''uso dell''IA nella sicurezza cibernetica, con un focus sugli attacchi basati sull''IA.',
    8,
    programma3_id,
    '30 minutes');

call add_intervento(
    'Protezione della privacy nell''era digitale: Il ruolo delle leggi e delle tecnologie',
    ' Esamineremo come la legislazione sulla privacy e le tecnologie come la crittografia contribuiscano a proteggere la privacy degli utenti online.',
    9,
    programma3_id,
    '30 minutes');

-- Specifica programma sessione 4
select id_programma from programma where id_sessione = sessione4_id into programma4_id;
call add_evento('Apertura sessione',programma4_id,'10 minutes');
call add_intervento(
    'Apprendimento automatico nell''istruzione: Personalizzazione e valutazione',
    'Questo intervento esplorerà come l''apprendimento automatico possa essere utilizzato per personalizzare l''istruzione e migliorare la valutazione degli studenti.',
    10,
    programma4_id,
    '30 minutes');

call add_intervento(
    'Educazione virtuale e realtà aumentata: Sfide e opportunità',
    ' Una panoramica delle applicazioni della realtà aumentata nell''istruzione e delle sfide legate all''adozione di queste tecnologie.',
    11,
    programma4_id,
    '30 minutes');

call add_evento('Sessione di domande e risposte',programma4_id,'30 minutes');

call add_evento('Networking e dibattito',programma4_id,'30 minutes');

call add_intervento(
    'Accesso all''istruzione globale: Come la tecnologia sta abbattendo le barriere',
    'Esamineremo come la tecnologia sta contribuendo a fornire accesso all''istruzione di qualità a livello globale, superando le barriere geografiche ed economiche.',
    12,
    programma4_id,
    '30 minutes');

call add_intervallo('coffee break', programma4_id, '20 minutes');

-- Specifica programma sessione 5
select id_programma from programma where id_sessione = sessione5_id into programma5_id;
call add_evento('Apertura sessione',programma5_id,'10 minutes');
call add_intervento(
    'Telemedicina e IoT: Rivoluzionare la cura della salute a distanza',
    'Un''analisi delle soluzioni innovative che combinano la telemedicina con l''Internet delle cose per migliorare l''accesso e la qualità della cura della salute.',
    15,
    programma5_id,
    '30 minutes');

call add_intervento(
    'Medicina di precisione: Trattamenti personalizzati grazie alla genomica',
    'Questo intervento esplorerà come la genomica e l''analisi dei dati genomici stiano consentendo trattamenti medici altamente personalizzati.',
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