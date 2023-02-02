package main

import (
	"database/sql"
	"github.com/gorilla/mux"
	_ "github.com/lib/pq"
	"log"
	"net/http"
	"os"
)

var db *sql.DB

func main() {
	db = GetConnection()
	router := mux.NewRouter()
	router.HandleFunc("/{short_url}", redirect)
	err := http.ListenAndServe(":"+GetHttpServerPort(), router)
	if err != nil {
		log.Fatalln("There's an error with the server,", err)
	}
}

func GetHttpServerPort() string {
	port := os.Getenv("PORT")
	if port == "" {
		return "8080"
	}
	return port
}

func redirect(writer http.ResponseWriter, request *http.Request) {
	tinyUrl := request.URL.Path[1:]
	longUrl := GetLongUrl(tinyUrl)
	if longUrl == "" {
		writer.WriteHeader(404)
	} else {
		writer.Header().Add("Location", longUrl)
		writer.WriteHeader(302)
	}
}

func GetConnection() *sql.DB {

	dbHost := "localhost"    // os.Getenv("DB_HOST")
	dbPort := "5432"         // os.Getenv("DB_PORT")
	dbUsername := "postgres" // os.Getenv("DB_USERNAME")
	dbPass := "postgres"     // os.Getenv("DB_PASSWORD")
	dbName := "url_shortner" // os.Getenv("DB_NAME")
	dbSslMode := ""          // os.Getenv("DB_SSL_MODE")

	if dbSslMode == "" {
		dbSslMode = "disable"
	}

	connStr := "postgres://" + dbUsername + ":" + dbPass + "@" + dbHost + ":" + dbPort + "/" + dbName + "?sslmode=" + dbSslMode
	db, err := sql.Open("postgres", connStr)

	CheckError(err)

	err = db.Ping()

	CheckError(err)

	println("The database is connected")

	return db
}

func GetLongUrl(tinyUrl string) string {
	rows, err := db.Query(`SELECT "short_url", "full_url" FROM "url_map"`)

	CheckError(err)

	for rows.Next() {
		var shortUrl string
		var fullUrl string

		err = rows.Scan(&shortUrl, &fullUrl)

		CheckError(err)

		if shortUrl == tinyUrl {
			return fullUrl
		}
	}

	return ""
}

func CheckError(err error) {
	if err != nil {
		panic(err)
	}
}
