-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO authorities(id,authority) VALUES (1,'ADMIN');
INSERT INTO appusers(id,username,password,authority) VALUES (1,'admin1','$2a$10$nMmTWAhPTqXqLDJTag3prumFrAJpsYtroxf0ojesFYq0k4PmcbWUS',1);
INSERT INTO admin(id) VALUES (1);
-- Ten player users, named player1 with passwor 0wn3r
INSERT INTO authorities(id,authority) VALUES (2,'PLAYER');
INSERT INTO appusers(id,username,password,authority) VALUES (4,'player1','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (5,'player2','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (6,'player3','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (7,'player4','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (8,'player5','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (9,'player6','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (10,'player7','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (11,'player8','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (12,'player9','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority) VALUES (13,'player10','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2);
INSERT INTO appusers(id,username,password,authority,profile_photo) VALUES (14,'javpalgon','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2,'/images/profile/image1.png');
INSERT INTO appusers(id,username,password,authority,profile_photo) VALUES (15,'guilinbor','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2,'/images/profile/image2.png');
INSERT INTO appusers(id,username,password,authority,profile_photo) VALUES (16,'raqgarhor','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2,'/images/profile/image4.png');
INSERT INTO appusers(id,username,password,authority,profile_photo) VALUES (17,'sanbre','$2a$10$DaS6KIEfF5CRTFrxIoGc7emY3BpZZ0.fVjwA3NiJ.BjpGNmocaS3e',2,'/images/profile/image3.png');


INSERT INTO players(id,firstname,username,lastname,user_id,is_online) VALUES (1,'javi','javpalgon','pallares',14,false);
INSERT INTO players(id,firstname,username,lastname,user_id,is_online) VALUES (2,'guille','guilinbor','linares',15,false);
INSERT INTO players(id,firstname,username,lastname,user_id,is_online) VALUES (3,'raquel','raqgarhor','garcia',16,false);
INSERT INTO players(id,firstname,username,lastname,user_id,is_online) VALUES (4,'santia','sanbre','bregu',17,false);




INSERT INTO player_friends (player_id, friend_id) VALUES (1, 2);
INSERT INTO player_friends (player_id, friend_id) VALUES (2, 1);
INSERT INTO player_friends (player_id, friend_id) VALUES (4, 3);
INSERT INTO player_friends (player_id, friend_id) VALUES (3, 4);


-- meto en la base de datos todas las fichas
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (1,false,'RED_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (2,false,'RED_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (3,false,'RED_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (4,false,'RED_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (5,false,'RED_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (6,false,'BLUE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (7,false,'BLUE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (8,false,'BLUE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (9,false,'BLUE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (10,false,'BLUE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (11,false,'PURPLE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (12,false,'PURPLE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (13,false,'PURPLE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (14,false,'PURPLE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (15,false,'PURPLE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (16,false,'YELLOW_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (17,false,'YELLOW_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (18,false,'YELLOW_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (19,false,'YELLOW_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (20,false,'YELLOW_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (21,false,'GREEN_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (22,false,'GREEN_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (23,false,'GREEN_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (24,false,'GREEN_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (25,false,'GREEN_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (26,false,'ORANGE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (27,false,'ORANGE_BACK','RED');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (28,false,'ORANGE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (29,false,'ORANGE_BACK','BLUE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (30,false,'ORANGE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (31,false,'RED_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (32,false,'RED_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (33,false,'RED_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (34,false,'RED_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (35,false,'RED_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (36,false,'BLUE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (37,false,'BLUE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (38,false,'BLUE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (39,false,'BLUE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (40,false,'BLUE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (41,false,'PURPLE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (42,false,'PURPLE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (43,false,'PURPLE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (44,false,'PURPLE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (45,false,'PURPLE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (46,false,'YELLOW_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (47,false,'YELLOW_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (48,false,'YELLOW_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (49,false,'YELLOW_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (50,false,'YELLOW_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (51,false,'GREEN_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (52,false,'GREEN_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (53,false,'GREEN_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (54,false,'GREEN_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (55,false,'GREEN_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (56,false,'ORANGE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (57,false,'ORANGE_BACK','GREEN');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (58,false,'ORANGE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (59,false,'ORANGE_BACK','YELLOW');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (60,false,'ORANGE_BACK','PURPLE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (61,false,'RED_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (62,false,'RED_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (63,false,'BLUE_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (64,false,'BLUE_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (65,false,'PURPLE_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (66,false,'PURPLE_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (67,false,'YELLOW_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (68,false,'YELLOW_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (69,false,'GREEN_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (70,false,'GREEN_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (71,false,'ORANGE_BACK','ORANGE');
INSERT INTO tile(id,is_reversed,back_color,color) VALUES (72,false,'ORANGE_BACK','ORANGE');

-- creo una cell (casilla) por cada casilla que hay en el tablero
INSERT INTO cell(id,status) VALUES (1,false);
INSERT INTO cell(id,status) VALUES (2,false);
INSERT INTO cell(id,status) VALUES (3,false);
INSERT INTO cell(id,status) VALUES (4,false);
INSERT INTO cell(id,status) VALUES (5,false);
INSERT INTO cell(id,status) VALUES (6,false);
INSERT INTO cell(id,status) VALUES (7,false);
INSERT INTO cell(id,status) VALUES (8,false);
INSERT INTO cell(id,status) VALUES (9,false);
INSERT INTO cell(id,status) VALUES (10,false);
INSERT INTO cell(id,status) VALUES (11,false);
INSERT INTO cell(id,status) VALUES (12,false);
INSERT INTO cell(id,status) VALUES (13,false);
INSERT INTO cell(id,status) VALUES (14,false);
INSERT INTO cell(id,status) VALUES (15,false);
INSERT INTO cell(id,status) VALUES (16,false);
INSERT INTO cell(id,status) VALUES (17,false);
INSERT INTO cell(id,status) VALUES (18,false);
INSERT INTO cell(id,status) VALUES (19,false);


INSERT INTO board(id) VALUES (10);
INSERT INTO board(id) VALUES (20);
INSERT INTO board(id) VALUES (26);
INSERT INTO board(id) VALUES (8);
INSERT INTO board(id) VALUES (5);

-- creo una bolsa (bag) que va asociada a cada tablero
INSERT INTO bag(id,num_fichas,tablero_id) VALUES (1,72,10);
INSERT INTO bag(id,num_fichas,tablero_id) VALUES (2,72,20);
INSERT INTO bag(id,num_fichas,tablero_id) VALUES (3,72,26);
INSERT INTO bag(id,num_fichas,tablero_id) VALUES (4,72,8);
INSERT INTO bag(id,num_fichas,tablero_id) VALUES (5,72,5);

INSERT INTO stats(id, total_points, played_games, won_games, player_id, bronze_medals, silver_medals, platinum_medals, gold_medals) VALUES (4, 4, 2, 1, 4,0 ,0 ,0 ,0);
INSERT INTO stats(id, total_points, played_games, won_games, player_id, bronze_medals, silver_medals, platinum_medals, gold_medals) VALUES (1, 3, 1, 1, 1,0 ,0 ,0 ,0);
INSERT INTO stats(id, total_points, played_games, won_games, player_id, bronze_medals, silver_medals, platinum_medals, gold_medals) VALUES (2, 10, 2, 2, 2,0 ,0 ,0 ,0);
INSERT INTO stats(id, total_points, played_games, won_games, player_id, bronze_medals, silver_medals, platinum_medals, gold_medals) VALUES (3, 5, 1, 1, 3,0 ,0 ,0 ,0);

INSERT INTO hand(id,status) VALUES (1,false);

-- 10 Games Played
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 1, 1, 10, FALSE, '10 Games Played', '/images/achievements/10games.png', 'Play 10 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 2, 2, 10, FALSE, '10 Games Played', '/images/achievements/10games.png', 'Play 10 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 3, 3, 10, FALSE, '10 Games Played', '/images/achievements/10games.png', 'Play 10 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 4, 4, 10, FALSE, '10 Games Played', '/images/achievements/10games.png', 'Play 10 games to unlock this achievement.', 'GAMES_PLAYED');

-- 5 Games Played
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 5, 1, 5, FALSE, '5 Games Played', '/images/achievements/5games.png', 'Play 5 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 6, 2, 5, FALSE, '5 Games Played', '/images/achievements/5games.png', 'Play 5 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 7, 3, 5, FALSE, '5 Games Played', '/images/achievements/5games.png', 'Play 5 games to unlock this achievement.', 'GAMES_PLAYED');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 8, 4, 5, FALSE, '5 Games Played', '/images/achievements/5games.png', 'Play 5 games to unlock this achievement.', 'GAMES_PLAYED');

-- 1 Game Won
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 9, 1, 1, FALSE, '1 Game Won', '/images/achievements/win1.png', 'Win 1 game to unlock this achievement.', 'WON_GAMES');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 10, 2, 1, FALSE, '1 Game Won', '/images/achievements/win1.png', 'Win 1 game to unlock this achievement.', 'WON_GAMES');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 11, 3, 1, FALSE, '1 Game Won', '/images/achievements/win1.png', 'Win 1 game to unlock this achievement.', 'WON_GAMES');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 12, 4, 1, FALSE, '1 Game Won', '/images/achievements/win1.png', 'Win 1 game to unlock this achievement.', 'WON_GAMES');

-- 1 Bronze Medal
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 13, 1, 1, FALSE, '1 Bronze Medal', '/images/achievements/bronze1.png', 'Win 1 bronze medal to unlock this achievement.', 'BRONZE_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 14, 2, 1, FALSE, '1 Bronze Medal', '/images/achievements/bronze1.png', 'Win 1 bronze medal to unlock this achievement.', 'BRONZE_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 15, 3, 1, FALSE, '1 Bronze Medal', '/images/achievements/bronze1.png', 'Win 1 bronze medal to unlock this achievement.', 'BRONZE_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 16, 4, 1, FALSE, '1 Bronze Medal', '/images/achievements/bronze1.png', 'Win 1 bronze medal to unlock this achievement.', 'BRONZE_MEDALS');

-- 1 Gold Medal
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 17, 1, 1, FALSE, '1 Gold Medal', '/images/achievements/gold1.png', 'Win 1 gold medal to unlock this achievement.', 'GOLD_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 18, 2, 1, FALSE, '1 Gold Medal', '/images/achievements/gold1.png', 'Win 1 gold medal to unlock this achievement.', 'GOLD_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 19, 3, 1, FALSE, '1 Gold Medal', '/images/achievements/gold1.png', 'Win 1 gold medal to unlock this achievement.', 'GOLD_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 20, 4, 1, FALSE, '1 Gold Medal', '/images/achievements/gold1.png', 'Win 1 gold medal to unlock this achievement.', 'GOLD_MEDALS');

-- 1 Silver Medal
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 21, 1, 1, FALSE, '1 Silver Medal', '/images/achievements/silver1.png', 'Win 1 silver medal to unlock this achievement.', 'SILVER_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 22, 2, 1, FALSE, '1 Silver Medal', '/images/achievements/silver1.png', 'Win 1 silver medal to unlock this achievement.', 'SILVER_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 23, 3, 1, FALSE, '1 Silver Medal', '/images/achievements/silver1.png', 'Win 1 silver medal to unlock this achievement.', 'SILVER_MEDALS');
INSERT INTO achievement (claimed, id, player_id, threshold, unlocked, name, badge_image, description, metric)
VALUES (FALSE, 24, 4, 1, FALSE, '1 Silver Medal', '/images/achievements/silver1.png', 'Win 1 silver medal to unlock this achievement.', 'SILVER_MEDALS');


/*
INSERT INTO game(id,turn,start,finish,gamemode,game_status,tablero_id,creador_id) VALUES (1,1,'2021-06-01 00:00:00','2021-06-01 00:00:00','SOLO','FINISHED',10,4);
*/