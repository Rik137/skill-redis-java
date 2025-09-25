# Redis Queue Demo

This is a simple training project that demonstrates how to use **Redis** (via the [Redisson](https://github.com/redisson/redisson) Java client) to implement a user queue with priority management.  

The project simulates users being displayed on a website homepage in a queue-like fashion, with the ability to move certain users to the front (for example, when they make a payment for promotion).  

---

## Features

- Connects to a local Redis instance using **Redisson**  
- Initializes a queue of users (`user_1 ... user_20`)  
- Rotates the queue in a round-robin style (first user goes to the end)  
- Allows moving specific users to the front of the queue (simulating paid promotion)  
- Logs queue operations in the console  

---

## Technologies Used

- **Java 17+**  
- **Redis** (Docker container or standalone)  
- **Redisson** library  

---

## How It Works

1. **`RedisStorageQueue` class**  
   - Creates a Redis-backed list for the user queue  
   - Provides operations:  
     - `showNextUser()` – removes the first user and places them at the end (rotation)  
     - `moveToFront(userId)` – moves a given user to the front of the queue  
     - `getQueueSnapshot()` – returns the current queue state  

2. **`RedisTest` class**  
   - Initializes the queue with 20 demo users  
   - Continuously simulates activity:  
     - Every user is displayed in turn  
     - In 1 out of 10 cases, a random user “pays” to be moved to the front  

---

## Running the Project

### 1. Start Redis in Docker

```bash
docker run --rm --name skill-redis -p 127.0.0.1:6379:6379/tcp -d redis
2. Build and Run Java Code
Make sure you have Maven or Gradle set up. Then run:
javac -cp "path/to/redisson.jar" com/skillbox/redisdemo/*.java
java -cp ".:path/to/redisson.jar" com.skillbox.redisdemo.RedisTest
Example Output
Shown on homepage: user_1
Shown on homepage: user_2
User paid for promotion: user_7
Shown on homepage: user_7
Shown on homepage: user_3
...
Notes
The queue is stored in Redis, so multiple applications could share the same data
This is only a training/demo project and is not meant for production use
Extendable: you can add expiration logic, scoring with RScoredSortedSet, or track “online” users separately
