
DROP TABLE IF EXISTS game;
CREATE TABLE `game`(
    id VARCHAR(32)  NOT NULL,
    state CHAR(8) NOT NULL,
    PRIMARY KEY (id),
)