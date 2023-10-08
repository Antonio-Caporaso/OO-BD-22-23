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
        'Global Tech Summit', --Titolo conferenza
        '2023-10-10 9:00', --Inizio
        '2023-10-12 18:00',--Fine
        1,
        'Global Tech Summit is a premier technology conference bringing together industry leaders, innovators, and experts to discuss the latest trends and advancements in the tech industry.', --Descrizione
        'CNR,ASI', --Ente organizzatore
        1 --Utente che inserisce la conferenza 
    ) into conferenza_id;
    
    --Aggiungo sponsorizzazioni
    call add_sponsorizzazione(3, 50000.00, 'USD', conferenza_id); --Microsoft
    call add_sponsorizzazione(5, 10000.00, 'USD', conferenza_id); --Facebook
    call add_sponsorizzazione(14, 3500.50, 'USD', conferenza_id); --Intel
    call add_sponsorizzazione(29, 2000.00, 'USD', conferenza_id); --Oracle

    select comitato_s from conferenza where id_conferenza = conferenza_id into comitato_sc_id;
    select comitato_l from conferenza where id_conferenza = conferenza_id into comitato_lc_id;

    --Aggiungo membri comitato
    call add_membri_comitato('107,109,114', comitato_sc_id); -- Comitato scientifico
    call add_membri_comitato('142,150,250', comitato_lc_id); -- Comitato locale

    -- Aggiunta sessioni
    sessione1_id = add_new_sessione('Artificial Intelligence and Machine Learning', '2023-10-10 10:00', '2023-10-10 13:00', 1, conferenza_id, 107);
    sessione2_id = add_new_sessione('Cybersecurity and Data Privacy', '2023-10-10 14:00', '2023-10-10 17:00', 2, conferenza_id, 109);
    sessione3_id = add_new_sessione('Emerging Technologies: Blockchain and Internet of Things', '2023-10-11 10:30', '2023-10-11 12:30', 3, conferenza_id, 114);
    sessione4_id = add_new_sessione('Future of Technology: Trends and Innovations', '2023-10-11 13:30', '2023-10-11 18:00', 2, conferenza_id, 107);
    sessione5_id = add_new_sessione('Tech Startups and Entrepreneurship', '2023-10-12 9:30', '2023-10-12 17:00', 3, conferenza_id, 109);

    -- Specifica programma sessione 1
    select id_programma from programma where id_sessione = sessione1_id into programma1_id;

    call add_evento(
        'Opening Keynote',
        programma1_id,
        '30 minutes'
    );

    call add_intervento(
        'Applications of Artificial Intelligence in Healthcare',
        'This talk will explore the various applications of artificial intelligence and machine learning in the healthcare industry, discussing how AI can improve diagnosis, treatment, and patient care.',
        160,
        programma1_id,
        '30 minutes'
    );

    call add_intervento(
        'Ethical Considerations in AI Development',
        'This talk will address the ethical challenges and considerations associated with the development and deployment of artificial intelligence systems, emphasizing the need for responsible AI practices.',
        200,
        programma1_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma1_id, '107 minutes');

    call add_intervento(
        'Advancements in Natural Language Processing',
        'This talk will highlight the recent advancements in natural language processing (NLP) technologies, discussing their applications in areas such as chatbots, language translation, and sentiment analysis.',
        248,
        programma1_id,
        '30 minutes'
    );

    -- Specifica programma sessione 2
    select id_programma from programma where id_sessione = sessione2_id into programma2_id;

    call add_evento('Introduction to Cybersecurity', programma2_id, '107 minutes');

    call add_intervento(
        'Data Privacy in the Digital Age',
        'This talk will explore the challenges of data privacy in the digital age, discussing the importance of protecting personal information and the role of regulations such as GDPR.',
        131,
        programma2_id,
        '30 minutes'
    );

    call add_intervento(
        'Cybersecurity Threats and Countermeasures',
        'This talk will provide an overview of cybersecurity threats faced by organizations and individuals, discussing common attack vectors and best practices for cybersecurity defense.',
        103,
        programma2_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma2_id, '107 minutes');

    call add_intervento(
        'The Future of Cybersecurity',
        'This talk will delve into the future of cybersecurity, discussing emerging technologies and trends that will shape the landscape of cybersecurity in the coming years.',
        1,
        programma2_id,
        '30 minutes'
    );

    -- Specifica programma sessione 3
    select id_programma from programma where id_sessione = sessione3_id into programma3_id;

    call add_evento('Introduction to Blockchain', programma3_id, '107 minutes');

    call add_intervento(
        'Blockchain Applications Beyond Cryptocurrency',
        'This talk will explore the potential applications of blockchain technology beyond cryptocurrency, discussing its impact on industries such as supply chain management, finance, and healthcare.',
        34,
        programma3_id,
        '30 minutes'
    );

    call add_intervento(
        'Internet of Things: Connecting the Physical World',
        'This talk will provide an overview of the Internet of Things (IoT), discussing its role in enabling connectivity and automation across various domains, including smart homes, cities, and industries.',
        242,
        programma3_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma3_id, '107 minutes');

    call add_intervento(
        'Securing Blockchain and IoT Ecosystems',
        'This talk will address the security challenges associated with blockchain and IoT ecosystems, discussing strategies for ensuring data integrity, privacy, and resilience.',
        52,
        programma3_id,
        '30 minutes'
    );
call add_keynote_speaker(734,sessione3_id);

    -- Specifica programma sessione 4
    select id_programma from programma where id_sessione = sessione4_id into programma4_id;

    call add_evento('Future Technology Trends', programma4_id, '107 minutes');

    call add_intervento(
        'Augmented Reality: Enhancing Human Experience',
        'This talk will explore the advancements in augmented reality (AR) technology and its potential to enhance human experiences in fields such as gaming, education, and industrial applications.',
        150,
        programma4_id,
        '30 minutes'
    );

    call add_intervento(
        'The Rise of Quantum Computing',
        'This talk will provide an introduction to quantum computing and its potential to revolutionize various industries, including cryptography, optimization, and drug discovery.',
        116,
        programma4_id,
        '30 minutes'
    );

    call add_intervento(
        'Emerging Technologies for Sustainable Development',
        'This talk will highlight emerging technologies that contribute to sustainable development goals, focusing on areas such as renewable energy, circular economy, and smart cities.',
        152,
        programma4_id,
        '30 minutes'
    );

    -- Specifica programma sessione 5
    select id_programma from programma where id_sessione = sessione5_id into programma5_id;

    call add_evento('Introduction to Tech Startups', programma5_id, '10 minutes');

    call add_intervento(
        'From Idea to Startup: Navigating the Entrepreneurial Journey',
        'This talk will provide insights into the process of transforming ideas into successful tech startups, discussing key challenges, strategies, and resources available to aspiring entrepreneurs.',
        213,
        programma5_id,
        '30 minutes'
    );

    call add_intervento(
        'Venture Capital and Startup Funding',
        'This talk will delve into the world of venture capital and startup funding, discussing the different stages of funding, investment strategies, and the role of investors in supporting startup growth.',
        114,
        programma5_id,
        '30 minutes'
    );

    call add_intervallo('coffee break', programma5_id, '10 minutes');

    call add_intervento(
        'Building a Culture of Innovation in Startups',
        'This talk will explore the importance of fostering a culture of innovation in startups, discussing strategies for promoting creativity, collaboration, and continuous learning within startup teams.',
        15,
        programma5_id,
        '30 minutes'
    );
call add_keynote_speaker(213,sessione5_id);
exception
    when others then
        raise notice 'Errore inserimento conferenza';
        rollback;
end $$;
