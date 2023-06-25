-- Vista delle sedi con il loro indirizzo:

create view SediView as 
select s.nome as Sede, i.via ||', ' || i.civico || ', ' ||  i.cap ||', ' || i.city || ' (' || i.provincia ||'), '|| i.nazione as Indirizzo
from sede s natural join indirizzo i;

-- Vista che mostra il numero di conferenze per ogni sede:
create view conferenze_sede as
select s.nome as Sede, count(id_conferenza) as Numero_Conferenze
from sede s,conferenza c
where s.id_sede = c.id_sede
group by s.nome;

-- Vista che mostra il numero di interventi di ogni speaker:
create view interventi_speaker as
select s.nome || ' ' || s.cognome as Speaker, count(i.id_intervento)
from speaker s, intervento i 
where s.id_speaker = i.id_speaker
group by s.nome,s.cognome;