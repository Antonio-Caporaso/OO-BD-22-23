-- Vista delle sedi con il loro indirizzo:

create view SediView as 
select s.nome as Sede, i.via ||', ' || i.civico || ', ' ||  i.cap ||', ' || i.city || ' (' || i.provincia ||'), '|| i.nazione as Indirizzo
from sede s natural join indirizzo i;