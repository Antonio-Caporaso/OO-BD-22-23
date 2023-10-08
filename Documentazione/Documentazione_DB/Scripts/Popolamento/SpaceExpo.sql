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
'International Space Expo', --Titolo conferenza
'2023-08-03 8:00', --Inizio
'2023-08-05 20:00',--Fine
2,
'International Space Expo Italy 2023 is a three-day event held in Milan, Italy, showcasing a range of cutting-edge aerospace prototypes. The Expo features multiple sessions during which various speakers will discuss diverse projects.', --Descrizione
'ASI', --Ente organizzatore
2 --Utente che inserisce la conferenza 
) into conferenza_id;

--Aggiungo sponsorizzazioni
call add_sponsorizzazione(50,50000.00,'USD',conferenza_id); --Space X
call add_sponsorizzazione(51,10000.00,'USD',conferenza_id); --Blue Origins LLC
call add_sponsorizzazione(52,3500.50,'USD',conferenza_id); --Virgin Galactic
call add_sponsorizzazione(48,3500.50,'USD',conferenza_id); --SSC Napoli

select comitato_s from conferenza where id_conferenza=conferenza_id into comitato_sc_id;
select comitato_l from conferenza where id_conferenza=conferenza_id into comitato_lc_id;

--Aggiungo membri comitato
call add_membri_comitato('137,142',comitato_sc_id); -- Comitato scientifico
call add_membri_comitato('161,250',comitato_lc_id); -- Comitato locale

-- Aggiunta sessioni
sessione1_id = add_new_sessione('Exploring Mars: The Future of Human Colonization','2023-08-03 10:00','2023-08-03 13:00',4,conferenza_id,137);
sessione2_id = add_new_sessione('Space Tourism: A New Era of Extraterrestrial Travel','2023-08-03 13:30','2023-08-03 17:30',5,conferenza_id,142);
sessione3_id = add_new_sessione('Satellite Constellations: Revolutionizing Global Connectivity','2023-08-04 10:30','2023-08-04 12:30',6,conferenza_id,142);
sessione4_id = add_new_sessione('Sustainable Space Exploration: Protecting Celestial Bodies and Earth','2023-08-04 13:30','2023-08-04 19:30',4,conferenza_id,137);
sessione5_id = add_new_sessione('Innovations in Space Research: From Astrophysics to Exoplanet Discoveries','2023-08-05 9:30','2023-08-05 20:00',5,conferenza_id,142);

-- Specifica programma sessione 1
select id_programma from programma where id_sessione = sessione1_id into programma1_id;

call add_evento(
    'Opening Event',
    programma1_id,
    '15 minutes'
    );

call add_intervento(
    'Advancements in Mars Rover Technology',
    ' This talk will delve into the latest advancements in Mars rover technology, highlighting the capabilities and challenges of exploring the Martian surface.',
    1,
    programma1_id,
    '30 minutes'
    );

call add_intervento(
    'Sustainable Life Support Systems for Martian Habitats', --Titolo intervento
    'This talk will focus on the development of sustainable life support systems that can sustain human life on Mars, discussing innovative solutions for food production, water recycling, and waste management.', --Descrizione intervento
    2, --Id speaker
    programma1_id, --Id programma
    '30 minutes'
    ); --Durata intervento

call add_intervallo('coffee break', programma1_id, '20 minutes');

call add_intervento(
    'The Psychological Challenges of Long-Duration Space Missions',
    'This talk will explore the psychological effects of long-duration space missions on astronauts and propose strategies to mitigate the associated challenges, emphasizing the importance of mental health support.',
    3,
    programma1_id,
    '30 minutes'
    );

-- Specifica programma sessione 2
select id_programma from programma where id_sessione = sessione2_id into programma2_id;

call add_evento('Introduction',programma2_id,'10 minutes');

call add_intervento(
    'Commercial Spaceflights: Making Space Accessible to Everyone',
    'This talk will discuss the rise of commercial spaceflights and the democratization of space travel, exploring the opportunities and challenges in making space accessible to a broader audience.',
    4,
    programma2_id,
    '30 minutes');

call add_intervento(
    'Space Tourism and Its Impact on Earth''s Economy',
    'This talk will analyze the economic impact of space tourism on Earth, examining the potential benefits for industries such as hospitality, transportation, and entertainment.',
    5,
    programma2_id,
    '30 minutes');

call add_intervallo('coffee break', programma2_id, '20 minutes');

call add_intervento(
    'Designing Luxurious Space Hotels',
    'This talk will explore the architectural and design considerations for creating luxurious space hotels, showcasing innovative concepts and technologies that redefine the future of hospitality in space.',
    6,
    programma2_id,
    '30 minutes');
-- Specifica programma sessione 3
select id_programma from programma where id_sessione = sessione3_id into programma3_id;

call add_evento('Welcome address and brief overview of the session',programma3_id,'10 minutes');

call add_intervento(
    'The Role of Satellite Constellations in Bridging the Digital Divide',
    'This talk will highlight the significance of satellite constellations in providing global connectivity, focusing on their potential to bridge the digital divide and connect underserved regions.',
    7,
    programma3_id,
    '30 minutes');

call add_intervento('Challenges and Solutions in Managing Satellite Constellations',
    'This talk will address the challenges associated with managing large-scale satellite constellations, including orbital debris, spectrum allocation, and collision avoidance, while proposing solutions for a sustainable space environment.',
    8,
    programma3_id,'30 minutes');

call add_intervallo('coffee break', programma3_id, '20 minutes');

call add_intervento(
    'Next-Generation Satellite Communication Technologies',
    'This talk will showcase the latest advancements in satellite communication technologies, including high-speed data transfer, inter-satellite links, and improved signal reliability, revolutionizing global connectivity',
    9,
    programma3_id,
    '30 minutes');

-- Specifica programma sessione 4
select id_programma from programma where id_sessione = sessione4_id into programma4_id;
call add_evento('Welcome address and brief overview of the session',programma4_id,'10 minutes');
call add_intervento(
    'Preserving Lunar Heritage: Responsible Exploration of the Moon',
    'This talk will emphasize the importance of preserving the historical and cultural significance of lunar sites during future lunar missions, discussing guidelines and protocols for responsible lunar exploration.',
    10,
    programma4_id,
    '30 minutes');
call add_intervento(
    'Environmental Impact Assessment for Space Launches',
    'This talk will explore the environmental impact of space launches and propose methods to minimize their ecological footprint, including propulsion advancements, launch site selection, and post-launch debris management.',
    11,
    programma4_id,
    '30 minutes');
call add_intervento(
    'Space-Based Solutions for Climate Change Monitoring',
    'This talk will highlight the role of satellite-based technologies in monitoring and addressing climate change, showcasing examples of Earth observation missions and their impact on climate research and policy-making',
    12,
    programma4_id,
    '30 minutes');
-- Specifica programma sessione 5

select id_programma from programma where id_sessione = sessione5_id into programma5_id;

call add_evento('Welcome address and brief overview of the session',programma5_id,'10 minutes');

call add_intervento(
    'Revolutionizing Space Telescopes: From Hubble to James Webb',
    'This talk will provide an overview of the advancements in space telescope technology, discussing the contributions of iconic missions like Hubble and the upcoming James Webb Space Telescope in revolutionizing astrophysics.',
    15,
    programma5_id,
    '30 minutes');

call add_intervento(
    'Discovering Exoplanets: From Kepler to TESS',
    'This talk will focus on the discoveries and insights gained from exoplanet-hunting missions like Kepler and TESS, shedding light on the diversity of exoplanetary systems and their potential for harboring life.',
    16,
    programma5_id,
    '30 minutes');

call add_evento('Questions and answers',programma5_id,'20 minutes');

call add_intervallo('pranzo', programma5_id, '30 minutes');

exception
    when others then
        raise notice 'Errore inserimento conferenza';
        rollback;
end $$;