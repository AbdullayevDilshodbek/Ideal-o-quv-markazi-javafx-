# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Overview

"IDEAL O'QUV MARKAZI" — a JavaFX desktop app for managing a tutoring/education center (subjects, teachers, groups, students, payments, salaries, attendance). Identifiers, UI text and comments are in Uzbek (Latin): `fan` = subject, `guruh` = group, `oquvchi` = student, `tolov` = payment, `maosh` = salary, `davomad` = attendance, `parol` = password.

## Build & run

Maven project (`pom.xml`), Java **17**, JavaFX from OpenJFX artifacts. There is no test suite.

```sh
./run.sh             # loads .env, then runs `mvn javafx:run` from the repo root
mvn compile          # build
mvn javafx:run       # run the app without .env (not plain `java -jar`)
mvn package          # thin jar in target/ (dependencies not bundled)
```

- Entry point: `sample.Tools.Main`.
- Run from the repo root: `text.css` (see below) and the exported `.xls` files are read/written relative to the working directory.
- Standard layout: Java in `src/main/java/sample/...`; FXML in `src/main/resources/sample/...` (same package path as its controller), shared `css/` and `img/` at the resources root. FXML/CSS reference them by relative paths (`@../../../css/x.css`), so keep the directory depth when moving files. Load images from the classpath (`new Image("/img/icon.png")`), not `file:` paths.
- JFoenix 9.0.10 needs the `--add-opens` flags configured on `javafx-maven-plugin`; add more there if a new JFoenix control throws `InaccessibleObjectException`.
- `JFXProgressBar` is incompatible with JavaFX 17 (its skin calls the removed `NodeHelper.treeShowingProperty`) — use the standard `ProgressBar`.
- MySQL connector must stay on 5.1.x: the code imports `com.mysql.jdbc.Connection`, which 8.x removed.

## Database

- MySQL/MariaDB, configured in `sample/Tools/MysqlConnection.java` from environment variables: `DB_URL` (default `jdbc:mysql://localhost/ideal`), `DB_USER` / `DB_PASSWORD` (default `root` / `root`). MySQL 8+/9 needs `?useSSL=false&allowPublicKeyRetrieval=true` on the URL. Local values go in `.env` (gitignored; template in `.env.example`); `.env` is `source`d by bash, so values containing `&` must be quoted.
- Create a fresh database with `mysql -u root -p < database.sql` (idempotent: `CREATE ... IF NOT EXISTS`, `INSERT IGNORE`). It seeds an admin user (login `admin` / password `admin`) and the subject list. `ideal.sql` is the old phpMyAdmin dump with real 2020 data; `database.sql` keeps the same schema. All columns stay `VARCHAR` because the code reads and writes strings (e.g. it can write `''` into `guruh.fan_id`).
- Tables: `fan`, `teacher`, `guruh`, `oquvchilar`, `oquvchi_guruh` (student↔group join), `tolov`, `datee`, `maosh_history`, `davomad`, `karzinka`.

## Architecture

All code lives under the `sample` package. Each feature is a package with the same parts:

- `*.fxml`: the view, bound to its controller with `fx:controller`. Stylesheets come from `css/` at the resources root (`@../../../css/x.css`).
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
