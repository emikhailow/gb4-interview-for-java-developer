DROP
DATABASE IF EXISTS cinema;
CREATE
DATABASE cinema;

USE
cinema;

DROP TABLE IF EXISTS sales;
DROP TABLE IF EXISTS timetable;
DROP TABLE IF EXISTS movies;

CREATE TABLE movies
(
    id       INT UNSIGNED NOT NULL AUTO_INCREMENT,
    name     VARCHAR(45),
    duration INT,
    PRIMARY KEY (id)
);
INSERT INTO movies (name, duration)
VALUES ('Titanic', 90),
       ('Avatar', 120),
       ('Gorko', 90),
       ('Ocean''s eleven', 60);

CREATE TABLE timetable
(
    id         INT UNSIGNED NOT NULL AUTO_INCREMENT,
    movie_id   INT UNSIGNED NOT NULL,
    start_time DATETIME NOT NULL,
    price      DECIMAL NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    FOREIGN KEY (movie_id)
        REFERENCES movies (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

INSERT INTO timetable (movie_id, start_time, price)
VALUES (1, '2022-05-19 09:00:00', 300),
       (1, '2022-05-19 11:00:00', 250),
       (1, '2022-05-19 10:00:00', 400),
       (3, '2022-05-19 10:15:00', 300),
       (3, '2022-05-19 19:00:00', 270),
       (4, '2022-05-22 19:00:00', 220);

CREATE TABLE sales
(
    id          INT UNSIGNED NOT NULL AUTO_INCREMENT,
    timeslot_id INT UNSIGNED NOT NULL,
    count       INT UNSIGNED NOT NULL,
    price       DECIMAL NULL DEFAULT 0,
    sum         DECIMAL NULL DEFAULT 0,
    PRIMARY KEY (id),
    UNIQUE INDEX id_UNIQUE (id ASC) VISIBLE,
    FOREIGN KEY (timeslot_id)
        REFERENCES timetable (id)
        ON DELETE CASCADE
        ON UPDATE NO ACTION
);

INSERT INTO sales (timeslot_id, count, price, sum)
VALUES (1, 2, 300, 600),
       (1, 5, 300, 1500),
       (2, 3, 250, 750),
       (2, 3, 250, 750),
       (3, 1, 400, 2400),
       (3, 7, 400, 2800),
       (4, 2, 300, 600),
       (4, 12, 300, 3600),
       (5, 1, 270, 270),
       (5, 5, 270, 1350),
       (6, 3, 220, 660),
       (6, 10, 220, 2200);

/* 1. Сеансы с пересекающимся расписанием */
SELECT #timetable1.id        as id1,
       movie1.name           as "Movie 1",
       timetable1.start_time as "Start time",
       #timetable1.end_time  as end_time1,
       movie1.duration       as "Duration",
       #timetable2.id        as id2,
       movie2.name           as "Movie 2",
       timetable2.start_time as "Start time",
       #timetable2.end_time  as end_time2,
       movie2.duration       as "Duration"
FROM timetable as timetable1
         left join
     movies as movie1
     on
         timetable1.movie_id = movie1.id
         inner join
     timetable timetable2
     on
                 timetable1.id <> timetable2.id and
                 timetable1.start_time <= timetable2.start_time and
                 date_add(timetable1.start_time, interval movie1.duration MINUTE) > timetable2.start_time

         left join
     movies as movie2
     on
         timetable2.movie_id = movie2.id

ORDER BY timetable1.start_time;

/* 2. Сеансы, между которыми есть более 30 минут перерыва */
/* Получаю для всех сеансов, у которых разница с предыдущим более 30 минут, максимальное время окончания предыдущего сеанса */
DROP TABLE IF EXISTS tt_timeslots_with_latest_prev_slot;
CREATE
TEMPORARY TABLE tt_timeslots_with_latest_prev_slot
SELECT timetable2.id                                                         as id,
       timetable2.start_time                                                 as start_time,
       date_add(timetable2.start_time, INTERVAL movie2.duration MINUTE)      as end_time,
       timetable2.movie_id                                                   as movie_id,
       MAX(date_add(timetable1.start_time, INTERVAL movie1.duration MINUTE)) as prev_timeslot_end_time
FROM timetable as timetable1
         left join
     movies as movie1
     on
         timetable1.movie_id = movie1.id
         inner join
     timetable timetable2
     on
         (true and
          timetable1.id < timetable2.id and
          date_add(timetable1.start_time, INTERVAL movie1.duration MINUTE) < timetable2.start_time)
         left join
     movies as movie2
     on
         timetable2.movie_id = movie2.id


WHERE TIMESTAMPDIFF(SECOND, date_add(timetable1.start_time, INTERVAL movie1.duration MINUTE), timetable2.start_time) >
      1800
GROUP BY timetable2.id,
         timetable2.start_time,
         date_add(timetable2.start_time, INTERVAL movie2.duration MINUTE),
         timetable2.movie_id;

/* Соединяю предыдущую таблицу со всем расписанием по дате конца предыдущего сеанса, чтобы получить id предыдущего сеанса */
SELECT #timetable1.id                                                                                                 as prev_timeslot_id,
       movie1.name                                                                                                    as "Movie 1",
       timetable1.start_time                                                                                          as "Start time",
       #timetable1.end_time                                                                                           as prev_timeslot_end_time,
       TIMESTAMPDIFF(MINUTE, timetable1.start_time,
                             DATE_ADD(timetable1.start_time, INTERVAL movie1.duration MINUTE))                        as "Duration",
       #timetable2.id                                                                                                 as id,
       movie2.name                                                                                                    as "Movie 2",
       timetable2.start_time                                                                                          as "Start time",
       #timetable2.end_time                                                                                           as end_time,
       #TIMESTAMPDIFF(MINUTE, timetable2.start_time, timetable2.end_time)                                             as "Duration",
       TIMESTAMPDIFF(MINUTE, DATE_ADD(timetable1.start_time, INTERVAL movie1.duration MINUTE),
                             timetable2.start_time)                                                                   as "Gap"
FROM movies as movie1
         INNER JOIN
     timetable as timetable1
     ON
         timetable1.movie_id = movie1.id
         INNER JOIN
     tt_timeslots_with_latest_prev_slot as timetable2
     ON
             timetable2.prev_timeslot_end_time = DATE_ADD(timetable1.start_time, INTERVAL movie1.duration MINUTE)
         INNER JOIN
     movies as movie2
     ON
         timetable2.movie_id = movie2.id;


/* 3. Список фильмов, для каждого — с указанием общего числа посетителей за все время, среднего числа зрителей за сеанс и общей суммы
 сборов по каждому фильму (отсортировать по убыванию прибыли). Внизу таблицы должна быть строчка «итого», содержащая данные по всем фильмам сразу */
DROP TABLE IF EXISTS tt;
CREATE
TEMPORARY TABLE tt
SELECT movies.id    as movie_id,
       movies.name  as movie_name,
       timetable.id as timeslot_id,
       sales.count as count,
    sales.sum as sum,
    AVG(sales.count) OVER (PARTITION BY timetable.id) as avg
FROM
    sales
    LEFT JOIN
    timetable as timetable
ON
    sales.timeslot_id = timetable.id
    LEFT JOIN
    movies as movies
    ON
    timetable.movie_id = movies.id;
DROP TABLE IF EXISTS tt_2;
CREATE
TEMPORARY TABLE tt_2
SELECT tt.movie_id   AS movie_id,
       tt.movie_name AS movie_name,
       sum(count) AS count,
    sum(sum) AS sum,
    avg(avg) AS avg
FROM
    tt as tt
GROUP BY
    movie_id,
    movie_name
ORDER BY
    sum DESC;
DROP TABLE IF EXISTS tt_2_total;
CREATE
TEMPORARY TABLE tt_2_total
SELECT NULL    AS movie_id,
       "Total" AS movie_name,
       sum(tt_2.count) AS count,
    sum(tt_2.sum) AS sum,
    avg(avg) AS avg
FROM
    tt_2 as tt_2;

SELECT #tt_2.movie_id,
       tt_2.movie_name AS "Movie",
       tt_2.count      AS "Tickets",
       tt_2.sum        AS "Sum",
       tt_2.avg        AS "Avg. audience per timeslot"
FROM tt_2 as tt_2
UNION
SELECT #tt_2_total.movie_id,
       tt_2_total.movie_name,
       tt_2_total.count,
       tt_2_total.sum,
       tt_2_total.avg
FROM tt_2_total AS tt_2_total;

/*4. Число посетителей и кассовые сборы, сгруппированные по времени начала фильма:
с 9 до 15, с 15 до 18, с 18 до 21, с 21 до 00:00
(сколько посетителей пришло с 9 до 15 часов и т.д.). */
SELECT CASE
           WHEN (HOUR(timetable.start_time) >= 9 AND HOUR (timetable.start_time) < 15) THEN '9:00:00 - 15:00:00'
           WHEN (HOUR(timetable.start_time) >= 15 AND HOUR (timetable.start_time) < 18) THEN '15:00:00 - 18:00:00'
           WHEN (HOUR(timetable.start_time) >= 18 AND HOUR (timetable.start_time) < 21) THEN '18:00:00 - 21:00:00'
           WHEN (HOUR(timetable.start_time) > 21) THEN '21:00:00 - 00:00:00'
           END    as "Interval",
       sum(count) as "Tickets",
       sum(sum)   AS "Sum"
FROM sales
         LEFT JOIN
     timetable as timetable
     ON
         sales.timeslot_id = timetable.id
GROUP BY CASE
             WHEN (HOUR(timetable.start_time) >= 9 AND HOUR (timetable.start_time) < 15) THEN '9:00:00 - 15:00:00'
             WHEN (HOUR(timetable.start_time) >= 15 AND HOUR (timetable.start_time) < 18) THEN '15:00:00 - 18:00:00'
             WHEN (HOUR(timetable.start_time) >= 18 AND HOUR (timetable.start_time) < 21) THEN '18:00:00 - 21:00:00'
             WHEN (HOUR(timetable.start_time) > 21) THEN '21:00:00 - 00:00:00'
             END








