-- IDEAL O'QUV MARKAZI — bo'sh bazani yaratish skripti (MySQL 8+/9, MariaDB 10.3+).
-- ideal.sql asosida tuzilgan: jadval tuzilmasi o'sha, lekin real ma'lumotlar (o'quvchilar, o'qituvchilar) yo'q.
-- Qayta ishga tushirish xavfsiz: mavjud jadvallar va ma'lumotlar o'chirilmaydi.
--
-- Ishlatish:
--   mysql -u root -p < database.sql
--
-- Barcha ustunlar ataylab VARCHAR: kod qiymatlarni satr sifatida yozadi/o'qiydi
-- (masalan, guruh.fan_id ga '' yozilishi mumkin), shuning uchun turlarni o'zgartirmang.

CREATE DATABASE IF NOT EXISTS `ideal`
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

USE `ideal`;

-- Fanlar
CREATE TABLE IF NOT EXISTS `fan` (
  `id`   INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- O'qituvchilar va admin. admin: '1' = admin, '0' = o'qituvchi.
-- parol: String.valueOf(String.valueOf(parol.hashCode()).hashCode()) ko'rinishida saqlanadi.
-- amount: o'qituvchi ulushi (maosh hisobi uchun).
CREATE TABLE IF NOT EXISTS `teacher` (
  `id`      INT NOT NULL AUTO_INCREMENT,
  `name`    VARCHAR(255) NOT NULL,
  `familya` VARCHAR(255) NOT NULL,
  `fan_id`  VARCHAR(255) NOT NULL,
  `parol`   VARCHAR(255) NOT NULL,
  `admin`   VARCHAR(255) NOT NULL,
  `amount`  VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Guruhlar. narx: oylik to'lov.
CREATE TABLE IF NOT EXISTS `guruh` (
  `id`         INT NOT NULL AUTO_INCREMENT,
  `guruh_name` VARCHAR(255) NOT NULL,
  `fan_id`     VARCHAR(255) NOT NULL,
  `teacher_id` VARCHAR(255) NOT NULL,
  `narx`       VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- O'quvchilar. fish: familiya-ism-sharif.
CREATE TABLE IF NOT EXISTS `oquvchilar` (
  `id`             INT NOT NULL AUTO_INCREMENT,
  `fish`           VARCHAR(255) NOT NULL,
  `phone_number`   VARCHAR(255) NOT NULL,
  `qoshilgan_sana` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- O'quvchi ↔ guruh bog'lanishi, qarz va chegirma bilan.
CREATE TABLE IF NOT EXISTS `oquvchi_guruh` (
  `id`          INT NOT NULL AUTO_INCREMENT,
  `oquvchi_id`  VARCHAR(255) NOT NULL,
  `fan_id`      VARCHAR(255) NOT NULL,
  `teacher_id`  VARCHAR(255) NOT NULL,
  `guruh_id`    VARCHAR(255) NOT NULL,
  `qarz`        VARCHAR(255) NOT NULL,
  `chegirma`    VARCHAR(255) NOT NULL,
  `qayd_sanasi` VARCHAR(255) NOT NULL,
  `update_date` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- To'lovlar
CREATE TABLE IF NOT EXISTS `tolov` (
  `id`         INT NOT NULL AUTO_INCREMENT,
  `oquvchi_id` VARCHAR(255) NOT NULL,
  `fan_id`     VARCHAR(255) NOT NULL,
  `teacher_id` VARCHAR(255) NOT NULL,
  `guruh_id`   VARCHAR(255) NOT NULL,
  `tolov`      VARCHAR(255) NOT NULL,
  `sana`       VARCHAR(255) NOT NULL,
  `status`     VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Davomat
CREATE TABLE IF NOT EXISTS `davomad` (
  `id`         INT NOT NULL AUTO_INCREMENT,
  `oquvchi_id` VARCHAR(255) NOT NULL,
  `teacher_id` VARCHAR(255) NOT NULL,
  `guruh_id`   VARCHAR(255) NOT NULL,
  `sana`       VARCHAR(255) NOT NULL,
  `status`     VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- O'qituvchilarga to'langan maoshlar tarixi
CREATE TABLE IF NOT EXISTS `maosh_history` (
  `id`          INT NOT NULL AUTO_INCREMENT,
  `teacher_id`  VARCHAR(255) NOT NULL,
  `total_summa` VARCHAR(255) NOT NULL,
  `miqdor`      VARCHAR(255) NOT NULL,
  `olgan_summa` VARCHAR(255) NOT NULL,
  `sana`        VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- O'chirilgan o'quvchilar (qarzi bilan) arxivi
CREATE TABLE IF NOT EXISTS `karzinka` (
  `id`             INT NOT NULL AUTO_INCREMENT,
  `fish`           VARCHAR(255) NOT NULL,
  `fan_id`         VARCHAR(255) NOT NULL,
  `teacher_id`     VARCHAR(255) NOT NULL,
  `qarz`           VARCHAR(255) NOT NULL,
  `ochrilgan_sana` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Hozirgi kodda ishlatilmaydi; ideal.sql bilan moslik uchun qoldirilgan.
CREATE TABLE IF NOT EXISTS `datee` (
  `id`         INT NOT NULL AUTO_INCREMENT,
  `last_tolov` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- ---------------------------------------------------------------------------
-- Boshlang'ich ma'lumotlar
-- ---------------------------------------------------------------------------

-- Admin foydalanuvchi. Login: admin, parol: admin — kirgandan keyin almashtiring.
-- fan_id = '0' ataylab: admin o'qituvchilar ro'yxatida (fan bilan JOIN) ko'rinmaydi.
INSERT IGNORE INTO `teacher` (`id`, `name`, `familya`, `fan_id`, `parol`, `admin`, `amount`) VALUES
(1, 'admin', 'admin', '0', '-715947788', '1', '0');

-- Fanlar ro'yxati (ixtiyoriy, kerak bo'lmasa o'chiring)
INSERT IGNORE INTO `fan` (`id`, `name`) VALUES
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
