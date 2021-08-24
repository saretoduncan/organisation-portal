# ORGANISATION-PORTAL
This a rest REST API for querying and retrieving scoped news and information of employees in an organization

## Built With

- JAVA 
- J-UNIT5
- GRADLE
- JAVA SPARK



## Getting Started

To get a local copy, follow these simple example steps.

### Prerequisites

A computer with a working and up to date web browser.

### Setup

- Clone the repository to your folder of choice using Git (or just download all the files)
- Install JVM 
- Install Java IDE of your choice

```
$ git clone https://github.com/saretoduncan/organisation-portal.git
$ psql
$ CREATE DATABASE organisation_db;
$\c organisation_db
$ CREATE TABLE users (id serial PRIMARY KEY, name varchar, role varchar, position varchar, departmentid int);
$ CREATE TABLE departments (id serial PRIMARY KEY, name varchar, description varchar);
$ CREATE TABLE generalnews (id serial PRIMARY KEY, newsheading varchar, news varchar, personid int);
$ CREATE TABLE departmentnews (id serial PRIMARY KEY, newsheading varchar, news varchar, personid int,departmentid int);
```

## Author

👤 **Author**

By Duncan Kipkosgei Moiyo

- GitHub: [@saretoduncan](https://github.com/saretoduncan)
- twitter: [@duncan_sareto](https://twitter.com/duncan_sareto)
- email: <a href="mailto:duncan.moiyo@student.moringaschool.com"> mail📪</a>

## 🤝 Contributing

Contributions, issues, and feature requests are welcome!

Feel free to check the [issues page](./ISSUE_TEMPLATE/feature_request.md).


## Show your support

Give a ⭐️ if you like this project!

## Acknowledgments

- Appreciation to Moringa school for giving me this opportunity to learn 😊

## 📝 License

This project is [MIT](./LICENSE) licensed.
