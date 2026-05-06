# HabitOS

## 🚀 Run with Docker Compose

This setup builds and runs the microservices, database, and supporting services locally.

### Services
- Eureka Server: `server-registry` (8761)
- API Gateway: `api-gateway` (8080)
- Auth Service: `auth-service` (8081)
- Habit Service: `hatbit-service` (8082)
- Postgres: `postgres` (5432)
- Redis: `redis` (6379)
- Kafka + Zookeeper (9092/2181)

### Prerequisites
- Docker + Docker Compose

### Start
```powershell
cd d:\HabitOS
# ensure habitos.env exists (already in repo)
# build + run
# optional: set JWT_SECRET / DB_PASSWORD values inside habitos.env

docker compose up --build
```

### Stop
```powershell
docker compose down
```

## Notes
- `habitos.env` is used for shared environment variables; compose overrides container hostnames (e.g., `DB_HOST=postgres`).
- Databases `authdb` and `habitdb` are initialized via `docker/init-db.sql`.
