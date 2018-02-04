CREATE TABLE IF NOT EXISTS myrep(
    TIMEZONE varchar(10),
    TIMEZONE_IANA varchar(50)
);

-- Test data
INSERT INTO myrep (TIMEZONE, TIMEZONE_IANA) VALUES ('+0000', 'London');
INSERT INTO myrep VALUES ('+0300', 'European/Moscow');
INSERT INTO myrep VALUES ('+1200', 'NewYork');
INSERT INTO myrep VALUES ('+3200', 'I_DONT_KNOW');