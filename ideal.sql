-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Время создания: Мар 16 2020 г., 07:57
-- Версия сервера: 10.3.13-MariaDB
-- Версия PHP: 7.1.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База данных: `ideal`
--

-- --------------------------------------------------------

--
-- Структура таблицы `datee`
--

CREATE TABLE `datee` (
  `id` int(11) NOT NULL,
  `last_tolov` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `datee`
--

INSERT INTO `datee` (`id`, `last_tolov`) VALUES
(1, '01/03/2020');

-- --------------------------------------------------------

--
-- Структура таблицы `davomad`
--

CREATE TABLE `davomad` (
  `id` int(11) NOT NULL,
  `oquvchi_id` varchar(255) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `guruh_id` varchar(255) NOT NULL,
  `sana` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `davomad`
--

INSERT INTO `davomad` (`id`, `oquvchi_id`, `teacher_id`, `guruh_id`, `sana`, `status`) VALUES
(1, '48', '21', '21', '05/03/2020', '1');

-- --------------------------------------------------------

--
-- Структура таблицы `fan`
--

CREATE TABLE `fan` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `fan`
--

INSERT INTO `fan` (`id`, `name`) VALUES
(1, 'Matematika'),
(2, 'Fizika'),
(3, 'Ingliz tili'),
(4, 'Kimyo'),
(5, 'Ona tili'),
(6, 'Tarix'),
(7, 'IELTS'),
(8, 'Biologiya'),
(9, 'Chizma chilik'),
(10, 'Qalam Tasvir');

-- --------------------------------------------------------

--
-- Структура таблицы `guruh`
--

CREATE TABLE `guruh` (
  `id` int(11) NOT NULL,
  `guruh_name` varchar(255) NOT NULL,
  `fan_id` varchar(255) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `narx` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `guruh`
--

INSERT INTO `guruh` (`id`, `guruh_name`, `fan_id`, `teacher_id`, `narx`) VALUES
(1, 'Matematika1', '1', '1', '200000'),
(2, 'A guruh', '2', '6', '100000'),
(3, 'Tarix1', '6', '12', '200000'),
(4, 'B guruh', '2', '6', '100000'),
(5, 'C guruh', '2', '6', '100000'),
(6, 'D guruh', '2', '6', '100000'),
(7, '1-A', '7', '13', '130000'),
(8, '1 - A guruh', '1', '9', '100000'),
(9, '2 - A guruh', '1', '9', '100000'),
(10, '2 - B guruh', '1', '9', '100000'),
(11, '2 - C guruh', '1', '9', '100000'),
(12, '2 - D guruh', '1', '9', '100000'),
(13, '1-C', '7', '13', '130000'),
(14, 'A-GURUH', '8', '17', '100000'),
(15, 'B-GURUH', '8', '17', '100000'),
(16, 'C-GURUH', '8', '17', '100000'),
(17, 'YEVRO', '8', '17', '100000'),
(18, 'D-GURUH', '8', '17', '100000'),
(19, 'A-guruh', '8', '15', '101000'),
(20, 'A', '', '', '100000'),
(21, 'A', '10', '21', '100000');

-- --------------------------------------------------------

--
-- Структура таблицы `karzinka`
--

CREATE TABLE `karzinka` (
  `id` int(11) NOT NULL,
  `fish` varchar(255) NOT NULL,
  `fan_id` varchar(255) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `qarz` varchar(255) NOT NULL,
  `ochrilgan_sana` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `maosh_history`
--

CREATE TABLE `maosh_history` (
  `id` int(11) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `total_summa` varchar(255) NOT NULL,
  `miqdor` varchar(255) NOT NULL,
  `olgan_summa` varchar(255) NOT NULL,
  `sana` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Структура таблицы `oquvchilar`
--

CREATE TABLE `oquvchilar` (
  `id` int(11) NOT NULL,
  `fish` varchar(255) NOT NULL,
  `phone_number` varchar(255) NOT NULL,
  `qoshilgan_sana` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `oquvchilar`
--

INSERT INTO `oquvchilar` (`id`, `fish`, `phone_number`, `qoshilgan_sana`) VALUES
(1, 'Ergasheva Nozila', '+998911237532', '17/02/2020'),
(2, 'Javohir Abdijabborov', '+998905303948', '01/02/2020'),
(3, 'Hamdamov Eldor', '+yoq', '01/02/2020'),
(4, 'Nodira Abdulmuhammadova', '+998903015620', '01/02/2020'),
(5, 'Hasanova Robiya', '+yoq', '01/02/2020'),
(6, 'Ma`murjonova Gulshanxon', '+yoq', '01/02/2020'),
(7, 'Asadbek  G\'aniyev', '+yo\'q', '01/02/2020'),
(8, 'Sirojiddinova Muazzam', 'yo\'q', '01/03/2020'),
(9, 'Anvarova Matluba', '90-958-24-88', '01/03/2020'),
(10, 'Esonboyev Umriddin', '91-487-27-81', '01/03/2020'),
(11, 'Ne\'matov Hasanboy', '90-232-68-41', '01/03/2020'),
(12, 'Nuraliyeva Muborak', '91-651-91-03', '01/03/2020'),
(13, 'Obidova Odina', 'yo\'q', '01/03/2020'),
(14, 'Odilova Nargiza', 'y\'oq', '01/03/2020'),
(15, 'Olimova Gulhayo', '99-363-06-81', '01/03/2020'),
(16, 'Rustamxo\'jayeva Feruza', '93-644-71-71', '01/03/2020'),
(17, 'Xolmatova Zuhra', 'yo\'q', '01/03/2020'),
(18, 'Xojakbarov Abrorjon', '99-404-56-48', '01/03/2020'),
(19, 'Ibrohimov Shukurullo', 'yo\'q', '01/03/2020'),
(20, 'Alijonova Maftuna', '97-501-75-79', '01/03/2020'),
(21, 'Mamadolimov AHliddin', '99-602-06-23', '01/03/2020'),
(22, 'Olimjonova Zuhraxon', '90-291-35-47', '01/03/2020'),
(23, 'Ro\'zimatova Gulbahor', '93-478-04-26', '01/03/2020'),
(24, 'Solijonov Asadbek', '99-495-25-69', '01/03/2020'),
(25, 'Toshbekova O\'g\'iloy', '91-696-13-58', '01/03/2020'),
(26, 'Xomidaliyeva Muattar', '99-328-19-34', '01/03/2020'),
(27, 'Xoldarova Gavharoy', '91-688-06-56', '01/03/2020'),
(28, 'Zokirjonov Abror', '90-623-75-63', '01/03/2020'),
(29, 'Ibrohimova Gulshanoy', '90-303-23-88', '01/03/2020'),
(30, 'Ro\'zimatov Farruh', 'yo\'q', '01/03/2020'),
(31, 'Mutalipova Nozima', '91-669-57-17', '01/03/2020'),
(32, 'Otahonova Rohila', '91-325-91-01', '01/03/2020'),
(33, 'Sultonova Diyora', '99-997-01-05', '01/03/2020'),
(34, 'Valiyeva Kamolat', '97-337-78-83', '01/03/2020'),
(35, 'Dostonjonova Odina', '91-658-23-06', '01/03/2020'),
(36, 'Abdubannopova Aziza', '90-302-49-09', '01/03/2020'),
(37, 'Abdulhamidova Gulchehra', '93-734-62-23', '01/03/2020'),
(38, 'Abdunazarov Jonibek', '90-534-67-31', '01/03/2020'),
(39, 'Baxtiyorov Shohruh', 'yo\'q', '01/03/2020'),
(40, 'Nurullayeva Zebohon', '98-576-07-65', '01/03/2020'),
(41, 'Qobilov Islomjon', '90-302-43-81', '01/03/2020'),
(42, 'Sotvoldiyev Dostonbek', '97-625-55-71', '01/03/2020'),
(43, 'Sotvoldiyeva Guloyim', '97-625-55-71', '01/03/2020'),
(44, 'Meliboyeva Husnida', '91-671-29-37', '01/03/2020'),
(45, 'Maxmudova Husniyabonu', 'yo\'q', '01/03/2020'),
(46, 'Abduqahhorova Sarvinoz', '91-112-94-53', '01/03/2020'),
(47, 'Nishonboyev Muhammadrasul', '90-001-25-27', '01/03/2020'),
(48, 'Tulkiboev Davlatbek', '+998911701354', '01/03/2020');

-- --------------------------------------------------------

--
-- Структура таблицы `oquvchi_guruh`
--

CREATE TABLE `oquvchi_guruh` (
  `id` int(11) NOT NULL,
  `oquvchi_id` varchar(255) NOT NULL,
  `fan_id` varchar(255) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `guruh_id` varchar(255) NOT NULL,
  `qarz` varchar(255) NOT NULL,
  `chegirma` varchar(255) NOT NULL,
  `qayd_sanasi` varchar(255) NOT NULL,
  `update_date` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `oquvchi_guruh`
--

INSERT INTO `oquvchi_guruh` (`id`, `oquvchi_id`, `fan_id`, `teacher_id`, `guruh_id`, `qarz`, `chegirma`, `qayd_sanasi`, `update_date`) VALUES
(1, '1', '7', '13', '7', '188275.8620689655', '0', '17/02/2020', '01/03/2020'),
(2, '2', '2', '6', '2', '200000.0', '0', '01/02/2020', '01/03/2020'),
(3, '3', '7', '13', '7', '260000.0', '0', '01/02/2020', '01/03/2020'),
(4, '4', '2', '6', '2', '100000.0', '0', '01/02/2020', '01/03/2020'),
(5, '5', '7', '13', '7', '260000.0', '0', '01/02/2020', '01/03/2020'),
(6, '6', '7', '13', '7', '260000.0', '0', '01/02/2020', '01/03/2020'),
(7, '4', '1', '9', '11', '200000.0', '0', '01/02/2020', '01/03/2020'),
(8, '7', '2', '6', '2', '200000.0', '0', '01/02/2020', '01/03/2020'),
(9, '8', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(10, '9', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(11, '10', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(12, '11', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(13, '12', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(14, '13', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(15, '14', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(16, '15', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(17, '16', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(18, '17', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(19, '18', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(20, '19', '8', '17', '14', '100000.0', '0', '01/03/2020', '01/03/2020'),
(21, '20', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(22, '21', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(23, '22', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(24, '23', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(25, '24', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(26, '25', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(27, '26', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(28, '27', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(29, '28', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(30, '29', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(31, '30', '8', '17', '15', '100000.0', '0', '01/03/2020', '01/03/2020'),
(32, '31', '8', '17', '16', '100000.0', '0', '01/03/2020', '01/03/2020'),
(33, '32', '8', '17', '16', '100000.0', '0', '01/03/2020', '01/03/2020'),
(34, '33', '8', '17', '16', '100000.0', '0', '01/03/2020', '01/03/2020'),
(35, '34', '8', '17', '16', '100000.0', '0', '01/03/2020', '01/03/2020'),
(36, '35', '8', '17', '16', '100000.0', '0', '01/03/2020', '01/03/2020'),
(37, '36', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(38, '37', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(39, '38', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(40, '39', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(41, '40', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(42, '41', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(43, '42', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(44, '43', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(45, '44', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(46, '45', '8', '17', '17', '100000.0', '0', '01/03/2020', '01/03/2020'),
(47, '46', '8', '17', '18', '100000.0', '0', '01/03/2020', '01/03/2020'),
(48, '47', '8', '17', '18', '100000.0', '0', '01/03/2020', '01/03/2020'),
(49, '48', '10', '21', '21', '100000.0', '0', '01/03/2020', '01/03/2020');

-- --------------------------------------------------------

--
-- Структура таблицы `teacher`
--

CREATE TABLE `teacher` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `familya` varchar(255) NOT NULL,
  `fan_id` varchar(255) NOT NULL,
  `parol` varchar(255) NOT NULL,
  `admin` varchar(255) NOT NULL,
  `amount` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `teacher`
--

INSERT INTO `teacher` (`id`, `name`, `familya`, `fan_id`, `parol`, `admin`, `amount`) VALUES
(1, 'Ideal plus', 'Bek', '1448', '1669', '1', '0.5'),
(6, 'Nurullo', 'Qodirov', '2', '2106940583', '0', '0.7'),
(7, 'Mashrabjon', 'Muhammadjonov', '2', '111111', '0', '0.7'),
(8, 'Abduraufbek', 'Nasirdinov', '1', '2002222', '0', '0.4'),
(9, 'Burhonjon', 'Rahmatullayev', '1', '2002545454', '0', '0.7'),
(10, 'Rustambek', 'Mamadaliyev', '1', '56565656', '0', '0.6'),
(11, 'Abdulboriy', 'G\'ulomov', '1', '56565654', '0', '0.4'),
(12, 'Ziyodbek', 'Sharipov', '6', '2105010063', '0', '0.7'),
(13, 'Umarjon', 'Bo\'riyev', '7', '2138189516', '0', '0.7'),
(14, 'Bahromboy', 'Muxtoraliyev', '5', '2079267634', '0', '0.006999999999999999'),
(15, 'Dostonbek', 'Ibrohimov', '8', '-2131894855', '0', '0.006999999999999999'),
(16, 'Isomiddin ', 'Jo\'rayev', '3', '2101430210', '0', '0.006999999999999999'),
(17, 'Sanjarbek', 'Soliyev', '8', '2102264365', '0', '0.005'),
(18, 'Sardor', 'Elchiyev', '4', '2138249810', '0', '0.006999999999999999'),
(19, 'Rustambek', 'Madvaliyev', '1', '2107624883', '0', '0.006'),
(20, 'Abduraufboy', 'Nasriddinov', '1', '2101401355', '0', '0.004'),
(21, 'Qodirjon', 'Rustamov', '10', '535321245', '0', '0.7');

-- --------------------------------------------------------

--
-- Структура таблицы `tolov`
--

CREATE TABLE `tolov` (
  `id` int(11) NOT NULL,
  `oquvchi_id` varchar(255) NOT NULL,
  `fan_id` varchar(255) NOT NULL,
  `teacher_id` varchar(255) NOT NULL,
  `guruh_id` varchar(255) NOT NULL,
  `tolov` varchar(255) NOT NULL,
  `sana` varchar(255) NOT NULL,
  `status` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Дамп данных таблицы `tolov`
--

INSERT INTO `tolov` (`id`, `oquvchi_id`, `fan_id`, `teacher_id`, `guruh_id`, `tolov`, `sana`, `status`) VALUES
(1, '4', '2', '6', '2', '100000', '17/02/2020', '1');

--
-- Индексы сохранённых таблиц
--

--
-- Индексы таблицы `datee`
--
ALTER TABLE `datee`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `davomad`
--
ALTER TABLE `davomad`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `fan`
--
ALTER TABLE `fan`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `guruh`
--
ALTER TABLE `guruh`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `karzinka`
--
ALTER TABLE `karzinka`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `maosh_history`
--
ALTER TABLE `maosh_history`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `oquvchilar`
--
ALTER TABLE `oquvchilar`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `oquvchi_guruh`
--
ALTER TABLE `oquvchi_guruh`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `teacher`
--
ALTER TABLE `teacher`
  ADD PRIMARY KEY (`id`);

--
-- Индексы таблицы `tolov`
--
ALTER TABLE `tolov`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT для сохранённых таблиц
--

--
-- AUTO_INCREMENT для таблицы `datee`
--
ALTER TABLE `datee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `davomad`
--
ALTER TABLE `davomad`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT для таблицы `fan`
--
ALTER TABLE `fan`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT для таблицы `guruh`
--
ALTER TABLE `guruh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT для таблицы `karzinka`
--
ALTER TABLE `karzinka`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `maosh_history`
--
ALTER TABLE `maosh_history`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT для таблицы `oquvchilar`
--
ALTER TABLE `oquvchilar`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT для таблицы `oquvchi_guruh`
--
ALTER TABLE `oquvchi_guruh`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=50;

--
-- AUTO_INCREMENT для таблицы `teacher`
--
ALTER TABLE `teacher`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT для таблицы `tolov`
--
ALTER TABLE `tolov`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
