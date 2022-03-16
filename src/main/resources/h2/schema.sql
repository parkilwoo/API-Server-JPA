    DROP TABLE IF EXISTS USER;

    create table USER (
        NAME VARCHAR(30) not null,
        PASSWORD VARCHAR(100) not null,
        REG_NO VARCHAR(14) UNIQUE not null,
        USER_ID VARCHAR(20) PRIMARY KEY
    );
