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
        'Future of Robotics Summit', --Titolo conferenza
        '2023-11-15 9:00', --Inizio
        '2023-11-17 18:00',--Fine
        7,
        'Future of Robotics Summit is a leading conference dedicated to exploring the latest advancements, challenges, and applications of robotics in various industries, from manufacturing and healthcare to space exploration and AI integration.', --Descrizione
        'CISAM', --Ente organizzatore
        2 --Utente che inserisce la conferenza 
    ) into conferenza_id;
    
    --Aggiungo sponsorizzazioni
    call add_sponsorizzazione(30, 60000.00, 'USD', conferenza_id); --IBM
    call add_sponsorizzazione(50, 20000.00, 'USD', conferenza_id); --BMW
    call add_sponsorizzazione(53, 15000.50, 'USD', conferenza_id); --Tesla
    call add_sponsorizzazione(54, 5000.00, 'USD', conferenza_id); --SpaceX

    select comitato_s from conferenza where id_conferenza = conferenza_id into comitato_sc_id;
    select comitato_l from conferenza where id_conferenza = conferenza_id into comitato_lc_id;

    --Aggiungo membri comitato
    call add_membri_comitato('21,105,303', comitato_sc_id); -- Comitato scientifico
    call add_membri_comitato('383,136', comitato_lc_id); -- Comitato locale

    -- Aggiunta sessioni
    sessione1_id = add_new_sessione('Industrial Robotics and Automation', '2023-11-15 10:00', '2023-11-15 13:00', 19, conferenza_id, 21);
    sessione2_id = add_new_sessione('Robotics in Healthcare: Innovations and Applications', '2023-11-15 14:00', '2023-11-15 17:00', 20, conferenza_id, 105);
    sessione3_id = add_new_sessione('Exploring Space with Robotics', '2023-11-16 10:30', '2023-11-16 12:30', 21, conferenza_id, 21);
    sessione4_id = add_new_sessione('Robotics and Artificial Intelligence Integration', '2023-11-16 13:30', '2023-11-16 18:00', 20, conferenza_id, 105);
    sessione5_id = add_new_sessione('Social and Ethical Implications of Robotics', '2023-11-17 9:30', '2023-11-17 17:00', 21, conferenza_id, 303);

    -- Specifica programma sessione 1
    select id_programma from programma where id_sessione = sessione1_id into programma1_id;

    call add_evento(
        'Opening Keynote',
        programma1_id,
        '30 minutes'
    );

    call add_intervento(
        'Advancements in Industrial Robotics',
        'This talk will explore the latest advancements in industrial robotics, including collaborative robots, autonomous systems, and human-robot interaction in manufacturing environments.',
        434,
        programma1_id,
        '30 minutes'
    );

    call add_intervento(
        'Robotics Process Automation: Streamlining Industrial Operations',
        'This talk will discuss the benefits of robotics process automation (RPA) in industrial settings, showcasing real-world examples and case studies of increased efficiency and productivity.',
        223,
        programma1_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma1_id, '20 minutes');

    call add_intervento(
        'Safety Considerations in Industrial Robotics',
        'This talk will address the safety challenges and regulations associated with industrial robotics, highlighting the importance of risk assessment, protective measures, and human-machine collaboration.',
        323,
        programma1_id,
        '30 minutes'
    );

    -- Specifica programma sessione 2
    select id_programma from programma where id_sessione = sessione2_id into programma2_id;

    call add_evento('Introduction to Robotics in Healthcare', programma2_id, '20 minutes');

    call add_intervento(
        'Surgical Robotics: Enhancing Precision and Minimally Invasive Procedures',
        'This talk will explore the applications of robotics in surgical procedures, discussing the advancements in surgical robotics, robotic-assisted surgeries, and their impact on patient outcomes.',
        467,
        programma2_id,
        '30 minutes'
    );

    call add_intervento(
        'Robotics in Rehabilitation and Assistive Technologies',
        'This talk will highlight the role of robotics in rehabilitation therapies and assistive technologies for individuals with disabilities, showcasing examples of exoskeletons, prosthetics, and robotic therapy devices.',
        532,
        programma2_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma2_id, '20 minutes');

    call add_intervento(
        'Robotics for Elderly Care and Aging Population',
        'This talk will discuss the applications of robotics in elderly care, addressing the challenges and benefits of using robots in assisting the elderly with daily activities, healthcare monitoring, and social interaction.',
        612,
        programma2_id,
        '30 minutes'
    );

    -- Specifica programma sessione 3
    select id_programma from programma where id_sessione = sessione3_id into programma3_id;

    call add_evento('Introduction to Space Robotics', programma3_id, '20 minutes');

    call add_intervento(
        'Exploring Planets with Robotic Probes',
        'This talk will highlight the role of robotics in space exploration, discussing the use of robotic probes, rovers, and landers to study and collect data from planets, moons, and asteroids.',
        754,
        programma3_id,
        '30 minutes'
    );

    call add_intervento(
        'Future of Lunar and Martian Robotics',
        'This talk will provide insights into the future of lunar and Martian robotics, discussing planned missions, challenges of operating in extraterrestrial environments, and the potential for human-robot collaboration in space.',
        814,
        programma3_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma3_id, '20 minutes');

    call add_intervento(
        'Robotic Assembly and Maintenance in Space',
        'This talk will explore the advancements in robotic assembly and maintenance of space infrastructure, discussing concepts such as in-orbit servicing, space debris removal, and satellite repair missions.',
        956,
        programma3_id,
        '30 minutes'
    );

    -- Specifica programma sessione 4
    select id_programma from programma where id_sessione = sessione4_id into programma4_id;

    call add_evento('Introduction to Robotics and AI Integration', programma4_id, '20 minutes');

    call add_intervento(
        'Cognitive Robotics: Integrating AI and Robotics',
        'This talk will explore the integration of artificial intelligence (AI) techniques with robotics, discussing cognitive capabilities, machine learning algorithms, and the future of intelligent robotic systems.',
        134,
        programma4_id,
        '30 minutes'
    );

    call add_intervento(
        'Human-Robot Interaction: Challenges and Solutions',
        'This talk will address the challenges and solutions in human-robot interaction, discussing natural language processing, gesture recognition, and user-centered design for intuitive and collaborative robot interfaces.',
        511,
        programma4_id,
        '30 minutes'
    );

    call add_intervento(
        'Robots in Industry 4.0: Smart Factories and Automation',
        'This talk will discuss the role of robotics in Industry 4.0, exploring concepts such as autonomous vehicles, smart factories, and the integration of robotics with Internet of Things (IoT) technologies.',
        712,
        programma4_id,
        '30 minutes'
    );
    call add_keynote_speaker(511,sessione4_id);

    -- Specifica programma sessione 5
    select id_programma from programma where id_sessione = sessione5_id into programma5_id;

    call add_evento('Social and Ethical Implications of Robotics', programma5_id, '20 minutes');

    call add_intervento(
        'Robotics and Employment: Reshaping the Workforce',
        'This talk will explore the impact of robotics on employment and the workforce, discussing the potential benefits, challenges, and strategies for managing the transition towards a robotic-driven future.',
        313,
        programma5_id,
        '30 minutes'
    );

    call add_intervento(
        'Ethical Considerations in Robotics Development',
        'This talk will address the ethical considerations in robotics development, including issues related to privacy, bias, autonomy, and the responsible use of robots in society.',
        614,
        programma5_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma5_id, '20 minutes');

    call add_intervento(
        'Robotics and Social Impact',
        'This talk will showcase the social impact of robotics in areas such as education, disaster response, and environmental conservation, discussing how robots can contribute to solving global challenges.',
        915,
        programma5_id,
        '30 minutes'
    );
    call add_keynote_speaker(915,sessione5_id);

exception
    when others then
        raise notice 'Errore inserimento conferenza: %', sqlerrm ;
        rollback;
end $$;
