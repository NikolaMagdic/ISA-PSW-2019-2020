# Projekat iz predmeta Internet softverske arhitekture i Projektovanje softvera na FTN-u

Web aplikacija koja implementira informacioni sistem  kliničkog centra.

## Korišćene tehnologije

- Server 
  - Java + SpringBoot
  - PostgreSQL baza podataka
 
- Klijent
  - Vue.js 2
  - Axios.js 
  - OpenLayers.js
  
## Pokretanje projekta

### Potrebni alati
 - Maven
 - PostgreSQL 
 - Eclipse, InteliJ ili slično razvojno okruženje
 - Node.js
 
### Server
 - Importovati projekat kao Maven projekat u izabrano razvojno okruženje
 - U PostgreSQL bazi podataka napraviti novu konekciju sa kredencijalima iz application.properties fajla ili izmeniti podatke u fajlu da odgovaraju vašoj konfiguraciji
 - Pokrenuti projekat
 
### Klijent
  - Pozicionirati se u folder **isa_frontend** u okviru projekta koristeći komandnu liniju
  - Izvršiti komandu  `npm install` za instaliranje svih neophodnih dependency-ja
  - Zatim `npm run serve` za pokretanje projekta
  - Aplikacija je dostupna na adresi http://localhost:8080


## Funkcionalnosti
- Zakazivanje pregleda od strane pacijenata
- Unošenje izveštaja o izvršenim pregledima i operacijama i zakazivanje istih za pacijente
- Vođenje evidencije o zaposlenima, registrovanim klinikama, salama za preglede i operacije, pacijentima i njihovim zdravstvenim kartonima