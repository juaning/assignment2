-- -----------------------------------------------------
-- Schema assign2
-- -----------------------------------------------------
CREATE SCHEMA assign2;

-- -----------------------------------------------------
-- Table `assign2`.`Age_Rating`
-- -----------------------------------------------------
DROP TABLE assign2.Age_Rating;
CREATE TABLE assign2.Age_Rating (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT age_rating_pk PRIMARY KEY,
  rating VARCHAR(255) NOT NULL);
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_Genre`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Genre;
CREATE TABLE assign2.Movie_Genre (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_genre_pk PRIMARY KEY,
  Genre VARCHAR(255) NOT NULL);
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie`
-- -----------------------------------------------------
DROP TABLE assign2.Movie;
CREATE TABLE assign2.Movie (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_pk PRIMARY KEY,
  Title VARCHAR(255) NOT NULL,
  Poster VARCHAR(255) DEFAULT NULL,
  Synopsis CLOB DEFAULT NULL,
  Release_date DATE NOT NULL,
  Age_Rating_id INT NOT NULL CONSTRAINT fk_Movie_Age_Rating REFERENCES assign2.Age_Rating(id),
  Movie_Genre_id INT NOT NULL CONSTRAINT fk_Movie_Movie_Genre1 REFERENCES assign2.Movie_Genre(id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_Character_Type`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Character_Type;
CREATE TABLE assign2.Movie_Character_Type (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_character_type_pk PRIMARY KEY,
  character_type VARCHAR(255) NOT NULL);
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_Character`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Character;
CREATE TABLE assign2.Movie_Character (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_character_pk PRIMARY KEY,
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255) NOT NULL,
  Movie_Character_Type_id INT NOT NULL CONSTRAINT fk_Movie_Character_Movie_Character_Type1 REFERENCES assign2.Movie_Character_Type(id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_has_Movie_Character`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_has_Movie_Character;
CREATE TABLE assign2.Movie_has_Movie_Character (
  Movie_id INT NOT NULL CONSTRAINT fk_Movie_has_Movie_Character_Movie1 REFERENCES assign2.Movie(id),
  Movie_Character_id INT NOT NULL CONSTRAINT fk_Movie_has_Movie_Character_Movie_Character1 REFERENCES assign2.Movie_Character(id),
  PRIMARY KEY (Movie_id, Movie_Character_id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Address`
-- -----------------------------------------------------
DROP TABLE assign2.Address;
CREATE TABLE assign2.Address (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT address_pk PRIMARY KEY,
  Street VARCHAR(255) DEFAULT NULL,
  City VARCHAR(255) DEFAULT NULL,
  State VARCHAR(255) DEFAULT NULL,
  Postcode INT DEFAULT NULL);
  
-- -----------------------------------------------------
-- Table `assign2`.`Cinema`
-- -----------------------------------------------------
DROP TABLE assign2.Cinema;
CREATE TABLE assign2.Cinema (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT cinema_pk PRIMARY KEY,
  Name VARCHAR(255) NOT NULL,
  Capacity INT NOT NULL,
  Address_id INT NOT NULL CONSTRAINT fk_Cinema_Address1 REFERENCES assign2.Address(id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Cinema_Amenities`
-- -----------------------------------------------------
DROP TABLE assign2.Cinema_Amenities;
CREATE TABLE assign2.Cinema_Amenities (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT cinema_amenities_pk PRIMARY KEY,
  Amenity VARCHAR(255) DEFAULT NULL);
  
-- -----------------------------------------------------
-- Table `assign2`.`Cinema_has_Cinema_Amenities`
-- -----------------------------------------------------
DROP TABLE assign2.Cinema_has_Cinema_Amenities;
CREATE TABLE assign2.Cinema_has_Cinema_Amenities (
  Cinema_id INT NOT NULL CONSTRAINT fk_Cinema_has_Cinema_Amenities_Cinema1 REFERENCES assign2.Cinema(id),
  Cinema_Amenities_id INT NOT NULL CONSTRAINT fk_Cinema_has_Cinema_Amenities_Cinema_Amenities1 REFERENCES assign2.Cinema_Amenities(id),
  PRIMARY KEY (Cinema_id, Cinema_Amenities_id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_Cinema`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Cinema;
CREATE TABLE assign2.Movie_Cinema (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_cinema_pk PRIMARY KEY,
  Release_date DATE NOT NULL,
  Cinema_id INT NOT NULL CONSTRAINT fk_Movie_Cinema_Cinema1 REFERENCES assign2.Cinema(id),
  Movie_id INT NOT NULL CONSTRAINT fk_Movie_Cinema_Movie1 REFERENCES assign2.Movie(id));

-- -----------------------------------------------------
-- Table `assign2`.`Times`
-- -----------------------------------------------------
DROP TABLE assign2.Times;
CREATE TABLE assign2.Times (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT times_pk PRIMARY KEY,
  Day VARCHAR(45) NOT NULL,
  Time TIME NOT NULL,
  CONSTRAINT times_unique UNIQUE (Day, Time));

-- -----------------------------------------------------
-- Table `assign2`.`Movie_Cinema_has_Times`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Cinema_has_Times;
CREATE TABLE assign2.Movie_Cinema_has_Times (
  Movie_Cinema_id INT NOT NULL CONSTRAINT fk_Movie_Cinema_has_Times_Movie_Cinema1 REFERENCES assign2.Movie_Cinema(id),
  Times_id INT NOT NULL CONSTRAINT fk_Movie_Cinema_has_Times_Times1 REFERENCES assign2.Times(id),
  PRIMARY KEY (Movie_Cinema_id, Times_id));

-- -----------------------------------------------------
-- Table `assign2`.`User_Type`
-- -----------------------------------------------------
DROP TABLE assign2.User_Type;
CREATE TABLE assign2.User_Type (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT user_type_pk PRIMARY KEY,
  user_type VARCHAR(255) NOT NULL);

-- -----------------------------------------------------
-- Table `assign2`.`user`
-- -----------------------------------------------------
DROP TABLE assign2.Cinema_User;
CREATE TABLE assign2.Cinema_User (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT cinema_user_pk PRIMARY KEY,
  username VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL,
  password VARCHAR(255) NOT NULL,
  create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  nickname VARCHAR(255) DEFAULT NULL,
  first_name VARCHAR(255) DEFAULT NULL,
  last_name VARCHAR(255) DEFAULT NULL,
  registration_code VARCHAR(255) DEFAULT NULL,
  User_Type_id INT NOT NULL CONSTRAINT fk_user_User_Type1 REFERENCES assign2.User_Type(id));
  
-- -----------------------------------------------------
-- Table `assign2`.`Movie_Comment`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Comment;
CREATE TABLE assign2.Movie_Comment (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_comment_pk PRIMARY KEY,
  Movie_id INT NOT NULL CONSTRAINT fk_Movie_has_user_Movie1 REFERENCES assign2.Movie(id),
  cinema_user_id INT NOT NULL CONSTRAINT fk_Movie_has_user_user1 REFERENCES assign2.Cinema_User(id),
  review CLOB DEFAULT NULL,
  rating INT DEFAULT NULL,
  created_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP);

-- -----------------------------------------------------
-- Table `assign2`.`Movie_Booked`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Booked;
CREATE TABLE assign2.Movie_Booked (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_booked_pk PRIMARY KEY,
  Movie_Cinema_id INT NOT NULL CONSTRAINT fk_Movie_Booked_Movie_Cinema1 REFERENCES assign2.Movie_Cinema(id),
  Times_id INT NOT NULL CONSTRAINT fk_Movie_Booked_Times1 REFERENCES assign2.Times(id),
  cinema_user_id INT NOT NULL CONSTRAINT fk_Movie_Booked_user1 REFERENCES assign2.Cinema_User(id),
  seats INT DEFAULT NULL);
--  CONSTRAINT fk_Movie_Booked_Movie_Cinema_has_Times1 
--  FOREIGN KEY (Movie_Cinema_id, Times_id) 
--  REFERENCES assign2.Movie_Cinema_has_Times(Movie_Cinema_id,Times_id));

-- -----------------------------------------------------
-- Table `assign2`.`Movie_Booked_Payment`
-- -----------------------------------------------------
DROP TABLE assign2.Movie_Booked_Payment;
CREATE TABLE assign2.Movie_Booked_Payment (
  id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) CONSTRAINT movie_booked_payment_pk PRIMARY KEY,
  Movie_Booked_id INT NOT NULL CONSTRAINT fk_Movie_Booked_Payment_Movie_Booked1 REFERENCES assign2.Movie_Booked(id),
  Credit_Card_Name VARCHAR(255) DEFAULT NULL,
  Credit_Card_Number VARCHAR(255) DEFAULT NULL,
  Credit_Card_CSV INT DEFAULT NULL);