CREATE DATABASE CS_426_Database;


/*
CREATE TABLE GEOCODING(
    latitude NUMERIC(3,5),
    longitude NUMERIC(3,5),
    gcode VARCHAR(255) PRIMARY KEY
);*/

CREATE TABLE BUILDING( 
    gps_coords geography(POINT),
    building_code VARCHAR(10) UNIQUE,
    building_name VARCHAR(255),
    building_num VARCHAR(10),
    department VARCHAR(255),
    operating_hours VARCHAR(255), --change to varchar Format M-F: Start - End, Sa: Start End, Su: Start End
    PRIMARY KEY(building_code)
);


CREATE TABLE LIBRARY(
    building_code VARCHAR(10) references Building(building_code),
    location VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE HANGOUTSPOTS( --add outlet bool 
    building_code VARCHAR(10) references Building(building_code),
    location VARCHAR(255),
    description VARCHAR(255),
    outlet boolean
);

CREATE TABLE RESTROOM(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255),
    accessibility boolean
);

CREATE TABLE VENDINGMAC(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255),
    type VARCHAR (20)
);

CREATE TABLE RESOURCE(
    building_code VARCHAR(10) references BUILDING(building_code),
    description VARCHAR(255),
    location VARCHAR(255),
    operating_hours VARCHAR(50),
    weblink VARCHAR(255) 
);

CREATE TABLE STORE(
    building_code VARCHAR(10) references BUILDING(building_code),
    description VARCHAR(255),
    location VARCHAR(255),
    operating_hours VARCHAR(50)
);

CREATE TABLE DINING_OPTION(
    building_code VARCHAR(10) references BUILDING(building_code),
    description VARCHAR(255),
    location VARCHAR(255),
    operating_hours VARCHAR(255),
    weblink VARCHAR(255),
    menulinkk VARCHAR(255)
);

CREATE TABLE LAB(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255),
    type VARCHAR (20),
    department VARCHAR(255)
);

CREATE TABLE ELEVATOR(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255)
);

CREATE TABLE STAIRCASE(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255)
);

CREATE TABLE SPECIAL_FEATURE(
    building_code VARCHAR(10) references BUILDING(building_code),
    location VARCHAR(255),
    description VARCHAR(255)
);


--labs, elevators, staircases
--special features

--39.540041518463504, -119.81186738652406 <- Coordinates of the WNPUB
INSERT INTO building VALUES ('WPEB','William N. Pennington Engineering Building','53', 'ENGINEERING', 'M-Th: 7AM-12AM , F: 7AM - 5:30PM , Sa:12PM-5PM, Su:12PM-12AM');
--Values Missing = NULL

--Add example to database
--restroom
--restroom firstfloor wheel chair accessible?
--Second floor has one near the outdoor entrance
--Third floor has one infront of the staircase/ elevator near a window
--Second floor engineering 300 has standalone accessible restrooms
INSERT INTO RESTROOM VALUES ('WPEB', 'Floor 1: Near Computer Lab #100', FALSE);
INSERT INTO RESTROOM VALUES ('WPEB', 'Floor 2: Near Entrance South, CSE Room 200', FALSE);
INSERT INTO RESTROOM VALUES ('WPEB', 'Floor 2: Opposite Entrance North, Engineering Education Lab',TRUE);
INSERT INTO RESTROOM VALUES ('WPEB', 'Floor 3: Infront of South Corridor Elevator and Staircase', FALSE);

--Dining
--bowlife on the first floor
INSERT INTO DINING_OPTION VALUES ('WPEB', 'Bowl Life', 'First Floor: South Cooridor past the Computer Lab #100', 'M-F: 11AM-3AM');

--resources
--resource Vihn and Sarah? have offices 
--Dean of engineering
--clean lab first floor
--breakout rooms
INSERT INTO RESOURCE VALUES ('WPEB', 'Professor Vihn Office','Room 205. Located on second floor south corridor.', '', '' );
INSERT INTO RESOURCE VALUES ('WPEB', 'Professor Sarah Office','Room 315. Located on third floor south corridor.', '', '' );
INSERT INTO RESOURCE VALUES ('WPEB', 'College of Engineering Dean Suite','Rooms 441-461 Located on fourth floor','','');
INSERT INTO RESOURCE VALUES ('WPEB', 'Collaboration Rooms','Rooms 204,214,314,320,326,340-342, 410, 418, 424, 438','','');

--Hangout spots
--Near bowlife
--In front of the computer labs seating, both floors
--second floor in front of the computer lab
INSERT INTO HANGOUTSPOTS VALUES ('WPEB', 'First Floor & Second Floor. Infront of 100,200', 'Sofas and Tables are offered for students to use.', TRUE);

--LAB
INSERT INTO LAB VALUES ('WPEB', 'First Floor Computer Lab First Floor Room 100 and 101','Computer','Computer Science');
INSERT INTO LAB VALUES ('WPEB', 'First Floor Clean Room. Past BOWL LIFE. Room 104','Clean Room','Variable');

--Elevator
INSERT INTO ELEVATOR VALUES ('WPEB','South Corridor');
INSERT INTO ELEVATOR VALUES ('WPEB','North Corridor');

--Staircase
INSERT INTO STAIRCASE VALUES ('WPEB', 'Near Elevators');
INSERT INTO STAIRCASE VALUES ('WPEB', 'Far South of the building');


--Queries Example
--See all UNR buildigns
SELECT * FROM Building;
--See the hours of a building
SELECT operating_hours FROM BUILDING WHERE building_code = 'WPEB';
--Find all resources of a building 
SELECT * FROM RESOURCE WHERE building_code = 'WPEB';

--Find Restrooms
SELECT * FROM RESTROOM WHERE building_code = 'WPEB';

--Find Accessible Restroom in a particular building
SELECT * FROM RESTROOM WHERE building_code='WPEB' and accessibility = TRUE;

--Find the locations of all elevators & stairs in a building
SELECT * FROM ELEVATOR,STAIRCASE  WHERE elevator.building_code='WPEB' or staircase.building_code = 'WPEB';

--Find the labs of a bulding
SELECT * FROM LAB WHERE building_code = 'WPEB';
--Find all computer labs
SELECT * FROM LAB WHERE type ='Computer'; 

--Find dining options
SELECT * FROM DINING_OPTION;

--Find the operation hours for a particular DINING_OPTION
SELECT operating_hours FROM DINING_OPTION WHERE description = 'Bowl Life';


--CREATE Extension call for enable POSTGIS for lat and long geography datatypes
CREATE EXTENSION postgis;

--Insert Calls for Building Database
INSERT INTO building values ('POINT(-119.81340531019366  39.53979774918553  )','SEM', 'Scrugham Engineering and Mines','056','Engineering','M-F: 6:30AM - 12AM Su: 12PM-12AM');
INSERT INTO building values ('POINT(-119.80433072832727  39.49184409511278  )','M745', 'University Health Building', '935','Medicine','TBD');
INSERT INTO building values ('POINT( -119.80714192765134 39.537933963204324 )','AE', 'Agricultural Education Health Building', '173','Agriculture','TBD');
INSERT INTO building values ('POINT(-119.81470412193153  39.54007058321954  )','AB', 'Ansari Business Building', '063','Business','M-F: 7:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.81691800338545 39.549433835782935 )','AHS', 'Anderson Health Science', '128','Medicine','TBD');
INSERT INTO building values ('POINT(-119.81479058989372  39.54468807462003  )','ARF', 'Applied Research Facility', '090','Science','M-F: 8:00AM - 8:00PM Sa:10:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81801802058668 39.539360806965625 )','AHS', 'Argenta Hall', '008','Residence','N/A');
INSERT INTO building values ('POINT(-119.81806124756608  39.53872881130598  )','ARTM', 'Artemisia Building', '012','Faculty','N/A');
INSERT INTO building values ('POINT( -119.81828858804131 39.549105445421496 )','PD', 'BCN Purchasing', '137','Faculty','N/A')  ; 
INSERT INTO building values ('POINT(-119.81450046105799  39.53989658633081  )','FRX', 'Building 058', '058','Faculty','N/A')  ; 
INSERT INTO building values ('POINT(-119.81781584571382  39.53980491701399  )','CH', 'Canada Hall', '006','Residence','N/A') ;
INSERT INTO building values ('POINT(-119.81934805245452  39.54901031756331  )','CS', 'Central Services', '136','Faculty','N/A') ;
INSERT INTO building values ('POINT( -119.81510913589473 39.550119417012034 )','CMM', 'Center for Molecular Medicine', '160','Medicine','M-F: 8:00AM - 5:00PM') ;
INSERT INTO building values ('POINT( -119.8143333037202  39.540771252129545 )','CB', 'Chemistry Building', '071','Science','M-F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.8214527652187  39.550542772148745 )','CCF', 'Child Care Facility', '141','Services','N/A');
--INSERT INTO building values ('POINT( -119.8109346187296  39.54647390468932 )','N/A', 'Christina M. Hixson Softball Park', 'N/A','Sports','N/A')
INSERT INTO building values ('POINT( -119.81689053222183 39.541387361525885 )','CFA', 'Church Fine Arts', '078','Arts','M-F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81489581687788 39.537826317244416 )','CA', 'Clark Administration', '035','Administration','N/A');
INSERT INTO building values ('POINT( -119.81224908804133 39.55131774080492  )','SAB', 'Claude Howard System Administration (NSHE)', '154','Administration','N/A');
INSERT INTO building values ('POINT( -119.81933604571356 39.547161411311954 )','CC', 'Computer Center', '133','Faculty','N/A');
INSERT INTO building values ('POINT( -119.93850028441018 39.24510897462628  )','LTD', 'David Hall', '502','Residence','N/A');
INSERT INTO building values ('POINT( -119.81238200338602 39.53915804016227  )','DMSC', 'Davidson Math and Science Center', '045','Various','M-F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81633160153339 39.54791329379318  )','PDO', 'Department of Pediatrics', '117','Medicine','N/A');
INSERT INTO building values ('POINT( -119.81812906105802 39.53894473275393  )','DCC', 'Dining Conference Center', '009','Dining','Variable');
INSERT INTO building values ('POINT( -119.81257064756609 39.540647310686346 )','EEL', 'Earthquake Engineering Laboratory', '066','Engineering','N/A');
INSERT INTO building values ('POINT( -119.81409834571366 39.5421630071627   )','EJCH', 'Edmund J. Cain Hall', '081','Education','M-F: 7:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.81513256139232 39.54061282624209  )','EJCH', 'Effie Mona Mack', '072','Education','M-F: 7:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.81941283036929 39.548034312301894 )','ERF', 'Environmental Research Facility', '130','Science','N/A');
INSERT INTO building values ('POINT( -119.81745600338566 39.543854482953776 )','WFC', 'E. L. Wiegand Fitness Center', '086','Fitness','M-F: 6:00AM - 12:00AM, Sa: 8:00AM - 10:00PM, Su: 10:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.80748154571378 39.54119421444668  )','EC', 'Equestrian Center', '170','Equine','N/A');
INSERT INTO building values ('POINT( -119.81854326989018 39.55011702047574  )','FRW', 'Facilities Maintenance Services Receiving Warehouse', '144','Facuilty','N/A');
INSERT INTO building values ('POINT( -119.81315381687766 39.5417404976077   )','FS', 'Facilities Services', '076','Facilty','N/A');
INSERT INTO building values ('POINT( -119.81660616105756 39.54850857379747  )','EBB', 'Edna S. Brigham Clinical Education Building', '123','Medicine','M-F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81606167454979 39.542247759730685 )','FSSB', 'Fitzgerald Student Services Building', '082','Administration','M-F: 7:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81208134448457 39.53772042828378  )','FA', 'Fleischmann Agriculture', '031','Agriculture','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81916637454964 39.54600129823881  )','FP', 'Fleischmann Planetarium', '105','Astronomy','Various');
INSERT INTO building values ('POINT( -119.81516851687792 39.53845424544374  )','FH', 'Frandsen Humanities', '042','Liberal Arts','7:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.81516851687792 39.53845424544374  )','GPC', 'Gateway Parking Complex', '027','Parking','N/A');
INSERT INTO building values ('POINT( -119.81685792866423 39.5397255652871   )','GBH', 'Great Basin Hall', '068','Residence','N/A');
INSERT INTO building values ('POINT( -119.81331782117087 39.540576535244604 )','HREL', 'Harry Reid Engineering Laboratory', '065','Engineering','N/A');
INSERT INTO building values ('POINT( -119.93560759612333 39.24635978565449  )','LTH', 'Holman Arts & Media Center', '507','Arts','N/A') ;--Tahoe Location
INSERT INTO building values ('POINT( -119.81546294571338 39.54930614500257  )','HMS', 'Howard Medical Sciences', '125','Medicine','N/A');
INSERT INTO building values ('POINT( -119.8080915470262  39.522164025140086 )','IC', 'Innevation Center', 'N/A','Resources','8:00AM-5:00PM');
INSERT INTO building values ('POINT( -119.8160496168775  39.544714801229915 )','JCSU', 'Joe Crowley Student Union', '087','Resources','7:00AM - 10:00PM');
--INSERT INTO building values ('POINT( -119.81427534571344 39.54804319349113  )','N/A', 'John Sala Intramural Practice Fields', 'N/A','Sports','N/A')
INSERT INTO building values ('POINT( -119.81463206105809 39.53830323455984  )','JC', 'Jones Center', '043','Philosophy','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81655530380719 39.53863054749339  )','JTB', 'Jot Travis Building', '048','Various','7:00AM - 3:00PM');
INSERT INTO building values ('POINT( -119.81659953585844 39.53800708874398  )','JH', 'Juniper Hall', '041','Residence','N/A');
INSERT INTO building values ('POINT( -119.80702042306237 39.53866406410878  )','KRC', 'Knudtsen Resource Center', '171','Various','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81831999352988 39.54508262817868  )','LEC', 'Lawlor Events Center', '104','Sports/Events','N/A');
INSERT INTO building values ('POINT( -119.81727249428408 39.5452995536064   )','LEC', 'Legacy Hall', '103','Sports','N/A');
INSERT INTO building values ('POINT( -119.81405833921025 39.5411347920165   )','LP', 'Leifson Physics', '074','Physics','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81243122791577 39.53830962250762  )','LS', 'Life Science', '032','Science','N/A'); -- May be a duplicate of the FLeishmann Life Sciences building
INSERT INTO building values ('POINT( -119.81634831431705 39.53939404616184  )','LH', 'Lincoln Hall', '060','Faculty','N/A');
INSERT INTO building values ('POINT( -119.81508967547738 39.54583244685586  )','LRC', 'Lombardi Recreation Center', '095','Sports','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81441614448298 39.53925784281859  )','MM', 'Mackay Mines', '057','Library','9:00AM - 4:00PM');
INSERT INTO building values ('POINT( -119.81371007393031 39.53817235446425  )','MS', 'Mackay Science', '036','Science','7:00AM - 10:00PM');
INSERT INTO building values ('POINT( -119.81753560338552 39.54691546202966  )','S', 'Mackay Stadium', '109','Sports','N/A');
INSERT INTO building values ('POINT( -119.81622530337829 39.54967603750378  )','MHS', 'Manville Health Science', '126','Medicine','N/A');
INSERT INTO building values ('POINT( -119.81622934448338 39.53730313732111  )','MAH', 'Manzanita Hall', '040','Residence','N/A');
INSERT INTO building values ('POINT( -119.8157398033857  39.54344534908103  )','MIKC', 'Mathewson-IGT Knowledge Center', '085','Resources','8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81390335945643 39.53761787555032  )','MH', 'Morrill Hall', '034','Residence','N/A')
INSERT INTO building values ('POINT( -119.81811830337998 39.54982313524179  )','MP', 'Motor Pool', '145','Facilty','N/A');
INSERT INTO building values ('POINT( -119.81774227454976 39.54034528703201  )','NLLC', 'Nevada Living Learning Community', '004','Residence','N/A');
INSERT INTO building values ('POINT( -119.81390522117059 39.54293239807674  )','NJC', 'National Judicial College Building', '084','Law','M-F: 7:30AM - 4:30PM');
INSERT INTO building values ('POINT( -119.81632800337917 39.54824683515152  )','NJR', 'Nell J. Redfield (Student Health Center)', '122','Medicine','M-F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.76512370927549 39.39166142141875  )','RC-A', 'Nell J. Redfield Building A, Redfield Campus', 'N/A','N/A','N/A'); -- Located off campus to the south of reno 
INSERT INTO building values ('POINT( -119.8166275522579  39.54974493258539  )','NBS', 'Nellor Biomedical Sciences', '127','Medicine','N/A');
INSERT INTO building values ('POINT( -119.81800673426197 39.548940089174295 )','NSHL', 'Nevada State Health Laboratory', '138','Medicine','M-Sa: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81788644796711 39.5391506124423   )','NH', 'Nye Hall', '138','Residence','N/A');
INSERT INTO building values ('POINT( -119.81292484448404 39.53806740306788  )','OB', 'Orvis Building', '033','Classrooms','M - F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81303752669206 39.539444048256826 )','PE', 'Palmer Engineering', '050','Engineering','M - F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.8199783622738  39.546618403323514 )','PS', 'Parking Services', '106','Resources','M - F: 8:00AM - 4:30PM');
INSERT INTO building values ('POINT( -119.93935969858425 39.24430949386029  )','LTPA', 'Patterson Hall', '503','Dining','M - F: 8:00AM - 5:00PM'); -- Located in Tahoe
INSERT INTO building values ('POINT( -119.81369792640373 39.53907873385332  )','LME', 'Paul Laxalt Mineral Engineering', '046','Engineering','M - F: 8:00AM - 5:00PM');
INSERT INTO building values ('POINT( -119.81323500338166 39.53870297159459  )','LMR', 'Paul Laxalt Mineral Research', '044','Engineering','N/A');
INSERT INTO building values ('POINT( -119.81799384266482 39.53803450073156  )','PH', 'Peavine Hall', '019','Residence','N/A');
INSERT INTO building values ('POINT( -119.81188186850397 39.53986785587337  )','WPEB', 'William N. Pennington Engineering Building', '53','Engineering','M-F: 6:30AM-8:00PM');
INSERT INTO building values ('POINT( -119.81370400890174 39.549154335205756 )','PHS', 'William N. Pennington Health Sciences Building', '165','Nursing','M-F: 8:00AM-5:00PM');
INSERT INTO building values ('POINT( -119.81587800337755 39.54859900801333  )','PMB', 'William N. Pennington Medical Education', '121','Administration','M-F: 8:00AM-5:00PM Sa-Su: 1:00-5:00PM') ;
INSERT INTO building values ('POINT( -119.81524766227444 39.53951022217315  )','PSAC', 'William N. Pennington Student Achievement Center', '067','Resources','M-T: 7:00AM - 12:00PM F: 7:00AM - 5:30PM: Sa:12:00AM-5:00PM Su:12:00PM - 12:00AM') ;
INSERT INTO building values ('POINT( -119.81686333308174 39.54558271262498  )','MWPB', 'Marguerite W. Petersen Athletic Academic Center', '100','Resources','M-F: 8:00AM - 5:00PM') ;
INSERT INTO building values ('POINT( -119.8141445036932  39.546235708503474 )','PV', 'Ponderosa Village', '097','Residence','N/A') ;
INSERT INTO building values ('POINT( -119.93912500340029 39.24303719876431  )','LTPL', 'Prim Library', '505','Library','N/A'); -- Located in Tahoe
INSERT INTO building values ('POINT( -119.93888532951102 39.24481460416749  )','LTPS', 'Prim-Schultz Hall', '504','Residence','N/A'); --Located in Tahoe
--INSERT INTO building values ('POINT( -119.81406786227453 39.53809777716565 )','TQ', 'The Quad', 'N/A','N/A','N/A') --Prob best to exclude
INSERT INTO building values ('POINT( -119.81509042010947 39.5362181388223   )','REO', 'Real Estate Office', '022','Facilty','N/A') ;
INSERT INTO building values ('POINT( -119.80732907455003 39.538311734560345 )','RRC', 'Renewable Resource Center', '172','Facilty','N/A') ;
INSERT INTO building values ('POINT( -119.82051420338185 39.548061835136025 )','PBS', 'PBS Reno', '135','Broadcasting','N/A') ;
INSERT INTO building values ('POINT( -119.81523559663584 39.5414638091013   )','RSK', 'Reynolds School of Journalism', '077','Journalism','M-F: 8:00AM - 5:00PM') ;
INSERT INTO building values ('POINT( -119.8163889800642  39.54580548379871  )','CFH', 'Robert Cashell Fieldhouse', '102','Residence','N/A') ;
INSERT INTO building values ('POINT( -119.81465024117061 39.53860578063892  )','RH', 'Ross Hall', '047','Administration','N/A') ;
INSERT INTO building values ('POINT( -119.81288894487862 39.53770821100202  )','SFB', 'Sarah H. Fleischmann Building', '030','Health Sciences','N/A') ;
INSERT INTO building values ('POINT( -119.81638964448285 39.548869712606525 )','SMS', 'Savitt Medical Science', '124','Medicine','N/A') ;
INSERT INTO building values ('POINT( -119.8145605669878 39.5408769376099    )','SLH', 'Schulich Lecture Hall', '073','Classrooms','7:00AM - 5:00PM' ) ;
INSERT INTO building values ('POINT( -119.81336873896389 39.53973466719673  )','SEM', 'Scrugham Engineering and Mines', '056','Engineering','M-Th: 6:30AM - 12:00AM F:6:30AM - 10:00PM Su:12:00 PM - 12:00AM' );
INSERT INTO building values ('POINT( -119.81704070338104 39.53731653449576  )','SH', 'Sierra Hall', '021','Residence','N/A' );
INSERT INTO building values ('POINT( -119.81883972681433 39.540145865242394 )','SPC', 'Sierra Street Parking Complex', '005','Parking','N/A' );
INSERT INTO building values ('POINT( -119.8160090966358  39.546220244725156 )','SMC', 'Sports Medicine Complex', '101','Parking','M-F: 8:00AM - 5:00PM' ); 
INSERT INTO building values ('POINT( -119.93964527456427 39.24262905387518  )','LTC', 'Tahoe Center for Environmental Sciences', '506','Science','N/A' ); --Located in Tahoe
INSERT INTO building values ('POINT( -119.81525953620587 39.539022671903    )','TB', 'Thompson Building', '049','Liberal Arts','M-F: 8:00AM - 5:00PM' );
INSERT INTO building values ('POINT( -119.81569775948446 39.54127371662348  )','UFA', 'University Foundation Arts', '079','Arts','M-F: 8:00AM - 5:00PM' );
INSERT INTO building values ('POINT( -119.81397160338153 39.54013207159345  )','UP', 'Utility Plant', '064','Facilty','N/A' );
INSERT INTO building values ('POINT( -119.80615856162557 39.53776836351721  )','VRGC', 'Valley Road Greenhouse Complex', '175','Argiculture','N/A' );
INSERT INTO building values ('POINT( -119.81658738558696 39.5402145085141   )','VSG', 'Virginia Street Gym', '062','Fitness','N/A' );
INSERT INTO building values ('POINT( -119.81658738558696 39.5402145085141   )','SL', 'Visitors Locker Room', '110','Fitness','N/A' );
INSERT INTO building values ('POINT( -119.81899064448312 39.54676030332822  )','WSPC', 'West Stadium Parking Complex', '107','Parking','N/A' );
INSERT INTO building values ('POINT( -119.81711134174756 39.54301146245057  )','BWPC', 'Brian J. Whalen Parking Complex', '083','Parking','N/A' );
INSERT INTO building values ('POINT( -119.81509340338572 39.54203468203159  )','WRB', 'William J. Raggio', '080','Education','M-F: 8:00AM - 5:00PM' );
INSERT INTO building values ('POINT( -119.81276665232328 39.548421152290835  )','WPP', 'William Peccole Park', '114','Sports','N/A' );

--Building Details
    --Argenta Hall Downunder options
        --Building Code 'AHS'
        INSERT INTO dining_option VALUES ('AHS','Pack Place','The Downunder in Argenta Hall','Variable','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('AHS','Student Choice by CaiE Foods','The Downunder in Argenta Hall','M-Th: 11:00AM-11:00PM F:11:00AM-6:00PM Su: 7:00PM-11:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('AHS','Market','The Downunder in Argenta Hall','M - Th: 11:00AM-11:00PM F:11:00AM-6:00PM Su: 7:00PM-11:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('AHS','The Halal Shack','The Downunder in Argenta Hall','M - Th: 11:00AM-11:00PM F: 11:00AM-6:00PM Su: 7:00PM-11:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    --The Overlook
        --Building Code 'JTB'
        INSERT INTO dining_option VALUES ('JTB','BRKFST & Co.','The Overlook in the Jot Travis','M-F: 9:00AM-3:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JTB','Overlook Grab & Go','The Overlook in the Jot Travis','M-Th: 7:00AM-10:00PM, F: 7:00AM-3:00PM ','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JTB','Peet''s Coffee & Tea','The Overlook in the Jot Travis','M-Th: 7:00AM-10:00PM, F: 7:00AM-3:00PM ','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JTB','Chavos','The Overlook in the Jot Travis','M-Th: 11:00AM-5:00PM, F: 11:00AM-3:00PM ','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JTB','Paper Lantern','The Overlook in the Jot Travis','M-Th: 11:00AM-5:00PM, F: 11:00AM-3:00PM ','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JTB','Wild Pie','The Overlook in the Jot Travis','M-F: 11:00AM-3:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    -- Patterson Hall Located In Tahoe
        --Building Code 'LTPA'
        INSERT INTO dining_option VALUES ('LTPA','Social House @ Lake Tahoe','Patterson Hall','Variable','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    --Davidson Math and Science Center
        --Building Code 'DMSC'
        INSERT INTO dining_option VALUES ('DMSC','Sierra Street Subs','Davidson Math and Science Center 1st Floor South East Corner','M-Th: 7:30AM-6:00PM, F:7:30AM-3:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    --Pennington Engineering Building
        --Building Code 'WPEB'
        INSERT INTO dining_option VALUES ('WPEB','Streaming by CaiE Foods','Pennington Engineering Building 1st Floor South Cooridor','M-Th: 11:00AM-5:00PM F:11:00AM-3:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    --Pennington Student Achievement Center
        --Building Code 'PSAC'
        INSERT INTO dining_option VALUES ('PSAC','Create Chop''d & Wrap''d','Pennington Student Achievement Center 2nd floor near stairs.','M-Th: 11:00AM-5:00PM F:11:00AM-4:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
    --Mathewson-IGT Knowledge Center
        --Building Code 'MIKC'
        INSERT INTO dining_option VALUES ('MIKC','The Lounge','Located in the Mathewson-IGT Knowledge Center','M-F: 11:00AM-2:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('MIKC','Bytes - Grab & Go','Near the North Entrance','M-Th: 7:30AM-12:00AM, F: 7:30AM-7:00AM, Sa: 12:00PM-12:00AM, Su: 12:00PM-7:00PM ','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO library Values ('MIKC', 'Floors 2 to 5 of the Mathewson IGT Knowledge Center', 'This library is known as the knowledge center and features a robust collection of books, servicies and assistance for all students.');
    --Joe Crowley Student Center
        --Building Code 'JCSU'
        INSERT INTO dining_option VALUES ('JCSU','The Habit Burger','Joe Crowley Student Union building 2nd Floor','M-Th: 11:00AM-7:00PM F:11:00AM-5:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');
        INSERT INTO dining_option VALUES ('JCSU','Starbucks','Joe Crowley Student Union building 1st Floor','M-F: 7:00AM-9:00PM Sa-Su:10:00AM-7:00PM','https://www.starbucks.com/store-locator/store/1022313/virginia-15th-unr-1664-n-virginia-street-192-c-reno-nv-895030705-us', 'www.starbucks.com');
        INSERT INTO dining_option VALUES ('JCSU','Panda Express','Joe Crowley Student Union building 2nd Floor','M-F: 11:00AM-4:00PM','www.pandaexpress.com', 'https://www.pandaexpress.com/location/university-nevada-reno-px/menu');
        INSERT INTO dining_option VALUES ('JCSU','Del Lobo Mexican Grill','Joe Crowley Student Union building 2nd Floor','M-Th: 10:00AM-5:00PM F:11:00AM-2:00PM','N/A', 'https://www.zmenu.com/del-lobo-mexican-grille-reno-online-menu/');
    --Fitzgerald Student Services Center
        --Building Code 'FSSB'
        INSERT INTO dining_option VALUES ('FSSB','Panera Bread','Located on the First Floor of the Fitzgerald Student Services Center.','M-F: 7:30AM-5:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://www.panerabread.com/en-us/home.html');
    -- Center for Molecular Medicine
        --Building Code 'CMM' 
        --May not be in operation
        INSERT INTO dining_option VALUES ('CMM','Pathways - Grab & Go','Center for Molecular Medicines','N/A','https://dineoncampus.com/unr/whats-on-the-menu', 'https://www.panerabread.com/en-us/home.html');
    -- Ansari Business Building
        --Building Code 'AB'
        INSERT INTO dining_option VALUES ('AB','Las Trojes Express','Ground Floor of The Ansari Buisness Building left from Northern Entrance','M-Th: 7:30AM-6:00PM , F: 7:30AM-3:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu');




WITH moved_rows AS (
    DELETE FROM DINING_OPTION a
    USING webservices_building b
    WHERE a.building_code = b.building_code
    RETURNING a.*
)
INSERT INTO webservices_dining_option (id, description, location, operating_hours, weblink, menulink, building_id)
SELECT
    id,
    description,
    location,
    operating_hours,
    weblink,
    menulinkk,
    -- Assuming building_id corresponds to the row number in DINING_OPTION
    ROW_NUMBER() OVER () AS building_id
FROM moved_rows;





sudo -u postgres psql















webservices_building
INSERT INTO webservices_building values ( 1,'POINT(-119.81340531019366  39.53979774918553  )','SEM', 'Scrugham Engineering and Mines','056','Engineering','M-F: 6:30AM - 12AM Su: 12PM-12AM');
INSERT INTO webservices_building values ( 2,'POINT(-119.80433072832727  39.49184409511278  )','M745', 'University Health Building', '935','Medicine','TBD');
INSERT INTO webservices_building values ( 3,'POINT( -119.80714192765134 39.537933963204324 )','AE', 'Agricultural Education Health Building', '173','Agriculture','TBD');
INSERT INTO webservices_building values ( 4,'POINT(-119.81470412193153  39.54007058321954  )','AB', 'Ansari Business Building', '063','Business','M-F: 7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 5,'POINT( -119.81691800338545 39.549433835782935 )','AHS', 'Anderson Health Science', '128','Medicine','TBD');
INSERT INTO webservices_building values ( 6,'POINT(-119.81479058989372  39.54468807462003  )','ARF', 'Applied Research Facility', '090','Science','M-F: 8:00AM - 8:00PM Sa:10:00AM - 5:00PM');
INSERT INTO webservices_building values ( 7,'POINT( -119.81801802058668 39.539360806965625 )','AH', 'Argenta Hall', '008','Residence','N/A');
INSERT INTO webservices_building values ( 8,'POINT(-119.81806124756608  39.53872881130598  )','ARTM', 'Artemisia Building', '012','Faculty','N/A');
INSERT INTO webservices_building values ( 9,'POINT( -119.81828858804131 39.549105445421496 )','PD', 'BCN Purchasing', '137','Faculty','N/A')  ; 
INSERT INTO webservices_building values ( 10,'POINT(-119.81450046105799  39.53989658633081  )','FRX', 'Building 058', '058','Faculty','N/A')  ; 
INSERT INTO webservices_building values ( 11,'POINT(-119.81781584571382  39.53980491701399  )','CH', 'Canada Hall', '006','Residence','N/A') ;
INSERT INTO webservices_building values ( 12,'POINT(-119.81934805245452  39.54901031756331  )','CS', 'Central Services', '136','Faculty','N/A') ;
INSERT INTO webservices_building values ( 13,'POINT( -119.81510913589473 39.550119417012034 )','CMM', 'Center for Molecular Medicine', '160','Medicine','M-F: 8:00AM - 5:00PM') ;
INSERT INTO webservices_building values ( 14,'POINT( -119.8143333037202  39.540771252129545 )','CB', 'Chemistry Building', '071','Science','M-F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 15,'POINT( -119.8214527652187  39.550542772148745 )','CCF', 'Child Care Facility', '141','Services','N/A');
INSERT INTO webservices_building values ( 16,'POINT( -119.81689053222183 39.541387361525885 )','CFA', 'Church Fine Arts', '078','Arts','M-F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 17,'POINT( -119.81489581687788 39.537826317244416 )','CA', 'Clark Administration', '035','Administration','N/A');
INSERT INTO webservices_building values ( 18,'POINT( -119.81224908804133 39.55131774080492  )','SAB', 'Claude Howard System Administration (NSHE)', '154','Administration','N/A');
INSERT INTO webservices_building values ( 19,'POINT( -119.81933604571356 39.547161411311954 )','CC', 'Computer Center', '133','Faculty','N/A');
INSERT INTO webservices_building values ( 20,'POINT( -119.93850028441018 39.24510897462628  )','LTD', 'David Hall', '502','Residence','N/A');
INSERT INTO webservices_building values ( 21,'POINT( -119.81238200338602 39.53915804016227  )','DMSC', 'Davidson Math and Science Center', '045','Various','M-F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 22,'POINT( -119.81633160153339 39.54791329379318  )','PDO', 'Department of Pediatrics', '117','Medicine','N/A');
INSERT INTO webservices_building values ( 23,'POINT( -119.81812906105802 39.53894473275393  )','DCC', 'Dining Conference Center', '009','Dining','Variable');
INSERT INTO webservices_building values ( 24,'POINT( -119.81257064756609 39.540647310686346 )','EEL', 'Earthquake Engineering Laboratory', '066','Engineering','N/A');
INSERT INTO webservices_building values ( 25,'POINT( -119.81409834571366 39.5421630071627   )','EJCH', 'Edmund J. Cain Hall', '081','Education','M-F: 7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 26,'POINT( -119.81513256139232 39.54061282624209  )','EMM', 'Effie Mona Mack', '072','Education','M-F: 7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 27,'POINT( -119.81941283036929 39.548034312301894 )','ERF', 'Environmental Research Facility', '130','Science','N/A');
INSERT INTO webservices_building values ( 28,'POINT( -119.81745600338566 39.543854482953776 )','WFC', 'E. L. Wiegand Fitness Center', '086','Fitness','M-F: 6:00AM - 12:00AM, Sa: 8:00AM - 10:00PM, Su: 10:00AM - 10:00PM');
INSERT INTO webservices_building values ( 29,'POINT( -119.80748154571378 39.54119421444668  )','EC', 'Equestrian Center', '170','Equine','N/A');
INSERT INTO webservices_building values ( 30,'POINT( -119.81854326989018 39.55011702047574  )','FRW', 'Facilities Maintenance Services Receiving Warehouse', '144','Facuilty','N/A');
INSERT INTO webservices_building values ( 31,'POINT( -119.81315381687766 39.5417404976077   )','FS', 'Facilities Services', '076','Facilty','N/A');
INSERT INTO webservices_building values ( 32,'POINT( -119.81660616105756 39.54850857379747  )','EBB', 'Edna S. Brigham Clinical Education Building', '123','Medicine','M-F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 33,'POINT( -119.81606167454979 39.542247759730685 )','FSSB', 'Fitzgerald Student Services Building', '082','Administration','M-F: 7:00AM - 5:00PM');
INSERT INTO webservices_building values ( 34,'POINT( -119.81208134448457 39.53772042828378  )','FA', 'Fleischmann Agriculture', '031','Agriculture','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 35,'POINT( -119.81916637454964 39.54600129823881  )','FP', 'Fleischmann Planetarium', '105','Astronomy','Various');
INSERT INTO webservices_building values ( 36,'POINT( -119.81516851687792 39.53845424544374  )','FH', 'Frandsen Humanities', '042','Liberal Arts','7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 37,'POINT( -119.81516851687792 39.53845424544374  )','GPC', 'Gateway Parking Complex', '027','Parking','N/A');
INSERT INTO webservices_building values ( 38,'POINT( -119.81685792866423 39.5397255652871   )','GBH', 'Great Basin Hall', '068','Residence','N/A');
INSERT INTO webservices_building values ( 39,'POINT( -119.81331782117087 39.540576535244604 )','HREL', 'Harry Reid Engineering Laboratory', '065','Engineering','N/A');
INSERT INTO webservices_building values ( 40,'POINT( -119.93560759612333 39.24635978565449  )','LTH', 'Holman Arts & Media Center', '507','Arts','N/A') ;--Tahoe Location
INSERT INTO webservices_building values ( 41,'POINT( -119.81546294571338 39.54930614500257  )','HMS', 'Howard Medical Sciences', '125','Medicine','N/A');
INSERT INTO webservices_building values ( 42,'POINT( -119.8080915470262  39.522164025140086 )','IC', 'Innevation Center', 'N/A','Resources','8:00AM-5:00PM');
INSERT INTO webservices_building values ( 43,'POINT( -119.8160496168775  39.544714801229915 )','JCSU', 'Joe Crowley Student Union', '087','Resources','7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 44,'POINT( -119.81463206105809 39.53830323455984  )','JC', 'Jones Center', '043','Philosophy','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 45,'POINT( -119.81655530380719 39.53863054749339  )','JTB', 'Jot Travis Building', '048','Various','7:00AM - 3:00PM');
INSERT INTO webservices_building values ( 46,'POINT( -119.81659953585844 39.53800708874398  )','JH', 'Juniper Hall', '041','Residence','N/A');
INSERT INTO webservices_building values ( 47,'POINT( -119.80702042306237 39.53866406410878  )','KRC', 'Knudtsen Resource Center', '171','Various','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 48,'POINT( -119.81831999352988 39.54508262817868  )','LEC', 'Lawlor Events Center', '104','Sports/Events','N/A');
INSERT INTO webservices_building values ( 49,'POINT( -119.81727249428408 39.5452995536064   )','LEGH', 'Legacy Hall', '103','Sports','N/A');
INSERT INTO webservices_building values ( 50,'POINT( -119.81405833921025 39.5411347920165   )','LP', 'Leifson Physics', '074','Physics','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 51,'POINT( -119.81243122791577 39.53830962250762  )','LS', 'Life Science', '032','Science','N/A'); -- May be a duplicate of the FLeishmann Life Sciences webservices_building
INSERT INTO webservices_building values ( 52,'POINT( -119.81634831431705 39.53939404616184  )','LH', 'Lincoln Hall', '060','Faculty','N/A');
INSERT INTO webservices_building values ( 53,'POINT( -119.81508967547738 39.54583244685586  )','LRC', 'Lombardi Recreation Center', '095','Sports','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 54,'POINT( -119.81441614448298 39.53925784281859  )','MM', 'Mackay Mines', '057','Library','9:00AM - 4:00PM');
INSERT INTO webservices_building values ( 55,'POINT( -119.81371007393031 39.53817235446425  )','MS', 'Mackay Science', '036','Science','7:00AM - 10:00PM');
INSERT INTO webservices_building values ( 56,'POINT( -119.81753560338552 39.54691546202966  )','S', 'Mackay Stadium', '109','Sports','N/A');
INSERT INTO webservices_building values ( 57,'POINT( -119.81622530337829 39.54967603750378  )','MHS', 'Manville Health Science', '126','Medicine','N/A');
INSERT INTO webservices_building values ( 58,'POINT( -119.81622934448338 39.53730313732111  )','MAH', 'Manzanita Hall', '040','Residence','N/A');
INSERT INTO webservices_building values ( 59,'POINT( -119.8157398033857  39.54344534908103  )','MIKC', 'Mathewson-IGT Knowledge Center', '085','Resources','8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 60,'POINT( -119.81390335945643 39.53761787555032  )','MH', 'Morrill Hall', '034','Residence','N/A');
INSERT INTO webservices_building values ( 61,'POINT( -119.81811830337998 39.54982313524179  )','MP', 'Motor Pool', '145','Facilty','N/A');
INSERT INTO webservices_building values ( 62,'POINT( -119.81774227454976 39.54034528703201  )','NLLC', 'Nevada Living Learning Community', '004','Residence','N/A');
INSERT INTO webservices_building values ( 63,'POINT( -119.81390522117059 39.54293239807674  )','NJC', 'National Judicial College Building', '084','Law','M-F: 7:30AM - 4:30PM');
INSERT INTO webservices_building values ( 64,'POINT( -119.81632800337917 39.54824683515152  )','NJR', 'Nell J. Redfield (Student Health Center)', '122','Medicine','M-F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 65,'POINT( -119.76512370927549 39.39166142141875  )','RC-A', 'Nell J. Redfield Building A, Redfield Campus', 'N/A','N/A','N/A'); -- Located off campus to the south of reno 
INSERT INTO webservices_building values ( 66,'POINT( -119.8166275522579  39.54974493258539  )','NBS', 'Nellor Biomedical Sciences', '127','Medicine','N/A');
INSERT INTO webservices_building values ( 67,'POINT( -119.81800673426197 39.548940089174295 )','NSHL', 'Nevada State Health Laboratory', '138','Medicine','M-Sa: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 68,'POINT( -119.81788644796711 39.5391506124423   )','NH', 'Nye Hall', '138','Residence','N/A');
INSERT INTO webservices_building values ( 69,'POINT( -119.81292484448404 39.53806740306788  )','OB', 'Orvis Building', '033','Classrooms','M - F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 70,'POINT( -119.81303752669206 39.539444048256826 )','PE', 'Palmer Engineering', '050','Engineering','M - F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 71,'POINT( -119.8199783622738  39.546618403323514 )','PS', 'Parking Services', '106','Resources','M - F: 8:00AM - 4:30PM');
INSERT INTO webservices_building values ( 72,'POINT( -119.93935969858425 39.24430949386029  )','LTPA', 'Patterson Hall', '503','Dining','M - F: 8:00AM - 5:00PM'); -- Located in Tahoe
INSERT INTO webservices_building values ( 73,'POINT( -119.81369792640373 39.53907873385332  )','LME', 'Paul Laxalt Mineral Engineering', '046','Engineering','M - F: 8:00AM - 5:00PM');
INSERT INTO webservices_building values ( 74,'POINT( -119.81323500338166 39.53870297159459  )','LMR', 'Paul Laxalt Mineral Research', '044','Engineering','N/A');
INSERT INTO webservices_building values ( 75,'POINT( -119.81799384266482 39.53803450073156  )','PH', 'Peavine Hall', '019','Residence','N/A');
INSERT INTO webservices_building values ( 76,'POINT( -119.81188186850397 39.53986785587337  )','WPEB', 'William N. Pennington Engineering Building', '53','Engineering','M-F: 6:30AM-8:00PM');
INSERT INTO webservices_building values ( 77,'POINT( -119.81370400890174 39.549154335205756 )','PHS', 'William N. Pennington Health Sciences Building', '165','Nursing','M-F: 8:00AM-5:00PM');
INSERT INTO webservices_building values ( 78,'POINT( -119.81587800337755 39.54859900801333  )','PMB', 'William N. Pennington Medical Education', '121','Administration','M-F: 8:00AM-5:00PM Sa-Su: 1:00-5:00PM') ;
INSERT INTO webservices_building values ( 79,'POINT( -119.81524766227444 39.53951022217315  )','PSAC', 'William N. Pennington Student Achievement Center', '067','Resources','M-T: 7:00AM - 12:00PM F: 7:00AM - 5:30PM: Sa:12:00AM-5:00PM Su:12:00PM - 12:00AM') ;
INSERT INTO webservices_building values ( 80,'POINT( -119.81686333308174 39.54558271262498  )','MWPB', 'Marguerite W. Petersen Athletic Academic Center', '100','Resources','M-F: 8:00AM - 5:00PM') ;
INSERT INTO webservices_building values ( 81,'POINT( -119.8141445036932  39.546235708503474 )','PV', 'Ponderosa Village', '097','Residence','N/A') ;
INSERT INTO webservices_building values ( 82,'POINT( -119.93912500340029 39.24303719876431  )','LTPL', 'Prim Library', '505','Library','N/A'); -- Located in Tahoe
INSERT INTO webservices_building values ( 83,'POINT( -119.93888532951102 39.24481460416749  )','LTPS', 'Prim-Schultz Hall', '504','Residence','N/A'); --Located in Tahoe
INSERT INTO webservices_building values ( 84,'POINT( -119.81509042010947 39.5362181388223   )','REO', 'Real Estate Office', '022','Facilty','N/A') ;
INSERT INTO webservices_building values ( 85,'POINT( -119.80732907455003 39.538311734560345 )','RRC', 'Renewable Resource Center', '172','Facilty','N/A') ;
INSERT INTO webservices_building values ( 86,'POINT( -119.82051420338185 39.548061835136025 )','PBS', 'PBS Reno', '135','Broadcasting','N/A') ;
INSERT INTO webservices_building values ( 87,'POINT( -119.81523559663584 39.5414638091013   )','RSK', 'Reynolds School of Journalism', '077','Journalism','M-F: 8:00AM - 5:00PM') ;
INSERT INTO webservices_building values ( 88,'POINT( -119.8163889800642  39.54580548379871  )','CFH', 'Robert Cashell Fieldhouse', '102','Residence','N/A') ;
INSERT INTO webservices_building values ( 89,'POINT( -119.81465024117061 39.53860578063892  )','RH', 'Ross Hall', '047','Administration','N/A') ;
INSERT INTO webservices_building values ( 90,'POINT( -119.81288894487862 39.53770821100202  )','SFB', 'Sarah H. Fleischmann Building', '030','Health Sciences','N/A') ;
INSERT INTO webservices_building values ( 91,'POINT( -119.81638964448285 39.548869712606525 )','SMS', 'Savitt Medical Science', '124','Medicine','N/A') ;
INSERT INTO webservices_building values ( 92,'POINT( -119.8145605669878 39.5408769376099    )','SLH', 'Schulich Lecture Hall', '073','Classrooms','7:00AM - 5:00PM' ) ;
INSERT INTO webservices_building values ( 93,'POINT( -119.81336873896389 39.53973466719673  )','SEM', 'Scrugham Engineering and Mines', '056','Engineering','M-Th: 6:30AM - 12:00AM F:6:30AM - 10:00PM Su:12:00 PM - 12:00AM' );
INSERT INTO webservices_building values ( 94,'POINT( -119.81704070338104 39.53731653449576  )','SH', 'Sierra Hall', '021','Residence','N/A' );
INSERT INTO webservices_building values ( 95,'POINT( -119.81883972681433 39.540145865242394 )','SPC', 'Sierra Street Parking Complex', '005','Parking','N/A' );
INSERT INTO webservices_building values ( 96,'POINT( -119.8160090966358  39.546220244725156 )','SMC', 'Sports Medicine Complex', '101','Parking','M-F: 8:00AM - 5:00PM' ); 
INSERT INTO webservices_building values ( 97,'POINT( -119.93964527456427 39.24262905387518  )','LTC', 'Tahoe Center for Environmental Sciences', '506','Science','N/A' ); --Located in Tahoe
INSERT INTO webservices_building values ( 98,'POINT( -119.81525953620587 39.539022671903    )','TB', 'Thompson Building', '049','Liberal Arts','M-F: 8:00AM - 5:00PM' );
INSERT INTO webservices_building values ( 99,'POINT( -119.81569775948446 39.54127371662348  )','UFA', 'University Foundation Arts', '079','Arts','M-F: 8:00AM - 5:00PM' );
INSERT INTO webservices_building values ( 100,'POINT( -119.81397160338153 39.54013207159345  )','UP', 'Utility Plant', '064','Facilty','N/A' );
INSERT INTO webservices_building values ( 101,'POINT( -119.80615856162557 39.53776836351721  )','VRGC', 'Valley Road Greenhouse Complex', '175','Argiculture','N/A' );
INSERT INTO webservices_building values ( 102,'POINT( -119.81658738558696 39.5402145085141   )','VSG', 'Virginia Street Gym', '062','Fitness','N/A' );
INSERT INTO webservices_building values ( 103,'POINT( -119.81658738558696 39.5402145085141   )','SL', 'Visitors Locker Room', '110','Fitness','N/A' );
INSERT INTO webservices_building values ( 104,'POINT( -119.81899064448312 39.54676030332822  )','WSPC', 'West Stadium Parking Complex', '107','Parking','N/A' );
INSERT INTO webservices_building values ( 105,'POINT( -119.81711134174756 39.54301146245057  )','BWPC', 'Brian J. Whalen Parking Complex', '083','Parking','N/A' );
INSERT INTO webservices_building values ( 106,'POINT( -119.81509340338572 39.54203468203159  )','WRB', 'William J. Raggio', '080','Education','M-F: 8:00AM - 5:00PM' );
INSERT INTO webservices_building values ( 107,'POINT( -119.81276665232328 39.548421152290835  )','WPP', 'William Peccole Park', '114','Sports','N/A' );

INSERT INTO webservices_building values (108,'POINT( -119.81709093224457 39.537792180178506  )','CEB', 'Continuing Education Building', '808','Admin','M-F:8:00-5:00pm' );--added for title 9 and equal opportunity office

--The Following command was used to transfer data from dining_option to webservices_dining_option
INSERT INTO webservices_dining_option (building_id, description, location, operating_hours, weblink, menulink)
SELECT
    b.id AS building_id,
    d.description,
    d.location,
    d.operating_hours,
    d.weblink,
    d.menulinkk
FROM DINING_OPTION d
JOIN webservices_building b ON d.building_code = b.building_code;

INSERT INTO webservices_library values (1, 'Located on Floors 2 - 5 of the Mathewson-IGT Knowledge Center', 'Looking for help with research or just a comfortable place to study? Completed in 2008, the Mathewson-IGT Knowledge Center features the technology, spaces, and services you need.', 59);
INSERT INTO webservices_library values (2, 'Mackay School of Mines building', 'The DeLaMare Science and Engineering Library, located in the Mackay School of Mines building, is a dynamic and technology-rich environment that provides growing user-centered services to the University.', 54);
INSERT INTO webservices_library values (3, 'The Savitt Medical Library has two satellite locations:  Renown Regional Medical Center and UNR Med Moana Clinic.','The history of medicine library on the University of Nevada, Reno School of Medicine campus is located in the Savitt Library. The history of medicine collection consists of photographs, displays of medical artifacts, and books relating to  medicine.',91);

INSERT INTO webservices_hangoutspots values (1, 'Located on the Second Floor offers tables and sofas.', 'A nice window area that allows users to lounge at sofas or tables while waiting for class.',TRUE,76);

INSERT INTO webservices_restroom values (1, 'First Floor Near near Elevators next to staircases', TRUE, 76);

INSERT INTO webservices_vendingmachine values (1, 'Second Floor near Northwest Entrance', 'Drink', 92);

INSERT INTO webservices_resource values (1, 'In addition to over 100+ PCs and MACs, the @One Floor offers students impressive academic support amenities. From the technology-savvy @One Help Desk to the user-friendly computer labs across the floor.', '1st floor of the MIKC','M-F: 7:30am – 12:00am',  'https://library.unr.edu/places/atone', 59);

INSERT INTO webservices_store values (1, 'Nevada Wolf Shop','1st and 2cd Floor of the Joe Crowley Student Union', 'M-F: 7:30AM-7PM',43);

INSERT INTO webservices_lab values (1, 'First Floor Room 101, Located near first floor entrance infront of staircase and elevator','Computer','CSE',76);

INSERT INTO webservices_elevator values (1, 'Located Near the center of the building. Near 1st Floor and 2cd Floor Entrances', 76);

INSERT INTO webservices_staircase values (1, 'Located Near the center of the building. Near 1st Floor and 2cd Floor Entrances. Next to staircases', 76);

INSERT INTO webservices_special_feature values (1, 'Located on the first floor towards the south', '100 Clean Room', 76);
--Fixed missing JSCU Dining_Options
INSERT INTO webservices_dining_option VALUES (1,'The Habit Burger','Joe Crowley Student Union building 2nd Floor','M-Th: 11:00AM-7:00PM F:11:00AM-5:00PM','https://dineoncampus.com/unr/hours-of-operation', 'https://dineoncampus.com/unr/whats-on-the-menu',43);
INSERT INTO webservices_dining_option VALUES (2,'Starbucks','Joe Crowley Student Union building 1st Floor','M-F: 7:00AM-9:00PM Sa-Su:10:00AM-7:00PM','https://www.starbucks.com/store-locator/store/1022313/virginia-15th-unr-1664-n-virginia-street-192-c-reno-nv-895030705-us', 'www.starbucks.com',43);
INSERT INTO webservices_dining_option VALUES (22,'Panda Express','Joe Crowley Student Union building 2nd Floor','M-F: 11:00AM-4:00PM','www.pandaexpress.com', 'https://www.pandaexpress.com/location/university-nevada-reno-px/menu',43);
INSERT INTO webservices_dining_option VALUES (23,'Del Lobo Mexican Grill','Joe Crowley Student Union building 2nd Floor','M-Th: 10:00AM-5:00PM F:11:00AM-2:00PM','N/A', 'https://www.zmenu.com/del-lobo-mexican-grille-reno-online-menu/',43);
--Gender Exclusive Restrooms, ADA Compliant
--Center for Molecular Medicine
INSERT INTO webservices_restroom values (2,'Gender-Inclusive: East wing, first floor, #R107', TRUE, 13);
INSERT INTO webservices_restroom values (3,'Gender-Inclusive: East wing, first floor, #R108' , TRUE, 13);
INSERT INTO webservices_restroom values (4,'Gender-Inclusive: West wing, second floor, #LR201', TRUE, 13);
INSERT INTO webservices_restroom values (5,'Gender-Inclusive: West wing, second floor, #LR202', TRUE, 13);
INSERT INTO webservices_restroom values (6,'Gender-Inclusive: West wing, third floor, #LR301', TRUE, 13);
INSERT INTO webservices_restroom values (7,'Gender-Inclusive: West wing, third floor, #LR302' ,TRUE, 13);
--Howard Medical Sciences
INSERT INTO webservices_restroom values (8,'Gender-Inclusive: First floor, #159',TRUE, 41);
INSERT INTO webservices_restroom values (9,'Gender-Inclusive: Second floor, #213',TRUE, 41);
--Mackay Stadium
INSERT INTO webservices_restroom values (10,'Gender-Inclusive: SW Women’s Restroom Building, first floor, #R102 (available during Stadium events only)',TRUE, 56);
--Nell J.Redfield
INSERT INTO webservices_restroom values (11,'Gender-Inclusive: First floor, #R101',TRUE, 64);
--Savitt Medical Science
INSERT INTO webservices_restroom values (12,'Gender-Inclusive: Ground floor, #R001',FALSE, 91);
INSERT INTO webservices_restroom values (13,'Gender-Inclusive: Ground floor, #R002',FALSE, 91);
--Sporsts Medicine Complex
INSERT INTO webservices_restroom values (14,'Gender-Inclusive: First floor, #R100',TRUE, 96);
--William N Pennington Health Sciences
INSERT INTO webservices_restroom values (15,'Gender-Inclusive: First floor, #R100',TRUE, 77);
INSERT INTO webservices_restroom values (16,'Gender-Inclusive: First floor, #R101',TRUE, 77);
--William N. Pennington Medical Education
INSERT INTO webservices_restroom values (17,'Gender-Inclusive: Ground Floor, #R101',TRUE, 78);
--E. L. Wiegand Fitness Center 
INSERT INTO webservices_restroom values (18,'Gender-Inclusive: First floor, #R106 (fitness center pass required)',TRUE, 28);
INSERT INTO webservices_restroom values (19,'Gender-Inclusive: Third floor, #R301 (fitness center pass required)',TRUE, 28);
INSERT INTO webservices_restroom values (20,'Gender-Inclusive: Third floor, #R302 (fitness center pass required)',TRUE, 28);
--Joe Crowley Student Union 
INSERT INTO webservices_restroom values (21,'Gender-Inclusive: First floor, #R101',TRUE, 40);
INSERT INTO webservices_restroom values (22,'Gender-Inclusive: Fourth floor, #R402',TRUE, 40);
-- Mathewson-IGT Knowledge Center
INSERT INTO webservices_restroom values (23,'Gender-Inclusive: First floor, #R100',FALSE, 59);
INSERT INTO webservices_restroom values (24,'Gender-Inclusive: Second floor, #R202',TRUE, 59);
INSERT INTO webservices_restroom values (25,'Gender-Inclusive: Second floor, #R203',TRUE, 59);
INSERT INTO webservices_restroom values (26,'Gender-Inclusive: Third floor, #R302',TRUE, 59);
INSERT INTO webservices_restroom values (27,'Gender-Inclusive: Third floor, #R303',TRUE, 59);
INSERT INTO webservices_restroom values (28,'Gender-Inclusive: Third floor, #R304',TRUE, 59);
INSERT INTO webservices_restroom values (29,'Gender-Inclusive: Third floor, #R305',TRUE, 59);
INSERT INTO webservices_restroom values (30,'Gender-Inclusive: Fourth floor, #R402',TRUE, 59);
INSERT INTO webservices_restroom values (31,'Gender-Inclusive: Fourth floor, #R403',TRUE, 59);
INSERT INTO webservices_restroom values (32,'Gender-Inclusive: Fifth floor, #R502',TRUE, 59);
INSERT INTO webservices_restroom values (33,'Gender-Inclusive: Fifth floor, #R503',TRUE, 59);
--Schulich Lecture Hall
INSERT INTO webservices_restroom values (34,'Gender-Inclusive: Basement, #R001',FALSE, 92);
--University Foundation Arts 
INSERT INTO webservices_restroom values (35,'Gender-Inclusive: Second floor, #R209',TRUE, 99);
--Virginia Street Gym
INSERT INTO webservices_restroom values (36,'Gender-Inclusive: First floor, #R103',TRUE, 102);
--Clark Administration
INSERT INTO webservices_restroom values (37,'Gender-Inclusive: Second floor, #R200',TRUE, 17);
--Davidson Math and Science Center
INSERT INTO webservices_restroom values (38,'Gender-Inclusive: First floor, #R103',TRUE, 21);
--Earthquake Engineering Laboratory
INSERT INTO webservices_restroom values (39,'Gender-Inclusive: First floor, #R101',TRUE, 24);
INSERT INTO webservices_restroom values (40,'Gender-Inclusive: First floor, #R102',TRUE, 24);
INSERT INTO webservices_restroom values (41,'Gender-Inclusive: Third floor, #R301',TRUE, 24);
INSERT INTO webservices_restroom values (42,'Gender-Inclusive: Third floor, #R302',TRUE, 24);
--Fleischmann Agriculture and Life Science 
INSERT INTO webservices_restroom values (43,'Gender-Inclusive: Third floor inside Museum of Natural History, #R303 (available during museum open hours only)',TRUE, 64);
--Great Basin Hall
INSERT INTO webservices_restroom values (44,'Gender-Inclusive: First floor, #009',TRUE, 38);
INSERT INTO webservices_restroom values (45,'Gender-Inclusive: Ground floor, #G013',TRUE, 38);
--Jones Center
INSERT INTO webservices_restroom values (46,'Gender-Inclusive: First floor, #R100',FALSE, 44);
INSERT INTO webservices_restroom values (47,'Gender-Inclusive: First floor, #R101',FALSE, 44);
--Jot Travis Building
INSERT INTO webservices_restroom values (48,'Gender-Inclusive: First floor, #R129',FALSE, 45);
INSERT INTO webservices_restroom values (49,'Gender-Inclusive: First floor, #R130',FALSE, 45);
INSERT INTO webservices_restroom values (50,'Gender-Inclusive: Second floor, #R205',TRUE, 45);
--Lincoln Hall 
INSERT INTO webservices_restroom values (51,'Gender-Inclusive: Second floor, #R200',TRUE, 52);
INSERT INTO webservices_restroom values (52,'Gender-Inclusive: Second floor, #R201',TRUE, 52);
INSERT INTO webservices_restroom values (53,'Gender-Inclusive: Third floor, #R300',TRUE, 52);
INSERT INTO webservices_restroom values (54,'Gender-Inclusive: Third floor, #R301',TRUE, 52);
--Mackay Mines
INSERT INTO webservices_restroom values (55,'Gender-Inclusive: First floor, #R101',TRUE, 54);
INSERT INTO webservices_restroom values (56,'Gender-Inclusive: First floor, #R102',FALSE, 54);
INSERT INTO webservices_restroom values (57,'Gender-Inclusive: Ground floor, #R001',TRUE, 54);
INSERT INTO webservices_restroom values (58,'Gender-Inclusive: Ground floor, #R002',TRUE, 54);
INSERT INTO webservices_restroom values (59,'Gender-Inclusive: Second floor, #R201',TRUE, 54);
INSERT INTO webservices_restroom values (60,'Gender-Inclusive: Second floor, #R202',TRUE, 54);
INSERT INTO webservices_restroom values (61,'Gender-Inclusive: Third floor, #R301',FALSE, 54);
INSERT INTO webservices_restroom values (62,'Gender-Inclusive: Third floor, #R302',FALSE, 54);
--Mackay Science 
INSERT INTO webservices_restroom values (63,'Gender-Inclusive: First floor, #R101',TRUE, 55);
INSERT INTO webservices_restroom values (64,'Gender-Inclusive: First floor, #R102',TRUE, 55);
INSERT INTO webservices_restroom values (65,'Gender-Inclusive: Second floor, #R201',TRUE, 55);
--Morrill Hall
INSERT INTO webservices_restroom values (66,'Gender-Inclusive: Basement, #R001*',FALSE, 60);
INSERT INTO webservices_restroom values (67,'Gender-Inclusive: Basement, #R002*',FALSE, 60);
INSERT INTO webservices_restroom values (68,'Gender-Inclusive: First floor, #100',TRUE, 60);
INSERT INTO webservices_restroom values (69,'Gender-Inclusive: First floor, #103*',FALSE, 60);
INSERT INTO webservices_restroom values (70,'Gender-Inclusive: Second floor, #201',TRUE, 60);
INSERT INTO webservices_restroom values (71,'Gender-Inclusive: Second floor, #204*',FALSE, 60);
--Sarah H. Fleischmann
INSERT INTO webservices_restroom values (72,'Gender-Inclusive: Basement, #R002*',FALSE, 90);
INSERT INTO webservices_restroom values (73,'Gender-Inclusive: Basement, #R001*',FALSE, 90);
--Scrugham Engineering and Mines 
INSERT INTO webservices_restroom values (74,'Gender-Inclusive: Ground floor, #R001*',FALSE, 1);
INSERT INTO webservices_restroom values (75,'Gender-Inclusive: Third floor, #R303*',FALSE, 1);
INSERT INTO webservices_restroom values (76,'Gender-Inclusive: Third floor, #R304*',FALSE, 1);
INSERT INTO webservices_restroom values (77,'Gender-Inclusive: Fourth floor, #R401*',FALSE, 1);
--Thompson Building 
INSERT INTO webservices_restroom values (78,'Gender-Inclusive: First floor, #R101',TRUE, 98);
--William N. Pennington Engineering Building 
INSERT INTO webservices_restroom values (79,'Gender-Inclusive: Second floor, #R202',TRUE, 76);
INSERT INTO webservices_restroom values (80,'Gender-Inclusive: Second floor, #R203',TRUE, 76);
--Argenta Hall 
INSERT INTO webservices_restroom values (81,'Gender-Inclusive: Mezzanine Level, #R200 (currently closed for renovation)*',FALSE, 7);
INSERT INTO webservices_restroom values (82,'Gender-Inclusive: Mezzanine Level, #R201(currently closed for renovation)*',FALSE, 7);
--Peavine Hall
INSERT INTO webservices_restroom values (83,'Gender-Inclusive: First Floor, #R108',TRUE, 75);
--Sierra Hall 
INSERT INTO webservices_restroom values (84,'Gender-Inclusive: First Floor, #R100*',FALSE, 84);
INSERT INTO webservices_restroom values (85,'Gender-Inclusive: First Floor, #R100*',FALSE, 84);
--University Health Building
INSERT INTO webservices_restroom values (86,'Gender-Inclusive: First floor, #R105',TRUE, 2);
--Innevation Center
INSERT INTO webservices_restroom values (87,'Gender-Inclusive: Basement, #R001',TRUE, 42);
--Knudtsen Resource Center
INSERT INTO webservices_restroom values (88,'Gender-Inclusive: First floor, #129A',TRUE, 47);
INSERT INTO webservices_restroom values (89,'Gender-Inclusive: First floor, #129B',TRUE, 47);
--Holman Arts & Media Center
INSERT INTO webservices_restroom values (90,'Gender-Inclusive: First floor, #R101',TRUE, 48);
INSERT INTO webservices_restroom values (91,'Gender-Inclusive: Second floor, #R201',TRUE, 48);
INSERT INTO webservices_restroom values (92,'Gender-Inclusive: Second floor, #R202',TRUE, 48);
INSERT INTO webservices_restroom values (93,'Gender-Inclusive: Second floor, #R203',TRUE, 48);
--Patterson Hall
INSERT INTO webservices_restroom values (94,'Gender-Inclusive: First floor, #01C03A*',FALSE, 72);
--Prim Library
INSERT INTO webservices_restroom values (95,'Gender-Inclusive: Basement, #B007*',FALSE, 82);

--Resources
--Fleischmann Agriculture
INSERT INTO webservices_resource values (2, 'CABNR Student Center Academic Advising','Room 236','M-F: 9am-5pm','https://www.unr.edu/cabnr/advising', 34);
INSERT INTO webservices_resource values (3, 'CABNR Student Lounge, Containing Computers Printers, Group Study and Internet Access','Room 237A','M-F: 9am-5pm', 'N/A', 34);
--Health and Counseling Services
INSERT INTO webservices_resource values (4, 'UNR Student Health Center. If you need to be seen in an emergency, please call 911 and go to the nearest Emergency Department. Tel: (775) 784-6598 Fax: (775) 784-1298 E-mail: ejennings@unr.edu','Room 196','M-F: 9am-5pm','https://med.unr.edu/student-affairs/health-and-wellness/wellness-program/health-and-counseling-services', 64);
INSERT INTO webservices_resource values (5, 'UNR Counseling Services. Crisis Call Hotline: (775) 784-8090 National Suicide Prevention Hotline: (800) 273-8255 Hope Line Network: (800) 784-2433','Room 202','M-Th: 8am-8pm F:8:00am-5:00pm','https://med.unr.edu/student-affairs/health-and-wellness/wellness-program/health-and-counseling-services',98);
--DRC
INSERT INTO webservices_resource values (6, 'Disablity Resource Center DRC. Tel:(775)784-6000','Suite 230','M-F: 9am-5pm','https://www.unr.edu/accessibility#assistance', 79);
--Math Center
INSERT INTO webservices_resource values (7, 'Math Center. Tutoring Services, Computer Lab, Calculator Rentals','Room 236','M-Th: 9am-6pm F:9:00am-4:00pm','https://www.unr.edu/university-math-center', 79);
--Writing Center
INSERT INTO webservices_resource values (8, 'Writing & Speaking Center.','Suite 350 ','Check Website','https://www.unr.edu/writing-speaking-center', 79);
--Maker Space
INSERT INTO webservices_resource values (9, 'The makerspace hosts many tools for students to rent, computers for students to use, and a soldering suite for students to utilize.','Basement of DeLaMare take a left from the entrance and down a ramp','M-F:8:00am-12:00am ','https://library.unr.edu/places/delamare/themakerspace', 54);
--Chemistry Help Center
INSERT INTO webservices_resource values (10, 'Chemistry Help Center','Room 320','M-F:10:00am - 4:00pm ','https://www.unr.edu/chemistry/student-resources/chemistry-help-center', 79);
--Engineering tutoring 
INSERT INTO webservices_resource values (11, 'Engineering Tutoring ','Room 133','Tutoring Hours on website ','https://www.unr.edu/engineering/student-resources/tutoring', 1);
--Physics Help Center
INSERT INTO webservices_resource values (12, 'Physics Instructional Support Center','Room 320','M-F:9:00am-4:00pm ','https://www.unr.edu/physics/student-resources/physics-help', 79);
--University Testing Center
INSERT INTO webservices_resource values (13, 'University Testing Center','Room 1003','M-F:9:00am - 5:00pm','https://www.unr.edu/testing-center', 106);
--University Tutoring Center
INSERT INTO webservices_resource values (14, 'University Tutoring Center. Services include free appointments, Nevada PASS sessions, walk-in labs and weekly small group sessions.','Room 320','M-F: ','https://www.unr.edu/tutoring-center', 79);
--Cashier's Office
INSERT INTO webservices_resource values (15, 'Cashiers Office','Room 0124 ','M-F:9:00am - 5:00pm ','https://www.unr.edu/campus-business/controllers-office/cashiers-office', 33);
--Office of International Students and Scholars
INSERT INTO webservices_resource values (16, 'Office of International Students and Scholars','Room 330','M-F: 8:00am - 5:00pm','https://www.unr.edu/oiss', 33);
--University Police Services
INSERT INTO webservices_resource values (18, 'University Police Department-Northern Command','Ground Floor Seperate Entrance facing southward','M-F:7:30am - 4:30pm','https://www.unr.edu/police', 33);
--Admissions & Records
INSERT INTO webservices_resource values (19, 'Admissions & Records','2cd Floor Call (775)784-4700','M-F:8:00-5:00pm','https://www.unr.edu/admissions/records', 33);
INSERT INTO webservices_resource values (20, 'Transfer Center','2cd Floor','M-F:8:00-5:00pm','https://www.unr.edu/admissions/records', 33);
INSERT INTO webservices_resource values (21, 'Graduate School','Room 225','M-F:8:00-5:00pm','https://www.unr.edu/grad', 33);
--Equal Opportunity & Title IX
INSERT INTO webservices_resource values (22, 'Equal Opportunity & Title IX','Room 206','M-F:8:00-5:00pm','websihttps://www.unr.edu/equal-opportunity-title-ixte', 108);
--Office of Financial Aid and Scholarships
INSERT INTO webservices_resource values (23, 'Office of Financial Aid and Scholarships','Room 319','M-Th:8:00am - 5:00pm, F:9:00am - 5:00pm','https://www.unr.edu/financial-aid', 33);
--Money Mentors
INSERT INTO webservices_resource values (24, 'Money Mentors','3rd Floor in Financial Aid Office','M-F:8:00am - 5:00pm','https://www.unr.edu/nevada-money-mentors', 33);

--SEM
INSERT INTO webservices_staircase values (2, 'Located left of South Entrance', 1);
INSERT INTO webservices_staircase values (3, 'Located towards the right of North Entrance in car parking', 1);
INSERT INTO webservices_staircase values (4, 'Located towards west of the building near loading docks facing street access.', 1);
--M745
INSERT INTO webservices_resource values (25, 'Patient-Centered Family Medicine | Operated by Renown','First Floor','M-F:8:00am - 5:00pm','https://www.renown.org/health-services/university-health', 2);
--AE
--AB
INSERT INTO webservices_staircase values (5, 'Located Right from North Entrance past room 108. Door will be to the left.', 4);
INSERT INTO webservices_staircase values (6, 'Located Left from North Entrance. Door will be to the right.', 4);
INSERT INTO webservices_staircase values (7, 'Located in front of North Entrance connects first and second floors.', 4);
INSERT INTO webservices_elevator values (2, 'When entering from the North the elevators will be just ahead to the right.', 4);
--AHS
--ARF
--ARTM
--PD
--FRX
--CH
INSERT INTO webservices_vendingmachine values (2, 'Room 03 Near Entrance opposite of Room 05 Laundry', 'Drink', 11);
INSERT INTO webservices_staircase values (11, 'Right from Entrance', 11);
--CS
--CMM
--CB
--CCF
--CFA
--CA
--SAB
--CC
--LTD
--DMSC
INSERT INTO webservices_restroom values (96,'Restrooms located to the right of the North facing Entrance',TRUE, 21);
INSERT INTO webservices_restroom values (97,'Restrooms located to the South Corner near Siera Street Subs',TRUE, 21);
INSERT INTO webservices_lab values (2, 'Second Floor of the Facility','Physics','ENG',21);
INSERT INTO webservices_staircase values (9, 'Located Near South Entrance', 21);
--PDO
--DCC
--EEL
--EJCH
INSERT INTO webservices_staircase values (10, 'Right from East Facing Entrance. Down the Hall from South facing Entrance', 25);












INSERT INTO webservices_resource values (, 'Des','Room 236','M-F:','website', );

