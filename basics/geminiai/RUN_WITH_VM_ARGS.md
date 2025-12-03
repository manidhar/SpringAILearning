# How to Run Spring Boot Application with VM Arguments

## Method 1: Using Gradle bootRun (Recommended for Development)

### Option A: Using GRADLE_OPTS environment variable
```bash
export GRADLE_OPTS="-Xmx512m -Xms256m -Dcustom.property=value"
./gradlew bootRun
```

### Option B: Using gradle.properties file
Create or edit `gradle.properties` in the project root:
```properties
org.gradle.jvmargs=-Xmx512m -Xms256m -Dcustom.property=value
```

Then run:
```bash
./gradlew bootRun
```

### Option C: Using command line with system properties
```bash
./gradlew bootRun -Dorg.gradle.jvmargs="-Xmx512m -Xms256m -Dcustom.property=value"
```

## Method 2: Build JAR and Run with java command (Recommended for Production)

### Step 1: Build the JAR
```bash
./gradlew build
```

### Step 2: Run with VM arguments
```bash
java -Xmx512m -Xms256m -Dcustom.property=value \
     -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

### Example with common VM arguments:
```bash
java -Xmx1024m \
     -Xms512m \
     -XX:+UseG1GC \
     -Dspring.profiles.active=prod \
     -Dcustom.property=value \
     -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

## Method 3: Using Gradle Application Plugin (if configured)

If you have the application plugin, you can configure it in `build.gradle`:
```gradle
application {
    applicationDefaultJvmArgs = [
        '-Xmx512m',
        '-Xms256m',
        '-Dcustom.property=value'
    ]
}
```

Then run:
```bash
./gradlew run
```

## Common VM Arguments Examples

### Memory Settings:
- `-Xmx1024m` - Maximum heap size (1GB)
- `-Xms512m` - Initial heap size (512MB)
- `-XX:MaxMetaspaceSize=256m` - Maximum metaspace size

### Garbage Collection:
- `-XX:+UseG1GC` - Use G1 garbage collector
- `-XX:+UseParallelGC` - Use parallel garbage collector

### Debugging:
- `-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005` - Enable remote debugging on port 5005

### System Properties:
- `-Dspring.profiles.active=prod` - Set Spring profile
- `-Dlogging.level.root=DEBUG` - Set logging level
- `-Dcustom.property=value` - Custom system property

## Quick Example Commands

### Development with increased memory:
```bash
./gradlew bootRun -Dorg.gradle.jvmargs="-Xmx1024m -Xms512m"
```

### Production with JAR:
```bash
./gradlew build && java -Xmx1024m -Xms512m -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

### With Spring profile and custom properties:
```bash
java -Xmx1024m \
     -Dspring.profiles.active=prod \
     -DGOOGLE_GEMINI_API=your-api-key \
     -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

### Passing OPENAI_API_KEY as VM argument:

**Method 1: Using -D system property**
```bash
# Using Gradle bootRun
./gradlew bootRun -Dorg.gradle.jvmargs="-DOPENAI_API_KEY=your-openai-api-key-here"

# Or using JAR
java -DOPENAI_API_KEY=your-openai-api-key-here \
     -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

**Method 2: Using environment variable (recommended)**
```bash
# Set environment variable and run
export OPENAI_API_KEY=your-openai-api-key-here
./gradlew bootRun

# Or inline
OPENAI_API_KEY=your-openai-api-key-here ./gradlew bootRun
```

**Method 3: Using both VM args and environment variable**
```bash
java -Xmx1024m \
     -DOPENAI_API_KEY=your-openai-api-key-here \
     -DGOOGLE_GEMINI_API=your-gemini-api-key-here \
     -jar build/libs/geminiai-0.0.1-SNAPSHOT.jar
```

