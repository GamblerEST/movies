-- Create tables first
CREATE TABLE IF NOT EXISTS genres (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS actors (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    bio TEXT,
    birth_date TEXT,
    UNIQUE (name, birth_date)
);

CREATE TABLE IF NOT EXISTS movies (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL UNIQUE,
    description TEXT,
    release_year INTEGER DEFAULT 2000,  -- Set a default release year to avoid NULL constraint
    duration INTEGER
);

CREATE TABLE IF NOT EXISTS movie_genres (
    movie_id INTEGER,
    genre_id INTEGER,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (genre_id) REFERENCES genres(id),
    PRIMARY KEY (movie_id, genre_id)
    UNIQUE (movie_id, genre_id)
);

CREATE TABLE IF NOT EXISTS movie_actors (
    movie_id INTEGER,
    actor_id INTEGER,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (actor_id) REFERENCES actors(id),
    PRIMARY KEY (movie_id, actor_id)
    UNIQUE (movie_id, actor_id)
);

-- Insert default data with INSERT OR IGNORE to avoid duplicates
INSERT OR IGNORE INTO genres (name) VALUES
('Drama'),
('Comedy'),
('Action'),
('Thriller'),
('Sci-Fi');

INSERT OR IGNORE INTO actors (name, bio, birth_date) VALUES
('Meryl Streep', 'Multi-award-winning American actress known for her versatility.', '1949-06-22'),
('Denzel Washington', 'Known for intense performances in both action and drama roles.', '1954-12-28'),
('Scarlett Johansson', 'Famous for her roles in indie films and the Marvel Universe.', '1984-11-22'),
('Leonardo DiCaprio', 'Academy Award-winning actor with a passion for dramatic roles.', '1974-11-11'),
('Tom Hanks', 'Beloved actor known for his roles in dramas and comedies.', '1956-07-09'),
('Emma Stone', 'Oscar-winning actress recognized for her comedic timing and charm.', '1988-11-06'),
('Christian Bale', 'Chameleon actor who famously transforms for each role.', '1974-01-30'),
('Natalie Portman', 'Actress and director with roles in both indie and blockbuster films.', '1981-06-09'),
('Ryan Reynolds', 'Known for witty performances in action comedies.', '1976-10-23'),
('Viola Davis', 'Award-winning actress celebrated for powerful dramatic performances.', '1965-08-11'),
('Robert Downey Jr.', 'Best known as Iron Man, with a long and diverse filmography.', '1965-04-04'),
('Brad Pitt', 'Versatile actor and producer with decades of iconic roles.', '1963-12-18'),
('Jennifer Lawrence', 'Known for action, comedy, and heartfelt performances.', '1990-08-15'),
('Idris Elba', 'British actor with commanding screen presence.', '1972-09-06'),
('Kate Winslet', 'Acclaimed actress known for strong dramatic roles.', '1975-10-05'),
('Keanu Reeves', 'Known for action roles like The Matrix.', '1964-09-02'),
('Joaquin Phoenix', 'Versatile actor known for intense roles.', '1974-10-28'),
('Bradley Cooper', 'Actor and director with a range of dramatic and comedic roles.', '1975-01-05'),
('Amy Adams', 'Renowned for dramatic performances.', '1974-08-20'),
('Russell Crowe', 'Known for Gladiator and other dramatic roles.', '1964-04-07'),
('Ryan Gosling', 'Known for romantic and dramatic roles.', '1980-11-12'),
('Chris Evans', 'Famous for Captain America role.', '1981-06-13'),
('Taraji P. Henson', 'Award-winning actress in drama and comedy.', '1970-09-11'),
('Octavia Spencer', 'Academy Award-winning supporting actress.', '1972-05-25'),
('Hugh Jackman', 'Known for Wolverine and musical roles.', '1968-10-12'),
('Alicia Vikander', 'Oscar-winning actress.', '1988-10-03'),
('Domhnall Gleeson', 'Irish actor known for versatile roles.', '1983-05-12'),
('Edward Norton', 'Known for Fight Club and dramatic roles.', '1969-08-18'),
('Matthew McConaughey', 'Oscar-winning actor.', '1969-11-04'),
('Anne Hathaway', 'Oscar-winning actress.', '1982-11-12'),
('Tom Cruise', 'Famous actor known for roles in action films such as Top Gun and Mission: Impossible.', '1962-07-03'),
('Emily Blunt', 'English actress known for her roles in action and drama films.', '1983-02-23'),
('Robin Wright', 'American actress known for roles in drama and political thrillers.', '1966-04-08'),
('Morena Baccarin', 'Brazilian-American actress, known for her roles in sci-fi and action films.', '1979-06-02'),
('Laurence Fishburne', 'Actor, producer, and director, known for his work in both television and film.', '1961-07-30'),
('Tom Hardy', 'English actor known for his roles in action films and as a character actor.', '1977-09-15'),
('Joseph Gordon-Levitt', 'Actor known for his roles in films like Inception and 500 Days of Summer.', '1981-02-17');

INSERT OR IGNORE INTO movies (title, description, release_year, duration) VALUES
('Edge of Tomorrow', 'A soldier caught in a time loop fights alien invaders.', 2014, 113),
('Forrest Gump', 'An unlikely hero experiences key moments in U.S. history.', 1994, 142),
('Inception', 'A skilled thief enters dreams to extract secrets.', 2010, 148),
('The Help', 'African-American maids tell their story during the Civil Rights era.', 2011, 146),
('Deadpool', 'A fast-talking mercenary with a healing factor seeks revenge.', 2016, 108),
('The Matrix', 'A hacker discovers reality is a simulation.', 1999, 136),
('The Revenant', 'A frontiersman survives a bear attack and seeks revenge.', 2015, 156),
('Her', 'A lonely man falls in love with his AI assistant.', 2013, 126),
('Silver Linings Playbook', 'Two troubled souls bond through dance.', 2012, 122),
('Arrival', 'A linguist deciphers an alien language to prevent global war.', 2016, 116),
('Gladiator', 'A betrayed general seeks vengeance as a gladiator.', 2000, 155),
('La La Land', 'Two artists fall in and out of love in LA.', 2016, 128),
('Avengers: Endgame', 'Heroes unite to reverse the damage caused by Thanos.', 2019, 181),
('Hidden Figures', 'Black female mathematicians contribute to NASA space missions.', 2016, 127),
('Catch Me If You Can', 'A teenager successfully cons his way through multiple identities.', 2002, 141),
('Joker', 'A mentally ill loner transforms into a criminal mastermind.', 2019, 122),
('The Prestige', 'Rival magicians clash in 19th-century London.', 2006, 130),
('Ex Machina', 'A young programmer is invited to test a humanoid AI.', 2015, 108),
('Fight Club', 'A disillusioned man starts an underground fight club.', 1999, 139),
('Interstellar', 'Astronauts search for a new home for humanity.', 2014, 169);

-- Edge of Tomorrow
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Edge of Tomorrow' AND g.name = 'Sci-Fi';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Edge of Tomorrow' AND g.name = 'Action';

-- Forrest Gump
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Forrest Gump' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Forrest Gump' AND g.name = 'Comedy';

-- Inception
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Inception' AND g.name = 'Thriller';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Inception' AND g.name = 'Sci-Fi';

-- The Help
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Help' AND g.name = 'Drama';

-- Deadpool
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Deadpool' AND g.name = 'Comedy';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Deadpool' AND g.name = 'Action';

-- The Matrix
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Matrix' AND g.name = 'Sci-Fi';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Matrix' AND g.name = 'Thriller';

-- The Revenant
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Revenant' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Revenant' AND g.name = 'Thriller';

-- Her
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Her' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Her' AND g.name = 'Sci-Fi';

-- Silver Linings Playbook
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Silver Linings Playbook' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Silver Linings Playbook' AND g.name = 'Comedy';

-- Arrival
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Arrival' AND g.name = 'Sci-Fi';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Arrival' AND g.name = 'Thriller';

-- Gladiator
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Gladiator' AND g.name = 'Action';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Gladiator' AND g.name = 'Drama';

-- La La Land
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'La La Land' AND g.name = 'Comedy';

-- Avengers: Endgame
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Avengers: Endgame' AND g.name = 'Action';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Avengers: Endgame' AND g.name = 'Sci-Fi';

-- Hidden Figures
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Hidden Figures' AND g.name = 'Drama';

-- Catch Me If You Can
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Catch Me If You Can' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Catch Me If You Can' AND g.name = 'Comedy';

-- Joker
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Joker' AND g.name = 'Drama';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Joker' AND g.name = 'Thriller';

-- The Prestige
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'The Prestige' AND g.name = 'Thriller';

-- Ex Machina
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Ex Machina' AND g.name = 'Sci-Fi';

-- Fight Club
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Fight Club' AND g.name = 'Thriller';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Fight Club' AND g.name = 'Drama';

-- Interstellar
INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Interstellar' AND g.name = 'Sci-Fi';

INSERT OR IGNORE INTO movie_genres (movie_id, genre_id)
SELECT m.id, g.id FROM movies m, genres g WHERE m.title = 'Interstellar' AND g.name = 'Drama';

-- Edge of Tomorrow
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Edge of Tomorrow' AND a.name = 'Tom Cruise';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Edge of Tomorrow' AND a.name = 'Emily Blunt';

-- Forrest Gump
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Forrest Gump' AND a.name = 'Tom Hanks';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Forrest Gump' AND a.name = 'Robin Wright';

-- Inception
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Inception' AND a.name = 'Leonardo DiCaprio';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Inception' AND a.name = 'Joseph Gordon-Levitt';

-- The Help
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Help' AND a.name = 'Viola Davis';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Help' AND a.name = 'Emma Stone';

-- Deadpool
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Deadpool' AND a.name = 'Ryan Reynolds';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Deadpool' AND a.name = 'Morena Baccarin';

-- The Matrix
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Matrix' AND a.name = 'Keanu Reeves';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Matrix' AND a.name = 'Laurence Fishburne';

-- The Revenant
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Revenant' AND a.name = 'Leonardo DiCaprio';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Revenant' AND a.name = 'Tom Hardy';

-- Her
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Her' AND a.name = 'Joaquin Phoenix';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Her' AND a.name = 'Scarlett Johansson';

-- Silver Linings Playbook
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Silver Linings Playbook' AND a.name = 'Bradley Cooper';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Silver Linings Playbook' AND a.name = 'Jennifer Lawrence';

-- Arrival
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Arrival' AND a.name = 'Amy Adams';

-- Gladiator
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Gladiator' AND a.name = 'Russell Crowe';

-- La La Land
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'La La Land' AND a.name = 'Ryan Gosling';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'La La Land' AND a.name = 'Emma Stone';

-- Avengers: Endgame
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Avengers: Endgame' AND a.name = 'Robert Downey Jr.';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Avengers: Endgame' AND a.name = 'Chris Evans';

-- Hidden Figures
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Hidden Figures' AND a.name = 'Taraji P. Henson';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Hidden Figures' AND a.name = 'Octavia Spencer';

-- Catch Me If You Can
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Catch Me If You Can' AND a.name = 'Leonardo DiCaprio';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Catch Me If You Can' AND a.name = 'Tom Hanks';

-- Joker
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Joker' AND a.name = 'Joaquin Phoenix';

-- The Prestige
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Prestige' AND a.name = 'Christian Bale';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'The Prestige' AND a.name = 'Hugh Jackman';

-- Ex Machina
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Ex Machina' AND a.name = 'Alicia Vikander';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Ex Machina' AND a.name = 'Domhnall Gleeson';

-- Fight Club
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Fight Club' AND a.name = 'Brad Pitt';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Fight Club' AND a.name = 'Edward Norton';

-- Interstellar
INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Interstellar' AND a.name = 'Matthew McConaughey';

INSERT OR IGNORE INTO movie_actors (movie_id, actor_id)
SELECT m.id, a.id FROM movies m, actors a WHERE m.title = 'Interstellar' AND a.name = 'Anne Hathaway';