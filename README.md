## Misère Nim API

A Spring Boot-based REST API for playing the game of Misère Nim. In this version of Nim, the player who takes the last match loses.

The API supports playing against the computer. After every valid human move, the server automatically executes the computer's move and returns the resulting game state.

### Tech Stack

- Java 21
- Spring Boot 4.0.7
- JUnit 5 & MockMVC

### Getting Started
#### Prerequisites
- Docker installed

#### Run the Application
1. Clone the repository
```
git clone <repo>
cd <project-folder>
```
2. Build the docker image
```
docker build -t misere-nim-api .
```
3. Run the docker container
```
docker run -p 8080:8080 misere-nim-api
```

##### Run without Docker
```bash
./mvnw clean package
java -jar target/misere-nim-api.jar
```

### API Endpoints
#### 1. Create a New Game

```POST /api/v1/misere-nim/create```

```json
{
  "matches": 5,
  "player": "human",
  "strategyType": "random"
}
```


#### 2. Make a Move

```POST /api/v1/misere-nim/{gameId}/move```

```json
{
  "matches": 2
}
```

#### 3. Get Game State

```GET /api/v1/misere-nim/{gameId}```

```json
{
  "id": "...",
  "currentPlayer": "HUMAN",
  "matchesLeft": 1,
  "status": "IN_PROGRESS",
  "moveHistory": ["..."],
  "winner": null
}
```

### Allowed Constant
#### **Player Types**: 
- human, computer
#### **Strategy Types**:
- random, optimal

### Architecture Notes
- **Self-Protecting Domain**: The ```NimGame``` object contains all business logic. It knows how to validate its own moves and determine its own state.
- **Global Exception Handling**: The API uses a ```@RestControllerAdvice``` to translate domain-specific exceptions (e.g., ```InvalidMoveException```) into standard HTTP 400 Bad Request responses.

### CURL Examples

#### Create a New Game
```bash
curl -X POST http://localhost:8080/api/v1/misere-nim/create \
  -H "Content-Type: application/json" \
  -d '{
    "matches": 10,
    "player": "HUMAN",
    "strategyType": "OPTIMAL"
  }'
```

#### Make a Move
```bash
curl -X POST http://localhost:8080/api/v1/misere-nim/{gameId}/move \
  -H "Content-Type: application/json" \
  -d '{
    "matches": 2
  }'
```

#### Get Game State
```bash
curl -X GET http://localhost:8080/api/v1/misere-nim/{gameId} \
  -H "Accept: application/json"
```