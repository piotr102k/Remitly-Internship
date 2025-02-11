## Requirements

- [MySql Server](https://dev.mysql.com/downloads/mysql/5.5.html?os=3&version=5)

- `Recommended` [MySql workbench](https://dev.mysql.com/downloads/workbench/)

- `Recommended` [IntelliJ IDEA](https://www.jetbrains.com/idea/download/?section=windows)

## Setup

- Create and connect to a MySql server

- After a successful connection, click "Create a new schema in the connected server" and name it swift

- Nawigate to your desired directory and clone the repository

```sh
git clone https://github.com/piotr102k/Remitly-Internship.git
```

- Open the project in IntelliJ IDEA and run the following command to install packages

```sh
mvn install
```

- Run the project which will automatically update your database

## Tests

- After setting up you can run tests by using the following command

```sh
mvn test
```


## Parsed SWIFT codes

- To add the given SWIFT codes to your MySQL database after you are done setting up

  - Right click on your schema and "press table import wizard"

  - Find ParsedData.csv from the cloned repository and click next

  - Select Use existing table "swift.banks"

  - Click through the rest of the import wizard

- If you encounter a problem with importing try using the following query

```sh
ALTER TABLE swift.banks MODIFY COLUMN is_headquarter smallint(1);
```

- After you are done importing run the following query

```sh
ALTER TABLE swift.banks MODIFY COLUMN is_headquarter bit(1);
```
