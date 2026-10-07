# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

"IDEAL O'QUV MARKAZI" — a JavaFX desktop app for managing a tutoring/education center (subjects, teachers, groups, students, payments, salaries, attendance). Identifiers, UI text and comments are in Uzbek (Latin): `fan` = subject, `guruh` = group, `oquvchi` = student, `tolov` = payment, `maosh` = salary, `davomad` = attendance, `parol` = password.

## Build & run

There is no Maven/Gradle build and no test suite. The project is an IntelliJ IDEA module (`Tutorial_1.0.iml`):

- JDK **8** with bundled JavaFX (the module's SDK is named `8`). Newer JDKs need OpenJFX added separately.
- Libraries are checked in under `liberarys/` (sic): `jfoenix-9.0.4.jar`, `poi-4.1.2.jar`, `mysql-connector-java-5.1.23-bin.jar`.
- Entry point: `sample.Tools.Main` (also set in `src/META-INF/MANIFEST.MF`).
- The IntelliJ jar artifact `Tutorial_1.1:jar` builds to `out/artifacts/Tutorial_1_1_jar/`. `out/` is build output.

Manual build without IntelliJ (JDK 8 with JavaFX). FXML/CSS/images must be copied next to the classes because they are loaded from the classpath:

```sh
CP="liberarys/*"
mkdir -p build && javac -encoding UTF-8 -cp "$CP" -d build $(find src -name '*.java')
rsync -a --exclude '*.java' src/ build/
java -cp "build:$CP" sample.Tools.Main
```

Run it from the repo root. Some paths are relative to the working directory (`file:src/img/icon.png`, `text.css`, the exported `.xls` files).

## Database

- MySQL/MariaDB at `jdbc:mysql://localhost/ideal`, user `root` / password `root`, hardcoded in `sample/Tools/MysqlConnection.java`.
- The schema and seed data are in `ideal.sql` (phpMyAdmin dump). Import it into a database named `ideal`.
- Tables: `fan`, `teacher`, `guruh`, `oquvchilar`, `oquvchi_guruh` (student↔group join), `tolov`, `datee`, `maosh_history`, `davomad`, `karzinka`.

## Architecture

All code lives under `src/sample/`. Each feature is a package with the same parts:

- `*.fxml`: the view, bound to its controller with `fx:controller`. Stylesheets come from `src/css/` (`@../../../css/x.css`).
- **Controller** (e.g. `Fan.java`, `Guruh.java`): implements `Initializable` and handles the UI events.
- **`*Modul` / `*Model`**: a plain data holder for `TableView` rows, using `PropertyValueFactory`, so getter names must match the column bindings.
- **`*Query` / `*Querys`**: raw JDBC. Each method calls `MysqlConnection.conDb()` for a new connection and runs `PreparedStatement`s. There is no ORM and no DAO layer.

`sample/Tools/SqlQuerys.java` holds shared queries (login, the subject/teacher/group lists) that several features use.

Navigation and roles:

1. `Main` loads `Tools/sample.fxml` (`Controller`). This is the login screen, with a splash animation and a check on the DB connection.
2. Login looks up `teacher` by `name` plus a hashed password. The hash is `String.valueOf(String.valueOf(pw.hashCode()).hashCode())`, and the same scheme is used wherever passwords are written.
3. If `admin == "1"`, the app opens `admin/admin.fxml` (`Admin`). This is a dashboard whose buttons each open a feature FXML in a new `Stage` (fan, teacher, guruh, oquvchi, tolov, maosh, davomad). Admin access is also gated by a hardcoded machine UUID check (`getMacAddress`), plus a hardcoded backdoor login.
4. If `admin == "0"`, the app opens `teacher/teacherRoom.fxml` (`TeacherRoom`), and from there `teacher/davomad/` (attendance and a line chart).

**Session hack:** there is no in-memory session. After a teacher logs in, `Controller` writes the plaintext password into `text.css` in the working directory. `TeacherRoom` and `TeacherDavomad` read it back to work out which teacher is logged in. `Controller.initialize` clears the file on startup. Keep this in mind when changing the login or teacher flows.

Excel export (`admin/maosh/Maosh.java`) uses Apache POI `HSSFWorkbook` and writes `.xls` files to the working directory. `excel code.txt` is a reference snippet, not part of the build.

Screens use fixed-size `AnchorPane` layouts. The scene sizes are hardcoded where each `Stage` is created (for example 1340×670 in `Main` and `Controller`).
