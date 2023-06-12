# Aplikacja Midas

Aplikacja MotoApp to przykładowa aplikacja webowa składająca się z frontendu napisanego w Angularze, backendu w Java Spring Boot oraz bazy danych MySQL. Ten projekt pokazuje, jak skonfigurować i uruchomić środowisko kontenerowe przy użyciu narzędzia Docker Compose.

## Wymagania systemowe

Aby uruchomić aplikację MotoApp w środowisku kontenerowym, musisz mieć zainstalowane następujące narzędzia:

- Docker: [Instrukcje instalacji](https://docs.docker.com/engine/install/)

## Uruchamianie aplikacji

Aby uruchomić aplikację Midas, wykonaj następujące kroki:

1. Sklonuj repozytorium:
git clone [https://github.com/DamWarczyk/MotoApp.git](https://github.com/DamWarczyk/MotoApp.git)


2. Przejdź do katalogu głównego aplikacji:
cd MotoApp


3. Skonfiguruj plik `.env` z odpowiednimi zmiennymi środowiskowymi, jeśli jest to wymagane.

4. Uruchom aplikację przy użyciu Docker Compose:
docker-compose up


5. Otwórz przeglądarkę internetową i przejdź pod adres: `http://localhost:4200`, aby uzyskać dostęp do aplikacji Midas.

## Komponenty aplikacji

Aplikacja Midas składa się z trzech komponentów:

- **MotoFront**: Aplikacja napisana w Angularze. Komunikuje się z backendem i wyświetla interfejs użytkownika.
- **MotoBackend**: Serwer backendowy napisany w Java Spring Boot. Udostępnia API, które obsługuje żądania z frontendu.
- **Baza danych**: Kontener MySQL, który przechowuje dane aplikacji.

## Konfiguracja bazy danych

Baza danych MySQL jest skonfigurowana z następującymi ustawieniami:

- **Nazwa bazy danych**: students
- **Użytkownik**: sammy
- **Hasło**: Student2-

## Dostępne usługi

Po uruchomieniu aplikacji, dostępne są następujące usługi:

- Frontend: `http://localhost:4200`
- Backend: `http://localhost:8080`

## Zatrzymywanie aplikacji

Aby zatrzymać aplikację Midas, wykonaj polecenie:
docker-compose down


## Podsumowanie

Aplikacja MotoApp jest przykładem kompletnego środowiska kontenerowego, w którym frontend napisany w Angularze, backend w Java Spring Boot i baza danych MySQL są uruchamiane jako osobne kontenery Docker. Docker Compose pozwala na łatwe uruchomienie i zarządzanie tymi kontenerami w jednym miejscu
