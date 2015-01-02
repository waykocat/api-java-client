# Abiquo API Java Client

[![Build Status](https://travis-ci.org/abiquo/api-java-client.svg?branch=master)](https://travis-ci.org/abiquo/api-java-client)

This is an API client for the [Abiquo API](http://wiki.abiquo.com/). The Abiquo API is a RESTful API,
so any REST client can be used to connect to it. This project uses [OkHttp](http://square.github.io/okhttp/) and [Jackson](https://github.com/FasterXML/jackson) and just provides some high level functions 
to enforce some best practices to make it easier to perform the common tasks.

It also provides an client for the Abiquo Streaming API based on [Atmosphere](http://async-io.org/) and
[RxJava](https://github.com/ReactiveX/RxJava) allowing users to develop reactive tools that integrate with
the Abiquo platform.

## Installation

The Abiquo API Java Client is released to the [Maven Central](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.abiquo%22%20AND%20a%3A%22ap%C3%AC-java-client%22) and [Sonatype snapshot
repositories](https://oss.sonatype.org/content/repositories/snapshots/com/abiquo/api-java-client/), so you just have to declare the dependencies you want in your pom.xml as follows:

```xml
<!-- REST API client -->
<dependency>
    <groupId>com.abiquo</groupId>
    <artifactId>api-java-client</artifactId>
    <version>1.0.0</version>
</dependency>

<!-- Streaming API client -->
<dependency>
    <groupId>com.abiquo</groupId>
    <artifactId>api-java-stream-client</artifactId>
    <version>1.0.0</version>
</dependency>
```

Or if you want to use the latest SNAPSHOT, just declare the snapshot repository in your
pom.xml:

```xml
<repositories>
    <repository>
        <id>oss-sonatype</id>
        <name>oss-sonatype</name>
        <url>https://oss.sonatype.org/content/repositories/snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
    </repository>
</repositories>

<dependency>
    <groupId>com.abiquo</groupId>
    <artifactId>api-java-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
<dependency>
    <groupId>com.abiquo</groupId>
    <artifactId>api-java-stream-client</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

## REST API Client Usage

To use the client you just have to create an instance of the `ApiClient` class providing the configuration needed to connect to the target Abiquo API. For example:

```java
// Create a basic client
ApiClient api = ApiClient.builder()
    .endpoint("http://abiquo-server/api")
    .credentials("username", "password")
    .build();

// Create a client for a specific version of the Abiquo API
ApiClient api = ApiClient.builder()
    .endpoint("http://abiquo-server/api")
    .credentials("username", "password")
    .version("3.2")
    .build();

// Create a client with custom SSL configuration
ApiClient api = ApiClient.builder()
    .endpoint("https://abiquo-server/api")
    .credentials("username", "password")
    .sslConfiguration(customSSLConfig)
    .build();
```

Once the `ApiClient` has been created you can use it to talk with the different endpoints exposed in the Abiquo API.

## Stream API Client Usage

To use the client you just have to create an instance of the `StreamClient` class providing the configuration needed to connect to the target Abiquo Streaming API. For example:

```java
// Create a basic client
StreamClient stream = StreamClient.builder()
    .endpoint("http://abiquo-server/m/stream")
    .credentials("username", "password")
    .build();

// Create a client with custom SSL configuration
StreamClient stream = StreamClient.builder()
    .endpoint("https://abiquo-server/m/stream")
    .credentials("username", "password")
    .sslConfiguration(customSSLConfig)
    .build();
```

Once you are done processing events, don't forget to close the streaming client:

```java
stream.close();
```

### Streaming API Examples

The Streaming API uses [RxJava](https://github.com/ReactiveX/RxJava) to provide reactive features on top of the
Abiquo Streaming API. You can have a look at the fantastic [RxJava wiki](https://github.com/ReactiveX/RxJava/wiki)
for a detailed reference, but here are some examples using Java 8:

```java
Observable<Event> events = stream.events();

// Subscribe to all actions performed to virtual machines
events.filter(event -> event.getType().equals("VIRTUAL_MACHINE")) //
    .map(Event::getAction) //
    .forEach(System.out::println);
    
// Count how many virtual machines are deployed every hour
events.filter(event -> event.getType().equals("VIRTUAL_MACHINE"))
    .buffer(1, TimeUnit.HOURS)
    .map(List::size)
    .forEach(count -> log.info("VMs deployed in the last hour: {}", count));
    
// Use the REST API client to get the details for every undeployed virtual machine
events.filter(event -> event.getType().equals("VIRTUAL_MACHINE"))
    .filter(event -> event.getAction().equals("UNDEPLOY_FINISH"))
    .forEach(event -> {
        String vmUri = event.getEntityIdentifier().get();
        VirtualMachineDto vm = restClient.getClient()
            .get(vmUri, VirtualMachineDto.MEDIA_TYPE, VirtualMachineDto.class);
        System.out.println(vm.getName());
    });
```

As you can see, the [RxJava Observable](https://github.com/ReactiveX/RxJava/wiki/Observable) provides a very rich
interface that allows you to work in real time with the events you are interested in.

## Contributing

This project is still in an early development stage and is still incomplete. All
contributions are welcome, so feel free to [raise a pull request](https://help.github.com/articles/using-pull-requests/).

## License

The Abiquo API Java Client is licensed under the Apache License version 2. For
further details, see the [LICENSE](LICENSE) file.
