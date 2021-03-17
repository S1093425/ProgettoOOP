# Progetto esame Programmazione ad Oggetti a.a. 2020/2021

<h1>Componenti gruppo:</h3><br>
Alessandro Rongoni <b>S1092514</b><br>
Gregorio Vecchiola <b>S1093425</b><br>

<h1>Spiegazione progetto:</h1><br>
  Il seguente programma permette di gestire le CALL da PostMan per la visualizzazione degli eventi di ciascuno stato degli U.S.A. Il codice dovrà essere importato sul programma Eclipse e mandato in run come applicazione SpringBoot. Con la chiamata alle statistiche si riceverà in risposta sia le statistiche per stato, sia le statistiche globali, entrambe si potranno filtrare a piacimento secondo dei parametri standard (per stato, per genere, per source e per data). <br>
  
   <h1><b>Premesse:</b><br></h1>
   <ul>
   <li>I PC con poca memoria potrebbero avere problemi a stampare gli stati con molti eventi. Inoltre si consiglia di usare un range temporale breve per il giusto filtraggio degli eventi e per la giusta visualizzazione.</li>
   <li>La API non è in grado di stampare più di 1194 eventi, perciò verranno visualizzati solo i primi 1194 eventi di ogni stato ( page*size<1000, size<=200).</li>
   <li>Per la rotta cerca si usano le sigle degli stati della seguente ricerca, mentre per il filtraggio,si usa il nome.</li>
   </ul>
   
   <h1><b>Elenco delle SIGLE e NOMI degli stati degli U.S.A:</h1></b><br>
   
  <table> 
  <tr><td><b><h3>Sigla</h3></b></td><td><b><h3>Stato</h3></b></td><td><b><h3>Capitale</h3></b></td></tr> 
 <tr><td> AK</td><td>	Alaska</td><td>	Juneau</td></tr>
 <tr><td> AL</td><td>	Alabama	</td><td>Montgomery</td></tr>
 <tr><td> AR</td><td>	Arkansas</td><td>	Little Rock</td></tr>
  <tr><td>AZ</td><td>	Arizona</td><td>	Phoenix</td></tr>
  <tr><td>CA</td><td>	California</td><td>	Sacramento</td></tr>
  <tr><td>CO</td><td>	Colorado</td><td>	Denver</td></tr>
  <tr><td>CT</td><td>	Connecticut</td><td>	Hartford</td></tr>
  <tr><td>DE</td><td>	Delaware</td><td>Dover</td></tr>
  <tr><td>FL</td><td>	Florida</td><td>Tallahassee</td></tr>
  <tr><td>GA</td><td>	Georgia	</td><td>Atlanta</td></tr>
  <tr><td>HI</td><td>	Hawaii</td><td>	Honolulu</td></tr>
  <tr><td>IA</td><td>	Iowa</td><td>	Des Moines</td></tr>
  <tr><td>ID</td><td>	Idaho</td><td>	Boise</td></tr>
  <tr><td>IL</td><td>	Illinois</td><td>	Springfield</td></tr>
  <tr><td>IN</td><td>	Indiana</td><td>	Indianapolis</td></tr>
   <tr><td> KS</td><td>	Kansas</td><td>	Topeka</td></tr>
   <tr><td> KY</td><td>	Kentucky</td><td>	Frankfort</td></tr>
  <tr><td>  LA</td><td>	Louisiana</td><td>	Baton Rouge</td></tr>
  <tr><td>  MA</td><td>	Massachusetts</td><td>	Boston</td></tr>
  <tr><td>  MD</td><td>	Maryland</td><td>	Annapolis</td></tr>
  <tr><td>  ME</td><td>	Maine</td><td>	Augusta  </td></tr>
  <tr><td> MI</td><td>	Michigan</td><td>	Lansing
  <tr><td>  MN</td><td>	Minnesota	</td><td>Saint Paul</td></tr>
  <tr><td>  MO</td><td>	Missouri	</td><td>Jefferson City</td></tr>
  <tr><td>  MS</td><td>	Mississippi</td><td>	Jackson</td></tr>
  <tr><td>  MT</td><td>	Montana</td><td>	Helena</td></tr>
  <tr><td>  NC</td><td>	North Carolina</td><td>	Raleigh</td></tr>
  <tr><td>  ND</td><td>	North Dakota</td><td>	Bismarck</td></tr>
  <tr><td>  NE</td><td>	Nebraska</td><td>	Lincoln</td></tr>
  <tr><td>  NH</td><td>	New Hampshire</td><td>	Concord</td></tr>
  <tr><td>  NJ</td><td>	New Jersey</td><td>	Trenton</td></tr>
  <tr><td>  NM</td><td>	New Mexico</td><td>	Santa Fe</td></tr>
  <tr><td>  NV</td><td>	Nevada</td><td>	Carson City</td></tr>
  <tr><td>  NY</td><td>	New York</td><td>	Albany</td></tr>
  <tr><td>  OH</td><td>	Ohio</td><td>	Columbus</td></tr>
  <tr><td>  OK</td><td>	Oklahoma</td><td>	Oklahoma City</td></tr>
  <tr><td>  OR</td><td>	Oregon</td><td>	Salem</td></tr>
  <tr><td>  PA</td><td>	Pennsylvania</td><td>	Harrisburg</td></tr>
  <tr><td>  RI	</td><td>Rhode Island	</td><td>Providence</td></tr>
  <tr><td>  SC</td><td>	South Carolina</td><td>	Columbia</td></tr>
  <tr><td>  SD</td><td>	South Dakota</td><td>	Pierre</td></tr>
  <tr><td>  TN</td><td>	Tennessee	</td><td>Nashville</td></tr>
  <tr><td>  TX</td><td>	Texas</td><td>	Austin</td></tr>
   <tr><td> UT</td><td>	Utah	</td><td>Salt Lake City</td></tr>
   <tr><td> VA</td><td>	Virginia	</td><td>Richmond</td></tr>
   <tr><td> VT</td><td>	Vermont	</td><td>Montpelier</td></tr>
  <tr><td>  WA</td><td>	Washington	</td><td>Olympia</td></tr>
  <tr><td>  WI</td><td>	Wisconsin</td><td>	Madison</td></tr>
  <tr><td>  WV</td><td>	West Virginia</td><td>	Charleston</td></tr>
  <tr><td>  WY	</td><td>Wyoming	</td><td>Cheyenne</td></tr>
  </table>
  
  <h1><b>Elenco delle CALL:</h1></b><br>
   <h2>Cerca evento(Post)</h2><br>
   <i>localhost:8080/Cerca</i><br>
   <h3>Body da passare in post:</h3><br>
  
  ```json
  
  {
    "stato": "AK"
  }
  
  ```
  
  Nel JsonObject 'stato' inserire la sigla dello stato del quale si vorrà richiedere la lista degli eventi.<br>
  
  Ritorna un ArrayList contenente gli eventi dello stato richiesto: <br>
  
  ```json
  
  [
    {
        "Name": "Jo Koy - Just Kidding World Tour",
        "Genere": "Arts & Theatre",
        "DataInizio": "Aug 13, 2022, 12:00:00 AM",
        "Stato": "Alaska",
        "SourceName": [
              "Ticketmaster"
         ]
    },
    {
        "Name": "Rodney Carrington",
        "Genere": "Arts & Theatre",
        "DataInizio": "Aug 8, 2021, 12:00:00 AM",
        "Stato": "Alaska",
        "SourceName": [
              "Ticketmaster"
         ]
    }
    
   ]
  
  ```
  
  
  <h2>Statistiche(Post):</h2><br>
  <i>localhost:8080/Stats</i><br>
  <h3>Body da passare in post:</h3><br>
  
  ```json
  
  {
        "stati":{
            "attivo":true,
            "filtro":"Alaska","Alabama"
        },
        "generi":{
            "attivo":true,
            "filtro":"Music,Sport"
        },
        "source":{
            "attivo":false,
            "filtro":"Ticketmaster"
        },
        "periodo":{
            "attivo":true,
            "filtro":"2021-04-12,2021-05-23"
        }
  }

```
Ogni filtro ha 2 Attributi : 'Attivo' e 'Filtro'.<br>
'Attivo' è un Boolean: se è 'true' attiva il seguente filtro ,mentre se è 'false' il filtro viene disattivato.<br>
Il paramentro 'filtro', invece, può variare a seconda del filtro che si vuole applicare:<br>
<h3>Filtro Stato</h3><br>
Si può filtrare per uno o più stati, per farlo basta dividerli con una ",". Esso flitrerà gli eventi in base allo/agli stato/i scritti.<br>
Ad esempio: "New York,Alaska"<br>
  
<h3>Filtro Genere</h3><br>
Si può filtrare per uno o più generi, per farlo basta dividere i generi con una ",". Esso filtrerà gli eventi in base al/ai genere/i selezionati. <br>
Possibili opzioni:<br>
  -Music : Prende gli eventi musicali (concerti, musical, ecc.)<br>
  -Arts & Theatre : Prende gli eventi artistici e teatrali<br>
  -Sport : Prende eventi gli sportivi<br>
  -Miscellaneous : Prende gli eventi di tipo misto<br>
  Ad esempio: "Music, Arts & Theatre" <br>

<h3>Filtro Source</h3><br>
 Si può filtrare per uno o più source, per farlo basta dividerle con una ",". Esso filtrerà gli eventi in base al sito della vendita dei tickets. <br>
 Possibili opzioni:<br>
  -Ticketmaster : Prende gli eventi venduti su TicketMaster<br>
  -Universe : Prende gli eventi venduti su Universe<br>
  -Frontgate : Prende gli eventi venduti su FrontGate Tickets Resale<br>
  -TMR : Prende gli eventi venduti su Ticket Master Resale<br>
  Ad esempio: "ticketmaster, tmr" <br>
  
  <h3>Filtro Data Inizio</h3><br>
 Si possono filtrare gli eventi per data. Ci sono diversi periodi predefiniti, inoltre si può anche impostare una data personalizzata.<br>
 Possibili opzioni:<br>
  - "Giornalieri": filtra gli eventi del giorno stesso. <br>
  - "Settimanali": filtra gli eventi dei prossimi 7 giorni. <br>
  - "Mensili": filtra gli eventi dei prossimi 31 giorni. <br>
  - "Semestrale": filtra gli eventi dei prossimi 186 giorni. <br>
  - "Annuali": filtra gli eventi dei prossimi 365 giorni. <br>
  - Personalizzata: per impostare una data personalizzata bisogna inserire la data di inizio e quella di fine separati da una virgola nel seguente formato, "yyyy-mm-dd,yyyy-mm-dd"

<h2>Statistiche per Stato:</h2><br>

```json
  
    {
        "Nome": 6541874,
        "Genere": "Fermo",
        "Data Inizio": "nubi sparse",
        "Stato": "Alaska",
        "Source": 1024.0,
    }
  
  ```
  Le statistiche per stato vengono così visualizzate. Esse mostrano:
  - Il numero di eventi totali dello stato;<br>
  - Il numero di eventi divisi per genere;<br>
  - Il numero di eventi divisi per source;<br>
<h2>Statistiche Globali:</h2><br>

```json
   
    {
        "Nome": 6541874,
        "Genere": "Fermo",
        "Data Inizio": "nubi sparse",
        "Stato": "Alaska",
        "Source": 1024.0,
    }
    
  ```
Le statistiche globali vengono così visualizzate. Esse mostrano:
  - Lo stato con il numero di eventi massimo/minimo di eventi in generale;<br>
  - Lo stato con il numero di eventi massimo/minimo di eventi divisi per genere;<br>
  - Lo stato con il numero di eventi massimo/minimo di eventi divisi per source;<br>

<h2>Lista Stati(Get)</h2>
<i>localhost:8080/StatiCerca</i><br>
Questa rotta stampa tutti gli stati degli Stati Uniti con le relative sigle.<br>

<h2>Stati Preferiti(Get)</h2>
<i><b>localhost:8080/StatiStats?Activity=</b>Aggiunta<b>&State=</b>Florida</i><br>
Questa rotta permette di gestire la lista degli stati standard già selezionati da noi. La lista può essere modificata in qualisasi momento dall'utente grazie alle varie azioni.<br>
<h3>Parametri:</h3><br>
Il parametro <b>'State'</b> va riempito con il nome dello stato. Ad esempio: "Florida".<br><br>
Il parametro <b>'Activity'</b> può assumere i seguenti valori: <br>
-Aggiunta = Aggiunge lo <b>stato</b> alla lista dei favoriti;<br>
-Rimozione = Rimuove <b>stato</b> dalla lista dei favoriti;<br>
-Stampa = Restituisce un JsonObject contenente la lista degli stati favoriti. In questo caso il parametro 'State' deve essere presente, ma può rimanere vuoto;<br>

