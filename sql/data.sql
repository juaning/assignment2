/* Movie Genre */
INSERT INTO ASSIGN2.MOVIE_GENRE (GENRE) VALUES 
	('Romance'), 
	('Horror'), 
	('Thriller'), 
	('Comedy'), 
	('Drama'), 
	('Biopic'), 
	('Action');

/* Age Rating */
INSERT INTO ASSIGN2.AGE_RATING (RATING) VALUES
	('CTC'),
	('G'),
	('PG'),
	('M'),
	('MA 15+'),
	('R 18+'),
	('X 18+');
	
/* Movies */
INSERT INTO ASSIGN2.MOVIE (TITLE,POSTER,SYNOPSIS,AGE_RATING_ID,MOVIE_GENRE_ID, RELEASE_DATE) VALUES
	('In Bloom', 'http://images.yourmovies.com.au/376632_376624_inbloom3.jpg',
	 'Celebrated as a major discovery of the 2013 Berlinale, Nana Ekvtimishvili and Simon Gross drama IN BLOOM is a powerful story of two young girls navigating the oppressive familial and societal expectations of post-Soviet Georgia.',
	9, 5,'2014-05-13'),
	('Life of Crime', 'http://images.yourmovies.com.au/376635_376625_lifeofcrime3.jpg', 'Ordell Robbie and Louis Gara hit it off in prison, where they were both doing time for grand theft auto. Now that they are out, they are joining forces for one big score.',
	10,4,'2014-05-15'),
	('The Equalizer', 'http://images.yourmovies.com.au/376638_376626_equalizer3.jpg', 'A man believes he has put his mysterious past behind him and has dedicated himself to beginning a new, quiet life. But when he meets a young girl under the control of ultra-violent Russian gangsters, he can not stand idly by - he has to help her.',
	10,7,'2014-05-17');
	
/* Character Type */
INSERT INTO ASSIGN2.MOVIE_CHARACTER_TYPE (CHARACTER_TYPE) VALUES
	('Actor'),
	('Actress'),
	('Director');
	
/* Character */
INSERT INTO ASSIGN2.MOVIE_CHARACTER (FIRST_NAME, LAST_NAME, MOVIE_CHARACTER_TYPE_ID) VALUES
	/* In Bloom */
	('Simon', 'Grob', 3),
	('Nana', 'Ekvtimishvili', 3),
	('Lika', 'Babluani', 2),
	('Mariam', 'Bokeria', 2),
	('Data', 'Zakareishvili', 1),
	/* Life of crime */
	('Daniel', 'Schechter', 3),
	('Jennifer', 'Aniston', 2),
	('Isla', 'Fisher', 2),
	('Tim', 'Robbins', 1),
	('Will', 'Forte', 1),
	('Mos', 'Def', 1),
	/* The Equalizer */
	('Antoine', 'Fuqua', 3),
	('Chloe', 'Moretz', 2),
	('Melissa', 'Leo', 2),
	('Denzel', 'Washington', 1),
	('Marton', 'Csokas', 1),
	('Dan', 'Bilzerian', 1);
	
/* Movie has Character */
INSERT INTO ASSIGN2.MOVIE_HAS_MOVIE_CHARACTER (MOVIE_ID, MOVIE_CHARACTER_ID) VALUES
	/* In Bloom */
	(2,1),
	(2,2),
	(2,3),
	(2,4),
	(2,5),
	/* Life of Crime */
	(3,6),
	(3,7),
	(3,8),
	(3,9),
	(3,10),
	(3,11),
	/* The Equalizer */
	(4,12),
	(4,13),
	(4,14),
	(4,15),
	(4,16),
	(4,17);
	
/* Address */
INSERT INTO ASSIGN2.ADDRESS (STREET, CITY, STATE, POSTCODE) VALUES 
	('Broadway Street', 'Broadway', 'NSW', 2007),
	('54 Park Street', 'Sydney', 'NSW', 2000),
	('152 Bunnerong Road', 'Pagewood', 'NSW', 2035);
	
/* Cinema */
INSERT INTO ASSIGN2.CINEMA (NAME, CAPACITY, ADDRESS_ID) VALUES
	('Hoyts Broadway', 100, 1),
	('Hoyts CBD', 50, 2),
	('Hoyts Eastgardens', 150, 3);
	
/* Cinema Amenities */
INSERT INTO ASSIGN2.CINEMA_AMENITIES (AMENITY) VALUES 
	('ATM'),
	('Widescreen'),
	('Snack Bar'),
	('Restaurant');
	
/* Cinema has amenities */
INSERT INTO ASSIGN2.CINEMA_HAS_CINEMA_AMENITIES (CINEMA_ID, CINEMA_AMENITIES_ID) VALUES 
	/* Broadway */
	(1, 1),
	(1, 2),
	(1, 3),
	/* CBD */
	(2, 1),
	(2, 3),
	/* Eastgardens */
	(3, 2),
	(3, 3),
	(3, 4);
	
/* Movie Cinema */
INSERT INTO ASSIGN2.MOVIE_CINEMA (RELEASE_DATE, CINEMA_ID, MOVIE_ID) VALUES 
	/* Broadway */
	('2014-08-07', 1, 2),
	('2014-09-14', 1, 3),
	/* CBD */
	('2014-08-07', 2, 2),
	('2014-10-03', 2, 4),
	/* Eastgardens */
	('2014-08-09', 3, 3),
	('2014-10-17', 3, 4);
	
/* Times */
INSERT INTO ASSIGN2.TIMES (DAY, Time) VALUES 
	('Monday', '10:00:00'),
	('Tuesday', '12:00:00'),
	('Wednesday', '14:00:00'),
	('Thursday', '16:00:00'),
	('Friday', '20:00:00');
	
/* Movie Cinema has Times */
INSERT INTO ASSIGN2.MOVIE_CINEMA_HAS_TIMES (MOVIE_CINEMA_ID, TIMES_ID) VALUES
	(1,1),
	(1,4),
	(2,2),
	(4,5),
	(5,1),
	(6,3);